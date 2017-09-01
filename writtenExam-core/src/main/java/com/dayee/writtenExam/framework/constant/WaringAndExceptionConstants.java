package com.dayee.writtenExam.framework.constant;

/**
 * 警告异常常量类
 * Created by yq.song on 2016/9/19.
 */
public class WaringAndExceptionConstants {

	/**公用异常警告**/
	public static final class COMMON{

		/**参数异常警告**/
		public static final String WARING_PARAM_BLANK="WARING_PARAM_BLANK";

		/**程序异常**/
		public static final String EXCEPTION_PROCESS="EXCEPTION_PROCESS";

		/**自定义提示信息**/
		public static final String USER_DEFINED_MESSAGE="USER_DEFINED_MESSAGE";

		/**重复提交警告**/
		public static final String WARNING_DUPLICATE_SUBMIT="WARNING_DUPLICATE_SUBMIT";

	}

	/**登录注册异常警告**/
	public static final class LOGIN_REGISTER {

		/**手机号码格式错误**/
		public static final String REGISTER_PHONE_FORMAT_ERROR = "REGISTER_PHONE_FORMAT_ERROR";

		/**用户已注册**/
		public static final String REGISTER_USERNAME_REPEAT = "REGISTER_USERNAME_REPEAT";

		/**用户未注册**/
		public static final String LOGIN_USER_NOT_REGISTER = "LOGIN_USER_NOT_REGISTER";
	}

	/**密码异常警告**/
	public static final class PASSWORD {

		/**密码为空**/
		public static final String PASSWORD_IS_BLANK = "PASSWORD_IS_BLANK";

		/**密码不符合规则(密码必须是6位以上,以数字和字母的形式组合)**/
		public static final String PASSWORD_NOT_MATCH = "PASSWORD_NOT_MATCH";
	}

	/**验证码异常警告**/
	public static final class VFCODE {

		/**验证码过期**/
		public static final String VFCODE_OUTOFTIME="VFCODE_OUTOFTIME";

		/**验证码错误**/
		public static final String VFCODE_ERROR="VFCODE_ERROR";
	}

	/**文件夹异常警告**/
	public static final class FOLDER {

		/**文件名已存在**/
		public static final String FOLDER_NAME_EXIST="FOLDER_NAME_EXIST";

		/**文件夹（邮箱导入文件夹）职位名已存在 **/
		public static final String FOLDER_POSITION_NAME_EXIST ="FOLDER_POSITION_NAME_EXIST";

		/**文件夹不存在**/
		public static final String FOLDER_NOT_EXIST="FOLDER_NOT_EXIST";

		/**系统预设文件夹不能删除**/
		public static final String FOLDER_CAN_NOT_DEL_TOP_FOLDER="FOLDER_CAN_NOT_DEL_FOLDER";

		/**系统预设文件夹不能移动**/
		public static final String FOLDER_CAN_NOT_MOVE_TOP_FOLDER="FOLDER_CAN_NOT_MOVE_TOP_FOLDER";

		/**系统预设文件夹不能重命名**/
		public static final String FOLDER_CAN_NOT_UPDATE_TOP_FOLDER="FOLDER_CAN_NOT_UPDATE_TOP_FOLDER";

		/**文件夹下存在子级，不能删除**/
		public static final String FOLDER_HAS_CHILD_FOLDER="FOLDER_HAS_CHILD_FOLDER";

		/**文件夹下存在简历，不能删除**/
		public static final String FOLDER_HAS_RESUME="FOLDER_HAS_RESUME";


	}

	/** 绑定邮箱异常 **/
	public static final class BINDEMAIL {

		/** 操作失败 **/
		public static final String OPT_ERROR = "OPT_ERROR";

		/** 找不到对应信息 **/
		public static final String CAN_NOT_FIND = "CAN_NOT_FIND";

		/** 该邮箱已绑定 **/
		public static final String EMAIL_HAVE_BINDED = "EMAIL_HAVE_BINDED";
	}

	/** 导出简历异常 **/
	public static final class EXPORTRESUME {

		/** 无法找到对应简历信息 **/
		public static final String RESUME_CAN_NOT_FOUND = "RESUME_CAN_NOT_FOUND";
	}

	/** 简历操作异常警告 **/
	public static final class RESUME {

		/**简历不存在**/
		public static final String RESUME_NOT_EXIST="RESUME_NOT_EXIST";

		/** 导入简历异常 - 没有找到邮箱信息 **/
		public static final String IMPORT_NO_EMAIL="IMPORT_NO_EMAIL";

		/**下载点数为0，无法下载**/
		public static final String RESUME_CAN_NOT_DOWNLOAD="RESUME_CAN_NOT_DOWNLOAD";

		/**提示可用下载数**/
		public static final String RESUME_CAN_DOWNLOAD_NUM="RESUME_CAN_DOWNLOAD_NUM";

		/**无法下载自己的简历**/
		public static final String RESUME_CAN_NOT_DOWNLOAD_MYSELF="RESUME_CAN_NOT_DOWNLOAD_MYSELF";

		/**余额不足，无法下载**/
		public static final String RESUME_CAN_NOT_DOWNLOAD_LACK_POINTS="RESUME_CAN_NOT_DOWNLOAD_LACK_POINTS";

		/**要下载的简历与已有简历疑似重复**/
		public static final String RESUME_DOWNLOAD_LIKE_REPEAT="RESUME_DOWNLOAD_LIKE_REPEAT";

		/**彻底删除成功提示**/
		public static final String RESUME_CLEAN_SUCCESS="RESUME_CLEAN_SUCCESS";

		/**彻底删除失败提示**/
		public static final String RESUME_CLEAN_FAIL="RESUME_CLEAN_FAIL";

		/**错误的token**/
		public static final String RESUME_DETAIL_TOKEN_FAIL="RESUME_DETAIL_TOKEN_FAIL";

		/**共享成功信息**/
		public static final String RESUME_SHARE_SUCCESS="RESUME_SHARE_SUCCESS";

		/**共享失败_已下载详细信息**/
		public static final String RESUME_SHARE_FAIL_DETAIL_HAS_DOWNLOAD="RESUME_SHARE_FAIL_DETAIL_HAS_DOWNLOAD";

		/**共享失败_已共享详细信息**/
		public static final String RESUME_SHARE_FAIL_DETAIL_HAS_SHARE="RESUME_SHARE_FAIL_DETAIL_HAS_SHARE";

		/**共享失败_不完整详细信息**/
		public static final String RESUME_SHARE_FAIL_DETAIL_NO_COMPLETE="RESUME_SHARE_FAIL_DETAIL_NO_COMPLETE";

		/**共享价格必须为正整数**/
		public static final String RESUME_SHARE_PRICE_MUST_POSITIVE_INTEGER="RESUME_SHARE_PRICE_MUST_POSITIVE_INTEGER";
		
		/**取消共享当前用户必须为共享人**/
		public static final String RESUME_CANCLE_SHARE_MUST_CURRENTUSER_NOT_SHAREUSER="RESUME_CANCLE_SHARE_MUST_CURRENTUSER_NOT_SHAREUSER";
		
		/**取消共享余额不足**/
		public static final String RESUME_CANCLE_SHARE_HAS_NOT_ENOUGH_SCORE="RESUME_CANCLE_SHARE_HAS_NOT_ENOUGH_SCORE";
		
		/**简历空间已满无法还原简历*/
		public static final String RESUME_CAN_NOT_RESTORE_ON_SPACE_FULL="RESUME_CAN_NOT_RESTORE_ON_SPACE_FULL";
		
		/**简历空间已满,还原失败说明*/
		public static final String RESUME_RESTORE_FAIL_DETAIL="RESUME_RESTORE_FAIL_DETAIL";
		
		/**简历不能添加重复标签*/
		public static final String RESUME_LABEL_REPEAT="RESUME_LABEL_REPEAT";
	}

	/** 外部系统绑定 **/
	public static final class EXTERNAL_BIND {

		//参数异常
		public static final String ILLEGAL_PARAM = "ILLEGAL_PARAM";

		//帐号或密码错误
		public static final String ERROR_ACCOUNT = "ERROR_ACCOUNT";

		//token值失效
		public static final String ERROR_TOKEN = "ERROR_TOKEN";

		//简历云用户已绑定其他用户
		public static final String HAVE_BINDED = "HAVE_BINDED";

		//简历云尚未绑定
		public static final String HAVE_NOT_BINDED = "HAVE_NOT_BINDED";

		/** 绑定信息不匹配 **/
		public static final String INFO_NOT_MATCH = "INFO_NOT_MATCH";
	}

	/**搜索器**/
	public static final class SEARCH_CONDITION{

		/**搜索器名称不能重复**/
		public static final String CONDITION_NAME_CANNOT_REPEAT="CONDITION_NAME_CANNOT_REPEAT";
	}

	/**定时任务**/
	public static final class QUARTZ{

		/**定时任务创建失败*/
		public static final String QUARTZ_CREATE_FAIL="QUARTZ_CREATE_FAIL";
	}

	/**简历评价**/
	public static final class REUSME_COMMENT{
		/** 只有下载简历后才能评价**/
		public static final String WARING_NOT_DOWNLOAD = "WARING_NOT_DOWNLOAD";

		/** 已经评论过了**/
		public static final String WARING_HAS_COMMENT = "WARING_HAS_COMMENT";

		/** 已经评论过了**/
		public static final String WARING_NO_FIND_DATA= "WARING_NO_FIND_DATA";

	}

	public static final class LABEL{

		/** 与系统标签冲突 **/
		public static final String WARING_CONFLICT = "WARING_CONFLICT";
		
		/** 标签名重复 **/
		public static final String WARING_LABEL_NAME_REPEAT = "WARING_LABEL_NAME_REPEAT";

		/** 系统标签不能删除 **/
		public static final String WARING_CAND_NOT_BE_DELETED= "WARING_CAND_NOT_BE_DELETED";
	}
	
	/**用户等级**/
	public static final class USER_LEVEL{
		
		/**简历空间超过90%即将存满**/
		public static final String RESUME_SPACE_WILL_FULL="RESUME_SPACE_WILL_FULL";
		
		/**简历空间已存满**/
		public static final String RESUME_SPACE_HAS_FULL="RESUME_SPACE_HAS_FULL";
	}

	/**冻结信息**/
	public static final class FROZEN{

		/**简历已冻结**/
		public static final String RESUME_FROZEN="FROZEN";

	}
	
	public static final class IMPORT{
		
		/**导入任务创建失败**/
		public static final String IMPORT_TASK_CREATE_FAIL="IMPORT_TASK_CREATE_FAIL";
	}

	public static final class CHANNEL {

		public static final String CHANNEL_HAS_EXISTED="CHANNEL_HAS_EXISTED";

		public static final String CHANNEL_ACCOUNT_NOT_EXISTED="CHANNEL_ACCOUNT_NOT_EXISTED";
	}

	public static final class THIRDPARTYLOGIN{
		public static final String GET_THIRDPARTY_INFO_FAIL = "GET_THIRDPARTY_INFO_FAIL";

		public static final String  ACCOUNT_BIND_OVER     = "ACCOUNT_BIND_OVER";

		public static final String  NO_BIND_MOBILE_PHONE = "NO_BIND_MOBILE_PHONE";
	}
}
