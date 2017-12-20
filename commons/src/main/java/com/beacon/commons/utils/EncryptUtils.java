package com.beacon.commons.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

/**
 * 加/解密操作帮助类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/5/26
 */
public class EncryptUtils {

    // 全局数组
    private final static String[] digits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private static String key = "liegou_key";

    /**
     * MD5加密为16进制字符串
     */
    public static String md5(String data) {
        return md5(data, false);
    }

    /**
     * MD5加密
     */
    public static String md5(String data, boolean encode) {
        String result = bytesToHex(encryptByDigest(data, "MD5"));
        if (encode) {
            result = base64Encode(result.getBytes());
        }
        return result;
    }

    /**
     * SHA加密
     */
    public static String sha(String data) {
        return bytesToHex(encryptByDigest(data, "SHA-1"));
    }

    private static byte[] encryptByDigest(String data, String type) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(data.getBytes());
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES加密（对称）
     */
    public static String aes(String data) throws Exception {
        SecretKey secretKey = createSecretKey("AES");
        return encrypt(data, secretKey);
    }

    /**
     * DSA签名（原始数据不加密）
     */
    public static Properties dsa(String data) throws Exception {
        Properties properties = new Properties();
        KeyPair keyPair = createPairKey("DSA");
        String signedData = sign(data, keyPair.getPrivate());
        properties.put("SignedData", signedData);
        properties.put("PublicKey", encodeKey(keyPair.getPublic()));

        boolean b = verify(data, signedData, keyPair.getPublic());
        return properties;
    }

    /**
     * RSA原始数据加密后签名
     * 甲方到乙方:通过私钥加密、私钥签名，通过公钥解密、公钥验证签名
     * 乙方到甲方:使用公钥加密、私钥解密，
     * 两次数据传递完成一整套的数据交互！
     */
    public static Properties rsa(String data) throws Exception {
        Properties properties = new Properties();
        KeyPair keyPair = createPairKey("RSA");

        //通过私钥加密、私钥签名，通过公钥解密、公钥验证签名
        String encryptData = encrypt(data, keyPair.getPrivate());
        String signedData = sign(encryptData, keyPair.getPrivate());
        properties.put("EncryptData", encryptData);
        properties.put("SignedData", signedData);
        properties.put("PublicKey", encodeKey(keyPair.getPublic()));

        boolean b = verify(encryptData, signedData, keyPair.getPublic());
        b = data.equals(decrypt(encryptData, keyPair.getPublic()));

        //使用公钥加密、私钥解密
        encryptData = encrypt(data, keyPair.getPublic());
        b = data.equals(decrypt(encryptData, keyPair.getPrivate()));

        return properties;
    }

    // 转换字节数组为16进制字串
    private static String bytesToHex(byte[] bytes) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sBuffer.append(byteToHex(bytes[i]));
        }
        return sBuffer.toString();
    }

    // 转换字节数组为16进制字串
    private static String byteToHex(byte b) {
        int i = b;
        if (i < 0) {
            i += 256;
        }
        return digits[i / 16] + digits[i % 16];
    }

    private static SecretKey createSecretKey(String algorithm) throws Exception {
        // 根据特定的算法algorithm获取一个密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);

        // 加密随机数生成器 (RNG),(可以不写)
        SecureRandom secureRandom = new SecureRandom();

        // 重新设置此随机对象的种子
        secureRandom.setSeed(key.getBytes());

        // 使用给定的随机源（和默认的参数集合）初始化确定密钥大小的密钥对生成器
        keyGenerator.init(128, secureRandom);

        // 生成一个密钥对象,返回
        return keyGenerator.generateKey();
    }

    private static KeyPair createPairKey(String algorithm) throws Exception {
        // 根据特定的算法algorithm获取一个密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);

        // 加密随机数生成器 (RNG),(可以不写)
        SecureRandom secureRandom = new SecureRandom();

        // 重新设置此随机对象的种子
        secureRandom.setSeed(key.getBytes());

        // 使用给定的随机源（和默认的参数集合）初始化确定密钥大小的密钥对生成器
        keyPairGenerator.initialize(1024, secureRandom);

        // 生成一个密钥对象,返回
        return keyPairGenerator.generateKeyPair();
    }

    private static String encrypt(String data, Key key) throws Exception {
        // 加密/解密器
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());

        // 用指定的密钥和模式初始化Cipher对象
        // 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, key);

        // 加密后，返回密文的base 64 code形式
        return base64Encode(cipher.doFinal(data.getBytes()));
    }

    private static String decrypt(String encryptData, Key key) throws Exception {
        // 加密/解密器
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());

        // 用指定的密钥和模式初始化Cipher对象
        // 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
        cipher.init(Cipher.DECRYPT_MODE, key);

        // 将base 64 code形式的密文解密
        return new String((cipher.doFinal(base64Decode(encryptData))));
    }

    private static String sign(String sourceData, String algorithm, String privateKey) throws Exception {
        return sign(sourceData, decodePrivateKey(algorithm, privateKey));
    }

    private static String sign(String sourceData, PrivateKey privateKey) throws Exception {
        // Signature 对象可用来生成和验证数字签名
        Signature signature;
        if ("RSA".equalsIgnoreCase(privateKey.getAlgorithm())) {
            signature = Signature.getInstance("MD5withRSA");
        } else {
            signature = Signature.getInstance(privateKey.getAlgorithm());
        }

        // 初始化签署签名的私钥
        signature.initSign(privateKey);

        // 更新要由字节签名或验证的数据(加密)
        signature.update(sourceData.getBytes());

        // 加密后返回密文的base 64 code形式
        return base64Encode(signature.sign());
    }

    private static boolean verify(String data, String signedData, String algorithm, String publicKey) throws Exception {
        return verify(data, signedData, decodePublicKey(algorithm, publicKey));
    }

    private static boolean verify(String data, String signedData, PublicKey publicKey) throws Exception {
        // Signature 对象可用来生成和验证数字签名
        Signature signature;
        if ("RSA".equalsIgnoreCase(publicKey.getAlgorithm())) {
            signature = Signature.getInstance("MD5withRSA");
        } else {
            signature = Signature.getInstance(publicKey.getAlgorithm());
        }

        // 初始化验证签名的公钥
        signature.initVerify(publicKey);

        // 使用指定的 byte 数组更新要签名或验证的数据
        signature.update(data.getBytes());

        // 验证传入的签名
        return signature.verify(base64Decode(signedData));
    }

    /**
     * 秘钥编码
     *
     * @param key 秘钥
     * @return 编码后的秘钥
     */
    private static String encodeKey(Key key) {
        return base64Encode(key.getEncoded());
    }

    /**
     * 私钥解码
     *
     * @param privateKey 编码的私钥
     * @return 解码后的私钥
     */
    private static PrivateKey decodePrivateKey(String algorithm, String privateKey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(base64Decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 公钥解码
     *
     * @param publicKey 编码的公钥
     * @return 解码后的公钥
     */
    private static PublicKey decodePublicKey(String algorithm, String publicKey) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(base64Decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {
        return Base64.encode(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) {
        return Base64.decode(base64Code);
    }

    /**
     * 将字符串输出到文件
     */
    private static void saveToFile(Properties properties) {
        FileOutputStream outputStream = null;
        try {
            outputStream = (new FileOutputStream(new File("ted.lic")));
            properties.store(outputStream, "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}