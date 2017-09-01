package com.dayee.writtenExam.exception;

import com.dayee.writtenExam.framework.constant.WaringAndExceptionConstants;
import com.dayee.writtenExam.framework.restful.json.JsonData;
import com.dayee.writtenExam.framework.util.PropertiesEnum;
import com.dayee.writtenExam.framework.util.StringUtils;

/**
 * Created by yq.song on 2016/10/5.
 */
public class ResumeCommonException extends RuntimeException {
	
	private String code;
	
	private String description;
	
	public static ResumeCommonException COMMON_EXCEPTION=new ResumeCommonException(WaringAndExceptionConstants.COMMON.EXCEPTION_PROCESS);
	
	public ResumeCommonException() {
	}
	
	public ResumeCommonException(String code) {
		
		super(code);
		this.code = code;
	}
	
	public ResumeCommonException(String code, String description) {
		super(code);
		this.code = code;
		this.description = description;
	}
	
	public JsonData parseJsonData(){
		
		String value= StringUtils.isBlank(description)? PropertiesEnum.PROMPT.getProperty(code):description;
		return JsonData.exception(code,value);
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
