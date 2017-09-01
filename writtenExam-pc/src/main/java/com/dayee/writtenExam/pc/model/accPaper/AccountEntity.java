
package com.dayee.writtenExam.pc.model.accPaper;

import com.dayee.writtenExam.framework.ideajdbc.baseEntity.BaseEntity;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;

import java.util.Date;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 9:50 2017/7/21
 * @Modified By:
 * @Version  
 */
@Entity(dataSet = "t_account_info")
public class AccountEntity extends BaseEntity {

    @Property(dataItem = "F_CODE")
    private String  code;

    @Property(dataItem = "F_PAPER_ID")
    private String  paperId;

    @Property(dataItem = "F_SCORE")
    private Float   score;

    @Property(dataItem = "F_EXAM_DATE")
    private Date    examDate;

    @Property(dataItem = "F_COMPLETE_DATE")
    private Date    completeDate;

    @Property(dataItem = "F_ATTACHMENT_ID")
    private Integer attachmentId;

    @Property(dataItem = "F_STATUS")
    private Integer status;

    @Property(dataItem = "F_COMPLETE_RATE")
    private Float   completeRate;

    @Property(dataItem = "F_ORGINAL_SCORE")
    private Float   orginalScore;

    @Property(dataItem = "F_PAPER_NAME_CH")
    private String  paperName;

    @Property(dataItem = "F_TOTAL_SCORE")
    private Integer totalScore;

    @Property(dataItem = "F_PASS_STATUS")
    private Integer passStatus;

    @Property(dataItem = "F_MARKING_STATE")
    private Integer markState;

    @Property(dataItem = "F_MIN_WINDOW_COUNT")
    private Integer minWindowCount;

    @Property(dataItem = "F_CLOSE_WINDOW_COUNT")
    private Integer closeWindowCount;

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getPaperId() {

        return paperId;
    }

    public void setPaperId(String paperId) {

        this.paperId = paperId;
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

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {

        this.status = status;
    }

    public String getPaperName() {

        return paperName;
    }

    public void setPaperName(String paperName) {

        this.paperName = paperName;
    }

    public Integer getTotalScore() {

        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {

        this.totalScore = totalScore;
    }

    public Integer getPassStatus() {

        return passStatus;
    }

    public void setPassStatus(Integer passStatus) {

        this.passStatus = passStatus;
    }

    public Integer getMarkState() {

        return markState;
    }

    public void setMarkState(Integer markState) {

        this.markState = markState;
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

    public Float getOrginalScore() {

        return orginalScore;
    }

    public void setOrginalScore(Float orginalScore) {

        this.orginalScore = orginalScore;
    }
}
