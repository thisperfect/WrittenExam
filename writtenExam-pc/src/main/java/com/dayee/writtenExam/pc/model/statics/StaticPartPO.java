
package com.dayee.writtenExam.pc.model.statics;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 14:17 2017/7/20
 * @Modified By:
 * @Version  
 */
public class StaticPartPO {

    @Field("PART_ID")
    private Integer            mgrPartId;

    @Field("PART_NAME")
    private String             title;

    @Field("NUMBER")
    private String             number;

    @Field("ORDER")
    private Integer            order;

    @Field("PART_SCORE")
    private Integer            partScore;

    @Field("PART_QUES_CNT")
    private Integer            countQues;

    @Field("QUES_LIST")
    private List<StaticQuesPO> squesList;

    public Integer getMgrPartId() {

        return mgrPartId;
    }

    public void setMgrPartId(Integer mgrPartId) {

        this.mgrPartId = mgrPartId;
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

    public Integer getPartScore() {

        return partScore;
    }

    public void setPartScore(Integer partScore) {

        this.partScore = partScore;
    }

    public Integer getCountQues() {

        return countQues;
    }

    public void setCountQues(Integer countQues) {

        this.countQues = countQues;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public List<StaticQuesPO> getSquesList() {

        return squesList;
    }

    public void setSquesList(List<StaticQuesPO> squesList) {

        this.squesList = squesList;
    }
}
