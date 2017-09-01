
package com.dayee.writtenExam.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

    private static final Logger  logger                    = LoggerFactory
                                                                   .getLogger(DateUtils.class);

    public static final String   SPLIT_STR                 = "-";

    public static final String[] CHINESE_YMD_STR           = { "年", "月", "日",
            "－", "、"                                      };

    public static final String[] CHINESE_YMD_REPLACE_CHAR  = { SPLIT_STR,
            SPLIT_STR, SPLIT_STR, SPLIT_STR, SPLIT_STR    };

    public static final String[] ENGLISH_M_STR             = { "january",
            "february", "march", "april", "may", "june", "july", "august",
            "september", "october", "november", "december", "jan.", "feb.",
            "mar.", "apr.", "jun.", "jul.", "aug.", "sep.", "oct.", "nov.",
            "dec.", "jan", "feb", "mar", "apr", "jun", "jul", "aug", "sep",
            "oct", "nov", "dec"                           };

    public static final String[] ENGLISH_M_REPLACE_CHAR    = { "-01-", "-02-",
            "-03-", "-04-", "-05-", "-06-", "-07-", "-08-", "-09-", "-10-",
            "-11-", "-12-", "-01-", "-02-", "-03-", "-04-", "-06-", "-07-",
            "-08-", "-09-", "-10-", "-11-", "-12-", "-01-", "-02-", "-03-",
            "-04-", "-06-", "-07-", "-08-", "-09-", "-10-", "-11-", "-12-" };

    // public static final String[] CHINESE_NOW_DATE = { "现在", "至今",
    // "Present", "Now", "今", "Today", "当前" };

    public static final Pattern  NOW_DATE_PATTERN          = Pattern
                                                                   .compile("现\\s*在|至\\s*今|当\\s*前|目\\s*前|今|Present|Today|Now",
                                                                            Pattern.CASE_INSENSITIVE);

    public static final char     SPLIT_CHAR                = '-';

    public static final String   DATE_FORMAT_DMY           = "dd/MM/yyyy";

    // 默认的日期显示格式
    public static final String   DATE_FORMAT_YMD           = "yyyy-MM-dd";

    public static final String   DATE_FORMAT_YM            = "yyyy-MM";

    public static final String   DATE_FORMAT_Y             = "yyyy";

    public static final String   DATE_FORMAT_YMDHMS        = "yyyy-MM-dd HH:mm:ss";

    public static final String   DATE_FORMAT_YMDHM         = "yyyy-MM-dd HH:mm";

    public static final String   DATE_FORMAT_MDHM          = "MM-dd HH:mm";

    public static final String   DATE_NUMBER_FORMAT_YMD    = "yyyyMMdd";

    public static final String   DATE_NUMBER_FORMAT_YMDHMS = "yyyyMMddHHmmssSS";

    public static final String   DATE_NUMBER_FORMAT_YM     = "yyyyMM";

    public static final String   DATE_NUMBER_FORMAT_Y      = "yyyy";

    public static final String   DATE_FORMAT_YMD_2         = "yyyy.MM.dd HH:mm:ss";

    public static final String   DATE_NUMBER_FORMAT_YMDH   = "yyyyMMddHH";

    public static final String   DATE_NUMBER_FORMAT_YMDHM  = "yyyyMMddHHmm";

    public static final String   DATE_FORMAT_YMDHMS_SS     = "yyyy-MM-dd HH:mm:ss SS";

    /**
     * 系统中至今代表的日期
     */
    public static final Date     SO_FAR_DAY                = createDate(1000,
                                                                        1, 1,
                                                                        0, 0,
                                                                        0, 0);

    // public static final String[][] DATE_FORMAT = {
    // { "(19|20)\\d{2}-\\d{2}-\\d{2}", "yyyy-MM-dd" },
    // { "(19|20)\\d{2}-\\d{2}-\\d{1}", "yy-MM-d" },
    // { "(19|20)\\d{2}-\\d{1}-\\d{2}", "yyyy-M-dd" },
    // { "(19|20)\\d{2}-\\d{1}-\\d{1}", "yyyy-M-d" },
    // { "\\d{2}-\\d{2}-\\d{2}", "yy-MM-dd" },
    // { "\\d{2}-\\d{2}-\\d{1}", "yy-MM-d" },
    // { "\\d{2}-\\d{1}-\\d{2}", "yy-M-dd" },
    // { "\\d{2}-\\d{1}-\\d{1}", "yy-M-d" } };

    /**
     * 日期比较 （24小时格式） 如果 大于0 则开始小于结束， 等于0 则开始等于结束，否则，开始大于结束
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static int compareDate(String startTime, String endTime) {

        return startTime.compareTo(endTime);
    }

    public static Date getNowDate() {

        return getNowDate(DATE_FORMAT_YMD);
    }

    public static Date getNowDate(String format) {

        try {
            return charToDate(DateUtils.format(new Date(), format), format);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static int getAge(Date birthDay) {

        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        // int monthNow = cal.get(Calendar.MONTH);
        // int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        // int monthBirth = cal.get(Calendar.MONTH);
        // int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        // ---------by hzp 2014.8.20 年龄不按天算，只按年算
        // if (monthNow <= monthBirth) {
        // if (monthNow == monthBirth) {
        // // monthNow==monthBirth
        // if (dayOfMonthNow < dayOfMonthBirth) {
        // age--;
        // } else {
        // // do nothing
        // }
        // } else {
        // // monthNow>monthBirth
        // age--;
        // }
        // } else {
        // // monthNow<monthBirth
        // // donothing
        // }
        // age += 1;
        return age;
    }

    /**
     * 解析简历内容时判读是否为日期型
     * 
     * @param str
     * @return
     */
    public static boolean isDate(String str) {

        str = str.trim();

        return NOW_DATE_PATTERN.matcher(str).matches() || DATE_PATTERN_1
                       .matcher(str).matches()
               || DATE_PATTERN_2.matcher(str).matches()
               || DATE_PATTERN_3.matcher(str).matches()
               || DATE_PATTERN_4.matcher(str).matches()
               || DATE_PATTERN_5.matcher(str).matches()
               || DATE_PATTERN_6.matcher(str).matches()
               || DATE_PATTERN_7.matcher(str).matches()
               || DATE_PATTERN_8.matcher(str).matches()
               || DATE_PATTERN_9.matcher(str).matches()
               || DATE_PATTERN_10.matcher(str).matches()
               || DATE_PATTERN_11.matcher(str).matches()
               || DATE_PATTERN_12.matcher(str).matches()
               || DATE_PATTERN_13.matcher(str).matches()
               || DATE_PATTERN_14.matcher(str).matches()
               || DATE_PATTERN_15.matcher(str).matches();
    }

    public static boolean isToDay(Date date) {

        String now = DateUtils.format(new Date(), DATE_FORMAT_YMD);
        String datestr = DateUtils.format(date, DATE_FORMAT_YMD);
        return now.equals(datestr);
    }

    // public static void main(String[] args) throws Exception {
    //
    // testParseDate();
    // }

    // public static void testParseDate() throws Exception {
    //
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("2010-10-10"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("2010/10/10"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("2010、10、10"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("2010.10.10"));
    //
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("2010-10"));
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("2010/10"));
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("2010、10"));
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("2010.10"));
    //
    // Assert.assertEquals(charToDate("2010", DATE_FORMAT_Y),
    // parseDate("2010"));
    //
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("2010年10月10日"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("2010年-10月-10日"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("2010年/10月/10日"));
    //
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("2010年10月"));
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("2010年-10月"));
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("2010年/10月"));
    //
    // Assert.assertEquals(charToDate("2010", DATE_FORMAT_Y),
    // parseDate("2010年"));
    //
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("10/10/2010"));
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("10/2010"));
    //
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("10 October 2010"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("10 Oct. 2010"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("10 Oct 2010"));
    //
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("10-October-2010"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("10-Oct.-2010"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("10-Oct-2010"));
    //
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("10/October/2010"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("10/Oct./2010"));
    // Assert.assertEquals(charToDate("2010-10-10", DATE_FORMAT_YMD),
    // parseDate("10/Oct/2010"));
    //
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("October 2010"));
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("Oct. 2010"));
    // Assert.assertEquals(charToDate("2010-10", DATE_FORMAT_YM),
    // parseDate("Oct 2010"));
    // }

    private static final Pattern DATE_PATTERN_1  = Pattern
                                                         .compile("(19|20)\\d{2}\\s*年?\\s*(\\p{Punct})\\s*(1[0-2]|0?[1-9])\\s*月?\\s*\\2\\s*(3[0-1]|[1-2][0-9]|0?[1-9])\\s*日?");

    private static final Pattern DATE_PATTERN_2  = Pattern
                                                         .compile("(19|20)\\d{2}\\s*年\\s*(1[0-2]|0?[1-9])\\s*月\\s*(3[0-1]|[1-2][0-9]|0?[1-9])\\s*日?");

    private static final Pattern DATE_PATTERN_3  = Pattern
                                                         .compile("(19|20)\\d{2}\\s*年?\\s*\\p{Punct}\\s*(1[0-2]|0?[1-9])\\s*月?");

    private static final Pattern DATE_PATTERN_4  = Pattern
                                                         .compile("((19|20)\\d{2}\\s*年\\s*(1[0-2]|0?[1-9])\\s*月?)");

    private static final Pattern DATE_PATTERN_5  = Pattern
                                                         .compile("(19|20)\\d{2}\\s*年?");

    private static final Pattern DATE_PATTERN_6  = Pattern
                                                         .compile("(3[0-1]|[1-2][0-9]|0?[1-9])\\s*(\\p{Punct})\\s*(1[0-2]|0?[1-9])\\s*\\2\\s*(19|20)\\d{2}");

    private static final Pattern DATE_PATTERN_7  = Pattern
                                                         .compile("(1[0-2]|0?[1-9])\\s*\\p{Punct}\\s*(19|20)\\d{2}");

    private static final Pattern DATE_PATTERN_8  = Pattern
                                                         .compile("(3[0-1]|[1-2][0-9]|0?[1-9])\\s+(January|February|March|April|May|June|July|August|September|October|November|December|(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\.?)\\s+(19|20)\\d{2}");

    private static final Pattern DATE_PATTERN_9  = Pattern
                                                         .compile("(3[0-1]|[1-2][0-9]|0?[1-9])\\s*(\\p{Punct})\\s*(January|February|March|April|May|June|July|August|September|October|November|December|(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\.?)\\s*\\2\\s*(19|20)\\d{2}");

    private static final Pattern DATE_PATTERN_10 = Pattern
                                                         .compile("(January|February|March|April|May|June|July|August|September|October|November|December|(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\.?)\\s+([Oo][Ff]\\s+)?(19|20)\\d{2}");

    private static final Pattern DATE_PATTERN_11 = Pattern
                                                         .compile("(January|February|March|April|May|June|July|August|September|October|November|December|(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\.?)\\s*\\p{Punct}\\s*(19|20)\\d{2}");

    private static final Pattern DATE_PATTERN_12 = Pattern
                                                         .compile("(\\d{2})\\s*\\p{Punct}\\s*(1[0-2]|0?[1-9])");

    private static final Pattern DATE_PATTERN_13 = Pattern
                                                         .compile("(19|20)\\d{2}\\s*年?\\s*(\\p{Punct})\\s*(1[0-2]|0?[1-9])\\s*月?\\s*\\2\\s*(3[0-1]|[1-2][0-9]|0?[1-9])\\s*日?\\s+\\d{2}\\p{Punct}\\d{2}");

    private static final Pattern DATE_PATTERN_14 = Pattern
                                                         .compile("(19|20)\\d{2}\\s*年?\\s*(\\p{Punct})\\s*(1[0-2]|0?[1-9])\\s*月?\\s*\\2\\s*(3[0-1]|[1-2][0-9]|0?[1-9])\\s*日?\\s+\\d{2}\\p{Punct}\\d{2}\\p{Punct}\\d{2}");

    private static final Pattern DATE_PATTERN_15 = Pattern
                                                         .compile("(19|20)\\d{2}\\s*年?\\s*(\\p{Punct})\\s*(1[0-2]|0?[1-9])\\s*月?\\s*\\2\\s*(3[0-1]|[1-2][0-9]|0?[1-9])\\s*日?\\s+\\d{2}\\p{Punct}\\d{2}\\p{Punct}\\d{2}\\p{Punct}\\d{1}");

    private static final String  Punct           = "\\p{Punct}{1,}|[Oo][Ff]";

    
    public static Date parseDate(String str,DateFormat format){
        
        if(StringUtils.isBlank(str) || format== null){
            return  null;
        }
        Date date=null;
        
        try {
            date=format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 将字符串转成Date型
     * 
     * @param str
     * @return
     * @throws Exception
     */
    public static Date parseDate(String str) throws ParseException {

        if (StringUtils.isEmpty(str)) {
            return null;
        }
        str = StringUtils.deleteWhitespace(str);

        if (NOW_DATE_PATTERN.matcher(str).matches()) {
            return null;
        }

        str = str.toLowerCase();
        str = StringUtils.replaceEach(str, CHINESE_YMD_STR,
                                      CHINESE_YMD_REPLACE_CHAR);
        str = StringUtils.replaceEach(str, ENGLISH_M_STR,
                                      ENGLISH_M_REPLACE_CHAR);

        str = StringUtils.deleteWhitespace(str);

        str = str.replaceAll(Punct, SPLIT_STR);

        str = cleanup(str);

        DateFormat format = null;
        if (DATE_PATTERN_14.matcher(str).matches()) {

            format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        } else if (DATE_PATTERN_13.matcher(str).matches()) {

            format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        } else if (DATE_PATTERN_1.matcher(str).matches() || DATE_PATTERN_2
                           .matcher(str).matches()) {

            format = new SimpleDateFormat(DATE_FORMAT_YMD);
        } else if (DATE_PATTERN_3.matcher(str).matches() || DATE_PATTERN_4
                           .matcher(str).matches()) {

            format = new SimpleDateFormat(DATE_FORMAT_YM);
        } else if (DATE_PATTERN_5.matcher(str).matches()) {

            format = new SimpleDateFormat(DATE_FORMAT_Y);
        } else if (DATE_PATTERN_6.matcher(str).matches()) {

            format = new SimpleDateFormat("dd-MM-yyyy");
        } else if (DATE_PATTERN_7.matcher(str).matches()) {

            format = new SimpleDateFormat("MM-yyyy");
        } else if (DATE_PATTERN_8.matcher(str).matches()) {

            format = new SimpleDateFormat("dd MM yyyy");
        } else if (DATE_PATTERN_9.matcher(str).matches()) {

            format = new SimpleDateFormat("dd-MM-yyyy");
        } else if (DATE_PATTERN_10.matcher(str).matches()) {

            format = new SimpleDateFormat("MM yyyy");
        } else if (DATE_PATTERN_11.matcher(str).matches()) {

            format = new SimpleDateFormat("MM-yyyy");
        } else if (DATE_PATTERN_12.matcher(str).matches()) {

            format = new SimpleDateFormat("yy-MM");
        }

        return format != null ? format.parse(str) : null;
    }

    public static boolean isValidDate(String str) throws Exception {

        if (StringUtils.isEmpty(str)) {
            return false;
        }
        str = StringUtils.deleteWhitespace(str);

        if (NOW_DATE_PATTERN.matcher(str).matches()) {
            return true;
        }

        str = str.toLowerCase();
        str = StringUtils.replaceEach(str, CHINESE_YMD_STR,
                                      CHINESE_YMD_REPLACE_CHAR);
        str = StringUtils.replaceEach(str, ENGLISH_M_STR,
                                      ENGLISH_M_REPLACE_CHAR);

        str = StringUtils.deleteWhitespace(str);

        str = str.replaceAll(Punct, SPLIT_STR);

        str = cleanup(str);

        if (DATE_PATTERN_14.matcher(str).matches()) {

            return true;
        } else if (DATE_PATTERN_13.matcher(str).matches()) {

            return true;
        } else if (DATE_PATTERN_1.matcher(str).matches() || DATE_PATTERN_2
                           .matcher(str).matches()) {

            return true;
        } else if (DATE_PATTERN_3.matcher(str).matches() || DATE_PATTERN_4
                           .matcher(str).matches()) {

            return true;
        } else if (DATE_PATTERN_5.matcher(str).matches()) {

            return false;
        } else if (DATE_PATTERN_6.matcher(str).matches()) {

            return true;
        } else if (DATE_PATTERN_7.matcher(str).matches()) {

            return true;
        } else if (DATE_PATTERN_8.matcher(str).matches()) {

            return true;
        } else if (DATE_PATTERN_9.matcher(str).matches()) {

            return true;
        } else if (DATE_PATTERN_10.matcher(str).matches()) {

            return true;
        } else if (DATE_PATTERN_11.matcher(str).matches()) {

            return true;
        } else if (DATE_PATTERN_12.matcher(str).matches()) {
            return true;
        }

        return false;
    }

    public static Date parseXlsDate(String str) throws Exception {

        if (StringUtils.isEmpty(str)) {
            return null;
        }

        str = str.replaceAll(Punct, SPLIT_STR);

        DateFormat format = null;
        if (DATE_PATTERN_14.matcher(str).matches()) {

            format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        } else if (DATE_PATTERN_13.matcher(str).matches()) {

            format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        } else if (DATE_PATTERN_1.matcher(str).matches() || DATE_PATTERN_2
                           .matcher(str).matches()) {

            format = new SimpleDateFormat(DATE_FORMAT_YMD);
        } else if (DATE_PATTERN_3.matcher(str).matches() || DATE_PATTERN_4
                           .matcher(str).matches()) {

            format = new SimpleDateFormat(DATE_FORMAT_YM);
        } else if (DATE_PATTERN_5.matcher(str).matches()) {

            format = new SimpleDateFormat(DATE_FORMAT_Y);
        } else if (DATE_PATTERN_6.matcher(str).matches()) {

            format = new SimpleDateFormat("dd-MM-yyyy");
        } else if (DATE_PATTERN_7.matcher(str).matches()) {

            format = new SimpleDateFormat("MM-yyyy");
        } else if (DATE_PATTERN_8.matcher(str).matches()) {

            format = new SimpleDateFormat("dd MM yyyy");
        } else if (DATE_PATTERN_9.matcher(str).matches()) {

            format = new SimpleDateFormat("dd-MM-yyyy");
        } else if (DATE_PATTERN_10.matcher(str).matches()) {

            format = new SimpleDateFormat("MM yyyy");
        } else if (DATE_PATTERN_11.matcher(str).matches()) {

            format = new SimpleDateFormat("MM-yyyy");
        } else if (DATE_PATTERN_12.matcher(str).matches()) {

            format = new SimpleDateFormat("yy-MM");
        }

        return format != null ? format.parse(str) : null;
    }

    public static Date charToDate(String dateStr, String format)
            throws Exception {

        Date dasql = null;
        if (StringUtils.isEmpty(dateStr)) {
            return dasql;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        dasql = sdf.parse(dateStr);

        return dasql;
    }

    // 获取转换过来的字符串
    public static String replaceYear(String webCorpCopyRight, String subStr) {

        // 此处对subStr进行替换
        if (StringUtils.hasLength(webCorpCopyRight) && webCorpCopyRight
                    .contains(subStr)) {
            webCorpCopyRight = webCorpCopyRight.replace(subStr, DateUtils
                    .dateToChar(new Date(), DateUtils.DATE_FORMAT_Y));
        }
        return webCorpCopyRight;
    }

    public static String dateToChar(Date dateIn, String format) {

        if (dateIn == null || format == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        String dateStr = sdf.format(dateIn);
        return dateStr;
    }

    private static String cleanup(String str) {

        if (StringUtils.isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char chs[] = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {

            if (Character.isDigit(str.charAt(i))) {

                chs[count++] = str.charAt(i);
            } else if (str.charAt(i) == DateUtils.SPLIT_CHAR) {

                chs[count++] = str.charAt(i);
            }
        }

        int end = count - 1;
        if (end > 0 && chs[end] == DateUtils.SPLIT_CHAR) {

            count--;
        }

        if (count != sz) {
            str = new String(chs, 0, count);
        }

        while (str.startsWith(DateUtils.SPLIT_STR)) {
            str = str.substring(DateUtils.SPLIT_STR.length());
        }
        while (str.endsWith(DateUtils.SPLIT_STR)) {
            str = str.substring(0,
                                (str.length() - DateUtils.SPLIT_STR.length()));
        }

        return str;
    }

    public static String format(Date date) {

        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YMD);
        return format.format(date);
    }

    public static String formatYMDHMS(Date date) {

        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        return format.format(date);
    }

    public static String format(Date date, String patten) {

        if (date == null) {
            return null;
        }
        if (StringUtils.isEmpty(patten)) {
            patten = DATE_FORMAT_YMD;
        }
        SimpleDateFormat format = new SimpleDateFormat(patten);
        return format.format(date);
    }

    /**
     * 两日期之间的天数
     * 
     * @param sDate
     * @param bDate
     * @return
     */
    public static int getDayBetweenTwoDate(Date sDate, Date bDate) {

        if (sDate != null && bDate != null) {
            long sTime = sDate.getTime();
            long bTime = bDate.getTime();
            long l = bTime - sTime;
            int day = (int) (l / (1000 * 3600 * 24));

            return day;
        }
        return 0;
    }

    /**
     * 两日期之间的小时数
     * 
     * @param sDate
     * @param bDate
     * @return
     */
    public static int getHoursBetweenTwoDate(Date sDate, Date bDate) {

        if (sDate != null && bDate != null) {
            long sTime = sDate.getTime();
            long bTime = bDate.getTime();
            long l = bTime - sTime;
            int day = (int) (l / (1000 * 3600));

            return day;
        }
        return 0;
    }

    /**
     * 两日期之间的分钟数
     * 
     * @param sDate
     * @param bDate
     * @return
     */
    public static int getMinutes(Date sDate, Date bDate) {

        if (sDate != null && bDate != null) {
            long sTime = sDate.getTime();
            long bTime = bDate.getTime();
            long l = bTime - sTime;
            int minute = (int) (l / (1000 * 60));

            return minute;
        }
        return 0;
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {

        SimpleDateFormat myFormatter = new SimpleDateFormat(DATE_FORMAT_YMD);
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
        return String.valueOf(day);
    }

    private static final String EEEE = "EEEE";

    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {

        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat(EEEE).format(c.getTime());
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param sdate
     * @return
     */
    public static String getWeek(Date date) {

        // 再转换为时间
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat(EEEE).format(c.getTime());
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     * 
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YMD);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 两个时间之间的天数
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {

        if (StringUtils.isEmpty(date1)) {
            return 0;
        }
        if (StringUtils.isEmpty(date2)) {
            return 0;
        }
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat(DATE_FORMAT_YMD);
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    public static int getMonths(Date date1, Date date2) {

        int iMonth = 0;
        int flag = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(date1);

            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(date2);

            if (objCalendarDate2.equals(objCalendarDate1))
                return 0;
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
                    .get(Calendar.DAY_OF_MONTH))
                flag = 1;

            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
                    .get(Calendar.YEAR)) {
                iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
                        .get(Calendar.YEAR)) * 12
                          + objCalendarDate2.get(Calendar.MONTH) - flag) - objCalendarDate1
                                 .get(Calendar.MONTH);
            } else {
                iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1
                                 .get(Calendar.MONTH) - flag;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

    // 计算当月最后一天,返回字符串

    public static Date getLastDayOfMonth() {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        lastDate.set(Calendar.HOUR_OF_DAY, 0);
        lastDate.set(Calendar.MINUTE, 0);
        lastDate.set(Calendar.SECOND, 0);
        lastDate.set(Calendar.MILLISECOND, 0);
        return lastDate.getTime();
    }

    // 计算当月最后一天,返回字符串
    public static String getLastDayOfMonthStr() {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD);

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        String str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获取当月第一天
    public static String getFirstDayOfMonth() {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD);

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        String str = sdf.format(lastDate.getTime());
        return str;
    }

    // 计算当月最后一天,返回字符串
    public static String getLastDayOfLastMonthStr() {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD);

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        String str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获取当月第一天
    public static String getFirstDayOfLastMonth() {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD);

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 加一个月，变为下月的1号
        String str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获取当月第一天
    public static Date getFirstDateOfMonth() {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.set(Calendar.HOUR_OF_DAY, 0);
        lastDate.set(Calendar.MINUTE, 0);
        lastDate.set(Calendar.SECOND, 0);
        lastDate.set(Calendar.MILLISECOND, 0);
        return lastDate.getTime();
    }

    // 获取当年的第一天
    public static Date getFirstDateOfYear() {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.set(Calendar.MONTH, 0);
        lastDate.set(Calendar.HOUR_OF_DAY, 0);
        lastDate.set(Calendar.MINUTE, 0);
        lastDate.set(Calendar.SECOND, 0);
        lastDate.set(Calendar.MILLISECOND, 0);
        return lastDate.getTime();
    }

    public static Date getFirstDateOfYear(int year) {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.YEAR, year);
        lastDate.set(Calendar.MONTH, 0);
        lastDate.set(Calendar.DAY_OF_MONTH, 1);
        lastDate.set(Calendar.HOUR_OF_DAY, 0);
        lastDate.set(Calendar.MINUTE, 0);
        lastDate.set(Calendar.SECOND, 0);
        lastDate.set(Calendar.MILLISECOND, 0);
        return lastDate.getTime();
    }

    public static Date getEndDateOfYear(int year) {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.YEAR, year);
        lastDate.set(Calendar.MONTH, 11);
        lastDate.set(Calendar.DAY_OF_MONTH, 31);
        lastDate.set(Calendar.HOUR_OF_DAY, 23);
        lastDate.set(Calendar.MINUTE, 59);
        lastDate.set(Calendar.SECOND, 59);
        return lastDate.getTime();
    }

    public static Date getFirstDateOfMonth(int year, int month) {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.YEAR, year);
        month = month - 1;
        lastDate.set(Calendar.MONTH, month);
        lastDate.set(Calendar.DAY_OF_MONTH, 1);
        lastDate.set(Calendar.HOUR_OF_DAY, 0);
        lastDate.set(Calendar.MINUTE, 0);
        lastDate.set(Calendar.SECOND, 0);
        lastDate.set(Calendar.MILLISECOND, 0);
        return lastDate.getTime();
    }

    public static Date getEndDateOfMonth(int year, int month) {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.YEAR, year);
        // month = month - 1;
        lastDate.set(Calendar.MONTH, month);
        lastDate.set(Calendar.DAY_OF_MONTH, 1);
        lastDate.set(Calendar.HOUR_OF_DAY, 23);
        lastDate.set(Calendar.MINUTE, 59);
        lastDate.set(Calendar.SECOND, 59);
        lastDate.add(Calendar.DATE, -1);
        return lastDate.getTime();
    }

    public static Date getFirstDateOfQuarter(int year, int quarter) {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.YEAR, year);
        int firstMonth = 0;
        if (quarter == 1) {
            firstMonth = 1;
        } else if (quarter == 2) {
            firstMonth = 4;
        } else if (quarter == 3) {
            firstMonth = 7;
        } else if (quarter == 4) {
            firstMonth = 10;
        }
        firstMonth = firstMonth - 1;
        lastDate.set(Calendar.YEAR, year);
        lastDate.set(Calendar.MONTH, firstMonth);
        lastDate.set(Calendar.DAY_OF_MONTH, 1);
        lastDate.set(Calendar.HOUR_OF_DAY, 0);
        lastDate.set(Calendar.MINUTE, 0);
        lastDate.set(Calendar.SECOND, 0);
        lastDate.set(Calendar.MILLISECOND, 0);
        return lastDate.getTime();
    }

    public static Date getEndDateOfQuarter(int year, int quarter) {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.YEAR, year);
        int firstMonth = 0;
        if (quarter == 1) {
            firstMonth = 3;
        } else if (quarter == 2) {
            firstMonth = 6;
        } else if (quarter == 3) {
            firstMonth = 9;
        } else if (quarter == 4) {
            firstMonth = 12;
        }
        firstMonth = firstMonth - 1;
        lastDate.set(Calendar.YEAR, year);
        lastDate.set(Calendar.MONTH, firstMonth);
        lastDate.set(Calendar.DAY_OF_MONTH, 31);
        lastDate.set(Calendar.HOUR_OF_DAY, 23);
        lastDate.set(Calendar.MINUTE, 59);
        lastDate.set(Calendar.SECOND, 59);
        return lastDate.getTime();
    }

    // 获得当前日期与本周日相差的天数
    private static int getMondayPlus() {

        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    // 获得本周一的日期
    public static String getMondayOfWeek() {

        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得本周星期日的日期
    public static String getCurrentWeekDay() {

        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static boolean isPassNow(Date startDate, Integer month) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.MONTH, month);
        return cal.getTime().compareTo(new Date()) >= 0 ? true : false;

    }

    /**
     * 得到几天前的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {

        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    public static Date getDateBeforeYear(Date d, int year) {

        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) - year);
        return now.getTime();
    }

    public static Date getDateAfterYear(Date d, int year) {

        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
        return now.getTime();
    }

    public static Date getDateBeforeMonth(Date d, int month) {

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, -month);
        return now.getTime();
    }

    public static Date getDateAfterMonth(Date d, int month) {

        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {

        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static final String DATE_FORMAT_REPLY_MESSAGE_YMDHMS_DIAGONAL = "yyyy/M/d HH:mm:ss"; // 短信回复日期格式

    // 获取当年的第一天
    public static Date getFirstDateOfCurrentYear() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 0);
        int days = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, days);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String getFirstDateOfCurrentYearStr() {

        return format(getFirstDateOfCurrentYear(), DATE_FORMAT_YMD);
    }

    // 获取当年的最后一天
    public static Date getLastDateOfCurrentYear() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 11);
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, days);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String getLastDateOfCurrentYearStr() {

        return format(getLastDateOfCurrentYear(), DATE_FORMAT_YMD);
    }

    public static Date getFirstDateOfCurrentQuarter() {

        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);

        if (month >= 0 && month <= 2) {
            c.set(Calendar.MONTH, 0);
        } else if (month >= 3 && month <= 5) {
            c.set(Calendar.MONTH, 3);
        } else if (month >= 6 && month <= 8) {
            c.set(Calendar.MONTH, 6);
        } else if (month >= 9 && month <= 11) {
            c.set(Calendar.MONTH, 9);
        }
        int days = c.getActualMinimum(Calendar.DAY_OF_MONTH);

        c.set(Calendar.DAY_OF_MONTH, days);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String getFirstDateOfCurrentQuarterStr() {

        return format(getFirstDateOfCurrentQuarter(), DATE_FORMAT_YMD);
    }

    public static Date getEndDateOfCurrentQuarter() {

        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);

        if (month >= 0 && month <= 2) {
            c.set(Calendar.MONTH, 2);
        } else if (month >= 3 && month <= 5) {
            c.set(Calendar.MONTH, 5);
        } else if (month >= 6 && month <= 8) {
            c.set(Calendar.MONTH, 8);
        } else if (month >= 9 && month <= 11) {
            c.set(Calendar.MONTH, 11);
        }
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        c.set(Calendar.DAY_OF_MONTH, days);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String getEndDateOfCurrentQuarterStr() {

        return format(getEndDateOfCurrentQuarter(), DATE_FORMAT_YMD);
    }

    /**
     * 得到本月最后一天
     * 
     * @return
     */
    public static Date getLastDateOfMonth() {

        Calendar c = Calendar.getInstance();

        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, days);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String getLastDateOfMonthStr() {

        return format(getLastDateOfMonth(), DATE_FORMAT_YMD);
    }

    /**
     * 得到本月第一天
     * 
     * @return
     */
    public static Date getFristDateOfMonth() {

        Calendar c = Calendar.getInstance();
        int days = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, days);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    // 得到上个月最后一天
    public static String getLastDateOfLastMonth() {

        Calendar c = Calendar.getInstance();

        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, days - 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return format(c.getTime(), DATE_FORMAT_YMD);
    }

    // 得到上个月第一天
    public static String getFristDateOfLastMonth() {

        Calendar c = Calendar.getInstance();
        int days = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, days - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return format(c.getTime(), DATE_FORMAT_YMD);
    }

    public static String getFristDateOfMonthStr() {

        return format(getFristDateOfMonth(), DATE_FORMAT_YMD);
    }

    // 获得本周一的日期
    public static Date getMondayOfThisWeek() {

        Calendar c = Calendar.getInstance();

        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0) {
            dayofweek = 7;
        }
        c.add(Calendar.DATE, -dayofweek + 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    // 获得上周六的日期
    public static Date getSaturdayOfLastWeek() {

        Calendar c = Calendar.getInstance();

        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0) {
            dayofweek = 7;
        }
        c.add(Calendar.DATE, -dayofweek - 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String getMondayOfThisWeekStr() {

        return format(getMondayOfThisWeek(), DATE_FORMAT_YMD);
    }

    // 获得本周星期日的日期
    public static Date getSundayOfThisWeek() {

        Calendar c = Calendar.getInstance();

        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0) {
            dayofweek = 7;
        }
        c.add(Calendar.DATE, -dayofweek + 7);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    // 获得本周星期五的日期
    public static Date getFridayOfThisWeek() {

        Calendar c = Calendar.getInstance();

        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0) {
            dayofweek = 7;
        }
        c.add(Calendar.DATE, -dayofweek + 5);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String getSundayOfThisWeekStr() {

        return format(getSundayOfThisWeek(), DATE_FORMAT_YMD);
    }

    /**
     * ｛formatHMS格式为 yyyy-MM-dd 00:00:00｝
     * 
     * @param inDate
     * @param formatHMS
     * @return
     * @author
     * @zuo_xz
     * @created 2013-9-4 下午03:57:49
     * @lastModified
     * @history
     */
    public static Date getFormatHMSDate(Date inDate, String formatHMS) {

        if (formatHMS != null) {
            String strHMS = formatHMS.trim().replaceAll("/", "-");
            String ss = format(inDate, strHMS);
            try {
                Date outDate = new SimpleDateFormat(DATE_FORMAT_YMDHMS)
                        .parse(ss);
                return outDate;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 根据输入的年龄，得到出生年 add by lchang
     */
    public static Integer getBirthDay(int age) {

        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        Integer birthDayInt = yearNow - age + 1;
        return birthDayInt;
    }

    public static String getMaxBirthday(int age) {

        String beginDate = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -age);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        beginDate = DateUtils.format(cal.getTime());

        return beginDate;
    }

    public static String getMinBirthday(int age) {

        String endDate = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -(age - 1));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        endDate = DateUtils.format(cal.getTime());
        return endDate;

    }

    private static final String TIMESHOW_MINUTES             = "RESUME_SCORE_TIMESHOW_MINUTES";

    private static final String TIMESHOW_HOURS               = "RESUME_SCORE_TIMESHOW_HOURS";

    private static final String RESUME_SCORE_TIMESHOW_SECOND = "RESUME_SCORE_TIMESHOW_SECOND";

    public static Date createDate(int year,
                                  int month,
                                  int date,
                                  int hour,
                                  int minute,
                                  int second,
                                  int millisecond) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    // joda-time优化start
    /**
     * @param year
     * @param month
     * @param date
     * @return
     */
    @Deprecated
    public static Date createDate(int year, int month, int date) {

        return createDate(year, month, date, 0, 0, 0, 0);
    }

    /**
     * 获取所给日期前几天或后 几天
     * 
     * @param date
     * @param offsetDay
     *            正为后，负为前。
     * @deprecated 
     *             使用DateTime.plusDays(int).toDate()和DateTime.plusDays.miniusDays
     *             (int).toDate()替代
     * @return
     */
    @Deprecated
    public static Date getDayOffset(Date date, int offsetDay) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + offsetDay);
        return calendar.getTime();
    }

    /**
     * 获取今天0时0分0秒的Date对象
     * 
     * @deprecated 使用DateTime.now().withTimeAtStartOfDay().toDate()替代
     * @return
     */
    @Deprecated
    public static Date getToday() {

        return DateTime.now().withTimeAtStartOfDay().toDate();
        // Calendar calendar = Calendar.getInstance();
        // calendar.setTime(new Date());
        // return createDate(calendar.get(Calendar.YEAR),
        // calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
    }

    /**
     * 获取明天0时0分0秒的Date对象
     * 
     * @deprecated 
     *             使用DateTime.now().withTimeAtStartOfDay().plusDays(1).toDate()替代
     * @return
     */
    @Deprecated
    public static Date getTomorrow() {

        return DateTime.now().withTimeAtStartOfDay().plusDays(1).toDate();
        // return getDayOffset(getToday(), 1);
    }

    // joda-time优化end

    public static void main(String[] args) {

        System.out.println(DateFormatUtils.format(getTomorrow(),
                                                  DATE_FORMAT_YMD));
    
        try {
            System.out.println(parseDate("2016-10-25 13:49:18"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static int getMondayDate(Date beginDate) {

        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        cd.setTime(beginDate);
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    public static Date getMondayOfWeek(Date beginDate) {

        int mondayPlus = 0;
        if (beginDate != null) {
            mondayPlus = getMondayDate(beginDate);
        } else {
            mondayPlus = getMondayPlus();
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        if (beginDate != null) {
            currentDate.setTime(beginDate);
        }
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        return monday;
    }

    public static String getMondayOfLastWeek(Date beginDate) {

        int mondayPlus = 0;
        if (beginDate != null) {
            mondayPlus = getMondayDate(beginDate);
        } else {
            mondayPlus = getMondayPlus();
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        if (beginDate != null) {
            currentDate.setTime(beginDate);
        }
        currentDate.add(GregorianCalendar.DATE, mondayPlus - 7);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static Date getMondayDateOfLastWeek(Date beginDate) {

        int mondayPlus = 0;
        if (beginDate != null) {
            mondayPlus = getMondayDate(beginDate);
        } else {
            mondayPlus = getMondayPlus();
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        if (beginDate != null) {
            currentDate.setTime(beginDate);
        }
        currentDate.add(GregorianCalendar.DATE, mondayPlus - 7);
        Date monday = currentDate.getTime();
        return monday;
    }

    public static String getMondayOfLastFourWeek(Date beginDate) {

        int mondayPlus = 0;
        if (beginDate != null) {
            mondayPlus = getMondayDate(beginDate);
        } else {
            mondayPlus = getMondayPlus();
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        if (beginDate != null) {
            currentDate.setTime(beginDate);
        }
        currentDate.add(GregorianCalendar.DATE, mondayPlus - 14);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static Date getMondayOfLastFourWeekDate(Date beginDate) {

        int mondayPlus = 0;
        if (beginDate != null) {
            mondayPlus = getMondayDate(beginDate);
        } else {
            mondayPlus = getMondayPlus();
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        if (beginDate != null) {
            currentDate.setTime(beginDate);
        }
        currentDate.add(GregorianCalendar.DATE, mondayPlus - 35);
        Date monday = currentDate.getTime();

        return monday;
    }

    public static String getSundayOfLastWeek(Date endDate) {

        int mondayPlus = 0;
        if (endDate != null) {
            mondayPlus = getMondayDate(endDate);
        } else {
            mondayPlus = getMondayPlus();
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        if (endDate != null) {
            currentDate.setTime(endDate);
        }
        currentDate.add(GregorianCalendar.DATE, mondayPlus - 1);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static Date getSundayDateOfLastWeek(Date endDate) {

        int mondayPlus = 0;
        if (endDate != null) {
            mondayPlus = getMondayDate(endDate);
        } else {
            mondayPlus = getMondayPlus();
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        if (endDate != null) {
            currentDate.setTime(endDate);
        }
        currentDate.add(GregorianCalendar.DATE, mondayPlus - 1);
        Date monday = currentDate.getTime();

        return monday;
    }

    public static String getSundayOfLastFourWeek(Date endDate) {

        int mondayPlus = 0;
        if (endDate != null) {
            mondayPlus = getMondayDate(endDate);
        } else {
            mondayPlus = getMondayPlus();
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        if (endDate != null) {
            currentDate.setTime(endDate);
        }
        currentDate.add(GregorianCalendar.DATE, mondayPlus - 8);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 日期格式化
     * 
     * @param date
     * @param partten
     * @return
     */
    public static Date changeCurrentDate(Date date, String partten) {

        if (StringUtils.isEmptyOrNullStr(partten) || null == date) {

            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(partten);

        String dateStr = df.format(date);

        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * @param num
     * @return
     */
    public static Date longToDate(Long num) {

        if (null == num) {
            return null;
        }

        return new Date(num);
    }

    public static Date getSundayOfLastFourWeekDate(Date endDate) {

        int mondayPlus = 0;
        if (endDate != null) {
            mondayPlus = getMondayDate(endDate);
        } else {
            mondayPlus = getMondayPlus();
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        if (endDate != null) {
            currentDate.setTime(endDate);
        }
        currentDate.add(GregorianCalendar.DATE, mondayPlus - 8);
        Date monday = currentDate.getTime();

        return monday;
    }

    /**
     * 当天开始时间 XX-XX-XX 00:00:00
     */
    public static String beginToday() {

        String dateStr = format(new Date());
        return dateStr + " 00:00:00";
    }

    /**
     * 当天结束时间 XX-XX-XX 23:59:59
     */
    public static String endToday() {

        String dateStr = format(new Date());
        return dateStr + " 23:59:59";
    }

    public static String getEndDate(String endDate) {

        if (StringUtils.hasLength(endDate, true)) {
            try {
                Date date = DateUtils.parseDate(endDate);
                date = DateUtils.getDateAfter(date, 1);
                return DateUtils.format(date, DateUtils.DATE_FORMAT_YMD);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String getMonthFirstDateStr(Integer num) {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD);// 格式化对象
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(new Date());// 设置当前日期
        calendar.add(Calendar.MONTH, -num);// 月份减一
        String date = sdf.format(calendar.getTime());// 输出格式化的日期
        return date;
    }

    /**
     * 计算当前两个时间之间相差的 时/分/秒
     * 
     * @param lastTime
     *            之前时间
     * @param currentTime
     *            当前时间
     * @return
     */
    public static String computeTime(Date lastTime, Date currentTime) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;

        // 获得两个时间的毫秒时间差异
        long diff = lastTime.getTime() - currentTime.getTime();
        diff = Math.abs(diff);
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算 毫秒
        long sec = diff % nd % nh % nm / ns;
        // 转换为小时数
        long sumHour = day * 24 + hour;
        String stringHour = "";
        if (sumHour > 0) {
            stringHour = day * 24 + hour + "小时";
        }
        String stringMin = "";
        if (min > 0) {
            stringMin = min + "分钟";
        }

        return stringHour + stringMin + sec + "秒";
    }

    /***
     * 将休假时间转化成为 天+小时 格式
     * 
     * @param vacationTime
     * @return String
     */
    public static String transVacationDay(Integer vacationTime) {

        if (vacationTime == null) {
            return "";
        }
        int day = vacationTime / 8;
        int hour = vacationTime % 8;
        StringBuffer applyDay = new StringBuffer();
        if (day > 0) {
            applyDay.append(day + "天");
            if (hour > 0) {
                applyDay.append(hour + "小时");
            }
        } else {
            if (hour >= 0) {
                applyDay.append(hour + "小时");
            }
        }
        return applyDay.toString();
    }

    public static void setStartAndEndTimeDay(Calendar startTime,
                                             Calendar endTime,
                                             Calendar now) {

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(Calendar.YEAR, now.get(Calendar.YEAR));
        start.set(Calendar.MONTH, now.get(Calendar.MONTH));
        start.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        startTime.setTime(start.getTime());

        end.set(Calendar.YEAR, now.get(Calendar.YEAR));
        end.set(Calendar.MONTH, now.get(Calendar.MONTH));
        end.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        endTime.setTime(end.getTime());
    }

    public static void setStartAndEndTimeYear(Calendar startTime,
                                              Calendar endTime,
                                              Calendar now) {

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(Calendar.YEAR, now.get(Calendar.YEAR));
        start.set(Calendar.MONTH, 0);
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        startTime.setTime(start.getTime());

        end.set(Calendar.YEAR, now.get(Calendar.YEAR));
        end.set(Calendar.MONTH, 11);
        end.set(Calendar.DAY_OF_MONTH, 31);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        endTime.setTime(end.getTime());
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param smdate
     *            较小的时间
     * @param bdate
     *            较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long time = time2 - time1 > 0 ? time2 - time1 : time1 - time2;
        long between_days = time / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /***
     * 获取某年第一天日期
     * 
     * @param year
     * @return
     */
    public static Calendar getBeginOfYear(Integer year) {

        Calendar start = Calendar.getInstance();
        if (year != null) {
            start.set(Calendar.YEAR, year);
        }
        start.set(Calendar.MONTH, 0);
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        return start;
    }

    /***
     * 获取某年最后一天日期
     * 
     * @param year
     * @return
     */
    public static Calendar getEndOfYear(Integer year) {

        Calendar end = Calendar.getInstance();
        if (year != null) {
            end.set(Calendar.YEAR, year);
        }
        end.set(Calendar.MONTH, 11);
        end.set(Calendar.DAY_OF_MONTH, 31);
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        return end;
    }

    /***
     * 获取当月第一天
     * 
     * @param year
     * @param month
     * @return
     */
    public static Calendar getBeginOfMonth(Integer year, Integer month) {

        Calendar start = Calendar.getInstance();
        if (year != null) {
            start.set(Calendar.YEAR, year);
        }
        if (month != null) {
            start.set(Calendar.MONTH, month - 1);
        }
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        return start;
    }

    /**
     * 获取当月最后一天
     * 
     * @param year
     * @param month
     * @return
     */
    public static Calendar getEndOfMonth(Integer year, Integer month) {

        Calendar end = Calendar.getInstance();
        if (year != null) {
            end.set(Calendar.YEAR, year);
        }
        if (month != null) {
            end.set(Calendar.MONTH, month);
        }
        end.set(Calendar.DAY_OF_MONTH, 0);
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        return end;
    }

    /**
     * 获取某个时间的当天零点时刻
     */
    public static Date getZeroTime(Date date) {

        if (date == null) {
            return null;
        }

        return new Date(date.getTime()/86400000*86400000 - TimeZone.getDefault().getRawOffset());
    }
}
