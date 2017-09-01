
package com.dayee.writtenExam.framework.exception;

import org.apache.shiro.authc.AuthenticationException;

public class YuncaiAuthenticationException extends AuthenticationException {

    public YuncaiAuthenticationException(YuncaiMessage yuncaiMessage) {

        super();
        this.yuncaiMessage = yuncaiMessage;
    }

    public YuncaiAuthenticationException() {

        super();
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private YuncaiMessage     yuncaiMessage;

    public YuncaiMessage getYuncaiMessage() {

        return yuncaiMessage;
    }

    public void setYuncaiMessage(YuncaiMessage yuncaiMessage) {

        this.yuncaiMessage = yuncaiMessage;
    }

}
