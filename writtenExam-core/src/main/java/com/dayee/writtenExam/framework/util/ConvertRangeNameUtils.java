
package com.dayee.writtenExam.framework.util;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;


public class ConvertRangeNameUtils {

    private static Pattern month_pattern = Pattern
                                                 .compile("([0-9]{4})年(第*)(.*)月(份*)");

    private static Pattern quar_pattern  = Pattern
                                                 .compile("([0-9]{4})年(第*)(.*)季(.*)");

    private static Pattern week_pattern  = Pattern
                                                 .compile("([0-9]{4})年(第*)(.*)周");

    private static Pattern half_pattern  = Pattern
                                                 .compile("([0-9]{4})年(第*)(.*)半年(.*)");

    private static Pattern year_pattern  = Pattern
                                                 .compile("([0-9]{4})年(整年度|年度|整年|一年)?");

    // 2015年整年度：{2015年，2015年全年}
    // 2015年上半年：{}
    // 2015年第1季度：{2015年一季度，2015年第1季度，2015年第一季度，2015年第1季，2015年一季，2015年第1季，2015年第一季}
    // 2015年1周：{2015年一周，2015年第1周，2015年第一周}
    // 2015年1月：{2015年一月，2015年第1月，2015年第一月，数据型转成}
    public static String parseName(String name) {

        if (StringUtils.isNumeric(name)) {
            // 注：在excel输入2015年1月，有可能会被自动转换成日期型2015/1/1,所以解析进来时要做个处理。
            Date date = HSSFDateUtil.getJavaDate(new Double(name));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.get(Calendar.YEAR);
            return getMonthName(cal.get(Calendar.YEAR),
                                cal.get(Calendar.MONTH) + 1);
        } else {
            if (month_pattern.matcher(name).matches()) {
                Matcher matcher = month_pattern.matcher(name);
                if (matcher.find()) {
                    String year_str = matcher.group(1);
                    String month_str = matcher.group(3);
                    return getMonthName(year_str, month_str);
                }
            } else if (week_pattern.matcher(name).matches()) {
                Matcher matcher = week_pattern.matcher(name);
                if (matcher.find()) {
                    String year_str = matcher.group(1);
                    String week_str = matcher.group(3);
                    return getWeekName(year_str, week_str);
                }
            } else if (quar_pattern.matcher(name).matches()) {
                Matcher matcher = quar_pattern.matcher(name);
                if (matcher.find()) {
                    String year_str = matcher.group(1);
                    String quar_str = matcher.group(3);
                    return getQuarterName(year_str, quar_str);
                }
            } else if (half_pattern.matcher(name).matches()) {
                Matcher matcher = half_pattern.matcher(name);
                if (matcher.find()) {
                    String year_str = matcher.group(1);
                    String half_str = matcher.group(3);
                    return getHalfyearName(year_str, half_str);
                }
            } else if (year_pattern.matcher(name).matches()) {
                Matcher matcher = year_pattern.matcher(name);
                if (matcher.find()) {
                    String year_str = matcher.group(1);
                    return getYearName(year_str);
                }
            }
        }
        return null;
    }

    // 2015年1月
    private static String getMonthName(int year, int month) {

        return year + "年" + month + "月";
    }

    // 2015年1月
    private static String getMonthName(String year, String month) {

        if (!StringUtils.isNumeric(year)) {
            return null;
        }
        if (StringUtils.isNumeric(month)) {
            return year + "年" + month + "月";
        } else {
            int num = ChineseNumberUtils.convertChinese(month);
            return year + "年" + num + "月";
        }
    }

    // 2015年1周
    private static String getWeekName(String year, String week) {

        if (!StringUtils.isNumeric(year)) {
            return null;
        }
        if (StringUtils.isNumeric(week)) {
            return year + "年" + week + "周";
        } else {
            int num = ChineseNumberUtils.convertChinese(week);
            return year + "年" + num + "周";
        }
    }

    // 2015年第1季度
    private static String getQuarterName(String year, String quarter) {

        if (!StringUtils.isNumeric(year)) {
            return null;
        }
        if (StringUtils.isNumeric(quarter)) {
            return year + "年第" + quarter + "季度";
        } else {
            int num = ChineseNumberUtils.convertChinese(quarter);
            return year + "年第" + num + "季度";
        }

    }

    // 2015年上半年 、2015年下半年
    private static String getHalfyearName(String year, String halfyear) {

        if (!StringUtils.isNumeric(year)) {
            return null;
        }
        if (halfyear.equals("上") || halfyear.equals("1")
            || halfyear.equals("一")) {
            return year + "年上半年";
        } else if (halfyear.equals("下") || halfyear.equals("2")
                   || halfyear.equals("二")) {
            return year + "年下半年";
        } else {
            return null;
        }
    }

    // 2015年整年度
    private static String getYearName(String year) {

        if (!StringUtils.isNumeric(year)) {
            return null;
        } else {
            return year + "年整年度";
        }
    }
}
