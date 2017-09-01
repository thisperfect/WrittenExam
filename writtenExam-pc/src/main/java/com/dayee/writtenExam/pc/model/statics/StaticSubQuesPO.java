
package com.dayee.writtenExam.pc.model.statics;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 14:26 2017/7/20
 * @Modified By:
 * @Version  
 */
public class StaticSubQuesPO {

    @Field("QUES_LEVEL")
    private Integer              quesLevel;

    @Field("QUES_TYPE")
    private Integer              questionType;

    @Field("CONTENT_CH")
    private String               content;

    @Field("SCORE")
    private Integer              score;

    @Field("SCORE_METHOD")
    private Integer              scoreMethod;

    @Field("EXAM_TIME")
    private Integer              examTime;

    @Field("NUMBER")
    private String               number;

    @Field("ORDER")
    private Integer              order;

    @Field("REFER_ANSWER")
    private String               referAnswer;

    @Field("HASATTACHMENT")
    private Integer              hasAttachment;

    @Field("OPTION_LIST")
    private List<StaticOptionPO> soptionList;

    public Integer getQuesLevel() {

        return quesLevel;
    }

    public void setQuesLevel(Integer quesLevel) {

        this.quesLevel = quesLevel;
    }

    public Integer getQuestionType() {

        return questionType;
    }

    public void setQuestionType(Integer questionType) {

        this.questionType = questionType;
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

    public Integer getHasAttachment() {

        return hasAttachment;
    }

    public void setHasAttachment(Integer hasAttachment) {

        this.hasAttachment = hasAttachment;
    }

    public List<StaticOptionPO> getSoptionList() {

        return soptionList;
    }

    public void setSoptionList(List<StaticOptionPO> soptionList) {

        this.soptionList = soptionList;
    }
}
