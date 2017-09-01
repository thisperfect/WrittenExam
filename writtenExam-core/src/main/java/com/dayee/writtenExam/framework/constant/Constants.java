
package com.dayee.writtenExam.framework.constant;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class Constants {


	public static final int YES = 0;


	public static final int NO = 1;


	public static final String DEFAULT_ENCODING = "UTF-8";


	public static final Integer DEFAULT_EMAIL_SMTP_PORT = 25;                        // 默认SMTP


	/***************************redis相关key定义**************************************/
	public static final String REDIS_SEARCH_COUNT = "redis.search.count";

	public static final String LOGIN_DELIMITER = "%&#@";

	public static final String LOGIN_PASSWORD = "pwd%&#@";

	public static final String REGISTER_DEFAULT_PASSWORD ="dayee8888";

	public static final String DEBUG_SENDSMS = "yuncai.debug.sendSms";

	//-------------------------------------------------------------------------------------

	/**
	 * 通用常量
	 **/
	public static final class COMMON {

		/**
		 * 通用——否
		 **/
		public static final String NO = "0";

		/**
		 * 通用——是
		 **/
		public static final String YES = "1";

		/**
		 * 程序执行成功
		 **/
		public static final String RETURN_SUCCESS = "success";

		/**
		 * 专用登录密码
		 **/
		public static final String LOGIN_PASSWORD = "pwd%&#@";

		/**
		 * 密码验证正则（6位以上，数字、字母组合)
		 **/
		public static final String PWD_REG = "^(?![0-9]*$)(?![a-zA-Z]*$)[a-zA-Z0-9]{6,}$";

		/**
		 * 全部文件夹ID
		 **/
		public static final String FOLDER_TOP_PARENT_ID = "-1";

		/**
		 * 虚拟顶级文件夹ID
		 **/
		public static final String FOLDER_VIRTUAL_TOP_PARENT_ID = "-2";

		/**
		 * 预置文件夹-收件箱
		 **/
		public static final String FOLDER_INBOX_ID = "0";

		/**
		 * 预置文件夹-收件箱——公海下载
		 **/
		public static final String FOLDER_INBOX_SEA_DOWNLOAD_ID = "01";

		/**
		 * 预置文件夹-收件箱——本地上传
		 **/
		public static final String FOLDER_INBOX_LOCAL_UPLOAD_ID = "02";

		/**
		 * 预置文件夹-收藏夹
		 **/
		public static final String FOLDER_FAVORITE_ID = "1";

		/**
		 * 预置文件夹-淘汰夹
		 **/
		public static final String FOLDER_ELIMINATE_ID = "2";

		/**
		 * 预置文件夹-回收站
		 **/
		public static final String FOLDER_RECYCLE_ID = "3";

		/**
		 * 预置文件夹-冻结简历
		 **/
		public static final String FOLDER_FREEZE_ID = "4";

		/**
		 * 预置文件夹-解冻简历
		 **/
		public static final String FOLDER_UN_FREEZE_ID = "5";

		/**
		 * 招聘宝HOST
		 **/
		public static final String ZPB_HOST = "zpb.host";

		/**
		 * 本机HOST
		 **/
		public static final String LOCAL_HOST = "local.host";

		public static final String PC_APP_LOCAL_HOST = "pc.app.local.host";

		/**
		 * 普通用户Id
		 **/
		public static final String USER_LEVEL_NORMAL_ID = "0";

		/**
		 * 注册邀请码 codes
		 **/
		public static final String PHONE_CODE = "defjbhagca";

	}

	/**
	 * 验证码相关常量
	 **/
	public static final class VFCODE {

		/**
		 * 验证码有效时间配置文件key
		 **/
		public static final String VFCODE_USEFULETIME = "vfCode.usefulTime";

		/**
		 * 短信验证码模板
		 **/
		public static final String VFCODE_PHONE_PATH = "msgTemplate/vfCode_phone.vm";

		/**
		 * 发送普通验证码
		 **/
		public static final String VFCODE_TYPE_COMMON = "0";

		/**
		 * 登录验证码
		 **/
		public static final String VFCODE_TYPE_LOGIN = "1";
		/**
		 * 绑定手机号
		 */
		public static final String VFCODE_TYPE_BIND = "2";

		/**
		 * 修改手机号
		 */
		public static final String VFCODE_TYPE_MODIFY_MOBILE = "3";
		
		
		/*****验证码存放session的Key*****/
		public static final String SESSION_PICTURE_VFCODE_KEY="pictureVfCode";
		
		/*****验证码限制次数key*****/
		public static final String VFCODE_IP_TIME_KEY="vfCode.ip.time";
		public static final String VFCODE_PHONE_TIME_KEY="vfCode.phone.time";
		
		/*****验证码限制发送时间*****/
		public static final String VFCODE_LIMIT_SECOND_KEY="vfCode.limit.second";

		/*****活动期间不做验证码限制*****/
		public static final String VFCODE_ACTIVITY_DATE="vfcode.activity.date";

	}

	/**
	 * 注册相关常量
	 **/
	public static final class REGISTER {

		/**
		 * 注册来源-本地
		 **/
		public static final int SOURCE_LOCAL = 0;

		/**
		 * 注册来源-招聘宝
		 **/
		public static final int SOURCE_ZPB = 1;
	}

	public static final class BIND_EMAIL {
		/**
		 * pop3协议 - 0
		 **/
		public static final Integer PROCOTOL_TYPE_POP3 = 0;

		/**
		 * IMAP协议 - 1
		 **/
		public static final Integer PROCOTOL_TYPE_IMAP = 1;

		/**
		 * Exchange协议 - 2
		 **/
		public static final Integer PROCOTOL_TYPE_EXCHANGE = 2;

		/**
		 * Exchange2007_SP1 - 1
		 **/
		public static final Integer EXCHANHE_TYPE_07SP1 = 1;

		/**
		 * Exchange2010 - 2
		 **/
		public static final Integer EXCHANGE_TYPE_10 = 2;

		/**
		 * Exchange2010SP1 - 3
		 **/
		public static final Integer EXCHANGE_TYPE_10SP1 = 3;

		/**
		 * 邮箱状态:有效 - 0
		 **/
		public static final Integer MAIL_STATUS_VALID = 0;

		/**
		 * 邮箱状态:无效 - 1
		 **/
		public static final Integer MAIL_STATUS_INVALID = 1;

		/**
		 * 连接状态 - 成功
		 */
		public static final Integer CONNECT_SUCCESS = 1;

		/**
		 * 连接状态 - 用户名或密码错误
		 */
		public static final Integer CONNECT_AUTH_FAIL = 2;

		/**
		 * 连接状态 - 连接失败
		 */
		public static final Integer CONNECT_FAIL = 3;
	}

	public static final class RESUME_RELA_INFO {

		/**
		 * 简历来源:邮箱
		 **/
		public static final int SOURCE_EMAIL = 0;

		/**
		 * 简历来源:文件上传
		 **/
		public static final int SOURCE_FILE = 1;

		/**
		 * 简历来源:下载
		 **/
		public static final int SOURCE_DOWNLOAD = 2;

		/**
		 * 简历来源:渠道
		 */
		public static final int SOURCE_NET_CHANNEL = 3;


		/**
		 * 渠道(51job)
		 */
		public static final int SOURCE_NET_CHANNEL_51JOB = 1;

		/**
		 * 渠道(智联)
		 */
		public static final int SOURCE_NET_CHANNEL_ZHILIAN = 2;

	}

	/**
	 * 简历查询相关常量
	 **/
	public static final class RESUME_SEARCH {

		/**
		 * 查询简历云盘
		 **/
		public static final String SEARCH_DISK = "0";

		/**
		 * 查询简历公海
		 **/
		public static final String SEARCH_SEA = "1";

		/**
		 * 查询简历公海——我共享的
		 **/
		public static final String SEARCH_SEA_SHARE = "0";

		/**
		 * 查询简历公海——我下载的
		 **/
		public static final String SEARCH_SEA_DOWNLOAD = "1";

		/**
		 * 查询简历公海——我暂存的
		 **/
		public static final String SEARCH_SEA_TEMPSAVE = "2";

		/**
		 * 查询简历云盘——已共享
		 **/
		public static final String SEARCH_DISK_SHARE = "0";

		/**
		 * 查询简历云盘——未共享
		 **/
		public static final String SEARCH_DISK_NO_SHARE = "1";

		/**
		 * 查询简历云盘——已发送
		 **/
		public static final String SEARCH_DISK_SEND = "2";

		/**
		 * 查询简历云盘——未发送
		 **/
		public static final String SEARCH_DISK_NO_SEND = "3";

	}

	/**
	 * 兑换明细查询相关常量
	 **/
	public static final class EXCHANGE_SEARCH {

		/**
		 * 消费
		 **/
		public static final String EXCHANGE_CONSUME = "0";

		/**
		 * 收益
		 **/
		public static final String EXCHANGE_INCOME = "1";

	}

	/**
	 * 邮件发送相关常量
	 **/
	public static final class EMAIL {

		/**
		 * 发送邮件模板
		 **/
		public static final String EMAIL_PATH = "emailTemplate/resume_send_email.vm";

	}

	/**
	 * 简历导出常量
	 **/
	public static final class RESUME_EXPORT {

		/**
		 * 导出文件类型-word2003
		 **/
		public static final int FILE_TYPE_WORD03 = 0;

		/**
		 * 导出文件类型-excel2003
		 **/
		public static final int FILE_TYPE_EXCEL03 = 1;

		/**
		 * 导出文件类型-xml
		 **/
		public static final int FILE_TYPE_XML = 2;
	}

	/**
	 * 简历常量
	 **/
	public static final class RESUME {

		/**
		 * 中文简历
		 **/
		public static final int LANGUAGE_CHINESE = 1;

		/**
		 * 英文简历
		 **/
		public static final int LANGUAGE_ENGLISH = 2;

	}

	/**
	 * 文件常量
	 **/
	public static final class FILE {

		/**
		 * 上传文件类型 - 照片
		 **/
		public static final int TYPE_PHOTO = 1;

		/**
		 * 上传文件类型 - 档案
		 **/
		public static final int TYPE_ARCHIVE = 2;

		/**
		 * 上传文件类型 - 合同
		 **/
		public static final int TYPE_CONTRACT = 3;

		/**
		 * 上传文件类型 - 简历
		 **/
		public static final int TYPE_RESUME = 4;

		/**
		 * 上传文件类型 - 简历预览文件
		 */
		public static final int TYPE_RESUME_PREVIEW = 5;

		/**
		 * 附件预览处理服务器url
		 */
		public static final String ATTACHMENT_PREVIEW_URL = "attachment.preview.service.url";
	}

	/**
	 * 用户规则常量
	 **/
	public static final class RULE {

		/**
		 * 是否目前绑定招聘宝
		 **/
		public static final String IS_BIND_ZPB = "IS_BIND_ZPB";

		/**
		 * 绑定的招聘宝userId
		 **/
		public static final String ZPB_USER_ID = "ZPB_USER_ID";

		/**
		 * 登录招聘宝所需authCode
		 **/
		public static final String ZPB_AUTHCODE = "ZPB_AUTHCODE";

		/**
		 * 招聘宝用户登录所需authCode
		 **/
		public static final String JLY_AUTHCODE = "JLY_AUTHCODE";

		/**
		 * 是否已完成新手指引任务
		 */
		public static final String FINISHED_GUIDE_TASK = "FINISHED_GUIDE_TASK";

	}

	/**
	 * 用户行为
	 */
	public static final class USER_BEHAVIOUR {

		/**
		 * 签到
		 */
		public static final String BEHAVIOUR_SIGN_IN = "signIn";
		/**
		 * 导入简历
		 */
		public static final String BEHAVIOUR_IMPORT = "importResume";
		/**
		 * 简历分享
		 */
		public static final String BEHAVIOUR_SHARE = "shareResume";
		/**
		 * 取消简历分享
		 */
		public static final String BEHAVIOUR_CANCEL_SHARE = "cancelShareResume";
		/**
		 * 绑定邮箱
		 */
		public static final String BEHAVIOUR_BIND_MAIL = "bindEmail";
		/**
		 * 绑定渠道
		 */
		public static final String BEHAVIOUR_BIND_CHANNEL = "bindChannel";
		/**
		 * 评论简历
		 */
		public static final String BEHAVIOUR_COMMENT = "commentResume";
		/**
		 * 发帖
		 */
		public static final String BEHAVIOUR_POST_FORUM = "postForum";
		/**
		 * 推荐简历
		 */
		public static final String BEHAVIOUR_RECOMMEND_RESUME = "recommendResume";
		/**
		 * 邀请用户
		 */
		public static final String BEHAVIOUR_RECOMMEND_USER = "recommendUser";
		/**
		 * 被用户邀请
		 */
		public static final String BEHAVIOUR_BE_RECOMMENDED_USER = "beRecommendedUser";
	}

	/**
	 * 简历标签
	 */
	public static final class LABEL {
		/**
		 * 系统标签
		 */
		public static final String TYPE_IS_SYS = "1";

		/**
		 * 简历评价标签
		 */
		public static final String  TYPE_IS_RESUME_COMMENT="3";

		/**
		 * 用户标签
		 */
		public static final String TYPE_IS_USER = "2";

		/**
		 * 0 代表有效
		 */
		public static final String STATUS_VALID = "0";

		/**
		 * 1 代表无效
		 */
		public static final String STATUS_INVALID = "1";

		public static final String UNIVERSITY_TYPE_211 = "1";

		public static final String UNIVERSITY_TYPE_985 = "3";

		public static final String LABEL_NAME_211 = "211";

		public static final String LABEL_NAME_985 = "985";

		public static final String NO_CONTACT_METHOD = "无联系方式";

	}

	/**
	 * 用户任务
	 */
	public static final class USER_TASK {

		/**
		 * 新手任务默认时间标示dateMark
		 */
		public static final String GUIDE_TASK_DATE_MARK = "00000000";
		/**
		 * 分享简历任务名
		 */
		public static final String SHARE_RESUME_TASK_NAME = "SHARE_RESUME";
		/**
		 * 任务类型-特殊任务
		 */
		public static final int TYPE_SPECIAL = -1;
		/**
		 * 任务类型-新手指引任务
		 */
		public static final int TYPE_GUIDE = 0;
		/**
		 * 任务类型-日常任务
		 */
		public static final int TYPE_DAILY = 1;
		/**
		 * 任务类型-奖励任务
		 */
		public static final int TYPE_REWORD = 2;
		/**
		 * 新手任务记录默认的uniqueKey集合值
		 */
		public static final List<JSONObject> GUIDE_TASK_UNIQUE_KEY_LIST = new ArrayList<>();

		static {
			GUIDE_TASK_UNIQUE_KEY_LIST.add(JSONObject.parseObject("{\"uniqueKey\":\"12345678901234567890123456789012\"}"));
		}
	}

	/**
	 * 用户等级
	 **/
	public static class USER_LEVEL {

		/**
		 * 免费账户
		 **/
		public static final String LEVEL_A = "A";

		/**
		 * 初级账户
		 **/
		public static final String LEVEL_B = "B";

		/**
		 * 中级账户
		 **/
		public static final String LEVEL_C = "C";

		/**
		 * 高级账户
		 **/
		public static final String LEVEL_D = "D";

		/**
		 * VIP账户
		 **/
		public static final String LEVEL_E = "E";

		/**
		 * 有效用户初始化状态
		 **/
		public static final String USER_VALID_INIT = "2";

		/**
		 * 邀请用户链接
		 **/
		public static final String INVITE_USER_URL = "invite.url";

	}

	/**
	 * 活动相关
	 **/
	public static class ACTIVITY {

		/**
		 * 登录是否跳转活动特定登录页
		 **/
		public static final String ACTIVITY_URL_ENABLE = "activity.url.enable";

		/**
		 * 活动注册页
		 **/
		public static final String ACTIVITY_REGISTER_URL = "activity.register.url";

		/**
		 * 正常注册页
		 **/
		public static final String NORMAL_REGISTER_URL = "normal.register.url";

	}

	/**
	 * 二维码
	 **/
	public static final class RQCODE {
		/**
		 * 生成二维码 格式
		 **/
		public static final String QRCODE_IMAGE_TYPE_JPG = ".jpg";
		public static final String QRCODE_IMAGE_TYPE_PNG = ".png";
		public static final String QRCODE_IMAGE_TYPE_GIF = ".gif";
		public static final String BIND_URL = "qrcode.app.bind.url";
	}

	public static class DIC {

		/**
		 * 男Code
		 **/
		public static final String GENDER_MAN = "0/599/604";

		/**
		 * 女Code
		 **/
		public static final String GENDER_WOMEN = "0/599/605";
	}

	public static class IMPORT {
		/**
		 * 导入线程队列大小
		 **/
		public static final String IMPORT_QUEUE_SIZE = "import.queue.size";

		/**
		 * 导入消费线程数量
		 **/
		public static final String IMPORT_CONSUME_THREAD_NUM = "import.consume.thread.num";
	}

	/**
	 * 第三方
	 */
	public static class THIRDPARTY {
		/**
		 * 第三登录方式
		 */
		public static final String  LOGIN_TYPE_QQ = "qq";
		public static final String  LOGIN_TYPE_WX = "wx";
		public static final String  LOGIN_TYPE_WX_PN = "wxpn";
		/**
		 * 微信公众号测试环境
		 */
		public static final String  LOGIN_TYPE_WX_PN_TEST="wxpntest";
		/**
		 *  微信开放平台 公众服务号
		 *  和
		 *  appsecret
		 */
		public static final String APP_ID_WX = "wx65be116c8ab9bee4";
		public static final String APP_KEY_WX = "2720aaa08cf575c06dd45dfbc871238d";

		/**
		 *  微信公众平台 公众服务号
		 *  和
		 *  appsecret
		 */
		public static final String APP_ID_WX_PN = "wx289e162f61a5ac76";
		public static final String  APP_KEY_WX_PN="191f130d71cf35f7637031c16c88cb93";
		/**
		 *  微信公众平台 公众服务号测试环境
		 *  和
		 *  appsecret
		 */
		public static final String APP_ID_WX_PN_TEST = "wxd0c4bfd889805b2d";
		public static final String  APP_KEY_WX_PN_TEST="57fdc42e424b90ddf0045ac7bbef159e";

		/**
		 * 获取 微信 Access Token 接口
		 */
		public static final String WEIXIN_USERINFO_ACCESS_TOKEN ="wechat.access.oauth2.token";
		/**
		 *，是用来获取用户的基本信息的 接口
		 */
		public static final String WEIXIN_USERINFO_URL = "wechat.snsapi_userinfo.url";

		/**
		 * qq  获取用户基本信息
		 *
		 */
		public static final String QQ_USERINFO_URL = "qq.userinfo.url";
		/**
		 * qq 获取Access Token 为下一步获取用户的OpenID做准备
		 */
		public static final String QQ_ACCESS_TOKEN = "qq.access.token.url";
		/**
		 * qq 获取openid 为下一步获取用户的基本信息做准备
		 */
		public static final String QQ_OPENID ="qq.openid.url";

		/**
		 * qq pc
		 * 开发这平台 公众服务号 appid
		 * 和
		 * appsecret
		 */
		public static final String APP_ID_QQ_PC = "101365225";
		public static final String APP_KEY_QQ_PC = "d23ac2f27f6d1ed0cc233b19332af92e";

		/**
		 * qq app
		 * 开发这平台 公众服务号 appid
		 * 和
		 * appsecret
		 */
		public static final String APP_ID_QQ_APP = "1105844428";
		public static final String APP_KEY_QQ_APP = "AdW0xLFLAFi4jnJO";

	}

	/**
	 * 微信公众号签名
	 */
	public static class SIGNATURE {
		public static final  String  JSAPI_TICKET = "JSAPI_TICKET";
		/**
		 *token 过期时间
		 */
		public static final  int  JSAPI_TICKET_EXPIR = 7000;
		public  static final String  WX_PUBLIC_NUMBER_URL_ACCESS_TOKEN = "wechat.public.number.access.token";
		public  static final String  WX_PUBLIC_NUMBER_APPID = "wechat.public.number.appid";
		public  static final String  WX_PUBLIC_NUMBER_SECRET = "wechat.public.number.secret";
		public static final  String WX_PUBLIC_NUMBER_URL_JSAPI_TICKET = "wechat.public.number.jsapi.ticket";
	}

	/**
	 * socket消息模板
	 */
	public static class MSGTEMPLET {
		/**
		 * 用户升级 信息模板
		 */
	}
	
	/**resume.properties相关常量**/
	public static final class RESUME_CONFIG{
		
		/**微信消息推送平台的url**/
		public static final String WECHAT_SEND_URL="weChat.send.url";
		
		/**微信消息推送平台注册的公众号Id**/
		public static final String WECHAT_PLATFORMID="weChat.platformId";
		
		/**微信消息等级变更模板ID**/
		public static final String TEMPLATEID_LEVELCHANGE="weChat.templateId.levelChange";
		
		/**微信消息完善信息模板ID**/
		public static final String TEMPLATEID_COMPLETEINFO="weChat.templateId.completeInfo";
		
		/**微信消息导入成功模板ID**/
		public static final String TEMPLATEID_IMPORTRESUME="weChat.templateId.importResume";
		
		/**微信跳转路径**/
		public static final String WECHAT_TEMPLATE_URL="weChat.template.url";
		/**微信消息用户长时间没登录**/
		public static final String TEMPLATEID_LONGTERMNOLOGIN="weChat.templateId.longTermNoLogin";
		
	}
	
	/**微信消息本地模板路径**/
	public static final class WECHATLOCALTEMPLATE{
		
		/**微信消息等级变更本地模板路径**/
		public static final String WECHAT_LOCALTEMPLATEPATH_LEVELCHANGE="weChatTemplate/weChatMsg_complete_UserLevel.vm";
		
		/**微信消息完善邮箱信息本地模板路径**/
		public static final String WECHAT_LOCALTEMPLATEPATH_COMPLETEEAMIL="weChatTemplate/weChatMsg_complete_email.vm";
		
		/**微信消息完善渠道信息本地模板路径**/
		public static final String WECHAT_LOCALTEMPLATEPATH_COMPLETECHANNEL="weChatTemplate/weChatMsg_complete_channel.vm";
		
		/**微信消息-邮箱导入简历成功本地模板路径**/
		public static final String WECHAT_LOCALTEMPLATEPATH_IMPORT_RESUME_EMAIL="weChatTemplate/weChatMsg_import_resume_email.vm";

		/**微信消息-渠道导入简历成功本地模板路径**/
		public static final String WECHAT_LOCALTEMPLATEPATH_IMPORT_RESUME_CHANNEL="weChatTemplate/weChatMsg_import_resume_channel.vm";

		/**微信消息-用户长期未登录**/
		public static final String WECHAT_LOCALTEMPLATEPATH_LONG_TERM_NOLOGIN="weChatTemplate/weChatMsg_complete_longTermNoLogin.vm";
	}

	/** 简历导入log **/
	public static final class IMPORT_LOG {

		//无重复简历
		public static final Integer STATUS_NO_REPEATED = 0;

		//有重复简历
		public static final Integer STATUS_REPEATED = 1;
	}

	/** 百度AI **/
	public static final class BAIDU_AI {

		//鉴权认证机制 - 请求url
		public static final String PROPERTIES_KEY_ACCESS_TOKEN_URL = "baidu.ai.access.token.url";

		//鉴权认证机制 - 请求 id
		public static final String PROPERTIES_KEY_ACCESS_TOKEN_CLIENT_ID = "baidu.ai.client.id";

		//鉴权认证机制 - 请求 secret
		public static final String PROPERTIES_KEY_ACCESS_TOKEN_CLIENT_SECRET = "baidu.ai.client.secret";

		//通用文字识别url
		public static final String PROPERTIES_KEY_OCR_URL = "baidu.ocr.url";

		public static final String ACCESS_TOKEN_PATTERN_CLIENT_ID = "\\$\\{baidu.ai.client.id\\}";

		public static final String ACCESS_TOKEN_PATTERN_CLIENT_SECRET = "\\$\\{baidu.ai.client.secret\\}";

		//通用文字识别 返回json - 错误 key
		public static final String OCR_JSON_KEY_ERROR_CODE = "error_code";

		//通用文字识别 返回json - 错误信息 key
		public static final String OCR_JSON_KEY_ERROR_MSG = "error_msg";

		//通用文字识别 返回json - 错误 error - Access Token过期失效
		public static final Integer OCR_JSON_VALUE_TOKEN_EXPIRED = 110;

		//鉴权认证机制 返回json - 错误 key
		public static final String ACCESS_TOKEN_JSON_KEY_ERROR = "error";

		//鉴权认证机制 返回json - 错误描述 key
		public static final String ACCESS_TOKEN_JSON_KEY_ERROR_DESC = "error_description";

		//鉴权认证机制 返回json - 错误描述 - API Key不正确
		public static final String ACCESS_TOKEN_JSON_VALUE_ERROR_API_KEY = "unknown client id";

		//鉴权认证机制 返回json - 错误描述 - Secret Key不正确
		public static final String ACCESS_TOKEN_JSON_VALUE_ERROR_SECRET_KEY = "Client authentication failed";

		//鉴权认证机制 返回json - token key
		public static final String ACCESS_TOKEN_JSON_KEY_TOKEN = "access_token";

		//鉴权认证机制 返回json - 过期时间（秒） key
		public static final String ACCESS_TOKEN_JSON_KEY_EXPIRE = "expires_in";

		//通用文字识别 请求头type
		public static final String OCR_REQUEST_CONTENT_TYPE = "application/x-www-form-urlencoded";

	}
	
}