package com.conpany.project;

import com.tongwii.ico.model.User;
import com.tongwii.ico.security.JwtTokenUtil;
import com.tongwii.ico.service.TransactionsService;
import com.tongwii.ico.util.MessageUtil;
import com.tongwii.ico.util.TokenMoneyUtil;
import com.tongwii.ico.util.ValidateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

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
        System.out.printf("friendlyString ----> %s",  transactionsService.getBitCoinAddressBalance(address));

        String ethAddress = "0x3a6e4D83689405a1EA16DafaC6f1614253f3Bb9A";
        System.out.println(transactionsService.getEthAddressBalance(ethAddress));
    }


}
