package com.dayee.writtenExam.model.writtenExam;


import com.dayee.writtenExam.model.ideaJdbc.BaseEntity;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/7.
 */

@Entity(dataSet="T_WRITTENEXAM_USER")
public class WrittenExamUser extends BaseEntity {

    /***
     * 用户名
     */
    @Property(dataItem="F_USER_NAME")
    private String   userName;

    /***
     * 密码
     */
    @Property(dataItem="F_PASSWORD")
    private String   passWord;

    /***
     * 开始日期
     */
    @Property(dataItem="F_BEGIN_TIME")
    private Date     beginTime;

    /***
     * 截止日期
     */
    @Property(dataItem="F_END_TIME")
    private Date     endTime;

    /***
     * 是否监控权限
     */
    @Property(dataItem="F_IS_MONITOR")
    private Integer  isMonitor;

    /****
     * 是否阅卷权限
     */
    @Property(dataItem="F_IS_SCOREPAPER")
    private Integer  isScorePaper;

    /***
     * 是否有分享权限
     */
    @Property(dataItem="F_IS_SHARE")
    private Integer  isShare;

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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsMonitor() {
        return isMonitor;
    }

    public void setIsMonitor(Integer isMonitor) {
        this.isMonitor = isMonitor;
    }

    public Integer getIsScorePaper() {
        return isScorePaper;
    }

    public void setIsScorePaper(Integer isScorePaper) {
        this.isScorePaper = isScorePaper;
    }

    public Integer getIsShare() {
        return isShare;
    }

    public void setIsShare(Integer isShare) {
        this.isShare = isShare;
    }
}
