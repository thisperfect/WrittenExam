package com.dayee.writtenExam.pc.model;

import com.dayee.writtenExam.model.BaseEntity;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;
import org.apache.shiro.dao.DataAccessException;

import java.util.Date;

/**
 * Created by wangchen on 2017/7/8.
 */
@Entity(dataSet = "t_login_user")
public class LoginUser extends BaseEntity{

    @Property(dataItem = "C_USER_NAME")
    private String userName;

    @Property(dataItem = "C_PASSWORD")
    private String passWord;

    @Property(dataItem = "C_LAST_LOGIN_TIME")
    private Date lastLoginTime;

    @Property(dataItem = "C_CREATE_TIME")
    private Date createTime;

    @Property(dataItem = "C_CREATOR")
    private String creator;

    @Property(dataItem = "C_MODIFY_TIME")
    private Date modifyTime;

    @Property(dataItem = "C_MODIFIER")
    private String modifier;

    @Property(dataItem = "C_STATUS")
    private String status;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
