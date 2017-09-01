
package com.dayee.writtenExam.framework.util;

/**
 * @Author: dayee_yangkai
 * @Description: 主要用于数据库字段与实体属性的转换
 * @Date: Created in 16:58 2017/7/18
 * @Modified By:
 * @Version  
 */
public class CamelUnderlineUtils {

    private static final char UNDERLINE = '_';

    public static String camelToUnderline(String valueField) {

        if (valueField == null || "".equals(valueField.trim())) {
            return "";
        }
        int len = valueField.length();
        StringBuilder sb = new StringBuilder(len + 2);
        sb.append("f_");
        for (int i = 0; i < len; i++) {
            char c = valueField.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String underlineToCamel(String dataField) {

        if (dataField == null || "".equals(dataField.trim())) {
            return "";
        }
        dataField = StringUtils.substring(dataField, 2);
        int len = dataField.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = dataField.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(dataField.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
