
package com.dayee.writtenExam.model.config;

import com.dayee.writtenExam.model.YesOrNo;
import org.springframework.util.StringUtils;

public class MailConfig {

    public static int FTP_OR_MAIL_ATTEMPT_TIME = 5;

    private String    mailAddress;

    private String    mailUserName;

    private String    mailPassword;

    private String    smtpServer;

    private String    smtpPort;

    private String    sslStatus;

    private String    tryTime;

    private String    sendMode                 = "0"; // 0

    // -默认配置
    // ,1
    // -360Mail,2
    // -qunar

    private String    mailToken;                     // 360

    // email
    // token

    // private Integer remainMailNum; // 剩余邮件发送数量

    private String    isEws;

    private String    corpCode;

    private String    showSendName;

    public String getShowSendName() {

        return showSendName;
    }

    public String getSmtpServer() {

        return smtpServer;
    }

    public String getMailToken() {

        return mailToken;
    }

    public void setShowSendName(String showSendName) {

        this.showSendName = showSendName;
    }

    public void setSmtpServer(String smtpServer) {

        this.smtpServer = smtpServer;
    }

    public void setMailToken(String mailToken) {

        this.mailToken = mailToken;
    }

    public String getCorpCode() {

        return corpCode;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

    public String getMailAddress() {

        return mailAddress;
    }

    public String getMailUserName() {

        return mailUserName;
    }

    public String getMailPassword() {

        return mailPassword;
    }

    public void setMailAddress(String mailAddress) {

        this.mailAddress = mailAddress;
    }

    public void setMailUserName(String mailUserName) {

        this.mailUserName = mailUserName;
    }

    public void setMailPassword(String mailPassword) {

        this.mailPassword = mailPassword;
    }

    public boolean isDefaultSendMode() {

        return sendMode != null && sendMode.equals(String.valueOf(YesOrNo.YES));
    }

    public boolean is360Mail() {

        return sendMode != null && sendMode.equals(String.valueOf(YesOrNo.NO));
    }

    public boolean isQunarMail() {

        return sendMode != null && sendMode.equals("2");
    }

    public int getIntTryTime() {

        return tryTime == null || tryTime.length() > 1
                                                      ? FTP_OR_MAIL_ATTEMPT_TIME
                                                      : Integer
                                                              .parseInt(tryTime);
    }

    public boolean canSendMail() {

        return (isDefaultSendMode() && StringUtils.hasLength(getMailAddress())
                && StringUtils.hasLength(getMailPassword()) && StringUtils
                    .hasLength(getSmtpServer())) || (is360Mail() && StringUtils
                       .hasLength(mailToken))
               || (isQunarMail() && StringUtils.hasLength(mailToken));
    }

    public String getShowName() {

        StringBuffer sb = new StringBuffer();
        if (StringUtils.hasLength(showSendName)) {
            sb.append(showSendName);
        }
        sb.append(" ( ");
        if (is360Mail()) {
            sb.append("360Email");
        } else if (isQunarMail()) {
            sb.append("Qunar Email");
        } else {
            sb.append("SMTP");
        }
        sb.append(" )");
        return sb.toString();
    }

    public String getSmtpPort() {

        return smtpPort;
    }

    public String getSslStatus() {

        return sslStatus;
    }

    public String getTryTime() {

        return tryTime;
    }

    public String getSendMode() {

        return sendMode;
    }

    public String getIsEws() {

        return isEws;
    }

    public void setSmtpPort(String smtpPort) {

        this.smtpPort = smtpPort;
    }

    public void setSslStatus(String sslStatus) {

        this.sslStatus = sslStatus;
    }

    public void setTryTime(String tryTime) {

        this.tryTime = tryTime;
    }

    public void setIsEws(String isEws) {

        this.isEws = isEws;
    }

    public void setSendMode(String sendMode) {

        this.sendMode = sendMode;
    }

}
