package com.tongwii.ico.service.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.tongwii.ico.util.CurrentConfigEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;

/**
 * 比特币业务类
 *
 * @author Zeral
 * @date 2017-08-22
 */
@Component
public class BitCoinService {
    private final static Logger logger = LogManager.getLogger();

    private WalletAppKit walletAppKit;

    @Value("${spring.profiles.active}")
    private String env;
    @Value("${storage.wallet.location}")
    private String walletPath;

    private NetworkParameters netParams;

    @Value("${wallet.seedWords}")
    private String seedWords;

    private static final int SHA256_LENGTH = 32;
    private static final int MAX_PREFIX_LENGTH = 8;
    private static final byte NULL_BYTE = (byte) '\0';

    private static MessageDigest SHA_256 = null;
    static {
        try {
            SHA_256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("程序内部错误", nsae);
        }
    }


    @Scheduled(fixedDelay = 30000)
    public void run() {
        if (!isReady()) {
            return;
        }

        logger.info("钱包当前接受地址: " + walletAppKit.wallet().currentReceiveAddress());
    }

    @PostConstruct
    public void postConstruct() {
        if(env.equals(CurrentConfigEnum.dev.toString())) {
            netParams = TestNet3Params.get();
            walletAppKit =  new WalletAppKit(netParams, new File(walletPath), "ICO_TT_Test");
        } else {
            netParams = MainNetParams.get();
            walletAppKit =  new WalletAppKit(netParams, new File(walletPath), "ICO_TT");
        }

        if(StringUtils.isNotBlank(this.seedWords)) {
            DeterministicSeed seed = null;
            try {
                seed = new DeterministicSeed(seedWords, null, "", Calendar.getInstance().getTimeInMillis());
            } catch (UnreadableWalletException uwe) {
                throw new RuntimeException(uwe);
            }
            walletAppKit.restoreWalletFromSeed(seed);

            logger.info(seed.toHexString());
        }

        start();

        walletAppKit.wallet().addCoinsReceivedEventListener((w, tx, prevBalance, newBalance) -> {
            Coin value = tx.getValueSentToMe(w);
            System.out.println("接收到交易金额为" + value.toFriendlyString() + "的交易：" + tx);
            Futures.addCallback(tx.getConfidence().getDepthFuture(1), new FutureCallback<TransactionConfidence>() {
                @Override
                public void onSuccess(TransactionConfidence result) {
                    try {
                        // TODO 接受代币后执行操作
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    // This kind of future can't fail, just rethrow in case
                    // something weird happens.
                    throw new RuntimeException(t);
                }
            });
        });

    }

    /**
     * 交易
     * @return
     * @throws Exception
     */
    public String sendTransaction() throws Exception {
        // 等待区块链同步
        waitUntilReady();

        final String message = "来自ICO.TT";
        final Wallet wallet = walletAppKit.wallet();
        byte[] hash = SHA_256.digest(message.getBytes());
        String prefix = "RSM";
        byte[] prefixBytes = prefix.getBytes(StandardCharsets.US_ASCII);
        if (MAX_PREFIX_LENGTH < prefix.length()) {
            throw new IllegalArgumentException("OP_RETURN prefix is too long: " + prefix);
        }

        byte[] opReturnValue = new byte[80];
        Arrays.fill(opReturnValue, NULL_BYTE);
        System.arraycopy(prefixBytes, 0, opReturnValue, 0, prefixBytes.length);
        System.arraycopy(hash, 0, opReturnValue, MAX_PREFIX_LENGTH, SHA256_LENGTH);

        Transaction transaction = new Transaction(wallet.getParams());
        transaction.addOutput(Coin.ZERO, ScriptBuilder.createOpReturnScript(hash));

        SendRequest sendRequest = SendRequest.forTx(transaction);

        try {
            wallet.completeTx(sendRequest);
        } catch (InsufficientMoneyException e) {
            throw new Exception("No balance on bitcoin wallet.");
        }

        // Broadcast and commit transaction
        walletAppKit.peerGroup().broadcastTransaction(transaction);
        wallet.commitTx(transaction);

        // Return a reference to the caller
        return transaction.getHashAsString();
    }


    public String getBalance(String addr) {
        Address address = Address.fromBase58(netParams, addr);
        Wallet wallet = new Wallet(netParams);
        wallet.addWatchedAddress(address, 0);
        logger.info("钱包当前查看地址"+wallet.getWatchedAddresses());
        waitUntilReady();
        Coin balance = wallet.getBalance();
        return balance.toFriendlyString();
    }

    public void start() {
        walletAppKit.setAutoSave(true);

        walletAppKit.startAsync();

    }

    public void stop() {
        walletAppKit.stopAsync();
    }

    public boolean isReady() {
        return walletAppKit.isRunning();
    }

    public void waitUntilReady() {
        walletAppKit.awaitRunning();
    }

    /**
     * 从seedWords恢复钱包
     * @param seedWords
     */
    public void setSeedWords(String seedWords) {
        this.seedWords = seedWords;
    }
}
