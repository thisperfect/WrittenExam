package com.dayee.writtenExam.console.service.email;

import com.dayee.writtenExam.console.logic.email.EmailLogic;
import com.dayee.writtenExam.model.email.Attachment;
import com.dayee.writtenExam.model.email.EmailConfig;
import com.dayee.writtenExam.rpcInterface.EmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
@Service
public class EmailServiceImpl implements EmailInterface{
    @Autowired
    private EmailLogic emailLogic;

    @Override
    public String saveEmailConfig(EmailConfig email) {
        return emailLogic.saveEmailConfig(email);
    }

    @Override
    public int sendEmail(String to, String mailSubject, String mailContent) {
        return emailLogic.sendEmail(to,mailSubject, mailContent);
    }

    @Override
    public int sendEmail(String to, String fromName, String mailSubject, String mailContent) {
        return emailLogic.sendEmail(to, fromName, mailSubject, mailContent);
    }

    @Override
    public int sendEmail(String to, String fromName, String mailSubject, String mailContent, List<Attachment> attList, String replyTo, String... ccTo) {
        return emailLogic.sendEmail(to, fromName, mailSubject, mailContent, attList, replyTo, ccTo);
    }
}
