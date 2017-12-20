package com.beacon.asch.sdk.codec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 解码辅助类
 * @author eagle
 *
 */
public class Decoding {
    private static final byte[] EMPTY_BUFFER = new byte[0];

    public static byte[] hex(String hexString) throws DecodingException{
        try {
            return Hex.decodeHex(hexString.toCharArray());
        }
        catch (Exception ex){
            throw new DecodingException("decode hex failed",ex);
        }
    }

    public static  byte[] unsafeDecodeHex(String hexString){
        if (null == hexString)
            return EMPTY_BUFFER;
        try{
            return Decoding.hex(hexString);
        }
        catch (Exception ex){
            return EMPTY_BUFFER;
        }
    }

    public static byte[] base64(String base64String){
        return Base64.decodeBase64(base64String);
    }
}
