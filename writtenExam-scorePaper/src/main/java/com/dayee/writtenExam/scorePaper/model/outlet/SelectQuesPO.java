
package com.dayee.writtenExam.scorePaper.model.outlet;

import java.util.List;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 13:40 2017/7/24
 * @Modified By:
 * @Version  
 */
public class SelectQuesPO {

    private String                 factorName;

    private List<SelectQuesUnitPO> quesUnits;

    public String getFactorName() {

        return factorName;
    }

    public void setFactorName(String factorName) {

        this.factorName = factorName;
    }

    public List<SelectQuesUnitPO> getQuesUnits() {

        return quesUnits;
    }

    public void setQuesUnits(List<SelectQuesUnitPO> quesUnits) {

        this.quesUnits = quesUnits;
    }
}
