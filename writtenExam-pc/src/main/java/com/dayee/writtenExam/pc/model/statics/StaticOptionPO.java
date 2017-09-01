
package com.dayee.writtenExam.pc.model.statics;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 14:26 2017/7/20
 * @Modified By:
 * @Version  
 */
public class StaticOptionPO {

    @Field("CONTENT_CH")
    private String  content;

    @Field("NUMBER")
    private String  number;

    @Field("ORDER")
    private Integer order;

    @Field("REFER_ANSWER")
    private String  referAnswer;

    public String getContent() {

        return content;
    }

    public void setContent(String content) {

        this.content = content;
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
}
