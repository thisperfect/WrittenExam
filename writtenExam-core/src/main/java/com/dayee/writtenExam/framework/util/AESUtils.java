
package com.dayee.writtenExam.framework.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * AES 加密/解密
 * @author Administrator
 *
 */
public class AESUtils {
    
    public static final String KEY = "vuUJLULu4GGszgck";

    public static void main(String[] args) throws Exception {

        String cSrc = "100010&201411171855";
        String kSrc=AESUtils.encrypt(cSrc, "vuUJLULu4GGszgny");
        
        String dSrc = AESUtils.decrypt(kSrc, "vuUJLULu4GGszgny");
        
        
        System.out.println("加密后："+kSrc);
        
        System.out.println("解密后："+dSrc);
    }
    
    public static String decrypt(String sSrc) throws Exception {
        
        return decrypt(sSrc,KEY);
    }
    
    public static String encrypt(String sSrc) throws Exception {
        
        return encrypt(sSrc,KEY);
    }

    public static String decrypt(String sSrc,String key) throws Exception {

        try {
            // 判断Key是否正确
            if (key == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (key.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = hex2byte(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public static String decrypt2(String input, String key) {

        byte[] output = null;

        try {
 
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, skey);

            output = cipher.doFinal(Base64.decodeBase64(input.getBytes()));
            return new String(output,"UTF-8");

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;

    }

    // 判断Key是否正确
    public static String encrypt(String sSrc,String key) throws Exception {

        if (key == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (key.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = key.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return byte2hex(encrypted).toLowerCase();
    }

    public static byte[] hex2byte(String strhex) {

        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                                           16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {

        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
}