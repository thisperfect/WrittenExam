/**
 * 
 */

package com.dayee.writtenExam.framework.exception;

/**
 * 用于返回校验信息
 * 
 * @author Chinakite
 */
public class YuncaiMessage extends RuntimeException {

    private static final long serialVersionUID = 5886604742404533428L;

    private String            state;

    private String            message;

    private Object            obj;

    public YuncaiMessage(String state, String message) {

        this.state = state;
        this.message = message;
    }

    /**
     * @return the state
     */
    public String getState() {

        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {

        this.state = state;
    }

    /**
     * @return the message
     */
    @Override
    public String getMessage() {

        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {

        this.message = message;
    }

    public Object getObj() {

        return obj;
    }

    public void setObj(Object obj) {

        this.obj = obj;
    }

}
