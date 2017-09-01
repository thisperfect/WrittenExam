
package com.dayee.writtenExam.framework.mail;

import com.dayee.writtenExam.framework.exception.YuncaiBaseException;

public class MailException extends YuncaiBaseException {

    /**
     * 
     */
    private static final long serialVersionUID = -9138071424743775553L;

    public MailException(String msg, Throwable e) {

        super(msg, e);
    }
}
