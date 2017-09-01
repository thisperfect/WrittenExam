
package com.dayee.writtenExam.pc.model.result;

/**
 * 数据库持久对象，与数据库字段一一对应
 * 
 * @author 杨路路
 */
public class UserInfoPO {

    private Integer mgrUserId;

    private String  userName;

    private String  gender;

    private String  mobilePhone;

    private String  email;

    private String  education;

    private Integer age;

    private String  workExperience;

    private String  workPlace;

    public Integer getMgrUserId() {

        return mgrUserId;
    }

    public void setMgrUserId(Integer mgrUserId) {

        this.mgrUserId = mgrUserId;
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

    public String getMobilePhone() {

        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {

        this.mobilePhone = mobilePhone;
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

    public Integer getAge() {

        return age;
    }

    public void setAge(Integer age) {

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

}
