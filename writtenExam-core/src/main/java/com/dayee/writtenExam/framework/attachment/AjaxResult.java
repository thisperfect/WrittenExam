
package com.dayee.writtenExam.framework.attachment;

import java.io.Serializable;

public class AjaxResult<T> implements Serializable {

    public static final String SUCCESS          = "success";

    public static final String ERROR            = "error";

    public static final String TOBACK           = "0001";    // 返回上一页

    public static final String TOINDEX          = "0002";   // 返回主页

    public static final String SHOW_LOGINFORM   = "0003";    // 显示登陆框

    /**
	 * 
	 */
    private static final long  serialVersionUID = 1L;

    private String             result;

    public T                   resultObj;                    // 返回对象

    private String             message;

    private String             flag;                         // 标记

    private String             after;                        // 返回成功后的处理

    public AjaxResult() {

    }

    public AjaxResult(String result, T resultObj, String message, String after) {

        super();
        this.result = result;
        this.resultObj = resultObj;
        this.message = message;
        this.after = after;
    }

    public AjaxResult(String result, T resultObj) {

        super();
        this.result = result;
        this.resultObj = resultObj;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {

        this.result = result;
    }

    public T getResultObj() {

        return resultObj;
    }

    public void setResultObj(T resultObj) {

        this.resultObj = resultObj;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getFlag() {

        return flag;
    }

    public void setFlag(String flag) {

        this.flag = flag;
    }

    public String getAfter() {

        return after;
    }

    public void setAfter(String after) {

        this.after = after;
    }

}
