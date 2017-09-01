
package com.dayee.writtenExam.framework.util;

import java.util.HashMap;
import java.util.Map;

public class ChineseNumberUtils {

    private static Map<String, Integer> map = new HashMap<String, Integer>();
    static {
        map.put("一", 1);
        map.put("二", 2);
        map.put("三", 3);
        map.put("四", 4);
        map.put("五", 5);
        map.put("六", 6);
        map.put("七", 7);
        map.put("八", 8);
        map.put("九", 9);
        map.put("十", 10);
        map.put("百", 100);
        map.put("千", 1000);
        map.put("万", 10000);

        map.put("零", 0);
        map.put("两", 2);
        map.put("拾", 10);
        map.put("佰", 100);
        map.put("仟", 1000);
        map.put("萬", 10000);
    }

    /**
     * 中文数字转阿拉伯数字 (只支持[0-99999)之间的转换)
     * 
     * @param str
     * @return
     */
    public static int convertChinese(String str) {

        char[] charArray = str.toCharArray();
        int sum = 0;
        try {
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                Integer mapNum = map.get(String.valueOf(c));
                if (mapNum == null) {
                    return 0;
                }
                if (mapNum == 10 || mapNum == 100
                    || mapNum == 1000
                    || mapNum == 10000) {
                    sum += mapNum;

                } else {

                    if (i + 1 < charArray.length) {
                        int numSize = getNumberSize(charArray[i + 1]);
                        if (numSize == 0) {
                            sum += mapNum;
                        } else {
                            i++;
                            sum += mapNum * numSize;
                        }
                    } else {
                        sum += mapNum;
                    }

                }

            }

        } catch (Exception e) {
            return 0;
        }
        return sum;
    }

    /**
     * 取得位阶
     * 
     * @param c
     * @return
     */
    private static int getNumberSize(char c) {

        int num = map.get(String.valueOf(c));
        if (num == 10 || num == 100 || num == 1000 || num == 10000) {
            return num;
        } else {
            return 0;
        }
    }
}
