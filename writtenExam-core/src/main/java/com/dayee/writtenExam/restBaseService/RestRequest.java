
package com.dayee.writtenExam.restBaseService;

public class RestRequest {

    private Object  content;

    private String  code;

    private String  groupType;

    private String  groupUser;

    private String  licenseKey;

    private String  ip;

    private Integer accountId;

    public Object getContent() {

        return content;
    }

    public void setContent(Object content) {

        this.content = content;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getGroupType() {

        return groupType;
    }

    public void setGroupType(String groupType) {

        this.groupType = groupType;
    }

    public String getGroupUser() {

        return groupUser;
    }

    public void setGroupUser(String groupUser) {

        this.groupUser = groupUser;
    }

    public String getLicenseKey() {

        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {

        this.licenseKey = licenseKey;
    }

    public String getIp() {

        return this.ip;
    }

    public void setIp(String ip) {

        this.ip = ip;
    }

    public Integer getAccountId() {

        return accountId;
    }

    public void setAccountId(Integer accountId) {

        this.accountId = accountId;
    }

}
