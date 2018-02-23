package io.loli.zto.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

public class ZtoDigestUtil {
    public static String md5WithBase64(String str, String charset) {
        return base64(md5Bytes(str, charset));
    }

    public static String base64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] md5Bytes(String str) {
        return md5Bytes(str, "UTF-8");
    }

    public static byte[] md5Bytes(String str, String charset) {
        try {
            return DigestUtils.md5(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5Str(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String md5Str(String str, String charset) {
        try {
            return DigestUtils.md5Hex(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String decodeBase64(String content) {
        return new String(Base64.decodeBase64(content));
    }

    public static String decodeBase64(String content, String encode) {
        try {
            return new String(Base64.decodeBase64(content), encode);
        } catch (UnsupportedEncodingException e) {
            return "";
        }

    }
}
