
package com.dayee.writtenExam.model.sercurity.vo;

import java.io.Serializable;

/**
 * 该对象会被放入shiro中
 * 
 * @author cooltain
 */
public class ShiroUserVo implements Serializable {

    private static final long serialVersionUID = 3388647156900010862L;

    private String            userId;

    private String            name;
    private String            nickName;
    
    private String            password;

    /** 头像URL地址 */
    private String            curPersonalPicURL;
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getCurPersonalPicURL() {

        return curPersonalPicURL;
    }

    public void setCurPersonalPicURL(String curPersonalPicURL) {

        this.curPersonalPicURL = curPersonalPicURL;
    }

}
