package com.dayee.writtenExam.model.config;

import com.dayee.writtenExam.model.CorpPartitionEntity;
import com.ideamoment.ideadata.annotation.DataItemType;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;
import com.ideamoment.ideadata.annotation.Transient;

@Entity(dataSet = "T_CORP_BASIC_INFO")
public class BaseCorpInfo extends CorpPartitionEntity{

	@Property(dataItem = "C_PHOTO_ID", type = DataItemType.VARCHAR, length = 200)
	private String photoId; // 照片
	
	@Transient
	private String logoUrl;
	
    @Transient
    protected String          currentDb;
    
	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getCurrentDb() {
		return currentDb;
	}

	public void setCurrentDb(String currentDb) {
		this.currentDb = currentDb;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	
}
