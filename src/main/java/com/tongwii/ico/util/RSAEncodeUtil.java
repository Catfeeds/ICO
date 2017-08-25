package com.tongwii.ico.util;

import com.tongwii.ico.exception.ApplicationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Properties;

@Component
public class RSAEncodeUtil {
    /**
     * String to hold name of the encryption algorithm.
     */
    public static final String ALGORITHM = "RSA";

    /**
     * 秘钥文件地址如C:/keys/private.key
     */
    public static String PRIVATE_KEY_FILE = null;

    /**
     * 公钥文件地址如C:/keys/public.key"
     */
    public static String PUBLIC_KEY_FILE = null;

    private static String fileDir;

    static {
        Resource resource = new ClassPathResource(
                "application.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            fileDir = properties.getProperty("publickey.location");
            Resource publicKey = new ClassPathResource(
                    fileDir+"/id_rsa.pub");
            Resource privateKey = new ClassPathResource(
                    fileDir+"/id_rsa");
            PRIVATE_KEY_FILE = publicKey.getFile().getPath();
            PRIVATE_KEY_FILE = privateKey.getURL().toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("加载配置文件出错");
        }
    }

    /**
     * Generate key which contains a pair of private and public key using 1024
     * bytes. Store the set of keys in Prvate.key and Public.key files.
     *
     */
    public static void generateKey() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024);
            final KeyPair key = keyGen.generateKeyPair();

            File privateKeyFile = new File(PRIVATE_KEY_FILE);
            File publicKeyFile = new File(PUBLIC_KEY_FILE);

            // Create files to store public and private key
            if (privateKeyFile.getParentFile() != null) {
                privateKeyFile.getParentFile().mkdirs();
            }
            privateKeyFile.createNewFile();

            if (publicKeyFile.getParentFile() != null) {
                publicKeyFile.getParentFile().mkdirs();
            }
            publicKeyFile.createNewFile();

            // Saving the Public key in a file
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(
                    new FileOutputStream(publicKeyFile));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();

            // Saving the Private key in a file
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                    new FileOutputStream(privateKeyFile));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * The method checks if the pair of public and private key has been generated.
     *
     * @return flag indicating if the pair of keys were generated.
     */
    public static boolean areKeysPresent() {

        File privateKey = new File(PRIVATE_KEY_FILE);
        File publicKey = new File(PUBLIC_KEY_FILE);

        if (privateKey.exists() && publicKey.exists()) {
            return true;
        }
        return false;
    }

    /**
     * Encrypt the plain text using public key.
     *
     * @param text
     *          : original plain text
     * @param key
     *          :The public key
     * @return Encrypted text
     * @throws java.lang.Exception
     */
    public static byte[] encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    /**
     * Decrypt text using private key.
     *
     * @param text
     *          :encrypted text
     * @param key
     *          :The private key
     * @return plain text
     * @throws java.lang.Exception
     */
    public static String decrypt(byte[] text, PrivateKey key) {
        byte[] dectyptedText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(text);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }

    /**
     * Test the EncryptionUtil
     */
    public static void main(String[] args) {

        try {

            /*// Check if the pair of keys are present else generate those.
            if (!areKeysPresent()) {
                // Method generates a pair of keys using the RSA algorithm and stores it
                // in their respective files
                generateKey();
            }*/

            // Check if the pair of keys are present else generate those.
            if (areKeysPresent()) {
                final String originalText = "Text to be encrypted ";
                ObjectInputStream inputStream = null;

                // Encrypt the string using the public key
                inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
                final PublicKey publicKey = (PublicKey) inputStream.readObject();
                final byte[] cipherText = encrypt(originalText, publicKey);

                // Decrypt the cipher text using the private key.
                inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
                final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
                final String plainText = decrypt(cipherText, privateKey);

                // Printing the Original, Encrypted and Decrypted Text
                System.out.println("Original: " + originalText);
                System.out.println("Encrypted: " +cipherText.toString());
                System.out.println("Decrypted: " + plainText);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
