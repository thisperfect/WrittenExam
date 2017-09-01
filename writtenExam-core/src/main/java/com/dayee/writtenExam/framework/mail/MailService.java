
package com.dayee.writtenExam.framework.mail;

import java.util.List;

public interface MailService {
    

    
    /**
     * 发送邮件(通用)
     * @param to   收件地址
     * @param mailSubject  主题
     * @param mailContent  邮件内容    
     * @return
     */
    public int  sendEmail(String to, String mailSubject, String mailContent);
    
    
    /**
     * 发送邮件（带发件名）
     * @param to  收件地址
     * @param fromName  发件人名称
     * @param mailSubject 主题
     * @param mailContent 邮件内容 
     * @return
     */
    public int  sendEmail(String to, String fromName, String mailSubject, String mailContent);
    
    
    /**
     * 发送邮件（可抄送，附件）
     * @param to
     * @param fromName
     * @param mailSubject
     * @param mailContent
     * @param attList
     * @param replyTo
     * @param ccTo
     * @return
     */
    public int sendEmail(String to,
                         String fromName,
                         String mailSubject,
                         String mailContent,
                         List<Attachment> attList,
                         String replyTo,
                         String... ccTo);
    
   
}
