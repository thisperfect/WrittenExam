
package com.dayee.writtenExam.scorePaper.model.ideaJdbc;

import com.dayee.writtenExam.framework.ideajdbc.baseEntity.BaseEntity;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 16:25 2017/7/26
 * @Modified By:
 * @Version  
 */
@Entity(dataSet = "t_score_officer")
public class ScoreOfficerEntity extends BaseEntity {

    @Property(dataItem = "F_NAME")
    private String  name;

    @Property(dataItem = "F_PHONE")
    private String  phone;

    @Property(dataItem = "F_EMAIL")
    private String  email;

    @Property(dataItem = "F_STATUS")
    private Integer status;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {

        this.status = status;
    }
}
