/**
 * 
 */

package com.dayee.writtenExam.framework.util;

import java.security.MessageDigest;

/**
 * @author Chinakite
 */
public class MD5Utils {
    
    private static final String PREFIX = "YuNcAil*B9ihEq~L(:";
    
    /**
     * MD5加密字符串
     * 
     * @param s 输入字符串
     * @return 加密后的字符串
     */
    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = (PREFIX+s).getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(MD5("1"));
        System.out.println(MD5("123456"));
        System.out.println(MD5("888888"));
        System.out.println(MD5("dayee123"));
    }
}
