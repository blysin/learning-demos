package com.keytop.demo.client.util;

import java.security.MessageDigest;


/**
 * md5 util
 *
 * @author zengwl
 * @date 2018/10/12
 */
public class MD5Util {

    /**
     * MD5 32位小写
     *
     * @param s
     * @return
     */
    public static String convert(String s) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        return dealMath(s, hexChars);
    }

    /**
     * MD5 32位大写
     *
     * @param s
     * @return
     */
    public static String convertBigChar(String s) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        return dealMath(s, hexChars);
    }

    private static String dealMath(String s, char[] hexChars) {
        try {
            byte[] bytes = s.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
            return null;
        }
    }
}
