
package com.dayee.writtenExam.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

public abstract class DataTypeUtils {

    public static boolean isNotEquals(Object s1, Object s2) {

        return s1 == null ? s2 != null : !s1.equals(s2);
    }

    public static boolean isEquals(Object s1, Object s2) {

        return s1 == null ? s2 == null : s1.equals(s2);
    }

    public static boolean isCollection(Object obj) {

        Assert.notNull(obj);
        return obj instanceof Collection<?>;
    }

    public static boolean isFile(Class<?> propType) {

        return (File.class == propType) ? true : false;
    }

    public static boolean isFile(Object obj) {

        Assert.notNull(obj);
        return obj instanceof File;
    }

    public static boolean isInputStream(Class<?> propType) {

        return (InputStream.class == propType) ? true : false;
    }

    public static boolean isInputStream(Object obj) {

        Assert.notNull(obj);
        return obj instanceof InputStream;
    }

    public static boolean isCharArray(Class<?> propType) {

        return (propType == char[].class) ? true : false;
    }

    public static boolean isStringArray(Class<?> propType) {

        return (propType == String[].class) ? true : false;
    }

    public static boolean isByteArray(Class<?> propType) {

        return (propType == byte[].class) ? true : false;
    }

    public static boolean isCharArray(Object obj) {

        Assert.notNull(obj);
        return isCharArray(obj.getClass());
    }

    public static boolean isStringArray(Object obj) {

        Assert.notNull(obj);
        return isStringArray(obj.getClass());
    }

    public static boolean isByteArray(Object obj) {

        Assert.notNull(obj);
        return isByteArray(obj.getClass());
    }

    public static boolean isBasicType(Class<?> propType) {

        return isString(propType) || isInteger(propType)
               || isLong(propType)
               || isDouble(propType)
               || isDate(propType);
    }

    public static boolean isBasicType(Object obj) {

        Assert.notNull(obj);
        Class<?> propType = obj.getClass();
        return isBasicType(propType);
    }

    public static boolean isDouble(Class<?> propType) {

        return (Double.class == propType || double.class == propType) ? true
                                                                     : false;
    }

    public static boolean isFloat(Class<?> propType) {

        return (Float.class == propType || float.class == propType) ? true
                                                                   : false;
    }

    public static boolean isInteger(Class<?> propType) {

        return (Integer.class == propType || int.class == propType) ? true
                                                                   : false;
    }

    public static boolean isBigDecimal(Object obj) {

        return isBigDecimal(obj.getClass());
    }

    public static boolean isBigDecimal(Class<?> propType) {

        return (BigDecimal.class == propType) ? true : false;
    }

    public static boolean isBytes(Class<?> propType) {

        return (byte[].class == propType) ? true : false;
    }

    public static boolean isDate(Class<?> propType) {

        return (Date.class == propType) || (java.sql.Date.class == propType)
               || (Timestamp.class == propType) ? true : false;
    }

    public static boolean isLong(Class<?> propType) {

        return (Long.class == propType || long.class == propType) ? true
                                                                 : false;
    }

    public static boolean isBoolean(Class<?> propType) {

        return (Boolean.class == propType || boolean.class == propType) ? true
                                                                       : false;
    }

    public static boolean isBlob(Class<?> propType) {

        return (Blob.class == propType) ? true : false;
    }

    public static boolean isBlob(Object obj) {

        Assert.notNull(obj);
        return isBlob(obj.getClass());
    }

    public static boolean isClob(Class<?> propType) {

        return (Clob.class == propType) ? true : false;
    }

    public static boolean isClob(Object obj) {

        Assert.notNull(obj);
        return isClob(obj.getClass());
    }

    public static boolean isString(Class<?> propType) {

        return (String.class == propType) ? true : false;
    }

    public static boolean isDouble(Object obj) {

        Assert.notNull(obj);
        Class<?> propType = obj.getClass();
        return (Double.class == propType || double.class == propType) ? true
                                                                     : false;
    }

    public static boolean isInteger(Object obj) {

        Assert.notNull(obj);
        Class<?> propType = obj.getClass();
        return (Integer.class == propType || int.class == propType) ? true
                                                                   : false;
    }

    public static boolean isDate(Object obj) {

        Assert.notNull(obj);
        Class<?> propType = obj.getClass();
        return (Date.class == propType) || (Timestamp.class == propType)
               || (java.sql.Date.class == propType) ? true : false;
    }

    public static boolean isLong(Object obj) {

        Assert.notNull(obj);
        Class<?> propType = obj.getClass();
        return (Long.class == propType || long.class == propType) ? true
                                                                 : false;
    }

    public static boolean isBoolean(Object obj) {

        Assert.notNull(obj);
        Class<?> propType = obj.getClass();
        return (Boolean.class == propType || boolean.class == propType) ? true
                                                                       : false;
    }

    public static boolean isString(Object obj) {

        Assert.notNull(obj);
        return (String.class == obj.getClass()) ? true : false;
    }

    public static boolean isBigInteger(Class<?> obj) {

        Assert.notNull(obj);
        return (BigInteger.class == obj) ? true : false;
    }

    public static boolean isBigInteger(Object obj) {

        Assert.notNull(obj);
        return (BigInteger.class == obj.getClass()) ? true : false;
    }

    public static boolean isList(Object obj) {

        Assert.notNull(obj);
        return obj instanceof List<?>;
    }

    public static boolean isMap(Object obj) {

        Assert.notNull(obj);
        return obj instanceof Map<?, ?>;
    }

    // 整数到字节数组的转换
    public static byte[] intToByte(int number) {

        int temp = number;
        byte[] b = new byte[4];
        for (int i = b.length - 1; i > -1; i--) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最高位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    // 字节数组到整数的转换
    public static int byteToInt(byte[] b) {

        int s = 0;
        for (int i = 0; i < 3; i++) {
            if (b[i] >= 0)
                s = s + b[i];
            else
                s = s + 256 + b[i];
            s = s * 256;
        }
        if (b[3] >= 0) // 最后一个之所以不乘，是因为可能会溢出
            s = s + b[3];
        else
            s = s + 256 + b[3];
        return s;
    }

    // 字符到字节转换
    public static byte[] charToByte(char ch) {

        int temp = (int) ch;
        byte[] b = new byte[2];
        for (int i = b.length - 1; i > -1; i--) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最高位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    // 字节到字符转换
    public static char byteToChar(byte[] b) {

        int s = 0;
        if (b[0] > 0)
            s += b[0];
        else
            s += 256 + b[0];
        s *= 256;
        if (b[1] > 0)
            s += b[1];
        else
            s += 256 + b[1];
        char ch = (char) s;
        return ch;
    }

    // 浮点到字节转换
    public static byte[] doubleToByte(double d) {

        byte[] b = new byte[8];
        long l = Double.doubleToLongBits(d);
        for (int i = 0; i < 8; i++) {
            b[i] = new Long(l).byteValue();
            l = l >> 8;
        }
        return b;
    }

    // 字节到浮点转换
    public static double byteToDouble(byte[] b) {

        long l;
        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        l &= 0xffffffffl;
        l |= ((long) b[4] << 32);
        l &= 0xffffffffffl;
        l |= ((long) b[5] << 40);
        l &= 0xffffffffffffl;
        l |= ((long) b[6] << 48);
        l |= ((long) b[7] << 56);
        return Double.longBitsToDouble(l);
    }

    public static int[] string2Int(String[] org) {

        if (CollectionUtils.notEmpty(org)) {
            int size = 0;
            for (int i = 0; i < org.length; i++) {
                if (StringUtils.hasLength(org[i])) {
                    size++;
                }
            }
            int array[] = new int[size];
            int j = 0;
            for (int i = 0; i < org.length; i++) {
                if (StringUtils.hasLength(org[i])) {
                    array[j++] = Integer.valueOf(org[i]);
                }
            }
            return array;
        }
        return null;
    }

    public static byte[] object2ByteArray(Object obj) throws Exception {

        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream outObj = new ObjectOutputStream(byteOut);
        outObj.writeObject(obj);
        return byteOut.toByteArray();
    }

    public static Object blob2Object(Blob blob) throws Exception {

        if (blob != null) {
            InputStream is = null;
            ObjectInputStream in = null;
            try {

                is = blob.getBinaryStream();
                byte[] buff = IOUtils.toByteArray(is);
                in = new ObjectInputStream(new ByteArrayInputStream(buff));
                Object obj = in.readObject();
                return obj;

            } finally {
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(in);
            }
        }
        return null;
    }

    public static String formatFloat(Float f, String formatStr)
            throws Exception {

        String result = null;
        if (f != null) {
            DecimalFormat df = new DecimalFormat(formatStr);
            result = df.format(f);
        }
        return result;
    }

    public static Integer bigInteger2Integer(BigInteger obj) {

        return obj == null ? null : obj.intValue();
    }

}
