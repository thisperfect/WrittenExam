package com.dayee.writtenExam.model;

import java.util.Date;


public class IdCardInfo {
    
    public  IdCardInfo(boolean isValidate){
        this.isValidate = isValidate;
    }
    
    private boolean isValidate;

    private Date birthDay;
    
    private String  gender = "430001";  //430001：男性，430002 女性

    
    
    
    
    public boolean isValidate() {
    
        return isValidate;
    }


    
    public void setValidate(boolean isValidate) {
    
        this.isValidate = isValidate;
    }


    public Date getBirthDay() {
    
        return birthDay;
    }

    
    public void setBirthDay(Date birthDay) {
    
        this.birthDay = birthDay;
    }

    
    public String getGender() {
    
        return gender;
    }

    
    public void setGender(String gender) {
    
        this.gender = gender;
    }
    
    
    
}
