package com.conpany.project;

import org.junit.Test;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The type DesEncoder test.
 *
 * @author Zeral
 * @date 2017-08-03
 */
public class EncoderTest extends Tester {

    @Test
    public void encoder() {
        String password = "admin";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode(password));
    }

    @Test
    public void test2() {
        String password = "user";
        PlaintextPasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
        System.out.println(passwordEncoder.isPasswordValid("admin", "admin", null));
    }
}
