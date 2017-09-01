
package com.dayee.writtenExam.scorePaper.model.ideaJdbc;

import com.dayee.writtenExam.framework.ideajdbc.baseEntity.BaseEntity;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;

import java.util.Date;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 15:18 2017/7/28
 * @Modified By:
 * @Version  
 */
@Entity(dataSet = "t_account_result")
public class AnswerResultEntity extends BaseEntity {

    @Property(dataItem = "F_ACCOUNT_ID")
    private Integer accountId;

    @Property(dataItem = "F_PROJECT_ID")
    private Integer projectId;

    @Property(dataItem = "F_PAPER_ID")
    private Integer paperId;

    @Property(dataItem = "F_FACTOR_ID")
    private Integer factorId;

    @Property(dataItem = "F_QUESTION_ID")
    private Integer questionId;

    @Property(dataItem = "F_QUESTION_TYPE")
    private Integer questionType;

    @Property(dataItem = "F_OPTION_ID")
    private Integer optionId;

    @Property(dataItem = "F_ANSWER_CONTENT")
    private String  answerContent;

    @Property(dataItem = "F_ASSESS_CONTENT")
    private String  assessContent;

    @Property(dataItem = "F_REAL_SCORE")
    private Float   realScore;

    @Property(dataItem = "F_MAX_SCORE")
    private Float   maxScore;

    @Property(dataItem = "F_TEST_DATE")
    private Date    testDate;

    @Property(dataItem = "F_ANSWERED_TIME")
    private Integer answeredTime;

    @Property(dataItem = "F_IS_SCORE")
    private Integer isScore;

    public Integer getAccountId() {

        return accountId;
    }

    public void setAccountId(Integer accountId) {

        this.accountId = accountId;
    }

    public Integer getProjectId() {

        return projectId;
    }

    public void setProjectId(Integer projectId) {

        this.projectId = projectId;
    }

    public Integer getPaperId() {

        return paperId;
    }

    public void setPaperId(Integer paperId) {

        this.paperId = paperId;
    }

    public Integer getFactorId() {

        return factorId;
    }

    public void setFactorId(Integer factorId) {

        this.factorId = factorId;
    }

    public Integer getQuestionId() {

        return questionId;
    }

    public void setQuestionId(Integer questionId) {

        this.questionId = questionId;
    }

    public Integer getQuestionType() {

        return questionType;
    }

    public void setQuestionType(Integer questionType) {

        this.questionType = questionType;
    }

    public Integer getOptionId() {

        return optionId;
    }

    public void setOptionId(Integer optionId) {

        this.optionId = optionId;
    }

    public String getAnswerContent() {

        return answerContent;
    }

    public void setAnswerContent(String answerContent) {

        this.answerContent = answerContent;
    }

    public String getAssessContent() {

        return assessContent;
    }

    public void setAssessContent(String assessContent) {

        this.assessContent = assessContent;
    }

    public Float getRealScore() {

        return realScore;
    }

    public void setRealScore(Float realScore) {

        this.realScore = realScore;
    }

    public Float getMaxScore() {

        return maxScore;
    }

    public void setMaxScore(Float maxScore) {

        this.maxScore = maxScore;
    }

    public Date getTestDate() {

        return testDate;
    }

    public void setTestDate(Date testDate) {

        this.testDate = testDate;
    }

    public Integer getAnsweredTime() {

        return answeredTime;
    }

    public void setAnsweredTime(Integer answeredTime) {

        this.answeredTime = answeredTime;
    }

    public Integer getIsScore() {

        return isScore;
    }

    public void setIsScore(Integer isScore) {

        this.isScore = isScore;
    }
}
