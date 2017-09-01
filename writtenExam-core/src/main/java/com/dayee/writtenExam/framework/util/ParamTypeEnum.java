
package com.dayee.writtenExam.framework.util;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 18:39 2017/7/18
 * @Modified By:
 * @Version  
 */
public enum ParamTypeEnum {

    COUNT("1"), ALL("2"), PARAMS("3"), MAXID("4");

    private String value;

    ParamTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    @Override
    public String toString() {

        return String.valueOf(this.value);
    }

}
