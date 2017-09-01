
package com.dayee.writtenExam.pc.model.accPaper;

import com.dayee.writtenExam.framework.ideajdbc.baseEntity.BaseEntity;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 9:48 2017/7/18
 * @Modified By:
 * @Version  
 */
@Entity(dataSet = "t_candidates_info")
public class CandidateEntity extends BaseEntity {

    @Property(dataItem = "f_account_id")
    private Integer accountId;

    @Property(dataItem = "f_user_name")
    private String  userName;

    @Property(dataItem = "f_gender")
    private String  gender;

    @Property(dataItem = "f_mobile_phone")
    private String  mbilePhone;

    @Property(dataItem = "f_email")
    private String  email;

    @Property(dataItem = "f_education")
    private String  education;

    @Property(dataItem = "f_age")
    private String  age;

    @Property(dataItem = "f_work_experience")
    private String  workExperience;

    @Property(dataItem = "f_work_place")
    private String  workPlace;

    @Property(dataItem = "f_corp_code")
    private String  corpCode;

    public Integer getAccountId() {

        return accountId;
    }

    public void setAccountId(Integer accountId) {

        this.accountId = accountId;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }

    public String getMbilePhone() {

        return mbilePhone;
    }

    public void setMbilePhone(String mbilePhone) {

        this.mbilePhone = mbilePhone;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getEducation() {

        return education;
    }

    public void setEducation(String education) {

        this.education = education;
    }

    public String getAge() {

        return age;
    }

    public void setAge(String age) {

        this.age = age;
    }

    public String getWorkExperience() {

        return workExperience;
    }

    public void setWorkExperience(String workExperience) {

        this.workExperience = workExperience;
    }

    public String getWorkPlace() {

        return workPlace;
    }

    public void setWorkPlace(String workPlace) {

        this.workPlace = workPlace;
    }

    public String getCorpCode() {

        return corpCode;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }
}
