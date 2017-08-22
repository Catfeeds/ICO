package com.conpany.project;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongwii.ico.model.User;
import com.tongwii.ico.security.JwtTokenUtil;
import com.tongwii.ico.service.TransactionsService;
import com.tongwii.ico.service.impl.BitCoinService;
import com.tongwii.ico.util.*;
import org.apache.tomcat.util.buf.HexUtils;
import org.bitcoinj.core.Utils;
import org.ethereum.core.Account;
import org.ethereum.core.Denomination;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

/**
 * 工具测试类
 *
 * @author Zeral
 * @date 2017-08-06
 */
public class UtilTest extends Tester {
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private TokenMoneyUtil tokenMoneyUtil;
    @Autowired
    private TransactionsService transactionsService;
    @Value("${storage.wallet.location}")
    private String walletPath;

    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件

    @Autowired
    private BitCoinService bitCoinjService;

    @Test
    public void validateUtilTest() {
        Assert.assertEquals(true, ValidateUtil.validateEmail("zeralzhang@gmail.com"));
    }


    @Test
    public void sendEmail() {
        User user = new User();
        user.setEmailAccount("admin");
        String token = jwtTokenUtil.generateToken(user);
        messageUtil.sendRegisterMail("zeralzhang@gmail.com", token);
    }

    @Test
    public void sendSMS() {
        Integer number = messageUtil.getSixRandNum();
        System.out.println(number);
        try {
            messageUtil.sendRegisterSMS("18829012080", number);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void generaterBitCoinAddress() {
        tokenMoneyUtil.generaterBitCoinWallet();
    }

    @Test
    public void getBalanceFromAddress() {
        String address = "155fzsEBHy9Ri2bMQ8uuuR3tv1YzcDywd4";
        System.out.println(transactionsService.getBitCoinAddressBalance(address));

        String ethAddress = "0x3a6e4D83689405a1EA16DafaC6f1614253f3Bb9A";
        System.out.println(transactionsService.getEthAddressBalance(ethAddress));
    }

    @Test
    public void getBalanceFromBlockChain() {
        String addr = "mxVGGj8S4ddYdP1ix6XbYYdVz9Li5oif44";

        System.out.println(bitCoinjService.getBalance(addr));

       /* final NetworkParameters netParams;
        if(env.equals(CurrentConfigEnum.dev.toString())) {
            netParams = TestNet3Params.get();
        } else {
            netParams = MainNetParams.get();
        }

        Address address = Address.fromBase58(netParams, addr);
        Wallet wallet = new Wallet(netParams);
        wallet.addWatchedAddress(address, 0);
        System.out.println("wallet.getWatchedAddresses()"+wallet.getWatchedAddresses());
        BlockChain chain;
        try {
            chain = new BlockChain(netParams, wallet,
                    new MemoryBlockStore(netParams));

            PeerGroup peerGroup = new PeerGroup(netParams, chain);
            peerGroup.addPeerDiscovery(new DnsDiscovery(netParams));
            peerGroup.addWallet(wallet);
            peerGroup.start();
            peerGroup.downloadBlockChain();
            Coin balance = wallet.getBalance();
            System.out.println("Wallet balance: " + balance.toFriendlyString());
        } catch (BlockStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        /*
        String addr = "mxVGGj8S4ddYdP1ix6XbYYdVz9Li5oif44";

        final NetworkParameters netParams;
        final WalletAppKit kit;
        if(env.equals(CurrentConfigEnum.dev.toString())) {
            netParams = TestNet3Params.get();
            kit =  new WalletAppKit(netParams, new File(walletPath), "ICO_TT_Test");
        } else {
            netParams = MainNetParams.get();
            kit =  new WalletAppKit(netParams, new File(walletPath), "ICO_TT");
        }


        // Download the block chain and wait until it's done.
        kit.startAsync();
        kit.awaitRunning();

        Address address = Address.fromBase58(netParams, addr);
        // If no creation time is specified, assume genesis (zero).
        kit.wallet().addWatchedAddress(address);

        System.out.println(kit.wallet().getBalance();
        System.out.println(transactionsService.getBitCoinAddressBalance(addr));*/
    }


    @Test
    public void bigIntegerTest() {
        JSONObject object = new JSONObject();
        object.put("test", "9907928010672141195");
        BigInteger value = object.getBigInteger("test");
        Double flo = value.doubleValue()/(Denomination.ETHER.value().doubleValue());
        String test = Float.toString(value.divide(Denomination.ETHER.value()).floatValue()) +  " ETHER";
        System.out.println(test + "  " + flo + "  " + Denomination.ETHER.value());
    }


    @Test
    public void sendCoin() {
        System.out.println(transactionsService.sendBitCoin("752dc4b3ae81f6fc8d19257ec02143925d0fa0ea66242cc2720d3cc50b76d2d84ea69ae4191fb03c8bea320268afc1d3150fe840cbeca172a506914bed96d40558aa971672d82253", "mpp9m4D38zX8ukuZyQLeX5diC68BL1d1xu", "0.01"));
    }

    @Test
    public void sendETH() {
        Account account = new Account();
        account.init();
        String from = Utils.HEX.encode(account.getEcKey().getAddress());
        System.out.println(transactionsService.sendETHCoin("554010e4567428d39c2c124507915eeda1fa91f31584bc0568ccba8f3495c9e754019c233b9d29a0e3aa1f74cbbbd98ac237af8cf39d73dbad10fe5c898c1a7358aa971672d82253", from, "0.01"));
    }


    @Test
    public void unitTest() {
        BigDecimal test = EthConverter.toWei("0.01", EthConverter.Unit.ETHER);
    }

    @Test
    public void getEthTransactionList() {
        JSONArray jsonArray = transactionsService.getETHAddressTransaction("0xC50580B6Bd9D917855fB822F90C40981F6540c0b");
        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
            JSONObject object = (JSONObject) iterator.next();
            System.out.println(EthConverter.fromWei(object.getBigDecimal("value"), EthConverter.Unit.ETHER) + EthConverter.Unit.ETHER.toString().toUpperCase());
        }
        System.out.println(jsonArray.toJSONString());
    }
}
