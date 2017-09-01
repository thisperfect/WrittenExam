
package com.dayee.writtenExam.pc.model.result;

import java.util.Date;
import java.util.List;

public class AccountPaperPO {

    private Integer        projectId;

    private Integer        mgrProjectId;

    private String         mgrProjectName;

    private Integer        accountId;

    private Integer        paperId;

    private Float          totalScore;

    private Integer        status;

    private String         corpCode;

    private Float          score;

    private Float          completeRate;

    private Integer        minWindowCount;

    private Integer        closeWindowCount;

    private Date           examDate;

    private Date           completeDate;

    private Integer        attachmentId;

    private Integer        answerQuestionNum;

    private Integer        totalQuestionNum;

    private String         paperAliasName;   // 试卷名称

    private List<ResultPO> resultList;

    public Integer getProjectId() {

        return projectId;
    }

    public void setProjectId(Integer projectId) {

        this.projectId = projectId;
    }

    public Integer getMgrProjectId() {

        return mgrProjectId;
    }

    public void setMgrProjectId(Integer mgrProjectId) {

        this.mgrProjectId = mgrProjectId;
    }

    public Integer getAccountId() {

        return accountId;
    }

    public void setAccountId(Integer accountId) {

        this.accountId = accountId;
    }

    public Integer getPaperId() {

        return paperId;
    }

    public void setPaperId(Integer paperId) {

        this.paperId = paperId;
    }

    public Float getTotalScore() {

        return totalScore;
    }

    public void setTotalScore(Float totalScore) {

        this.totalScore = totalScore;
    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {

        this.status = status;
    }

    public String getCorpCode() {

        return corpCode;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

    public Float getScore() {

        return score;
    }

    public void setScore(Float score) {

        this.score = score;
    }

    public Float getCompleteRate() {

        return completeRate;
    }

    public void setCompleteRate(Float completeRate) {

        this.completeRate = completeRate;
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

    public Integer getAttachmentId() {

        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {

        this.attachmentId = attachmentId;
    }

    public Integer getAnswerQuestionNum() {

        return answerQuestionNum;
    }

    public void setAnswerQuestionNum(Integer answerQuestionNum) {

        this.answerQuestionNum = answerQuestionNum;
    }

    public Integer getTotalQuestionNum() {

        return totalQuestionNum;
    }

    public void setTotalQuestionNum(Integer totalQuestionNum) {

        this.totalQuestionNum = totalQuestionNum;
    }

    public List<ResultPO> getResultList() {

        return resultList;
    }

    public void setResultList(List<ResultPO> resultList) {

        this.resultList = resultList;
    }

    public String getPaperAliasName() {

        return paperAliasName;
    }

    public void setPaperAliasName(String paperAliasName) {

        this.paperAliasName = paperAliasName;
    }

    public String getMgrProjectName() {

        return mgrProjectName;
    }

    public void setMgrProjectName(String mgrProjectName) {

        this.mgrProjectName = mgrProjectName;
    }
}
