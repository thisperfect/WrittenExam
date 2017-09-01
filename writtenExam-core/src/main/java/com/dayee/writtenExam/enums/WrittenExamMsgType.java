package com.dayee.writtenExam.enums;

/**
 * Created by yq.song on 2016/12/28.
 */
public enum WrittenExamMsgType {
	
	//邮箱导入
	import_email("email"),
	
	//本地文件上传
	import_local_upload("localUpload"),
	
	//渠道导入
	import_channel("channel"),
	
	//简历被下载
	resume_beDownload("download"),
	
	//简历被评价
	resume_beReply("reply"),
	
	//邀请用户成为有效用户
	resume_inviteToValidUser("valid"),
	
	//用户等级提升
	resume_levelUp("levelUp"),
	
	//用户等级下降
	resume_levelDown("levelDown");
	
	private String msgType;
	
	WrittenExamMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public String getMsgType(){
		return msgType;
	}
	
}
