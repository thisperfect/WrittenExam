
package com.dayee.writtenExam.restBaseService;

public class RestResponse {

    private String returnCode;

    private String returnDes;

    private Object returnValue;

    public Object getReturnValue() {

        return returnValue;
    }

    public void setReturnValue(Object returnValue) {

        this.returnValue = returnValue;
    }

    public void setReturnDes(String returnDes) {

        this.returnDes = returnDes;
    }

    public String getReturnDes() {

        return this.returnDes;
    }

    public void setReturnCode(String returnCode) {

        this.returnCode = returnCode;
    }

    public String getReturnCode() {

        return this.returnCode;
    }
}