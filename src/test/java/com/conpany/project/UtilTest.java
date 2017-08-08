package com.conpany.project;

import com.tongwii.ico.model.User;
import com.tongwii.ico.security.JwtTokenUtil;
import com.tongwii.ico.util.MessageUtil;
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
}
