package com.conpany.project;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongwii.ico.model.User;
import com.tongwii.ico.security.JwtTokenUtil;
import com.tongwii.ico.service.TransactionsService;
import com.tongwii.ico.util.EthConverter;
import com.tongwii.ico.util.MessageUtil;
import com.tongwii.ico.util.TokenMoneyUtil;
import com.tongwii.ico.util.ValidateUtil;
import org.apache.tomcat.util.buf.HexUtils;
import org.ethereum.core.Account;
import org.ethereum.core.Denomination;
import org.ethereum.net.eth.handler.Eth;
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
        System.out.println(transactionsService.sendBitCoin("32a305c705166c45ddef526a5576ddf5a7ae66e0d62c68f7f9e3a92fedfea6761e089246ea93fda2a6c44844074aff0ef0471cce1c328540e7191ac28fecd46558aa971672d82253", "19VGZMbirTFSifZ8TJj44fvskhp7TmeWhj", "0.01"));
    }

    @Test
    public void sendETH() {
        Account account = new Account();
        account.init();
        String from = HexUtils.toHexString(account.getEcKey().getAddress());
        System.out.println(transactionsService.sendETHCoin("554010e4567428d39c2c124507915eeda1fa91f31584bc0568ccba8f3495c9e754019c233b9d29a0e3aa1f74cbbbd98ac237af8cf39d73dbad10fe5c898c1a7358aa971672d82253", from, "0.01"));
    }


    @Test
    public void unitTest() {
        BigDecimal test = EthConverter.toWei("0.01", EthConverter.Unit.ETHER);
    }

    @Test
    public void getEthTransactionList() {
        JSONArray jsonArray = transactionsService.getETHAddressTransaction("0xC50580B6Bd9D917855fB822F90C40981F6540c0b", "1", "10");
        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
            JSONObject object = (JSONObject) iterator.next();
            System.out.println(EthConverter.fromWei(object.getBigDecimal("value"), EthConverter.Unit.ETHER) + EthConverter.Unit.ETHER.toString().toUpperCase());
        }
        System.out.println(jsonArray.toJSONString());
    }
}
