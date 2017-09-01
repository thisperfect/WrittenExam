
package com.dayee.writtenExam.pc.model.result;

import java.util.Date;

public class ResultPO {

    private Integer resultId;

    private Integer accountId;

    private Integer testPaperId;

    private Integer questionId;

    private Integer questionType;

    private Integer questionLevel;

    private Integer optionId;

    private String  answerContent;

    private Float   realScore;

    private Date    testDate;

    private Integer answeredTime;

    private Integer attachmentIds;

    private Integer storeQuesId;

    public Integer getResultId() {

        return resultId;
    }

    public void setResultId(Integer resultId) {

        this.resultId = resultId;
    }

    public Integer getAccountId() {

        return accountId;
    }

    public void setAccountId(Integer accountId) {

        this.accountId = accountId;
    }

    public Integer getTestPaperId() {

        return testPaperId;
    }

    public void setTestPaperId(Integer testPaperId) {

        this.testPaperId = testPaperId;
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

    public Integer getQuestionLevel() {

        return questionLevel;
    }

    public void setQuestionLevel(Integer questionLevel) {

        this.questionLevel = questionLevel;
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

    public Float getRealScore() {

        return realScore;
    }

    public void setRealScore(Float realScore) {

        this.realScore = realScore;
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

    public Integer getAttachmentIds() {

        return attachmentIds;
    }

    public void setAttachmentIds(Integer attachmentIds) {

        this.attachmentIds = attachmentIds;
    }

    public Integer getStoreQuesId() {

        return storeQuesId;
    }

    public void setStoreQuesId(Integer storeQuesId) {

        this.storeQuesId = storeQuesId;
    }

}
