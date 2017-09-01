package com.dayee.writtenExam.framework.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.dayee.writtenExam.model.IdCardInfo;

/**
 * 身份证验证的工具（支持5位或18位省份证）
 * 身份证号码结构：
 * 17位数字和1位校验码：6位地址码数字，8位生日数字，3位出生时间顺序号，1位校验码。
 * 地址码（前6位）：表示对象常住户口所在县（市、镇、区）的行政区划代码，按GB/T2260的规定执行。
 * 出生日期码，（第七位 至十四位）：表示编码对象出生年、月、日，按GB按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * 顺序码（第十五位至十七位）：表示在同一地址码所标示的区域范围内，对同年、同月、同日出生的人编订的顺序号，
 * 顺序码的奇数分配给男性，偶数分配给女性。 
 * 校验码（第十八位数）：
 * 十七位数字本体码加权求和公式 s = sum(Ai*Wi), i = 0,,16，先对前17位数字的权求和；   
 *  Ai:表示第i位置上的身份证号码数字值.Wi:表示第i位置上的加权因.Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2；
 * 计算模 Y = mod(S, 11) 
 * 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2 
 */
public class IDCardUtil {

    /** 中国公民身份证号码最小长度。 */
    public static final int           CHINA_ID_MIN_LENGTH = 15;

    /** 中国公民身份证号码最大长度。 */
    public static final int           CHINA_ID_MAX_LENGTH = 18;

    /** 每位加权因子 */
    public static final int           power[]             = { 7, 9, 10, 5, 8,
            4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2          };

    final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();
    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "宁夏");
        zoneNum.put(65, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "外国");
    }
     
    final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    final static int[] POWER_LIST = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 
        5, 8, 4, 2};
     
    /**
     * 身份证验证
     *@param s  号码内容
     *@return 是否有效 null和"" 都是false 
     */
    public static IdCardInfo isIDCard(String certNo){
        
        IdCardInfo cardInfo = new IdCardInfo(false);
        if(certNo == null || (certNo.length() != 15 && certNo.length() != 18))
            return cardInfo;
        final char[] cs = certNo.toUpperCase().toCharArray();
        //校验位数
        int power = 0;
        for(int i=0; i<cs.length; i++){
            if(i==cs.length-1 && cs[i] == 'X')
                break;//最后一位可以 是X或x
            if(cs[i]<'0' || cs[i]>'9')
                return cardInfo;
            if(i < cs.length -1){
                power += (cs[i] - '0') * POWER_LIST[i];
            }
        }
         
        //校验区位码
        if(!zoneNum.containsKey(Integer.valueOf(certNo.substring(0,2)))){
            return cardInfo;
        }
         
        //校验年份
        String year = certNo.length() == 15 ? getIdcardCalendar() + certNo.substring(6,8) :certNo.substring(6, 10);
         
        final int iyear = Integer.parseInt(year);
        if(iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
            return cardInfo;//1900年的PASS，超过今年的PASS
         
        //校验月份
        String month = certNo.length() == 15 ? certNo.substring(8, 10) : certNo.substring(10,12);
        final int imonth = Integer.parseInt(month);
        if(imonth <1 || imonth >12){
            return cardInfo;
        }
         
        //校验天数      
        String day = certNo.length() ==15 ? certNo.substring(10, 12) : certNo.substring(12, 14);
        final int iday = Integer.parseInt(day);
        if(iday < 1 || iday > 31)
            return cardInfo;       
        
        String dateStr = year+"-"+month+"-"+day;
        
        try {
            Date birthDaye = DateUtils.parseDate(dateStr);
            cardInfo.setBirthDay(birthDaye);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        boolean result =false;
        
        
        
        //校验"校验码"
        if(certNo.length() == 15){
            cardInfo.setValidate(true);
            return cardInfo;
        }
        result = cs[cs.length -1 ] == PARITYBIT[power % 11];
        
        String sex = certNo.substring(16, 17);
        if(Integer.parseInt(sex)%2==0){
            cardInfo.setGender("430002");
        } 
        
        if(result){
            cardInfo.setValidate(true);
        }
        
        return cardInfo;
    }
    
    
     
    private static int getIdcardCalendar() {        
         GregorianCalendar curDay = new GregorianCalendar();
         int curYear = curDay.get(Calendar.YEAR);
         int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));          
         return  year2bit;
    }     
     
    
    public static void getBirthDay(){
        
    }

    /**
     * 将15位身份证号码转换为18位
     * 
     * @param idCard
     *            15位身份编码
     * @return 18位身份编码
     */
    public static String conver15CardTo18(String idCard) {

        String idCard18 = "";
        if (idCard.length() != CHINA_ID_MIN_LENGTH) {
            return null;
        }
        if (isIDCard(idCard).isValidate()) {
            // 获取出生年月日
            String birthday = idCard.substring(6, 12);
            Date birthDate = null;
            try {
                birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            if (birthDate != null)
                cal.setTime(birthDate);
            // 获取出生年(完全表现形式,如：2010)
            String sYear = String.valueOf(cal.get(Calendar.YEAR));
            idCard18 = idCard.substring(0, 6) + sYear + idCard.substring(8);
            // 转换字符数组
            char[] cArr = idCard18.toCharArray();
            if (cArr != null) {
                int[] iCard = converCharToInt(cArr);
                int iSum17 = getPowerSum(iCard);
                // 获取校验位
                String sVal = getCheckCode18(iSum17);
                if (sVal.length() > 0) {
                    idCard18 += sVal;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
        return idCard18;
    }

    /**
     * 将字符数组转换成数字数组
     * 
     * @param ca
     *            字符数组
     * @return 数字数组
     */
    public static int[] converCharToInt(char[] ca) {

        int len = ca.length;
        int[] iArr = new int[len];
        try {
            for (int i = 0; i < len; i++) {
                iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return iArr;
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     * 
     * @param iArr
     * @return 身份证编码。
     */
    public static int getPowerSum(int[] iArr) {

        int iSum = 0;
        if (power.length == iArr.length) {
            for (int i = 0; i < iArr.length; i++) {
                for (int j = 0; j < power.length; j++) {
                    if (i == j) {
                        iSum = iSum + iArr[i] * power[j];
                    }
                }
            }
        }
        return iSum;
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断
     * 
     * @param iSum
     * @return 校验位
     */
    public static String getCheckCode18(int iSum) {

        String sCode = "";
        switch (iSum % 11) {
        case 10:
            sCode = "2";
            break;
        case 9:
            sCode = "3";
            break;
        case 8:
            sCode = "4";
            break;
        case 7:
            sCode = "5";
            break;
        case 6:
            sCode = "6";
            break;
        case 5:
            sCode = "7";
            break;
        case 4:
            sCode = "8";
            break;
        case 3:
            sCode = "9";
            break;
        case 2:
            sCode = "x";
            break;
        case 1:
            sCode = "0";
            break;
        case 0:
            sCode = "1";
            break;
        }
        return sCode;
    }

    /**
     * 根据身份编号获取生日
     * 
     * @param idCard
     *            身份编号
     * @return 生日(yyyyMMdd)
     */
    public static Date getBirthByIdCard(String idCard) {

        Integer len = idCard.length();
        if (len < CHINA_ID_MIN_LENGTH) {
            return null;
        } else if (len == CHINA_ID_MIN_LENGTH) {
            idCard = conver15CardTo18(idCard);
        }
        String birthday = idCard.substring(6, 14);
        Date birthdayDate = null;
        try {
            birthdayDate = DateUtils.charToDate(birthday, "yyyyMMdd");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return birthdayDate;
    }


    /**
     * 根据身份编号获取性别
     * 
     * @param idCard
     *            身份编号
     * @return 性别(M-男，F-女，N-未知)
     */
    public static String getGenderByIdCard(String idCard) {

        String sGender = "";
        if (idCard.length() == CHINA_ID_MIN_LENGTH) {
            idCard = conver15CardTo18(idCard);
        }
        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "430001";
        } else {
            sGender = "430002";
        }
        return sGender;
    }
     
     
    public static void main(String[] args) {    
        IdCardInfo cardInfo = isIDCard("36012419861023512X");    
         System.out.println(cardInfo.isValidate()+"\t"+DateUtils.formatYMDHMS(cardInfo.getBirthDay())+"\t"+cardInfo.getGender());
    }
 
}