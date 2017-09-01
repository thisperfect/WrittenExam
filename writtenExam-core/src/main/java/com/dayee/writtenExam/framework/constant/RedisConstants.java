package com.dayee.writtenExam.framework.constant;

/**
 * Created by yq.song on 2016/9/19.
 */
public class RedisConstants {
	
	/**redis通用key分隔符**/
	public static final String KEY_SEPARATOR=":";
	
	/**验证码key规则**/
	public static final String KEY_VFCODE_PERFIX="vfCode:";
	
	/**字典表key规则**/
	public static final String KEY_DIC_PERFIX="dic:";
	
	/**用户等级key规则**/
	public static final String KEY_USER_LEVEL_PERFIX="level:";
	/**用户等级field**/
	public static final String KEY_USER_LEVEL_FIELD_NAME="name";
	public static final String KEY_USER_LEVEL_FIELD_PRICE="price";
	public static final String KEY_USER_LEVEL_FIELD_SCORE="score";
	public static final String KEY_USER_LEVEL_FIELD_MAX_RESUME="maxResume";
	public static final String KEY_USER_LEVEL_FIELD_UPGRADE_PERSON_NUM="upgradePersonNum";
	public static final String KEY_USER_LEVEL_FIELD_NEXT_LEVEL_CODE="nextLevelCode";
	
	/**用户ID姓名对应关系key规则**/
	public static final String KEY_IDNAME_PREFIX ="idAndName:";
	
	/**用户ID头像对应关系key规则**/
	public static final String KEY_IDPHOTO_PREFIX ="idAndPhoto:";
	
	/**字典中文字段名**/
	public static final String KEY_DIC_FIELD_NAMECH="nameCh";
	
	/**字典英文文字段名**/
	public static final String KEY_DIC_FIELD_NAMEEN="nameEn";
	
	/**首次登陆标识**/
	public static final String KEY_FIRST_LOGIN_PREFIX="firstLogin:";

	/**任务配置项前缀**/
	public static final String KEY_TASK_PREFIX ="task:";

	public static final String KEY_UNIVERSITIES = "KEY_UNIVERSITYS";

	/**防止用户重复提交前缀**/
	public static final String KEY_DUPLICATE_SUBMIT_PREFIX = "dupSub:";

	/**导入taskId重复执行次数前缀**/
	public static final String KEY_IMPORT_TASK_TIME = "importTaskTime:";
	
	/**自动登录key前缀**/
	public static final String KEY_AUTO_LOGIN="autoLogin:";

	/**redis 导入队列Key - 常规队列**/
	public static final String KEY_IMPORT_QUEUE="importQueue";

	/** redis 导入队列Key - 定时任务队列 **/
	public static final String KEY_TIMER_IMPORT_QUEUE="timerImportQueue";

	/** 百度AI token **/
	public static final String KEY_BAIDU_AI_TOKEN="baiduAIToken";
	
	/**验证码相关key*/
	public static final class VFCODE{
		
		//验证码限制-手机号
		public static final String KEY_APP_VFCODE_PHONE = "app.vfCode.phone.";
		
		//验证码限制-IP
		public static final String KEY_APP_VFCODE_IP = "app.vfCode.ip.";
		
		//验证码每天发送次数限制-手机号
		public static final String KEY_APP_VFCODE_PHONE_TIME = "app.vfCode.phone.time.";
		
		//验证码每天发送次数限制-IP
		public static final String KEY_APP_VFCODE_IP_TIME = "app.vfCode.ip.time.";
		
		//验证码每天发送次数限制-IP
		public static final String KEY_PICTURE_CODE = "pictureCode.";
		
	}

}
