
package com.dayee.writtenExam.scorePaper.model.entrance;

import java.util.List;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 15:06 2017/7/26
 * @Modified By:
 * @Version  
 */
public class AssignGroupPO {

    private Integer               number;

    private List<AssignOfficerPO> officers;

    public Integer getNumber() {

        return number;
    }

    public void setNumber(Integer number) {

        this.number = number;
    }

    public List<AssignOfficerPO> getOfficers() {

        return officers;
    }

    public void setOfficers(List<AssignOfficerPO> officers) {

        this.officers = officers;
    }
}
