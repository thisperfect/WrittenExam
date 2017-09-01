
package com.dayee.writtenExam.pc.model.statics;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 14:24 2017/7/20
 * @Modified By:
 * @Version  
 */
public class StaticQuesPO {

    @Field("ID")
    private Integer               mgrQuesId;

    @Field("CORP_CODE")
    private String                corpCode;

    @Field("PAPER_ID")
    private String                paperId;

    @Field("QUESTION_ID")
    private String                storeQuesId;

    @Field("QUES_LEVEL")
    private Integer               quesLevel;

    @Field("QUES_TYPE")
    private Integer               quesType;

    @Field("CONTENT_CH")
    private String                content;

    @Field("FACTOR_CONTENT")
    private String                factorContent;

    @Field("SCORE")
    private Integer               score;

    @Field("SCORE_METHOD")
    private Integer               scoreMethod;

    @Field("EXAM_TIME")
    private Integer               examTime;

    @Field("NUMBER")
    private String                number;

    @Field("ORDER")
    private Integer               order;

    @Field("REFER_ANSWER")
    private String                referAnswer;

    @Field("ISSUPERIOR")
    private Integer               isSuperior;

    @Field("HASATTACHMENT")
    private Integer               hasAttachment;

    @Field("SUBQUES_LIST")
    private List<StaticSubQuesPO> ssubQuesList;

    @Field("OPTION_LIST")
    private List<StaticOptionPO>  soptionList;

    public Integer getMgrQuesId() {

        return mgrQuesId;
    }

    public void setMgrQuesId(Integer mgrQuesId) {

        this.mgrQuesId = mgrQuesId;
    }

    public String getCorpCode() {

        return corpCode;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

    public String getPaperId() {

        return paperId;
    }

    public void setPaperId(String paperId) {

        this.paperId = paperId;
    }

    public String getStoreQuesId() {

        return storeQuesId;
    }

    public void setStoreQuesId(String storeQuesId) {

        this.storeQuesId = storeQuesId;
    }

    public Integer getQuesLevel() {

        return quesLevel;
    }

    public void setQuesLevel(Integer quesLevel) {

        this.quesLevel = quesLevel;
    }

    public Integer getQuesType() {

        return quesType;
    }

    public void setQuesType(Integer quesType) {

        this.quesType = quesType;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public Integer getScore() {

        return score;
    }

    public void setScore(Integer score) {

        this.score = score;
    }

    public Integer getScoreMethod() {

        return scoreMethod;
    }

    public void setScoreMethod(Integer scoreMethod) {

        this.scoreMethod = scoreMethod;
    }

    public Integer getExamTime() {

        return examTime;
    }

    public void setExamTime(Integer examTime) {

        this.examTime = examTime;
    }

    public String getNumber() {

        return number;
    }

    public void setNumber(String number) {

        this.number = number;
    }

    public Integer getOrder() {

        return order;
    }

    public void setOrder(Integer order) {

        this.order = order;
    }

    public String getReferAnswer() {

        return referAnswer;
    }

    public void setReferAnswer(String referAnswer) {

        this.referAnswer = referAnswer;
    }

    public Integer getIsSuperior() {

        return isSuperior;
    }

    public void setIsSuperior(Integer isSuperior) {

        this.isSuperior = isSuperior;
    }

    public Integer getHasAttachment() {

        return hasAttachment;
    }

    public void setHasAttachment(Integer hasAttachment) {

        this.hasAttachment = hasAttachment;
    }

    public String getFactorContent() {

        return factorContent;
    }

    public void setFactorContent(String factorContent) {

        this.factorContent = factorContent;
    }

    public List<StaticSubQuesPO> getSsubQuesList() {

        return ssubQuesList;
    }

    public void setSsubQuesList(List<StaticSubQuesPO> ssubQuesList) {

        this.ssubQuesList = ssubQuesList;
    }

    public List<StaticOptionPO> getSoptionList() {

        return soptionList;
    }

    public void setSoptionList(List<StaticOptionPO> soptionList) {

        this.soptionList = soptionList;
    }
}
