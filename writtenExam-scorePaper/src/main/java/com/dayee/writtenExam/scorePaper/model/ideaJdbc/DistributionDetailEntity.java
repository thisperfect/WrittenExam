
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
@Entity(dataSet = "t_distribution_detil")
public class DistributionDetailEntity extends BaseEntity {

    @Property(dataItem = "F_ACCOUNT_ID")
    private String  accountId;

    @Property(dataItem = "F_PAPER_ID")
    private String  paperId;

    @Property(dataItem = "F_QUESTION_ID")
    private String  questionId;

    @Property(dataItem = "F_SCORE_OFFICER_ID")
    private Integer scoreOfficerId;

    @Property(dataItem = "F_PROMPT_STATUS")
    private Integer promptStatus;

    @Property(dataItem = "F_STATUS")
    private Integer status;

    @Property(dataItem = "F_PROMPT_DATE")
    private Date    promptDate;

    @Property(dataItem = "F_EXTERNAL_ID")
    private Integer externalId;

    public String getAccountId() {

        return accountId;
    }

    public void setAccountId(String accountId) {

        this.accountId = accountId;
    }

    public String getPaperId() {

        return paperId;
    }

    public void setPaperId(String paperId) {

        this.paperId = paperId;
    }

    public String getQuestionId() {

        return questionId;
    }

    public void setQuestionId(String questionId) {

        this.questionId = questionId;
    }

    public Integer getScoreOfficerId() {

        return scoreOfficerId;
    }

    public void setScoreOfficerId(Integer scoreOfficerId) {

        this.scoreOfficerId = scoreOfficerId;
    }

    public Integer getPromptStatus() {

        return promptStatus;
    }

    public void setPromptStatus(Integer promptStatus) {

        this.promptStatus = promptStatus;
    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {

        this.status = status;
    }

    public Date getPromptDate() {

        return promptDate;
    }

    public void setPromptDate(Date promptDate) {

        this.promptDate = promptDate;
    }

    public Integer getExternalId() {

        return externalId;
    }

    public void setExternalId(Integer externalId) {

        this.externalId = externalId;
    }
}
