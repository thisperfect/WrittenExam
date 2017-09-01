
package com.dayee.writtenExam.pc.model.statics;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 13:59 2017/7/20
 * @Modified By:
 * @Version  
 */
public class StaticPagePO {

    @Field("NAME_CH")
    private String             desc;

    @Field("HASATTACHMENT")
    private Integer            isAllowUpload;

    @Field("ORDER")
    private Integer            order;

    @Field("PAGE_SCORE")
    private Integer            pageScore;

    @Field("PAGE_QUES_CNT")
    private Integer            countQues;

    @Field("AUTO_COMMIT")
    private Integer            isAutoCommit;

    @Field("PART_LIST")
    private List<StaticPartPO> spartList;

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {

        this.desc = desc;
    }

    public Integer getIsAllowUpload() {

        return isAllowUpload;
    }

    public void setIsAllowUpload(Integer isAllowUpload) {

        this.isAllowUpload = isAllowUpload;
    }

    public Integer getOrder() {

        return order;
    }

    public void setOrder(Integer order) {

        this.order = order;
    }

    public Integer getPageScore() {

        return pageScore;
    }

    public void setPageScore(Integer pageScore) {

        this.pageScore = pageScore;
    }

    public Integer getCountQues() {

        return countQues;
    }

    public void setCountQues(Integer countQues) {

        this.countQues = countQues;
    }

    public Integer getIsAutoCommit() {

        return isAutoCommit;
    }

    public void setIsAutoCommit(Integer isAutoCommit) {

        this.isAutoCommit = isAutoCommit;
    }

    public List<StaticPartPO> getSpartList() {

        return spartList;
    }

    public void setSpartList(List<StaticPartPO> spartList) {

        this.spartList = spartList;
    }
}
