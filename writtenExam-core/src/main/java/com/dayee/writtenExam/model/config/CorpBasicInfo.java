
package com.dayee.writtenExam.model.config;

public class CorpBasicInfo extends MailConfig {

    private byte[] logo;

    private String color;

    private String type;		//是否上传过企业logo
    
    private BaseCorpInfo baseCorpInfo;
    
    public String getColor() {

        return color;
    }

    public void setColor(String color) {

        this.color = color;
    }

    public byte[] getLogo() {

        return logo;
    }

    public void setLogo(byte[] logo) {

        this.logo = logo;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BaseCorpInfo getBaseCorpInfo() {
		return baseCorpInfo;
	}

	public void setBaseCorpInfo(BaseCorpInfo baseCorpInfo) {
		this.baseCorpInfo = baseCorpInfo;
	}

}
