
package com.dayee.writtenExam.pc.model.statics;

import com.dayee.writtenExam.mongo.annotation.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 13:42 2017/7/20
 * @Modified By:
 * @Version  
 */
@Document(collection = "storePaper")
public class StaticPaperPO {

    @GeneratedValue
    @Id
    @Field("ID")
    private String             mgrPaperId;

    @Field("NAME_CH")
    private String             paperName;

    @Field("DISPLAY_METHOD")
    private Integer            displayMethod;

    @Field("ANSWER_DESCRIPTION")
    private String             answerDescription;

    @Field("EXAM_TIME")
    private Integer            allowTimes;

    @Field("CORP_CODE")
    private String             corpCode;

    @Field("PAPER_SCORE")
    private Integer            paperScore;

    @Field("PAGE_LIST")
    private List<StaticPagePO> spageList;

    public String getMgrPaperId() {

        return mgrPaperId;
    }

    public void setMgrPaperId(String mgrPaperId) {

        this.mgrPaperId = mgrPaperId;
    }

    public String getPaperName() {

        return paperName;
    }

    public void setPaperName(String paperName) {

        this.paperName = paperName;
    }

    public Integer getDisplayMethod() {

        return displayMethod;
    }

    public void setDisplayMethod(Integer displayMethod) {

        this.displayMethod = displayMethod;
    }

    public String getAnswerDescription() {

        return answerDescription;
    }

    public void setAnswerDescription(String answerDescription) {

        this.answerDescription = answerDescription;
    }

    public Integer getAllowTimes() {

        return allowTimes;
    }

    public void setAllowTimes(Integer allowTimes) {

        this.allowTimes = allowTimes;
    }

    public String getCorpCode() {

        return corpCode;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

    public Integer getPaperScore() {

        return paperScore;
    }

    public void setPaperScore(Integer paperScore) {

        this.paperScore = paperScore;
    }

    public List<StaticPagePO> getSpageList() {

        return spageList;
    }

    public void setSpageList(List<StaticPagePO> spageList) {

        this.spageList = spageList;
    }
}
