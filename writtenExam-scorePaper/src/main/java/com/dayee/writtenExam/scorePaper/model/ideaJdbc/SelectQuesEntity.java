
package com.dayee.writtenExam.scorePaper.model.ideaJdbc;

import com.dayee.writtenExam.framework.ideajdbc.baseEntity.BaseEntity;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;

import java.util.Date;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 9:50 2017/7/21
 * @Modified By:
 * @Version  
 */
@Entity(dataSet = "t_select_ques_rule")
public class SelectQuesEntity extends BaseEntity {

    @Property(dataItem = "F_CORP_CODE")
    private String corpCode;

    @Property(dataItem = "F_SELECT_CONTENT")
    private String selectQues;

    @Property(dataItem = "F_ADD_DATE")
    private Date   addDate;

    public String getCorpCode() {

        return corpCode;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

    public String getSelectQues() {

        return selectQues;
    }

    public void setSelectQues(String selectQues) {

        this.selectQues = selectQues;
    }

    public Date getAddDate() {

        return addDate;
    }

    public void setAddDate(Date addDate) {

        this.addDate = addDate;
    }
}
