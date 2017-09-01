
package com.dayee.writtenExam.model.mongo;

import com.dayee.writtenExam.mongo.annotation.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 17:03 2017/7/20
 * @Modified By:
 * @Version  
 */
@Document(collection = "subjectPaperQues")
public class SubjectiveQuesPO {

    @GeneratedValue
    @Id
    @Field("ID")
    private String                           id;

    @Field("PAPER_ID")
    private String                           paperId;

    @Field("PAPER_NAME")
    private String                           paperName;

    @Field("CORP_CODE")
    private String                           corpCode;

    @Field("SUBJECT_QUES_ID")
    private Map<String, Map<String, String>> sujectiveQues;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getPaperId() {

        return paperId;
    }

    public void setPaperId(String paperId) {

        this.paperId = paperId;
    }

    public String getPaperName() {

        return paperName;
    }

    public void setPaperName(String paperName) {

        this.paperName = paperName;
    }

    public String getCorpCode() {

        return corpCode;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

    public Map<String, Map<String, String>> getSujectiveQues() {

        return sujectiveQues;
    }

    public void setSujectiveQues(Map<String, Map<String, String>> sujectiveQues) {

        this.sujectiveQues = sujectiveQues;
    }
}
