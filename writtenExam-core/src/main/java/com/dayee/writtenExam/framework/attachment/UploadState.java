package com.dayee.writtenExam.framework.attachment;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 上传结果  
 *
 */
public class UploadState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String result;//上传是否成功，success or error
	
	private String fileId;//文件ID
	
	private String message;//失败消息
	
	
	public static final String ERROR = "error";
	
	public static final String SUCCESS = "success";
	
	public boolean isSuccess(){
	    
	    return SUCCESS.equals(result);
	}
	

	public UploadState() {
	}
	
	public UploadState(String result,String message){
		this.result = result;
		this.message = message;
	}

	public UploadState(String result, String fileId, String message) {
		this.result = result;
		this.fileId = fileId;
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
