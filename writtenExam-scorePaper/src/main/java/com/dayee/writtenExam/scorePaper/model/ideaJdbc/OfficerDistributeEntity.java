
package com.dayee.writtenExam.scorePaper.model.ideaJdbc;

import com.dayee.writtenExam.framework.ideajdbc.baseEntity.BaseEntity;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;

import java.util.Date;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 17:56 2017/7/26
 * @Modified By:
 * @Version  
 */
@Entity(dataSet = "t_officer_distribute")
public class OfficerDistributeEntity extends BaseEntity {

    @Property(dataItem = "F_SCORE_OFFICER_ID")
    private Integer scoreOfficerId;

    @Property(dataItem = "F_PAPER_IDS")
    private String  paperIds;

    @Property(dataItem = "F_SELECT_QUES_RULE")
    private Integer selectQuesRule;

    @Property(dataItem = "F_SEND_NUM")
    private Integer sendNum;

    @Property(dataItem = "F_SEND_TIME")
    private Date    sendTime;

    @Property(dataItem = "F_SEND_TYPE")
    private Integer sendType;

    @Property(dataItem = "F_SEND_PERSON")
    private String  sendPerson;

    @Property(dataItem = "F_PROMPT_NUM")
    private Integer promptNum;

    @Property(dataItem = "F_COMPLETE_RATE")
    private Float   completeRate;

    @Property(dataItem = "F_PROMPT_STATUS")
    private Integer promptStatus;

    @Property(dataItem = "F_ANONYMOUS")
    private Integer anonymous;

    @Property(dataItem = "F_SEND_SMS")
    private Integer sendSms;

    @Property(dataItem = "F_EMAIL_INFO")
    private String  emailInfo;

    public Integer getScoreOfficerId() {

        return scoreOfficerId;
    }

    public void setScoreOfficerId(Integer scoreOfficerId) {

        this.scoreOfficerId = scoreOfficerId;
    }

    public Integer getSelectQuesRule() {

        return selectQuesRule;
    }

    public void setSelectQuesRule(Integer selectQuesRule) {

        this.selectQuesRule = selectQuesRule;
    }

    public Integer getSendNum() {

        return sendNum;
    }

    public void setSendNum(Integer sendNum) {

        this.sendNum = sendNum;
    }

    public Date getSendTime() {

        return sendTime;
    }

    public void setSendTime(Date sendTime) {

        this.sendTime = sendTime;
    }

    public Integer getSendType() {

        return sendType;
    }

    public void setSendType(Integer sendType) {

        this.sendType = sendType;
    }

    public String getSendPerson() {

        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {

        this.sendPerson = sendPerson;
    }

    public Integer getPromptNum() {

        return promptNum;
    }

    public void setPromptNum(Integer promptNum) {

        this.promptNum = promptNum;
    }

    public Float getCompleteRate() {

        return completeRate;
    }

    public void setCompleteRate(Float completeRate) {

        this.completeRate = completeRate;
    }

    public Integer getPromptStatus() {

        return promptStatus;
    }

    public void setPromptStatus(Integer promptStatus) {

        this.promptStatus = promptStatus;
    }

    public Integer getAnonymous() {

        return anonymous;
    }

    public void setAnonymous(Integer anonymous) {

        this.anonymous = anonymous;
    }

    public Integer getSendSms() {

        return sendSms;
    }

    public void setSendSms(Integer sendSms) {

        this.sendSms = sendSms;
    }

    public String getEmailInfo() {

        return emailInfo;
    }

    public void setEmailInfo(String emailInfo) {

        this.emailInfo = emailInfo;
    }

    public String getPaperIds() {

        return paperIds;
    }

    public void setPaperIds(String paperIds) {

        this.paperIds = paperIds;
    }
}
