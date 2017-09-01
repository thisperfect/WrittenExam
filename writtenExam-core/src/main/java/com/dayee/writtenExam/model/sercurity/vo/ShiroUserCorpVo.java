
package com.dayee.writtenExam.model.sercurity.vo;

import java.io.Serializable;

public class ShiroUserCorpVo implements Serializable {

    private static final long serialVersionUID = -6399482479534596310L;

    private String            userId;

    private String            corpUserId;

    private String            dbName;

    private String            corpCode;

    private String            corpName;

    private Integer           corpAuditStatus;

    /** 所在企业的昵称 */
    private String            corpNickName;

    /** 所在企业的实名 */
    private String            corpRealName;

    public ShiroUserCorpVo() {

    }

    public ShiroUserCorpVo(String userId,
                           String corpCode,
                           String corpUserId,
                           String dbName,
                           String corpName,
                           Integer corpAuditStatus) {

        super();
        this.userId = userId;
        this.corpCode = corpCode;
        this.corpUserId = corpUserId;
        this.dbName = dbName;
        this.corpName = corpName;
        this.corpAuditStatus = corpAuditStatus;
    }

    public String getUserId() {

        return userId;
    }

    public String getCorpCode() {

        return corpCode;
    }

    public String getDbName() {

        return dbName;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

    public void setDbName(String dbName) {

        this.dbName = dbName;
    }

    public String getCorpUserId() {

        return corpUserId;
    }

    public void setCorpUserId(String corpUserId) {

        this.corpUserId = corpUserId;
    }

    public String getCorpName() {

        return corpName;
    }

    public void setCorpName(String corpName) {

        this.corpName = corpName;
    }

    public Integer getCorpAuditStatus() {

        return corpAuditStatus;
    }

    public void setCorpAuditStatus(Integer corpAuditStatus) {

        this.corpAuditStatus = corpAuditStatus;
    }

    public String getCorpNickName() {

        return corpNickName;
    }

    public void setCorpNickName(String corpNickName) {

        this.corpNickName = corpNickName;
    }

    public String getCorpRealName() {

        return corpRealName;
    }

    public void setCorpRealName(String corpRealName) {

        this.corpRealName = corpRealName;
    }

}
