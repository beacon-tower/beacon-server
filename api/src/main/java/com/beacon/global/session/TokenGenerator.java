package com.beacon.global.session;

import com.beacon.commons.exception.ResException;

import java.security.MessageDigest;
import java.util.UUID;

import static com.beacon.commons.code.PublicResCode.SERVER_EXCEPTION;

/**
 * 生成token
 *
 * @author luckyhua
 * @version 1.0.0
 * @since 2017/09/26
 */
public class TokenGenerator {

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] HEX_CODE = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(HEX_CODE[(b >> 4) & 0xF]);
            r.append(HEX_CODE[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new ResException(SERVER_EXCEPTION);
        }
    }
}
