package com.tongwii.ico.service.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import com.tongwii.ico.exception.ServiceException;
import com.tongwii.ico.util.CurrentConfigEnum;
import com.tongwii.ico.util.DesEncoder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.*;
import org.bitcoinj.core.listeners.DownloadProgressTracker;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.wallet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 比特币业务类
 *
 * @author Zeral
 * @date 2017-08-22
 */
@Component
public class BitCoinService {
    private final static Logger logger = LogManager.getLogger();

    private static WalletAppKit walletAppKit;

    @Value("${spring.profiles.active}")
    private String env;
    @Value("${storage.wallet.location}")
    private String walletPath;

    private static NetworkParameters netParams;

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


    @Scheduled(fixedDelay = 60000)
    public void run() {
        if (!isReady()) {
            return;
        }

        logger.info("比特币钱包当前接受地址: " + walletAppKit.wallet().currentReceiveAddress());
        logger.info("比特币钱包当前查看地址: " + walletAppKit.wallet().getWatchedAddresses());
        List<Address> watchedAddress = walletAppKit.wallet().getWatchedAddresses();
        logger.info("比特币查看的地址列表---------------------------------");
        for (Address address: watchedAddress) {
            logger.info(address.toBase58());
        }
        logger.info("比特币导入的地址列表---------------------------------");
        List<ECKey> importKeys = walletAppKit.wallet().getImportedKeys();
        for (ECKey key : importKeys) {
            logger.info(key.toAddress(netParams).toBase58());
            walletAppKit.wallet().removeKey(key);
        }
        logger.info("比特币接受的地址列表---------------------------------");
        List<Address> reveiveAddress = walletAppKit.wallet().getIssuedReceiveAddresses();
        for (Address address : reveiveAddress) {
            logger.info(address.toBase58());
        }
    }

    /**
     * PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次
     */
    @PostConstruct
    public void postConstruct() {
        // 简化日志输出
        BriefLogFormatter.initWithSilentBitcoinJ();

        if(env.equals(CurrentConfigEnum.dev.toString())) {
            netParams = TestNet3Params.get();
            walletAppKit =  new WalletAppKit(netParams, new File(walletPath), "ICO_TT_Test"){
                @Override
                protected void onSetupCompleted() {
                    // Don't make the user wait for confirmations for now, as the intention is they're sending it
                    // their own money!
                    walletAppKit.wallet().allowSpendingUnconfirmedTransactions();
                }
            };
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

        walletAppKit.setDownloadListener(new DownloadProgressTracker() {
            @Override
            protected void progress(double pct, int blocksSoFar, Date date) {
                logger.info(String.format(Locale.US, "区块下载进度 %d%% 剩余 %d 区块, 区块日期 %s", (int) pct, blocksSoFar,
                        Utils.dateTimeFormat(date)));
            }
        }).setBlockingStartup(false);

        // 同步区块链
        start();

        // 等待同步完
        waitUntilReady();

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

    @PreDestroy
    public void dostory(){
        System.out.println("暂停同步区块链......");
        stop();
    }

    /**
     * 比特币交易
     * @param fromAddress
     * @param toAddress
     * @param amount
     * @return
     * @throws Exception
     */
    public String sendTransaction(String fromAddress, String toAddress, String amount) {
        // 等待区块链同步
        waitUntilReady();
        try {
            Coin coin = Coin.parseCoin(amount);
            Address destination = Address.fromBase58(netParams, toAddress);
            SendRequest req;

            DesEncoder desEncoder = new DesEncoder();
            ECKey nowKey = ECKey.fromPrivate(Utils.HEX.decode(desEncoder.decrypt(fromAddress)));

            walletAppKit.wallet().importKey(nowKey);
            logger.info("钱包当前接受地址: " + walletAppKit.wallet().currentReceiveAddress());

            if (coin.equals(walletAppKit.wallet().getBalance()))
                req = SendRequest.emptyWallet(destination);
            else
                req = SendRequest.to(destination, coin);
            Wallet.SendResult sendResult = walletAppKit.wallet().sendCoins(req);
            // you can use a block explorer like https://www.biteasy.com/ to inspect the transaction with the printed transaction hash.
            Futures.addCallback(sendResult.broadcastComplete, new FutureCallback<Transaction>() {
                @Override
                public void onSuccess(@Nullable Transaction result) {
                    // TODO 交易成功操作
                    logger.info("交易成功，交易单号Hash256:{}", result.getHashAsString());
                    // 移除当前交易的地址
                    walletAppKit.wallet().removeKey(nowKey);
                }

                @Override
                public void onFailure(Throwable t) {
                    // TODO 交易失败操作
                    logger.error("交易失败, 失败原因:" + t.getMessage());
                    // 移除当前交易的地址
                    walletAppKit.wallet().removeKey(nowKey);
                }
            });
            sendResult.tx.getConfidence().addEventListener((tx, reason) -> {
                if (reason == TransactionConfidence.Listener.ChangeReason.SEEN_PEERS) {
                    int peers = sendResult.tx.getConfidence().numBroadcastPeers();
                    logger.info(String.format("广播交易结果中 ... 已经被 %d 个矿工确认", peers));
                }
            });

            return sendResult.broadcastComplete.get().getHashAsString();
        } catch (InsufficientMoneyException e) {
            throw new ServiceException("钱包地址余额不足，缺少" + e.missing.getValue() + "satoshis 比特币（包含fees）");
        } catch (ECKey.KeyIsEncryptedException e) {
            throw new ServiceException("钱包地址缺少密码");
        } catch (InterruptedException e) {
            throw new ServiceException("交易失败");
        } catch (ExecutionException e) {
            throw new ServiceException("交易失败");
        }
    }


    public String getBalance(String addr) {
        Address address = Address.fromBase58(netParams, addr);
        walletAppKit.wallet().addWatchedAddress(address, 0);
        logger.info("钱包当前查看地址"+walletAppKit.wallet().getWatchedAddresses());
        Coin balance = walletAppKit.wallet().getBalance();
        walletAppKit.wallet().removeWatchedAddress(address);
        logger.info("{}地址是否被监控： {}", address, walletAppKit.wallet().isAddressWatched(address));
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
