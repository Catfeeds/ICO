package com.conpany.project;

import com.tongwii.ico.util.DesEncoder;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.TestNet3Params;
import org.junit.Assert;
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

        Assert.assertEquals(true, passwordEncoder.matches("admin", "$2a$10$i689y0x9zhtnFtmGmkOPRuLChsxsdBbHLN2/bpMicev/VPcdZAnrC"));
    }

    @Test
    public void test2() {
        String password = "user";
        PlaintextPasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
        System.out.println(passwordEncoder.isPasswordValid("$2a$10$i689y0x9zhtnFtmGmkOPRuLChsxsdBbHLN2/bpMicev/VPcdZAnrC", "admin", null));
    }



    @Test
    public void fromPrivateToPublicAddress() {
        DesEncoder desEncoder = new DesEncoder();
        ECKey nowKey = ECKey.fromPrivate(Utils.HEX.decode(desEncoder.decrypt("752dc4b3ae81f6fc8d19257ec02143925d0fa0ea66242cc2720d3cc50b76d2d84ea69ae4191fb03c8bea320268afc1d3150fe840cbeca172a506914bed96d40558aa971672d82253")));
        System.out.println(nowKey.toAddress(new TestNet3Params()).toBase58());
    }
}
