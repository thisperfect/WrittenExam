
package com.dayee.writtenExam.scorePaper.model.entrance;

import java.util.List;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 15:02 2017/7/26
 * @Modified By:
 * @Version  
 */
public class AssignPaperPO {

    private List<String>        accunntIds;

    private List<String>        paperIds;

    private Integer             scoreType;

    private Integer             anonymous;

    private Integer             sms;

    private Integer             groupCnt;

    private Integer             selectRuleId;

    private String              emailInfo;

    private List<AssignGroupPO> groups;

    public List<String> getAccunntIds() {

        return accunntIds;
    }

    public void setAccunntIds(List<String> accunntIds) {

        this.accunntIds = accunntIds;
    }

    public List<String> getPaperIds() {

        return paperIds;
    }

    public void setPaperIds(List<String> paperIds) {

        this.paperIds = paperIds;
    }

    public Integer getScoreType() {

        return scoreType;
    }

    public void setScoreType(Integer scoreType) {

        this.scoreType = scoreType;
    }

    public Integer getAnonymous() {

        return anonymous;
    }

    public void setAnonymous(Integer anonymous) {

        this.anonymous = anonymous;
    }

    public Integer getSms() {

        return sms;
    }

    public void setSms(Integer sms) {

        this.sms = sms;
    }

    public Integer getGroupCnt() {

        return groupCnt;
    }

    public void setGroupCnt(Integer groupCnt) {

        this.groupCnt = groupCnt;
    }

    public Integer getSelectRuleId() {

        return selectRuleId;
    }

    public void setSelectRuleId(Integer selectRuleId) {

        this.selectRuleId = selectRuleId;
    }

    public String getEmailInfo() {

        return emailInfo;
    }

    public void setEmailInfo(String emailInfo) {

        this.emailInfo = emailInfo;
    }

    public List<AssignGroupPO> getGroups() {

        return groups;
    }

    public void setGroups(List<AssignGroupPO> groups) {

        this.groups = groups;
    }
}
