/**
 * Administrator4:37:25 PM
 */

package com.dayee.writtenExam.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import com.alibaba.fastjson.util.Base64;
import com.dayee.writtenExam.framework.constant.Constants;
import com.dayee.writtenExam.framework.exception.YuncaiBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author YHY 简单加密 发送更新通知用
 */
public class EncryptUtils {

    private static final Logger logger = LoggerFactory
                                               .getLogger(EncryptUtils.class);
    
    public static void createDesKey() throws Exception {

        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            KeyGenerator _generator = KeyGenerator.getInstance(DES);
            _generator.init(new SecureRandom(
                    "saSFdfj89SDaMBVs12KA12aslkVZ06jdSF".getBytes()));
            Key key = _generator.generateKey();

            fos = new FileOutputStream(new File("D:/temp/des.key"));
            os = new ObjectOutputStream(fos);
            os.writeObject(key);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(fos);
        }
    }

    // private static void createRandomKey(int length) {
    //
    // StringBuffer buffer = new StringBuffer();
    // Random r = new Random();
    // byte[] bs = new byte[length];
    // r.nextBytes(bs);
    // for (byte b : bs) {
    //
    // buffer.append(b).append(StringUtils.COMMA);
    //
    // }
    // }

    private static Key          KEY;

    private static Key          ZPB_KEY;

    private static final String DES = "DES";

    static {
        ObjectInputStream os = null;
        try {

            os = new ObjectInputStream(EncryptUtils.class
                    .getResource("/des.key").openStream());
            KEY = (Key) os.readObject();

            os = new ObjectInputStream(EncryptUtils.class
                    .getResource("/zpbDes.key").openStream());
            ZPB_KEY = (Key) os.readObject();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new YuncaiBaseException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    public static void main(String[] args) throws Exception {

        // String s = simpleDecode("1B2AD21C84185A885A573C7754F66C1A");
        // System.out.println(s);
        // System.out.println(simpleDecode(s));
        // createDesKey();
        //System.out
        //        .println(URLDecoder
        //                .decode("%e4%b8%ad%e5%9b%bd%e7%9f%b3%e6%b2%b9%e5%a4%a7%e5%ad%a6%ef%bc%88%e5%8d%8e%e4%b8%9c%ef%bc%89%26nbsp%3b%7c%26nbsp%3b%26nbsp%3b%e5%b7%a5%e7%a8%8b%e7%ae%a1%e7%90%86%26nbsp%3b%7c%26nbsp%3b%26nbsp%3b%e6%9c%ac%e7%a7%91",
        //                        "UTF-8"));
        String str="402881b255a44fae0155a45a2cff0016"+Constants.LOGIN_DELIMITER+"YA135";
        System.out.println(EncryptUtils.simpleEncode(str));
    }

    public static Key getDefaultkey() {

        return KEY;
    }

    public static Key createSecretkey() {

        try {
            KeyGenerator _generator;

            _generator = KeyGenerator.getInstance(DES);

            String pwd = randomPassword(35);
            _generator.init(new SecureRandom(pwd.getBytes()));
            Key key = _generator.generateKey();

            return key;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            throw new YuncaiBaseException(e.getMessage(), e);
        }
    }

    public static String simpleEncode(String str) {

        return encode(KEY, str);
    }

    public static String simpleDecode(String str) {

        return decode(KEY, str);
    }

    public static String zpbEncode(String str) {

        return encode(ZPB_KEY, str);
    }

    public static String zpbDecode(String str) {

        return decode(ZPB_KEY, str);
    }

    public static String encode(Key secretkey, String str) {

        if (secretkey == null) {
            secretkey = KEY;
        }
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, secretkey);
            byteFina = cipher.doFinal(str.getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new YuncaiBaseException(e.getMessage(), e);
        } finally {
            cipher = null;
        }
        return byte2hex(byteFina);
    }

    public static String decode(Key secretkey, String str) {

        if (secretkey == null) {
            secretkey = KEY;
        }
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, secretkey);
            // if(cipher==null){
            // logger.error("cipher....");
            // int k=0;
            // }
            byteFina = cipher.doFinal(hex2byte(str.getBytes()));
        } catch (Exception e) {
            // logger.error(e.getMessage(), e);
            throw new YuncaiBaseException(e.getMessage(), e);
        } finally {
            cipher = null;
        }
        // try {
        // if("sohu".equals(RequestUtils.getCorpCode())){
        // return new String(byteFina,"UTF-8");
        // }else{
        return new String(byteFina);
        // }
        // } catch (UnsupportedEncodingException e) {
        // e.printStackTrace();
        // return null;
        // }
    }

    private static final String ZERO = "0";

    private static String byte2hex(byte[] b) {

        String hs = StringUtils.EMPTY;
        String stmp = StringUtils.EMPTY;
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + ZERO + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    private static byte[] hex2byte(byte[] b) {

        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }

        return b2;
    }

    /*
     * 下面这些S11-S44实际上是�?�?44的矩阵，在原始的C实现中是�?#define 实现的， 这里把它们实现成为static
     * final是表示了只读，切能在同一个进程空间内的多�? Instance间共�?
     */
    private static final int    S11     = 7;

    private static final int    S12     = 12;

    private static final int    S13     = 17;

    private static final int    S14     = 22;

    private static final int    S21     = 5;

    private static final int    S22     = 9;

    private static final int    S23     = 14;

    private static final int    S24     = 20;

    private static final int    S31     = 4;

    private static final int    S32     = 11;

    private static final int    S33     = 16;

    private static final int    S34     = 23;

    private static final int    S41     = 6;

    private static final int    S42     = 10;

    private static final int    S43     = 15;

    private static final int    S44     = 21;

    private static final byte[] PADDING = { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0  };

    /*
     * 下面的三个成员是MD5计算过程中用到的3个核心数据，在原始的C实现�? 被定义到MD5_CTX结构�?
     */
    private static long[]       state   = new long[4]; // state (ABCD)

    private static long[]       count   = new long[2]; // number of bits,

    // modulo 2^64

    // (lsb

    // first)
    private static byte[]       buffer  = new byte[64]; // input buffer

    /*
     * digest,是最新一次计算结果的2进制内部表示，表�?128bit的MD5�?.
     */
    private static byte[]       digest  = new byte[16];

    /*
     * getMD5ofStr是类MD5�?主要的公共方法，入口参数是你想要进行MD5变换的字符串
     * 返回的是变换完的结果，这个结果是从公共成员digestHexStr取得的．
     */
    public static String getMD5(String inbuf) {

        synchronized (EncryptUtils.class) {
            md5Init();
            md5Update(inbuf.getBytes(), inbuf.length());
            md5Final();
            StringBuffer buffer = new StringBuffer();

            for (int i = 0; i < 16; i++) {
                buffer.append(byteHEX(digest[i]));
            }
            return buffer.toString();
        }

    }

    public static String getMD5(byte[] inbuf) {

        md5Init();
        md5Update(inbuf, inbuf.length);
        md5Final();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < 16; i++) {
            buffer.append(byteHEX(digest[i]));
        }
        return buffer.toString();

    }

    /* md5Init是一个初始化函数，初始化核心变量，装入标准的幻数 */
    private static void md5Init() {

        count[0] = 0L;
        count[1] = 0L;
        // /* Load magic initialization constants.

        state[0] = 0x67452301L;
        state[1] = 0xefcdab89L;
        state[2] = 0x98badcfeL;
        state[3] = 0x10325476L;

        return;
    }

    /*
     * F, G, H ,I �?4个基本的MD5函数，在原始的MD5的C实现中，由于它们�?
     * �?单的位运算，可能出于效率的�?�虑把它们实现成了宏，在java中，我们把它�? 实现成了private方法，名字保持了原来C中的�?
     */

    private static long F(long x, long y, long z) {

        return (x & y) | ((~x) & z);

    }

    private static long G(long x, long y, long z) {

        return (x & z) | (y & (~z));

    }

    private static long H(long x, long y, long z) {

        return x ^ y ^ z;
    }

    private static long I(long x, long y, long z) {

        return y ^ (x | (~z));
    }

    /*
     * FF,GG,HH和II将调用F,G,H,I进行近一步变�? FF, GG, HH, and II transformations for
     * rounds 1, 2, 3, and 4. Rotation is separate from addition to prevent
     * recomputation.
     */

    private static long FF(long a,
                           long b,
                           long c,
                           long d,
                           long x,
                           long s,
                           long ac) {

        a += F(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    private static long GG(long a,
                           long b,
                           long c,
                           long d,
                           long x,
                           long s,
                           long ac) {

        a += G(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    private static long HH(long a,
                           long b,
                           long c,
                           long d,
                           long x,
                           long s,
                           long ac) {

        a += H(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    private static long II(long a,
                           long b,
                           long c,
                           long d,
                           long x,
                           long s,
                           long ac) {

        a += I(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    /*
     * md5Update是MD5的主计算过程，inbuf是要变换的字节串，inputlen是长度，这个
     * 函数由getMD5ofStr调用，调用之前需要调用md5init，因此把它设计成private�?
     */
    private static void md5Update(byte[] inbuf, int inputLen) {

        int i, index, partLen;
        byte[] block = new byte[64];
        index = (int) (count[0] >>> 3) & 0x3F;
        // /* Update number of bits */
        if ((count[0] += (inputLen << 3)) < (inputLen << 3))
            count[1]++;
        count[1] += (inputLen >>> 29);

        partLen = 64 - index;

        // Transform as many times as possible.
        if (inputLen >= partLen) {
            md5Memcpy(buffer, inbuf, index, 0, partLen);
            md5Transform(buffer);

            for (i = partLen; i + 63 < inputLen; i += 64) {

                md5Memcpy(block, inbuf, 0, i, 64);
                md5Transform(block);
            }
            index = 0;

        } else

            i = 0;

        // /* Buffer remaining input */
        md5Memcpy(buffer, inbuf, index, i, inputLen - i);

    }

    /*
     * md5Final整理和填写输出结�?
     */
    private static void md5Final() {

        byte[] bits = new byte[8];
        int index, padLen;

        // /* Save number of bits */
        encode(bits, count, 8);

        // /* Pad out to 56 mod 64.
        index = (int) (count[0] >>> 3) & 0x3f;
        padLen = (index < 56) ? (56 - index) : (120 - index);
        md5Update(PADDING, padLen);

        // /* Append length (before padding) */
        md5Update(bits, 8);

        // /* Store state in digest */
        encode(digest, state, 16);

    }

    /*
     * md5Memcpy是一个内部使用的byte数组的块拷贝函数，从input的inpos�?始把len长度�?
     * 字节拷贝到output的outpos位置�?�?
     */

    private static void md5Memcpy(byte[] output,
                                  byte[] input,
                                  int outpos,
                                  int inpos,
                                  int len) {

        int i;

        for (i = 0; i < len; i++)
            output[outpos + i] = input[inpos + i];
    }

    /*
     * md5Transform是MD5核心变换程序，有md5Update调用，block是分块的原始字节
     */
    private static void md5Transform(byte block[]) {

        long a = state[0], b = state[1], c = state[2], d = state[3];
        long[] x = new long[16];

        decode(x, block, 64);

        /* Round 1 */
        a = FF(a, b, c, d, x[0], S11, 0xd76aa478L); /* 1 */
        d = FF(d, a, b, c, x[1], S12, 0xe8c7b756L); /* 2 */
        c = FF(c, d, a, b, x[2], S13, 0x242070dbL); /* 3 */
        b = FF(b, c, d, a, x[3], S14, 0xc1bdceeeL); /* 4 */
        a = FF(a, b, c, d, x[4], S11, 0xf57c0fafL); /* 5 */
        d = FF(d, a, b, c, x[5], S12, 0x4787c62aL); /* 6 */
        c = FF(c, d, a, b, x[6], S13, 0xa8304613L); /* 7 */
        b = FF(b, c, d, a, x[7], S14, 0xfd469501L); /* 8 */
        a = FF(a, b, c, d, x[8], S11, 0x698098d8L); /* 9 */
        d = FF(d, a, b, c, x[9], S12, 0x8b44f7afL); /* 10 */
        c = FF(c, d, a, b, x[10], S13, 0xffff5bb1L); /* 11 */
        b = FF(b, c, d, a, x[11], S14, 0x895cd7beL); /* 12 */
        a = FF(a, b, c, d, x[12], S11, 0x6b901122L); /* 13 */
        d = FF(d, a, b, c, x[13], S12, 0xfd987193L); /* 14 */
        c = FF(c, d, a, b, x[14], S13, 0xa679438eL); /* 15 */
        b = FF(b, c, d, a, x[15], S14, 0x49b40821L); /* 16 */

        /* Round 2 */
        a = GG(a, b, c, d, x[1], S21, 0xf61e2562L); /* 17 */
        d = GG(d, a, b, c, x[6], S22, 0xc040b340L); /* 18 */
        c = GG(c, d, a, b, x[11], S23, 0x265e5a51L); /* 19 */
        b = GG(b, c, d, a, x[0], S24, 0xe9b6c7aaL); /* 20 */
        a = GG(a, b, c, d, x[5], S21, 0xd62f105dL); /* 21 */
        d = GG(d, a, b, c, x[10], S22, 0x2441453L); /* 22 */
        c = GG(c, d, a, b, x[15], S23, 0xd8a1e681L); /* 23 */
        b = GG(b, c, d, a, x[4], S24, 0xe7d3fbc8L); /* 24 */
        a = GG(a, b, c, d, x[9], S21, 0x21e1cde6L); /* 25 */
        d = GG(d, a, b, c, x[14], S22, 0xc33707d6L); /* 26 */
        c = GG(c, d, a, b, x[3], S23, 0xf4d50d87L); /* 27 */
        b = GG(b, c, d, a, x[8], S24, 0x455a14edL); /* 28 */
        a = GG(a, b, c, d, x[13], S21, 0xa9e3e905L); /* 29 */
        d = GG(d, a, b, c, x[2], S22, 0xfcefa3f8L); /* 30 */
        c = GG(c, d, a, b, x[7], S23, 0x676f02d9L); /* 31 */
        b = GG(b, c, d, a, x[12], S24, 0x8d2a4c8aL); /* 32 */

        /* Round 3 */
        a = HH(a, b, c, d, x[5], S31, 0xfffa3942L); /* 33 */
        d = HH(d, a, b, c, x[8], S32, 0x8771f681L); /* 34 */
        c = HH(c, d, a, b, x[11], S33, 0x6d9d6122L); /* 35 */
        b = HH(b, c, d, a, x[14], S34, 0xfde5380cL); /* 36 */
        a = HH(a, b, c, d, x[1], S31, 0xa4beea44L); /* 37 */
        d = HH(d, a, b, c, x[4], S32, 0x4bdecfa9L); /* 38 */
        c = HH(c, d, a, b, x[7], S33, 0xf6bb4b60L); /* 39 */
        b = HH(b, c, d, a, x[10], S34, 0xbebfbc70L); /* 40 */
        a = HH(a, b, c, d, x[13], S31, 0x289b7ec6L); /* 41 */
        d = HH(d, a, b, c, x[0], S32, 0xeaa127faL); /* 42 */
        c = HH(c, d, a, b, x[3], S33, 0xd4ef3085L); /* 43 */
        b = HH(b, c, d, a, x[6], S34, 0x4881d05L); /* 44 */
        a = HH(a, b, c, d, x[9], S31, 0xd9d4d039L); /* 45 */
        d = HH(d, a, b, c, x[12], S32, 0xe6db99e5L); /* 46 */
        c = HH(c, d, a, b, x[15], S33, 0x1fa27cf8L); /* 47 */
        b = HH(b, c, d, a, x[2], S34, 0xc4ac5665L); /* 48 */

        /* Round 4 */
        a = II(a, b, c, d, x[0], S41, 0xf4292244L); /* 49 */
        d = II(d, a, b, c, x[7], S42, 0x432aff97L); /* 50 */
        c = II(c, d, a, b, x[14], S43, 0xab9423a7L); /* 51 */
        b = II(b, c, d, a, x[5], S44, 0xfc93a039L); /* 52 */
        a = II(a, b, c, d, x[12], S41, 0x655b59c3L); /* 53 */
        d = II(d, a, b, c, x[3], S42, 0x8f0ccc92L); /* 54 */
        c = II(c, d, a, b, x[10], S43, 0xffeff47dL); /* 55 */
        b = II(b, c, d, a, x[1], S44, 0x85845dd1L); /* 56 */
        a = II(a, b, c, d, x[8], S41, 0x6fa87e4fL); /* 57 */
        d = II(d, a, b, c, x[15], S42, 0xfe2ce6e0L); /* 58 */
        c = II(c, d, a, b, x[6], S43, 0xa3014314L); /* 59 */
        b = II(b, c, d, a, x[13], S44, 0x4e0811a1L); /* 60 */
        a = II(a, b, c, d, x[4], S41, 0xf7537e82L); /* 61 */
        d = II(d, a, b, c, x[11], S42, 0xbd3af235L); /* 62 */
        c = II(c, d, a, b, x[2], S43, 0x2ad7d2bbL); /* 63 */
        b = II(b, c, d, a, x[9], S44, 0xeb86d391L); /* 64 */

        state[0] += a;
        state[1] += b;
        state[2] += c;
        state[3] += d;

    }

    /*
     * Encode把long数组按顺序拆成byte数组，因为java的long类型�?64bit的， 只拆�?32bit，以适应原始C实现的用�?
     */
    private static void encode(byte[] output, long[] input, int len) {

        int i, j;

        for (i = 0, j = 0; j < len; i++, j += 4) {
            output[j] = (byte) (input[i] & 0xffL);
            output[j + 1] = (byte) ((input[i] >>> 8) & 0xffL);
            output[j + 2] = (byte) ((input[i] >>> 16) & 0xffL);
            output[j + 3] = (byte) ((input[i] >>> 24) & 0xffL);
        }
    }

    /*
     * Decode把byte数组按顺序合成成long数组，因为java的long类型�?64bit的，
     * 只合成低32bit，高32bit清零，以适应原始C实现的用�?
     */
    private static void decode(long[] output, byte[] input, int len) {

        int i, j;

        for (i = 0, j = 0; j < len; i++, j += 4)
            output[i] = b2iu(input[j]) | (b2iu(input[j + 1]) << 8)
                        | (b2iu(input[j + 2]) << 16)
                        | (b2iu(input[j + 3]) << 24);

        return;
    }

    /*
     * b2iu是我写的�?个把byte按照不�?�虑正负号的原则的＂升位＂程序，因为java没有unsigned运算
     */
    private static long b2iu(byte b) {

        return b < 0 ? b & 0x7F + 128 : b;
    }

    /*
     * byteHEX()，用来把�?个byte类型的数转换成十六进制的ASCII表示�?
     * 因为java中的byte的toString无法实现这一点，我们又没有C语言中的 sprintf(outbuf,"%02X",ib)
     */
    private static String byteHEX(byte ib) {

        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    private static final String[] NUMBER_ARRAY = new String[10];

    static {
        NUMBER_ARRAY[0] = "1";
        NUMBER_ARRAY[1] = "2";
        NUMBER_ARRAY[2] = "3";
        NUMBER_ARRAY[3] = "4";
        NUMBER_ARRAY[4] = "5";
        NUMBER_ARRAY[5] = "6";
        NUMBER_ARRAY[6] = "7";
        NUMBER_ARRAY[7] = "8";
        NUMBER_ARRAY[8] = "9";
        NUMBER_ARRAY[9] = "0";

    }

    private static final String[] LETTER_ARRAY = new String[52];

    static {

        LETTER_ARRAY[0] = "a";
        LETTER_ARRAY[1] = "b";
        LETTER_ARRAY[2] = "c";
        LETTER_ARRAY[3] = "d";
        LETTER_ARRAY[4] = "e";
        LETTER_ARRAY[5] = "f";
        LETTER_ARRAY[6] = "g";
        LETTER_ARRAY[7] = "h";
        LETTER_ARRAY[8] = "i";
        LETTER_ARRAY[9] = "j";
        LETTER_ARRAY[10] = "k";
        LETTER_ARRAY[11] = "l";
        LETTER_ARRAY[12] = "m";
        LETTER_ARRAY[13] = "n";
        LETTER_ARRAY[14] = "o";
        LETTER_ARRAY[15] = "p";
        LETTER_ARRAY[16] = "q";
        LETTER_ARRAY[17] = "r";
        LETTER_ARRAY[18] = "s";
        LETTER_ARRAY[19] = "t";
        LETTER_ARRAY[20] = "u";
        LETTER_ARRAY[21] = "v";
        LETTER_ARRAY[22] = "w";
        LETTER_ARRAY[23] = "x";
        LETTER_ARRAY[24] = "y";
        LETTER_ARRAY[25] = "z";
        LETTER_ARRAY[26] = "A";
        LETTER_ARRAY[27] = "B";
        LETTER_ARRAY[28] = "C";
        LETTER_ARRAY[29] = "D";
        LETTER_ARRAY[30] = "E";
        LETTER_ARRAY[31] = "F";
        LETTER_ARRAY[32] = "G";
        LETTER_ARRAY[33] = "H";
        LETTER_ARRAY[34] = "I";
        LETTER_ARRAY[35] = "J";
        LETTER_ARRAY[36] = "K";
        LETTER_ARRAY[37] = "L";
        LETTER_ARRAY[38] = "M";
        LETTER_ARRAY[39] = "N";
        LETTER_ARRAY[40] = "O";
        LETTER_ARRAY[41] = "P";
        LETTER_ARRAY[42] = "Q";
        LETTER_ARRAY[43] = "R";
        LETTER_ARRAY[44] = "S";
        LETTER_ARRAY[45] = "T";
        LETTER_ARRAY[46] = "U";
        LETTER_ARRAY[47] = "V";
        LETTER_ARRAY[48] = "W";
        LETTER_ARRAY[49] = "X";
        LETTER_ARRAY[50] = "Y";
        LETTER_ARRAY[51] = "Z";
    }

    public static String randomPassword(int length) {

        StringBuffer password = new StringBuffer();

        Random r = new Random();
        int size = length - 1;
        int letterLen = 0;
        for (int i = 0; i < size; i++) {
            if (r.nextInt() % 2 == 0) {
                letterLen++;
                password.append(LETTER_ARRAY[r.nextInt(LETTER_ARRAY.length)]);
            } else {
                password.append(NUMBER_ARRAY[r.nextInt(NUMBER_ARRAY.length)]);
            }
        }
        if (letterLen == size) {
            password.append(NUMBER_ARRAY[r.nextInt(NUMBER_ARRAY.length)]);
        } else if (letterLen == 0) {
            password.append(LETTER_ARRAY[r.nextInt(LETTER_ARRAY.length)]);
        } else {
            if (r.nextInt() % 2 == 0) {
                password.append(LETTER_ARRAY[r.nextInt(LETTER_ARRAY.length)]);
            } else {
                password.append(NUMBER_ARRAY[r.nextInt(NUMBER_ARRAY.length)]);
            }
        }
        return password.toString();
    }

    public static String encodeUrl(String url) {

        try {
            url = URLEncoder.encode(url, Constants.DEFAULT_ENCODING);
            url = url.replace(StringUtils.Percent, StringUtils.DOLLAR);
        } catch (UnsupportedEncodingException e) {
        }

        return url;
    }

    public static String decodeUrl(String url) {

        try {
            url = url.replace(StringUtils.DOLLAR, StringUtils.Percent);
            url = URLDecoder.decode(url, Constants.DEFAULT_ENCODING);

        } catch (UnsupportedEncodingException e) {
        }

        return url;
    }

    public static int[] changeEncryEncodeArry2intArray(String[] endcodeIdStrArry) {

        if (endcodeIdStrArry == null || endcodeIdStrArry.length == 0) {
            return null;
        }
        int[] applyId = new int[endcodeIdStrArry.length];
        int count = 0;
        boolean isRightFlg = true;
        for (String applyIdStr : endcodeIdStrArry) {
            try {
                applyIdStr = EncryptUtils.simpleDecode(applyIdStr);
            } catch (Exception e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("changeEncryEncode2intArray::+" + endcodeIdStrArry
                                 + "changeEncryEncode2intArray 出现异常 :: "
                                 + e.getMessage()
                                 + ")");
                }

                isRightFlg = false;
                break;
            }
            if (applyIdStr.matches("^-?[0-9]*")) {
                applyId[count] = Integer.valueOf(applyIdStr).intValue();
                count++;
            } else {
                // isRightFlg = false;
                // break;
            }
        }
        if (isRightFlg) {
            return applyId;
        }
        return null;
    }

    public static Integer changeEncryEncode2Int(String encodeStr) {

        if (StringUtils.isEmpty(encodeStr)) {
            return null;
        }

        Integer decodeId = null;
        try {
            encodeStr = EncryptUtils.simpleDecode(encodeStr);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("changeEncryEncode2int::+" + encodeStr
                             + "changeEncryEncode2int 出现异常 :: "
                             + e.getMessage()
                             + ")");
            }
        }
        if (StringUtils.isNumeric(encodeStr)) {
            decodeId = Integer.valueOf(encodeStr);
        }
        return decodeId;
    }

    /**
     * 64位加密
     * 
     * @param str
     * @return
     */
    public static String getBase64(String str) {

        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = BASE64Encoder.encode(b);
        }
        return s;
    }

    /**
     * 64位解密
     * 
     * @param str
     * @return
     */
    public static String decodeBase64(String str) {

        String decodeStr = str.trim().replaceAll(" ", "+")
                .replaceAll("\\*", "/");
        try {
            decodeStr = new String(Base64.decodeFast(decodeStr), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeStr;
    }
}