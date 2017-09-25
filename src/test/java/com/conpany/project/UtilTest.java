package com.conpany.project;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongwii.ico.configurer.TestConfig;
import com.tongwii.ico.dao.OperatorHistoryMapper;
import com.tongwii.ico.model.User;
import com.tongwii.ico.security.JwtTokenUtil;
import com.tongwii.ico.service.TransactionsService;
import com.tongwii.ico.util.*;
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
    TestConfig testConfig;

    @Test
    public void validateUtilTest() {
        Assert.assertEquals(true, ValidateUtil.validateEmail("zeralzhang@gmail.com"));
    }

    @Test
    public void propertiesTest() {
        System.out.println(testConfig.getBankcard());
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

    @Test
    public void ipTest() {
        System.out.println(IPUtil.getIpAddressInfo("117.82.224.167"));
    }
}
