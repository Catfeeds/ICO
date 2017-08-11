package com.tongwii.ico.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 代币工具
 *
 * @author Zeral
 * @date 2017-08-11
 */
@Component
public class TokenMoneyUtil {
    private final static Logger logger = LogManager.getLogger();

    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件

    @Value("${storage.wallet.location}")
    private String walletPath;

    /**
     * <pre>
     *      生成比特币地址，{@link ECKey#getPrivKey}密钥被保存在地址表中
     * </pre>
     *
     * @return
     */
    public String generaterBitCoinAddress() {
        final NetworkParameters netParams;

        if(env.equals(CurrentConfig.DEVELOPMENT)) {
            netParams = TestNet3Params.get();
        } else {
            netParams = MainNetParams.get();
        }

        // create a new EC Key ...
        ECKey key = new ECKey();

        // ... and look at the key pair
        logger.info("We created key: {}", key);

        // get valid Bitcoin address from public key
        Address addressFromKey = key.toAddress(netParams);

        logger.info("On the {} network, we can use this address: {}", env, addressFromKey);

        return addressFromKey.toBase58();
    }


    /**
     * 供项目使用
     * 生成比特币钱包，钱包中包含五个比特币随机生成地址,钱包使用请查看{@link Wallet}
     *
     * @return
     */
    public Wallet generaterBitCoinWallet() {
        Wallet wallet = null;
        final NetworkParameters netParams;

        final File walletFile;

        if(env.equals(CurrentConfig.DEVELOPMENT.getConfig())) {
            netParams = TestNet3Params.get();
        } else {
            netParams = MainNetParams.get();
        }

        try {
            wallet = new Wallet(netParams);

            // 5 times
            for (int i = 0; i < 5; i++) {

                // create a key and add it to the wallet
                wallet.addKey(new ECKey());
            }

            // 保存钱包文件
            Files.createDirectories(Paths.get(walletPath));
            walletFile = new File(walletPath+"/"+ wallet.getWatchingKey().serializePubB58(netParams) +".wallet");
            // save wallet contents to disk
            wallet.saveToFile(walletFile);

        } catch (IOException e) {
            System.out.println("Unable to create wallet file.");
        }

        // and here is the whole wallet
        logger.info("Complete content of the wallet: {}", wallet);
        return wallet;
    }




}
