
package com.dayee.writtenExam.pc.model.result;

import java.util.Date;
import java.util.List;

public class AccountInfoPO {

    private Integer              accountId;

    private Integer              mgrAccountId;

    private Integer              status;

    private String               code;

    private String               corpCode;

    /* 最大切屏次数 */
    private Integer              minWindowCount;

    /* 最大中断次数 */
    private Integer              closeWindowCount;

    /* 是否开启视频监控 */
    private Integer              openCameraEnable;

    private Date                 examDate;

    private Date                 completeDate;

    private Integer              questionCount;

    private Date                 beginTime;

    private Date                 endTime;

    private UserInfoPO           userInfo;

    private List<AccountPaperPO> examPaperList;

    public Integer getAccountId() {

        return accountId;
    }

    public void setAccountId(Integer accountId) {

        this.accountId = accountId;
    }

    public Integer getMgrAccountId() {

        return mgrAccountId;
    }

    public void setMgrAccountId(Integer mgrAccountId) {

        this.mgrAccountId = mgrAccountId;
    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {

        this.status = status;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getCorpCode() {

        return corpCode;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

    public Integer getMinWindowCount() {

        return minWindowCount;
    }

    public void setMinWindowCount(Integer minWindowCount) {

        this.minWindowCount = minWindowCount;
    }

    public Integer getCloseWindowCount() {

        return closeWindowCount;
    }

    public void setCloseWindowCount(Integer closeWindowCount) {

        this.closeWindowCount = closeWindowCount;
    }

    public Integer getOpenCameraEnable() {

        return openCameraEnable;
    }

    public void setOpenCameraEnable(Integer openCameraEnable) {

        this.openCameraEnable = openCameraEnable;
    }

    public Date getExamDate() {

        return examDate;
    }

    public void setExamDate(Date examDate) {

        this.examDate = examDate;
    }

    public Date getCompleteDate() {

        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {

        this.completeDate = completeDate;
    }

    public Integer getQuestionCount() {

        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {

        this.questionCount = questionCount;
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

    public List<AccountPaperPO> getExamPaperList() {

        return examPaperList;
    }

    public UserInfoPO getUserInfo() {

        return userInfo;
    }

    public void setUserInfo(UserInfoPO userInfo) {

        this.userInfo = userInfo;
    }

    public void setExamPaperList(List<AccountPaperPO> examPaperList) {

        this.examPaperList = examPaperList;
    }
}
