package com.tongwii.ico.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.MoreExecutors;
import com.tongwii.ico.exception.ServiceException;
import com.tongwii.ico.service.TransactionsService;
import com.tongwii.ico.util.DesEncoder;
import com.tongwii.ico.util.EthConverter;
import com.tongwii.ico.util.RestTemplateUtil;
import org.apache.tomcat.util.buf.HexUtils;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.Wallet;
import org.ethereum.core.Denomination;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
import org.ethereum.jsonrpc.TypeConverter;
import org.ethereum.util.ByteUtil;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-08-11
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionsService {

    private static final String BITCOIN_HOST = "https://chain.api.btc.com/v3";
    private static final String ETH_HOST = "https://api.etherscan.io";

    @Value("${etherscan.apiKeyToken}")
    private String ethApiKey;

    @Value("${storage.wallet.location}")
    private String walletPath;

    @Override
    public String getBitCoinAddressBalance(String address) {

        Map<String, String> params = Maps.newHashMap();
        params.put("address", address);
        String response = RestTemplateUtil.restTemplate(BITCOIN_HOST+"/address/{address}", null, String.class, params, HttpMethod.GET);
        JSONObject result = JSON.parseObject(response);
        if(result.getIntValue("err_no") == 0) {
            try{
                JSONObject data = result.getJSONObject("data");
                return Coin.valueOf(data.getLong("balance")).toFriendlyString();
            }catch (Exception e){
                return Coin.valueOf(0).toFriendlyString();
            }
        }
        return null;
    }


    @Override
    public JSONArray getBitCoinAddressTransaction(String address) {

        Map<String, String> params = Maps.newHashMap();
        params.put("address", address);
        String response = RestTemplateUtil.restTemplate(BITCOIN_HOST+"/address/{address}/tx", null, String.class, params, HttpMethod.GET);
        JSONObject result = JSON.parseObject(response);
        if(result.getIntValue("err_no") == 0) {
            JSONObject data = result.getJSONObject("data");
            return data.getJSONArray("list");
        }
        return null;
    }

    @Override
    public JSONObject getBitCoinTransaction(String address) {
        Map<String, String> params = Maps.newHashMap();
        params.put("address", address);
        String response = RestTemplateUtil.restTemplate(BITCOIN_HOST+"/tx/{address}", null, String.class, params, HttpMethod.GET);
        JSONObject result = JSON.parseObject(response);
        if(result.getIntValue("err_no") == 0) {
            return result.getJSONObject("data");
        }
        return null;
    }

    @Override
    public String getEthAddressBalance(String address) {
        Map<String, String> params = Maps.newHashMap();
        params.put("address", address);
        String response = RestTemplateUtil.restTemplate(ETH_HOST+"/api?module=account&action=balance&address={address}&tag=latest&apikey="+ethApiKey, null, String.class, params, HttpMethod.GET);
        JSONObject result = JSON.parseObject(response);
        if(result.getIntValue("status") == 1) {
            Double flo = result.getBigInteger("result").doubleValue()/Denomination.ETHER.value().doubleValue();
            return flo.toString();
        }
        return null;
    }

    @Override
    public String sendBitCoin(String fromAddress, String recipient, String amountToSend) {
        NetworkParameters params = MainNetParams.get();
        // Parse the address given as the first parameter.
        Address forwardingAddress = Address.fromBase58(params, recipient);
        // Start up a basic app using a class that automates some boilerplate.
        WalletAppKit kit = new WalletAppKit(params, new File(walletPath), "ICO_TT");

        // Download the block chain and wait until it's done.
        kit.startAsync();
        kit.awaitRunning();

        DesEncoder desEncoder = new DesEncoder();
        kit.wallet().importKey(ECKey.fromPrivate(HexUtils.fromHexString(desEncoder.decrypt(fromAddress))));

        try {
            Coin value = Coin.parseCoin(amountToSend);
            System.out.println("Forwarding " + value.toFriendlyString());
            // Now send the coins back! Send with a small fee attached to ensure rapid confirmation.
            final Wallet.SendResult sendResult = kit.wallet().sendCoins(kit.peerGroup(), forwardingAddress, value);
            checkNotNull(sendResult);  // We should never try to send more coins than we have!
            System.out.println("Sending ...");
            // Register a callback that is invoked when the transaction has propagated across the network.
            // This shows a second style of registering ListenableFuture callbacks, it works when you don't
            // need access to the object the future returns.
            sendResult.broadcastComplete.addListener(() -> {
                // The wallet has changed now, it'll get auto saved shortly or when the app shuts down.
                System.out.println("Sent coins onwards! Transaction hash is " + sendResult.tx.getHashAsString());
            }, MoreExecutors.directExecutor());
            return sendResult.tx.getHashAsString();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String sendETHCoin(String fromPrivateEncoderAddress, String toAddress, String amount) {
        try {
            //Sync BlockChain
            Ethereum ethereum = EthereumFactory.createEthereum() ;

            DesEncoder desEncoder = new DesEncoder();
            //Init Sender and Receipt Address
            org.ethereum.crypto.ECKey sender =  org.ethereum.crypto.ECKey.fromPrivate(Hex.decode(desEncoder.decrypt(fromPrivateEncoderAddress))) ;
            //Create tx to send ether
            org.ethereum.core.Transaction tx = new org.ethereum.core.Transaction(
                    ByteUtil.bigIntegerToBytes(ethereum.getRepository().getNonce(sender.getPubKey())),
                    ByteUtil.longToBytesNoLeadZeroes(ethereum.getGasPrice()),
                    ByteUtil.longToBytesNoLeadZeroes(200000),
                    TypeConverter.StringHexToByteArray(toAddress),
                    ByteUtil.bigIntegerToBytes(EthConverter.toWei(amount, EthConverter.Unit.ETHER).toBigInteger()),
                    new byte[0],
                    ethereum.getChainIdForNextBlock()) ;

            //Sign it
            tx.sign(sender);

            // Validate it
            tx.verify();

            //Submit it
            ethereum.submitTransaction(tx);

            return TypeConverter.toJsonHex(tx.getHash());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
