
package com.dayee.writtenExam.scorePaper.model.outlet;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 13:40 2017/7/24
 * @Modified By:
 * @Version  
 */
public class SelectQuesUnitPO {

    private String  quesId;

    private String  paperId;

    private String  paperName;

    private String  paperShortName;

    private String  number;

    private String  content;

    private String  corpCode;

    private Integer subquesNum;

    public String getQuesId() {

        return quesId;
    }

    public void setQuesId(String quesId) {

        this.quesId = quesId;
    }

    public String getPaperName() {

        return paperName;
    }

    public void setPaperName(String paperName) {

        this.paperName = paperName;
    }

    public String getPaperShortName() {

        return paperShortName;
    }

    public void setPaperShortName(String paperShortName) {

        this.paperShortName = paperShortName;
    }

    public String getNumber() {

        return number;
    }

    public void setNumber(String number) {

        this.number = number;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public String getPaperId() {

        return paperId;
    }

    public void setPaperId(String paperId) {

        this.paperId = paperId;
    }

    public String getCorpCode() {

        return corpCode;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

    public Integer getSubquesNum() {

        return subquesNum;
    }

    public void setSubquesNum(Integer subquesNum) {

        this.subquesNum = subquesNum;
    }

    @Override
    public int hashCode() {

        return paperId.hashCode() + quesId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        SelectQuesUnitPO u = (SelectQuesUnitPO) obj;
        return (paperId + quesId).equals(u.getPaperId() + u.getQuesId());
    }
}
