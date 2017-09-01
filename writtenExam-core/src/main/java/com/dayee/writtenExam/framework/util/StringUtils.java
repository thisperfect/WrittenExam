
package com.dayee.writtenExam.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.dayee.writtenExam.framework.constant.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final Logger logger                 = LoggerFactory
                                                               .getLogger(StringUtils.class);

    public static final String  AT                     = "@";

    public static final String  PAUSE                  = "、";

    public static final String  DIAGONAL               = "/";

    public static final String  DOUBLE_DIAGONAL        = "//";

    public static final String  HTTP_DIAGONAL          = "http://";

    // public static final int STRING_MAX_LENGTH = 20;

    public static final String  EMPTY_SPACE            = " ";

    public static final String  EMPTY_SPACE_OR         = " or ";

    // public static final String INIT_STRING = "";

    public static final String  NULL_STRING            = "null";

    public static final char    EMPTY_SPACE_CHAR       = ' ';

    public static final String  WEB_ENTER              = "\\r\\n";

    public static final String  ENTER                  = "\r\n";

    public static final String  DOC_ENTER              = "\n";

    public static final String  TAB                    = "\t";

    public static final String  LINE_PICK              = "|";

    public static final String  DOT                    = ".";

    public static final String  DOT_SPLIT              = "\\.";

    public static final String  COMMA                  = ",";

    public static final String  Q_MARK                 = "?";

    public static final String  SEMICOLON              = ";";

    public static final String  UNDERLINE              = "_";

    public static final String  PARAM_SEPARATOR        = ",SEPARATOR,";

    public static final String  Plus                   = "+";

    public static final String  Plus_Split             = "\\+";

    public static final String  LINE_SEPARATOR         = System.getProperty("line.separator");

    public static final String  ADD_CONTENT_SEPARATOR  = "<br>";

    public static final String  ADD_CONTENT_SEPARATOR_ = "<br/>";

    public static final String  NBSP                   = "&nbsp";

    public static final String  NBSP_SEMICOLON         = "&nbsp;";

    public static final String  Percent                = "%";

    public static final String  AND                    = "&";

    public static final String  SHORT_LINE             = "-";

    public static final String  EQUAL_SIGN             = "=";

    public static final String  MH                     = "：";

    public static final String  NULLSTRING_PATTERN     = "\\s*";

    public static final String  UrlSplit               = "~";

    public static final String  Left_Brackets          = "(";

    public static final String  Right_Brackets         = ")";

    public static final String  DOLLAR                 = "$";

    public static final String  DOUBLEDOLLAR           = "$$";

    public static final String  JING                   = "#";

    public static final String  SINGLE_REF             = "'";

    public static final String  DOUBLE_REF             = "\"";

    public static final String  RIGHT_ARROW            = "->";

    public static final String  RIGHT_ARROW_BRACKET    = ">";

    public static final String  LEFT_ARROW_BRACKET     = "<";

    public static final String  E_MARK                 = "!";

    public static final String  BR                     = "&lt;br&gt;";

    public static final String  BR2                    = "&lt;/br&gt;";

    public static final String  CONSTANT_1             = "1";

    public static final String  CONSTANT_2             = "2";

    public static final String  YES                    = "Y";

    public static final String  NO                     = "N";

    //
    // public static final String SYSTEM_REALITY_LINE_SEPARATOR = System
    // .getProperty("line.separator");

    // public static final char LINE_SEPARATOR_CHAR = '‖';

    public static final String  COLON                  = ":";

    public static final String  HTML_COLON             = ":&nbsp;";

    public static final String  COLON_REGEX            = ":|：";

    public static final String  PUNCT_REGEX            = "[^\\w\\d]+";

    public static String trimBlank(String str) {

        if (null == str) {
            return null;
        }
        return str.replaceAll(NULLSTRING_PATTERN, EMPTY);

    }

    public static final String[] chineseNumberStr = { "十", "一", "二", "三", "四",
            "五", "六", "七", "八", "九"              };

    public static String changeChinaNumber(Integer number) {

        String array = number.toString();
        StringBuffer buffer = new StringBuffer();
        int length = array.length();
        for (int i = 0; i < (length - 1); i++) {
            String c = array.substring(i, (i + 1));
            int num = Integer.valueOf(c);
            if (num == 1) {
                buffer.append(chineseNumberStr[0]);
            } else {
                buffer.append(chineseNumberStr[num])
                        .append(chineseNumberStr[0]);
            }
        }

        String c = array.substring(length - 1);
        int num = Integer.valueOf(c);
        if (num != 0) {

            buffer.append(chineseNumberStr[num]);
        }
        return buffer.toString();

    }

    // public static void change(String b) {
    //
    // b = b.replace("a", "b");
    // }

    /**
     * 任意字符串转全角字符串的函数(SBC case): 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     */
    public static String toSBC(String input) {

        // 半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 任意字符转半角字符串的函数(DBC case):全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     */
    public static String toDBC(String input) {

        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
            } else if (c[i] == 215) {
                // [×]转[*]
                c[i] = (char) 42;
            } else if (c[i] == 8208 || c[i] == 8209
                       || c[i] == 8210
                       || c[i] == 8211
                       || c[i] == 8212
                       || c[i] == 8213) {
                // [—]转[-]
                c[i] = (char) 45;
            } else if (c[i] == 8216 || c[i] == 8217
                       || c[i] == 8218
                       || c[i] == 8219) {
                // [‘’]转[']
                c[i] = (char) 39;
            } else if (c[i] == 8220 || c[i] == 8221
                       || c[i] == 8222
                       || c[i] == 8223) {
                // [“”]转["]
                c[i] = (char) 34;
            } else if (c[i] == 12290) {
                // [。]转[.]
                c[i] = (char) 46;
            } else if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * 英文字母转半角字符串的函数(DBC case):全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     */
    public static String letterToDBC(String input) {

        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
             if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    public static String substring(String regex, String str) {

        if (StringUtils.hasLength(str)) {
            Pattern pattern = Pattern.compile(regex.trim());
            Matcher m = pattern.matcher(str);
            if (m.find()) {
                return substring(str, m.start(), m.end());
            }
        }
        return EMPTY;
    }

    public static String substring(String str,
                                   int start,
                                   int end,
                                   String encoding) throws Exception {

        byte[] bs = str.getBytes(encoding);
        byte[] newBs = ArrayUtils.subarray(bs, start, end);
        return new String(newBs, encoding);
    }

    public static String[] splitAll(String str, List<String> splitChars) {

        String[] array = new String[splitChars.size()];
        array = splitChars.toArray(array);
        return splitAll(str, array);
    }

    public static String[] splitAll(String str, String[] splitChars) {

        if (splitChars == null) {
            return new String[] { str };
        } else {
            List<String> list = new ArrayList<String>();
            list.add(str);
            for (String splitChar : splitChars) {

                String allStr[] = new String[list.size()];
                list.toArray(allStr);
                list.clear();

                for (String s : allStr) {

                    String[] temp = s.split(splitChar);
                    CollectionUtils.addAll(list, temp);

                }

            }

            String[] result = new String[list.size()];
            list.toArray(result);
            return result;
        }
    }

    public static List<String> splitAllForList(String str, String[] splitChars) {

        List<String> result = new ArrayList<String>();
        if (splitChars == null || isEmpty(str)) {
            result.add(str);
            return result;
        } else {

            result.add(str);
            for (String splitChar : splitChars) {
                if (hasLength(splitChar)) {
                    String allStr[] = new String[result.size()];
                    result.toArray(allStr);
                    result.clear();

                    for (String s : allStr) {

                        String[] temp = s.split(splitChar);
                        CollectionUtils.addAll(result, temp);

                    }
                }
            }

            return result;
        }
    }

    public static List<String> splitForList(String str, String splitChar) {

        List<String> result = new ArrayList<String>();
        if (isEmpty(splitChar) || isEmpty(str)) {
            result.add(str);
            return result;
        } else {

            String[] temp = str.split(splitChar);
            CollectionUtils.addAll(result, temp);

            return result;
        }
    }

    public static String[] splitAny(String str, String[] splitChars) {

        if (splitChars != null) {
            for (String splitChar : splitChars) {
                if (str.contains(splitChar)) {
                    return str.split(splitChar);
                }

            }
        }

        return new String[] { str };
    }

    public static boolean containsAny(String str, String[] searchChars) {

        if (searchChars == null) {
            return false;
        } else {

            for (String s : searchChars) {
                if (contains(str, s)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean containsAll(String str, String[] searchChars) {

        if (searchChars == null) {
            return false;
        } else {

            for (String s : searchChars) {
                if (!contains(str, s)) {
                    return false;
                }
            }
            return true;
        }
    }

    // 删除多余的行标记符
    public static String deleteUnwantedWhitespace(String str) throws Exception {

        BufferedReader reader = new BufferedReader(new StringReader(str));
        List<String> list = new ArrayList<String>();
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {

            // line = line.trim();
            if (hasLength(line.trim())) {
                list.add(line);
            }
        }
        if (CollectionUtils.notEmpty(list)) {
            for (String s : list) {
                if (buffer.length() > 0) {
                    buffer.append(LINE_SEPARATOR);
                }
                buffer.append(s);
            }
        }
        return buffer.toString();
    }

    // 替换所有的行标记符
    public static String replaceWhitespace(String str, String replacement)
            throws Exception {

        BufferedReader reader = new BufferedReader(new StringReader(str));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {

            // line = line.trim();
            if (hasLength(line.trim())) {
                buffer.append(line).append(replacement);
            }
        }
        return buffer.toString();
    }

    // 删除所有的行标记符
    public static String deleteLineSeparator(String str) throws Exception {

        BufferedReader reader = new BufferedReader(new StringReader(str));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {

            // line = line.trim();
            if (hasLength(line.trim())) {
                buffer.append(line);
            }
        }
        return buffer.toString();
    }

    // 删除多余的空格符
    public static String deleteUnwantedSpaces(String str) {

        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char chs[] = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {

            if (Character.isSpaceChar(str.charAt(i)) || '\t' == str.charAt(i)) {

                if (i != 0 && (i != sz - 1)
                    && !Character.isSpaceChar(str.charAt(i - 1))) {
                    chs[count++] = str.charAt(i);
                }

            } else {
                chs[count++] = str.charAt(i);
            }
        }

        if (count == sz) {
            return str;
        } else {
            return new String(chs, 0, count);
        }
    }

    // 删除多余的空格符
    public static String deleteSpaces(String str) {

        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char chs[] = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            if (!Character.isSpaceChar(ch) && ch != '\t') {
                chs[count++] = ch;
            }
        }

        if (count == sz) {
            return str;
        } else {
            return new String(chs, 0, count);
        }
    }

    // 删除多余的空格符
    public static String deleteWhitespace(String str) {

        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char chs[] = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            if (!Character.isWhitespace(ch) && ch != '\t') {
                chs[count++] = ch;
            }
        }

        if (count == sz) {
            return str;
        } else {
            return new String(chs, 0, count);
        }
    }

    // 删除多余的空格符,且null对象返回""
    public static String delWhitespaceReturnNoNull(String str) {

        if (isEmpty(str)) {
            return "";
        }
        int sz = str.length();
        char chs[] = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            if (!Character.isWhitespace(ch) && ch != '\t') {
                chs[count++] = ch;
            }
        }

        if (count == sz) {
            return str;
        } else {
            return new String(chs, 0, count);
        }
    }

    // 删除可以忽略的标记符
    public static String deleteDentifierIgnorable(String str) {

        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char chs[] = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {

            if (!Character.isIdentifierIgnorable(str.charAt(i))) {
                chs[count++] = str.charAt(i);

            }
        }

        if (count == sz) {
            return str;
        } else {
            return new String(chs, 0, count);
        }
    }

    // 返回字符串中的所有数字
    public static String getAllNum(String str) {
        if (isEmpty(str)) {
            return "";
        }
        int sz = str.length();
        char chs[] = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {

            if (Character.isDigit(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }

        if (count == sz) {
            return str;
        } else {
            return new String(chs, 0, count);
        }
    }

    // public static String deleteUnwantedWhitespaceNotSpaces(String str) {
    //
    // if (isEmpty(str)) {
    // return str;
    // }
    // int sz = str.length();
    // char chs[] = new char[sz];
    // int count = 0;
    // for (int i = 0; i < sz; i++) {
    // System.out.println("处理：" + str.charAt(i));
    // if (Character.isWhitespace(str.charAt(i))) {
    //
    // int next = i + 1;
    //
    // if ((next < sz) && !Character.isWhitespace(str.charAt(next))) {
    // System.out.println("下一个字符 ：" + str.charAt(next) + "="
    // + Character.isSpaceChar(str.charAt(i)));
    // if (Character.isSpaceChar(str.charAt(i))) {
    //
    // chs[count++] = str.charAt(i);
    // } else {
    // System.out.println("jia ||");
    // chs[count++] = LINE_SEPARATOR_CHAR;
    // }
    // }
    //
    // } else {
    // chs[count++] = str.charAt(i);
    // }
    // }
    //
    // if (count == sz) {
    // return str;
    // } else {
    // return new String(chs, 0, count);
    // }
    // }

    // public static String deleteUnwantedWhitespace(String str) {
    //
    // if (isEmpty(str)) {
    // return str;
    // }
    // int sz = str.length();
    // char chs[] = new char[sz];
    // int count = 0;
    // for (int i = 0; i < sz; i++) {
    //
    // if (Character.isWhitespace(str.charAt(i))) {
    // int next = i + 1;
    // if ((next < sz) && !Character.isWhitespace(str.charAt(next))) {
    //
    // if (!Character.isSpaceChar(str.charAt(i))) {
    //
    // chs[count++] = LINE_SEPARATOR_CHAR;
    // }
    // }
    //
    // } else {
    // chs[count++] = str.charAt(i);
    // }
    // }
    //
    // if (count == sz) {
    // return str;
    // } else {
    // return new String(chs, 0, count);
    // }
    // }

    // public static String deleteDoubleWhitespace(String str) {
    //
    // if (isEmpty(str)) {
    // return str;
    // }
    // int sz = str.length();
    // char chs[] = new char[sz];
    // int count = 0;
    // for (int i = 0; i < sz; i++) {
    // if (Character.isWhitespace(str.charAt(i))) {
    //
    // if (Character.isSpaceChar(str.charAt(i))
    // && !Character.isSpaceChar(str.charAt(i + 1))) {
    // chs[count++] = str.charAt(i);
    // }
    // } else {
    // chs[count++] = str.charAt(i);
    // }
    // }
    //
    // if (count == sz) {
    // return str;
    // } else {
    // return new String(chs, 0, count);
    // }
    // }

    public static boolean hasLength(CharSequence s) {

        return !isEmpty(s);
    }

    public static boolean hasLength(CharSequence s, boolean isTrim) {

        return !isEmpty(s, isTrim);
    }

    public static boolean hasLength(StringBuffer s) {

        return s != null && s.length() > 0;
    }

    public static boolean isEmpty(CharSequence s) {

        return s == null || s.length() == 0;
    }

    public static boolean isEmpty(CharSequence s, boolean isTrim) {

        if (s == null || s.length() == 0) {
            return true;
        }
        if (isTrim) {
            String str = StringUtils.trim(s.toString());
            return str == null || str.length() == 0;
        } else {
            return false;
        }
    }

    public static String substring(String str, String start, String[] ends) {

        for (String end : ends) {
            if (contains(str, end)) {
                str = substring(str, start, end);
                break;
            }
        }

        return str;

    }

    public static String substring(String str,
                                   String start,
                                   String[] ends,
                                   int maxLength) {

        for (String end : ends) {
            if (contains(str, end)) {
                str = substring(str, start, end);
                break;
            }
        }

        if (str.length() > maxLength) {
            str = str.substring(0, maxLength);
        }
        return str;

    }

    public static String substring(String str,
                                   String start,
                                   String end,
                                   int maxLength) {

        str = substringAfter(str, start);

        str = substringBefore(str, end);

        if (str.length() > maxLength) {
            str = str.substring(0, maxLength);
        }
        return str;

    }

    public static String substring(String str, String start, String end) {

        str = substringAfter(str, start);

        str = substringBefore(str, end);

        return str;

    }

    public static boolean equalsAny(String str1, String[] str2) {

        for (String str : str2) {
            if (equals(str1, str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalsAnyIgnoreCase(String str1, String[] str2) {

        for (String str : str2) {
            if (equalsIgnoreCase(str1, str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllCharacter(String str) {

        if (isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllPunct(String str) {

        if (isEmpty(str)) {
            return false;
        }
        return PatternUtils.ALL_PUNCT_PATTERN.matcher(str).matches();
    }

    public static boolean hasPunct(String str) {

        if (isEmpty(str)) {
            return false;
        }
        return PatternUtils.PUNCT_PATTERN.matcher(str).find();
    }

    public static boolean isEmptyOrNullStr(String str) {

        return isEmpty(str) || str.equals(NULL_STRING);
    }

    public static boolean hasChineseLetter(String input) {

        if (isEmpty(input)) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (CharacterUtils.isChineseLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasLetter(String input) {

        if (isEmpty(input)) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasDigit(String input) {

        if (isEmpty(input)) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasWhitespace(String input) {

        if (isEmpty(input)) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c) && !Character.isSpaceChar(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSpace(String input) {

        if (isEmpty(input)) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c) || Character.isSpaceChar(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasEngLetter(String input) {

        if (isEmpty(input)) {
            return false;
        }

        return PatternUtils.ENG_LETTER_PATTERN.matcher(input).find();
    }

    public static final int    STR_SHOW_MAX_LEN = 20;

    public static final String STR_DOT          = "...";

    public static String subStringForShowLength(String str) {

        return subStringForShowLength(str, STR_SHOW_MAX_LEN);
    }

    public static String subStringForShowLength(String str,
                                                int length,
                                                boolean showDotFlg) {

        try {
            if (StringUtils.hasLength(str, true) && length > 0) {
                str = str.trim();
                double len = 0;
                StringBuffer buffer = new StringBuffer();
                for (char ch : str.toCharArray()) {
                    if (CharacterUtils.isChineseLetter(ch)) {
                        len += 2;
                    } else {
                        len++;
                    }
                    buffer.append(ch);
                    if (len > length) {
                        if (showDotFlg) {
                            buffer.append(STR_DOT);
                        }
                        return buffer.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String subStringForShowLength(String str, int length) {

        return subStringForShowLength(str, length, false);
    }

    public static String subForShowLengthWithoutDot(String str, int length) {

        try {
            if (StringUtils.hasLength(str, true)) {
                str = str.trim();
                double len = 0;
                StringBuffer buffer = new StringBuffer();
                for (char ch : str.toCharArray()) {
                    if (CharacterUtils.isChineseLetter(ch)) {
                        len += 2;
                    } else {
                        len++;
                    }
                    if (len > length) {
                        return buffer.toString();
                    }
                    buffer.append(ch);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static final int ORACLE_STR_MAX_LEN = 2000;

    public static final int MYSQL_STR_MAX_LEN  = 40000;

    public static String subStringForDbLength(String str) {

        str = StringUtils.trim(StringUtils
                .subStringForDbLength(str, MYSQL_STR_MAX_LEN));

        return str;
    }

    public static String subStringForDbLength(String str, int maxLen) {

        try {
            if (StringUtils.hasLength(str, true)) {
                StringBuffer buffer = new StringBuffer(str);
                int len = buffer.toString()
                        .getBytes(Constants.DEFAULT_ENCODING).length;
                while (len > maxLen) {
                    buffer.deleteCharAt(buffer.length() - 1);
                    len = buffer.toString()
                            .getBytes(Constants.DEFAULT_ENCODING).length;
                }
                return buffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void printCaller() {

        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            StackTraceElement ste = stack[i];
            System.out.println(ste.getClassName() + "."
                               + ste.getMethodName()
                               + "("
                               + ste.getLineNumber()
                               + ");");

        }
    }

    public static boolean isEqual(String s1, String s2) {

        if (isEmpty(s1, true))
            return false;
        if (isEmpty(s2, true))
            return false;
        if (s1.trim().equals(s2.trim())) {
            return true;
        }
        return false;

    }

    public static final Pattern EMAIL_PATTERN = Pattern
                                                      .compile("^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    /**
     * 检查字符串是否为EMAIL地址
     */
    public static boolean isEmail(String email) {

        if (isEmpty(email)) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static final Pattern MOBILE_PHONE_PATTERN       = Pattern
                                                                   .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[0-9]))\\d{8}$");

    public static final Pattern PHONE_PATTERN              = Pattern
                                                                   .compile("^[1][0-9][0-9]{9}$");

    public static final Pattern CURRENT_2016_PHONE_PATTERN = Pattern
                                                                   .compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,1,6,7,8]))\\d{8}$");

    /**
     * 检查字符串是否为手机号码
     */
    public static boolean is_2016_MobilePhone(String mobilePhone) {

        if (isEmpty(mobilePhone)) {
            return false;
        }
        Matcher matcher = CURRENT_2016_PHONE_PATTERN.matcher(mobilePhone);
        return matcher.matches();
    }

    /**
     * 检查字符串是否为手机号码
     */
    public static boolean isMobilePhone(String mobilePhone) {

        if (isEmpty(mobilePhone)) {
            return false;
        }
        Matcher matcher = MOBILE_PHONE_PATTERN.matcher(mobilePhone);
        return matcher.matches();
    }

    /**
     * 检查字符串是否为手机号码11位1开头
     */
    public static boolean isPhone(String mobilePhone) {

        if (isEmpty(mobilePhone)) {
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(mobilePhone);
        return matcher.matches();
    }

    public static String substring(StringBuffer str, int start, int end) {

        if (str == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length()) {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * @param str
     * @param regex
     * @return
     * @author chssheng 创建日期 Aug 30, 2012 创建时间 11:10:59 AM function:
     *         str是否满足与正则表达式
     */
    public static final String REG_STRING_IS_NAME         = "^[\\u4e00-\\u9fa5]{2,}|^[a-zA-Z]*";

    public static final String REG_STRING_ID_NUMBER       = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";

    public static final String REG_STRING_SPACE_T         = " |　|\t";

    public static final String REG_STRING_TEL             = "1[3|4|5|8]\\d{9}";

    public static final String REG_DATE_YYYY_MM_DD_STRING = "[1|2]\\d{3}-(0[1-9]{1}|1[0-2]{1})-([1-2]{1}[0-9]{1}|0[1-9]{1}|3[0-1]{1})";

    public static final String REG_STRING_IS_ENGLISH      = "^[a-zA-Z]*";

    public static boolean isStringEnglish(String str) {

        return matchesReg(str, true, REG_STRING_IS_ENGLISH);
    }

    /**
     * @author chssheng 创建日期 2013-8-28 创建时间 下午4:24:47 function: 通过正则，匹配日期字符
     *         REG_DATE_YYYY_MM_DD_STRING
     */
    public static boolean isDatematchesReg(String str) {

        return matchesReg(str, true, REG_DATE_YYYY_MM_DD_STRING);
    }

    public static boolean matchesReg(String str,
                                     boolean isDeleteAllSpace,
                                     String regex) {

        boolean flg = false;
        if (StringUtils.hasLength(str)) {
            if (isDeleteAllSpace) {
                str = str.replaceAll(REG_STRING_SPACE_T, "");
            }
            Pattern pattern = Pattern.compile(regex.trim());
            if (pattern.matcher(str).matches()) {
                flg = true;
            }
        }
        return flg;
    }

    // 清除掉所有特殊字符
    public static String removeSpecString(String str, boolean isDeleteAllSpace)
            throws PatternSyntaxException {

        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        if (isDeleteAllSpace) {
            str = str.replaceAll(REG_STRING_SPACE_T, "");
        }
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String dbNametoXmlNodeName(String input) {

        StringBuffer buffer = new StringBuffer();
        char[] array = input.toCharArray();
        boolean isUpperCase = true;
        for (char ch : array) {
            if (ch >= 'a' && ch <= 'z') {
                if (isUpperCase) {
                    ch = Character.toUpperCase(ch);
                    isUpperCase = false;
                }
                buffer.append(ch);
            } else if (ch >= 'A' && ch <= 'Z') {
                if (!isUpperCase) {
                    ch = Character.toLowerCase(ch);
                }
                isUpperCase = false;
                buffer.append(ch);
            } else {
                isUpperCase = true;
            }
        }

        return buffer.toString();
    }

    public static String InputStreamToString(InputStream in, String encoding)
            throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int count = -1;
        while ((count = in.read(data, 0, 4096)) != -1)
            outStream.write(data, 0, count);

        data = null;
        if (!hasLength(encoding)) {
            encoding = Constants.DEFAULT_ENCODING;
        }
        return new String(outStream.toByteArray(), encoding);
    }

    public static boolean isHanzi(char c) {

        String regEx = "[\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(c + "");
        if (m.find())
            return true;
        return false;
    }

    /**
     * JSON字符串特殊字符处理，比如：“\A1;1300”
     * 
     * @param s
     * @return String
     */
    public static String string2Json(String s) {

        if (StringUtils.hasLength(s)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case 31:
                    sb.append("");
                    logger.error("特殊字符  31SPECIAL CHARACTER：" + s);
                    break;
                case 65535:
                    sb.append("");
                    logger.error("特殊字符 65535SPECIAL CHARACTER：" + s);
                    break;
                default:
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        return s;
    }

    /**
     * 去除xml字符串中多余的空白
     * 
     * @param s
     * @return
     */
    public static String replaceXmlExcessBlank(String s) {

        return s.replaceAll("\r|\n", StringUtils.EMPTY_SPACE)
                .replaceAll("\\s+|\t", StringUtils.EMPTY_SPACE)
                .replaceAll("(?<=>)(\\s+|\r|\n)(?=<)", StringUtils.EMPTY);
    }

    /**
     * 格式化接口xml标签
     * 
     * @param tag
     * @return
     */
    public static String formatXmlTag(String tag) {

        if (StringUtils.hasLength(tag)) {
            String newTag = tag.replaceAll(PUNCT_REGEX, COMMA);
            List<String> list = StringUtils.splitForList(newTag, COMMA);
            if (CollectionUtils.notEmpty(list)) {
                StringBuffer buffer = new StringBuffer();
                for (String str : list) {
                    buffer.append(str.substring(0, 1).toUpperCase())
                            .append(str.substring(1));
                }
                tag = buffer.toString();
            }
        }
        return tag;
    }

    // public static void main(String[] args) throws Exception {
    //
    // String s = "aaaa";
    // change(s);
    //
    // WordExtractor extractor = new WordExtractor(new FileInputStream(
    // new File("E:/temp/简历.doc")));
    // StringBuffer buffer = new StringBuffer();
    // String paragraphText[] = extractor.getParagraphText();
    //
    // System.out.println("format(InputStream in=" + extractor.getText()
    // + ") - start");
    //
    // // StringBuffer buff = new StringBuffer("abc");
    // // // java.lang.StringBuffer类的reverse()方法可以将字符串反转
    // // System.out.println(buff.reverse().toString());
    // //
    // // String QJstr = "[ ][ ]ａｂｄ全角转，。；：半角ＤＡＯ";
    // //
    // // String result = toSBC(QJstr);
    // //
    // // System.out.println(QJstr + "\n" + result);
    //
    // }
    //
    // public static void main(String[] args) {
    //
    // // String s = toDBC("￥……·、");
    // // char[] a = s.toCharArray();
    // // for (char c : a) {
    // // System.out.println(c + ":" + (int) c);
    // // }
    // String s1 = "～！@#%&×（）—+-=【】『』|；‘：“，。/《》？";
    // // String s2 = toDBC("～！@#%&×（）—+-=【】『』|；‘：“，。/《》？");
    // String s3 = "~!@#%&*()-+-=[]{}|;':\",./<>?";
    // char a1[] = s1.toCharArray();
    // // char a2[] = s2.toCharArray();
    // char a3[] = s3.toCharArray();
    //
    // int size = a1.length;
    // for (int i = 0; i < size; i++) {
    // if (a1[i] != a3[i]) {
    // System.out.println(a1[i] + ":" + (int) a1[i]);
    // }
    // }
    // // System.out.println(toDBC());
    //
    // }

    public static String formatStrToJavaRuleName(String s) {

        String name = formatXmlTag(s);
        return new StringBuffer(name.substring(0, 1).toLowerCase())
                .append(name.substring(1)).toString();
    }

    /**
     * 折分字符串中的ID，并放入集合中
     * 
     * @param idsSet
     *            Set集合，存储id的集合, 会一直增加
     * @param idsString
     *            字符串，由多个ID组成，每个ID使用“,”分隔
     */
    public static Set<Integer> splicStringAppendSet(Set<Integer> idsSet,
                                                    String idsString) {

        if (StringUtils.hasLength(idsString)) {
            String[] array = idsString.trim().split(StringUtils.COMMA);
            if (array.length > 0) {
                for (String proIdStr : array) {
                    if (StringUtils.hasLength(proIdStr.trim()) && StringUtils
                                .isNumeric(proIdStr)) {
                        idsSet.add(Integer.parseInt(proIdStr));
                    }
                }
            }
        }

        return idsSet;
    }

    // public static void main(String[] args) {
    //
    // // System.out.println(replaceXmlExcessBlank("> \n  <"));
    // // System.out.println(formatStrToJavaRuleName("Org/Dept"));
    // System.out.println(formatXmlTag("ExpectedSalary(before tax)"));
    // }

    public static final String regex_Site   = "(http://|ftp://|https://){1,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*";

    public static final String target_Blank = "_Blank";

    public static final String target_Self  = "_Self";

    /**
     * 判断备注中网址并显示
     * 
     * @param regex
     *            正则表达式
     * @param str
     *            备注文本
     * @return
     */
    public static String matchUrl(String regex, String str, String target) {

        StringBuffer sb = new StringBuffer();
        String[] strs = str.split(regex);
        Integer indexLen = strs.length;
        if (indexLen != 0) {
            Integer indexFir = str.indexOf(strs[0]);
            Integer indexEnd = 0;
            String indexStr = "";
            if (indexFir != 0) {
                indexStr = str.substring(0, indexFir);
                str = str.substring(indexFir + strs[0].length());
                sb.append("<a href=\"" + indexStr
                          + "\"target=\""
                          + target
                          + "\">");
                sb.append(indexStr);
                sb.append("</a>");
            }

            for (int i = 0; i < indexLen - 1; i++) {
                indexFir = str.indexOf(strs[i]);
                str = str.substring(indexFir + strs[i].length());
                indexEnd = str.indexOf(strs[i + 1]);
                indexStr = str.substring(0, indexEnd);
                sb.append(strs[i]);
                sb.append("<a href=\"" + indexStr
                          + "\"target=\""
                          + target
                          + "\">");
                sb.append(indexStr);
                sb.append("</a>");
            }
            indexFir = str.indexOf(strs[indexLen - 1]);
            str = str.substring(indexFir + strs[indexLen - 1].length());
            indexStr = str;
            sb.append(strs[strs.length - 1]);
            if (StringUtils.isNotEmpty(indexStr)) {
                sb.append("<a href=\"" + indexStr
                          + "\"target=\""
                          + target
                          + "\">");
                sb.append(indexStr);
                sb.append("</a>");
            }
        } else {
            sb.append("<a href=\"" + str + "\"target=\"" + target + "\">");
            sb.append(str);
            sb.append("</a>");
        }
        return sb.toString();
    }

    public static List<String> toArrayList(String s1) {

        List<String> idList = new ArrayList<String>();
        if (StringUtils.hasLength(s1, true)) {
            String[] idAry = s1.split(",");
            for (String id : idAry) {
                if (StringUtils.hasLength(id, true)) {
                    idList.add(id.trim());
                }
            }
        }
        return idList;
    }

    // 截取数字
    public static int getNumbers(String content) {

        if (content == null) {
            return 0;
        }
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return Integer.parseInt(matcher.group(0));
        }
        return 0;
    }

    /**
     * 给定字符串进行增加
     * 
     * @param content
     * @param preview
     * @param numSize
     * @return
     */
    public static String addNumberForString(String content,
                                            String preview,
                                            int numSize) {

        StringBuffer buffer = new StringBuffer(preview);
        Integer nubmer = getNumbers(content) + 1;// 取出数字部分
        for (int i = nubmer.toString().length(); i < numSize; i++) {
            buffer.append("0");
        }
        buffer.append(nubmer);

        return buffer.toString();
    }

    private final static String  bracesRegxp   = "\\{([^\\}]*)\\}";           // 过滤所有以'{'开头，以'}'结尾的内容

    private final static Pattern bracesPattern = Pattern.compile(bracesRegxp);

    public static String replaceLog(String str) {

        if (str == null) {
            return null;
        }
        Matcher matcher = bracesPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    // 非负整数
    private final static String nonNegativeNumericPattern = "^\\d+$";

    /**
     * 判断字符串是否为非负整数
     * 
     * @param number
     * @return
     */
    public static boolean validateNonNegativeNumeric(String number) {

        return validateRegularExpression(nonNegativeNumericPattern, number);
    }

    // 非负数
    private final static String nonNegativeNumberPattern = "^\\d+(\\.{0,1}\\d+){0,1}$";

    /**
     * 判断字符串是否为非负数
     * 
     * @param number
     * @return
     */
    public static boolean validateNonNegativeNumber(String number) {

        return validateRegularExpression(nonNegativeNumberPattern, number);
    }

    // 正整数
    private final static String positiveNumberPattern = "^[0-9]*[1-9][0-9]*$";

    /**
     * 判断是否正整数
     * 
     * @param number
     * @return
     */
    public static boolean validatePositiveNumber(String number) {

        return validateRegularExpression(positiveNumberPattern, number);
    }

    /**
     * 正则校验
     * 
     * @param pattern
     * @param params
     * @return
     */
    public static boolean validateRegularExpression(String pattern,
                                                    String params) {

        // 对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(params);
        // System.out.println("---->>>>> validate istrue--:" + m.matches());
        return m.matches();
    }

    /**
     * 保留小数点后N位
     * 
     * @param value
     * @param num
     * @return
     */
    public static String decimalNum(String value, int num) {

        // NUM 小于等于零是默认返回整数部分
        String zeros = "0";
        if (num > 0) {
            zeros += ".";
            for (int i = 0; i < num; i++) {
                zeros += "0";
            }
        }
        DecimalFormat format = new DecimalFormat(zeros);
        return format.format(new BigDecimal(value));
    }
}
