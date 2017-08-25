package com.tongwii.ico.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.tongwii.ico.bean.PemFile;
import com.tongwii.ico.exception.ApplicationException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.crypto.Cipher;
import java.util.Properties;

public class RSAEncodeUtil {
    // 待加密文字
    public final static String data = "hello world";
    /**
     * String to hold name of the encryption algorithm.
     */
    public  static String RESOURCES_DIR = null;
    private static String fileDir;

    static {
        Resource resource = new ClassPathResource(
                "application.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            fileDir = properties.getProperty("publickey.location");
            Resource publicKey = new ClassPathResource(fileDir);
            RESOURCES_DIR = publicKey.getFile().toString();
            System.out.println(RESOURCES_DIR);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("加载配置文件出错");
        }
    }
    public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchProviderException, Exception {
        Security.addProvider(new BouncyCastleProvider());


        KeyFactory factory = KeyFactory.getInstance("RSA", "BC");
        try {
            // 读取证书文件并解析密钥
            PrivateKey priv = generatePrivateKey(factory, RESOURCES_DIR + "/id_rsa");
//            LOGGER.info(String.format("私钥：%s", priv));

            PublicKey pub = generatePublicKey(factory, RESOURCES_DIR + "/id_rsa.pub");
//            LOGGER.info(String.format("公钥：%s", pub));


            // 公钥加密
            byte[] encryptedBytes = encrypt(data.getBytes(), pub);
            // 加密后是乱码不便于存入数据库，所以用Base64转换下
            String eCode = Base64.encodeBase64String(encryptedBytes);
//            LOGGER.info(String.format("加密后：%s", eCode));
            System.out.println(String.format("加密后：%s", eCode));

            // 私钥解密
            byte[] decryptedBytes = decrypt(Base64.decodeBase64(eCode), priv);
//            LOGGER.info(String.format("加密后：%s", new String(decryptedBytes)));
            System.out.println(String.format("解密后：%s", new String(decryptedBytes)));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件获取私钥
     * @param factory
     * @param filename
     * @return
     * @throws InvalidKeySpecException
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static PrivateKey generatePrivateKey(KeyFactory factory, String filename) throws InvalidKeySpecException, FileNotFoundException, IOException {
        PemFile pemFile = new PemFile(filename);
        byte[] content = pemFile.getPemObject().getContent();
        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
        return factory.generatePrivate(privKeySpec);
    }

    /**
     * 文件获取公钥
     * @param factory
     * @param filename
     * @return
     * @throws InvalidKeySpecException
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static PublicKey generatePublicKey(KeyFactory factory, String filename) throws InvalidKeySpecException, FileNotFoundException, IOException {
        PemFile pemFile = new PemFile(filename);
        byte[] content = pemFile.getPemObject().getContent();
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
        return factory.generatePublic(pubKeySpec);
    }

    /**
     * 公钥加密
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    /**
     * 私钥解密
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }
}
