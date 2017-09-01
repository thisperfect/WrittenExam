/**
 * 
 */

package com.dayee.writtenExam.framework.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chinakite
 */
public class PcodeUtils {

    private static final Logger logger = LoggerFactory
                                               .getLogger(PcodeUtils.class);

    public static String nextPcode(String pcode) {

        String nextcode = "01";
        if (pcode == null) {
            logger.debug("pcode:{}, nextcode:{}", pcode, nextcode);
            return nextcode;
        }

        int level = getPcodeLevel(pcode);

        if (level == 1) {
            int oldCodeInt = Integer.parseInt(pcode);
            int newCodeInt = oldCodeInt + 1;
            nextcode = String.format("%02d", newCodeInt);
        } else if (level == 2) {
            int oldCodeInt = Integer.parseInt(pcode.substring(2));
            int newCodeInt = oldCodeInt + 1;
            nextcode = String.format("%02d", newCodeInt);
            nextcode = pcode.substring(0, 2) + nextcode;
        } else {
            int oldCodeInt = Integer
                    .parseInt(pcode.substring(pcode.length() - 2));
            int newCodeInt = oldCodeInt + 1;
            nextcode = String.format("%02d", newCodeInt);
            nextcode = pcode.substring(0, pcode.length() - 2) + nextcode;
        }
        logger.debug("pcode:{}, nextcode:{}", pcode, nextcode);
        return nextcode;
    }

    public static Integer nextOrder(String pcode) {

        int order = 1;
        if (pcode == null) {
            logger.debug("firstOrder:{}", order);
            return order;
        }
        int level = getPcodeLevel(pcode);
        if (level == 1) {// 两位
            order = Integer.parseInt(pcode) + 1;
        } else if (level == 2) {// 四位
            order = Integer.parseInt(pcode.substring(2)) + 1;// 从第3位开始截取(0,1,2)
        } else {
            order = Integer.parseInt(pcode.substring(pcode.length() - 3)) + 1;// 从倒数第3位开始截取
        }
        logger.debug(" nextOrder:{}", order);
        return order;
    }

    public static Integer getPcodeOrder(String pcode) {

        int order = 0;
        int level = getPcodeLevel(pcode);
        if (level == 1) {// 两位
            order = Integer.parseInt(pcode);
        } else if (level == 2) {// 四位
            order = Integer.parseInt(pcode.substring(2));// 从第3位开始截取(0,1,2)
        } else {
            order = Integer.parseInt(pcode.substring(pcode.length() - 3));// 从倒数第3位开始截取
        }
        return order;
    }

    public static int getPcodeLevel(String pcode) {

        if (pcode == null)
            return 0;

        if (pcode.length() == 2) {
            return 1;
        }

        if (pcode.length() == 4) {
            return 2;
        }

        return (pcode.length() - 4) / 3 + 2;
    }

    public static String getParentPcode(String pcode) {

        if (pcode == null) {
            throw new IllegalArgumentException("Pcode is null.");
        }

        int level = getPcodeLevel(pcode);

        if (level == 1) {
            return null;
        } else if (level == 2) {
            return pcode.substring(0, 2);
        } else {
            return pcode.substring(0, pcode.length() - 3);
        }

    }

    public static List<String> getAllParentPcodes(String pcode) {

        if (pcode == null) {
            throw new IllegalArgumentException("Pcode is null.");
        }

        int level = getPcodeLevel(pcode);

        List<String> result = new ArrayList<String>();

        if (level == 1) {

        } else if (level == 2) {
            result.add(pcode.substring(0, 2));
        } else {
            result.add(pcode.substring(0, 2));
            for (int i = level; i > 2; i--) {
                pcode = pcode.substring(0, pcode.length() - 3);
                result.add(pcode);
            }
        }
        return result;
    }

    public static String getFirstChildPcode(String pcode) {

        if (pcode == null) {
            throw new IllegalArgumentException("Pcode is null.");
        }

        int level = getPcodeLevel(pcode);
        if (level == 1) {
            return pcode + "01";
        } else {
            return pcode + "001";
        }
    }

    /**
     * 过滤掉下级的Pcode
     * 
     * @param pcodeList
     * @return
     */
    public static List<String> filterChildPcode(List<String> pcodeList) {

        List<String> newPcodeList = new ArrayList<String>();
        for (String pcode : pcodeList) {
            boolean needAppend = true;
            Iterator<String> iterator = newPcodeList.iterator();
            while (iterator.hasNext()) {
                String hasPcode = iterator.next();
                if (pcode.startsWith(hasPcode)) {
                    needAppend = false;
                    break;
                } else if (hasPcode.startsWith(pcode)) {
                    // hasPcode = null;
                    iterator.remove();
                }
            }
            if (needAppend) {
                newPcodeList.add(pcode);
            }
        }
        return newPcodeList;
    }

    public static void main(String[] args) {

        List<String> pcodeList = new ArrayList<String>();
        pcodeList.add("0102");
        pcodeList.add("010203");
        pcodeList.add("010202");
        pcodeList.add("010101");
        pcodeList.add("010103");
        pcodeList.add("0101");
        System.out.println(pcodeList);
        pcodeList = filterChildPcode(pcodeList);
        System.out.println(pcodeList);
    }
}
