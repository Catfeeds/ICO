package com.conpany.project;

import com.alibaba.fastjson.JSON;
import com.tongwii.ico.core.Result;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-08-03
 */
public class EncoderTest {
    @Test
    public void test1() {
        String password = "user";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode(password));
    }
}
