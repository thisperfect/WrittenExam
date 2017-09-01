package com.dayee.writtenExam.model.email;

import com.dayee.writtenExam.model.ideaJdbc.BaseEntity;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/8.
 */
@Entity(dataSet = "T_EMAIL_CONF")
public class EmailConfig extends BaseEntity {

    @Property(dataItem="F_EMAIL_ADDRESS")
    private String emailAddress;

    @Property(dataItem="F_USER_NAME")
    private String userNane;

    @Property(dataItem="F_PASSWORD")
    private String passWord;

    @Property(dataItem="F_SMTP_SERVER")
    private String SMTPServer;

    @Property(dataItem="F_SMTP_PORT")
    private String SMTPPort;

    @Property(dataItem="F_ADD_DATE")
    private Date addDate;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserNane() {
        return userNane;
    }

    public void setUserNane(String userNane) {
        this.userNane = userNane;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSMTPServer() {
        return SMTPServer;
    }

    public void setSMTPServer(String SMTPServer) {
        this.SMTPServer = SMTPServer;
    }

    public String getSMTPPort() {
        return SMTPPort;
    }

    public void setSMTPPort(String SMTPPort) {
        this.SMTPPort = SMTPPort;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
}
