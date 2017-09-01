/**
 * 
 */

package com.dayee.writtenExam.framework.exception;

/**
 * @author Chinakite
 */
public class YuncaiSystemExceptionCode {

    public static final String YUNCAI_CONFIG_INIT_DUPLICATE = "SYS-00001";

    public static final String JSP_TAG_ERROR                = "SYS-00002";

    public static final String IO_ERROR                     = "SYS-00003";

    public static final String NOT_HAVE_TABLE_ERROR         = "SYS-00004";                      // 未对应表

    public static final String DATE_FORMAT_ERROR            = "SYS-00005";                      // 日期转换错误

    // 用户名密码错误
    public static final String LOGIN_FAIL_PASSWORD_WRONG    = "LOGIN_FAIL_000006";
    
    // 用户名验证码错误
    public static final String LOGIN_FAIL_CAPTCHA_WRONG     = "LOGIN_FAIL_000007";

    //体验用户IP被禁用
    public static final String LOGIN_IP_FORBIDDEN="LOGIN_IP_FORBIDDEN_000003)";

    // 多个企业
    public static final String LOGIN_FAIL_MUTI_CORP         = "LOGIN_FAIL_000001";

    // 无权限
    public static final String LOGIN_FAIL_NO_PERMISSION     = "LOGIN_FAIL_000002";

    // 缺少关键信息
    public static final String ADD_USER_FAIL_NO_INFO        = "ADD_USER_FAIL_000001";

    // 关键信息重复
    public static final String ADD_USER_FAIL_REPEAT         = "ADD_USER_FAIL_000002";

    // 具有【高管用户】角色权限的用户不能同时拥有【系统管理员】权限
    public static final String ADD_USER_FAIL_SENIOR         = "ADD_USER_FAIL_000004";

    // 一个企业用户中，同一时期只能一个【超级管理员】用户
    public static final String ADD_USER_FAIL_SUPER          = "ADD_USER_FAIL_000005";

    // 超过DB最大创建企业数量
    public static final String CREATE_CORP_EXCEED_MAX       = "CREATE_CORP_000001";

    // 判断未审核通过企业企业用户限制
    public static final String ADD_USER_FAIL_AUDIT          = "ADD_USER_FAIL_000006";

    // 不是手机号
    public static final String REGIST_CORP_NOT_MOBILE       = "REGIST_CORP_000001";

    // 短信发送限制
    public static final String REGIST_CORP_LIMIT_SMS        = "REGIST_CORP_000002";

    // 短信验证码错误
    public static final String REGIST_CORP_SMS_CODE         = "REGIST_CORP_000003";

    // 短信验证码发送失败
    public static final String REGIST_CORP_SMS_FAIL         = "REGIST_CORP_000004";

    // 忘记密码手机号不存在
    public static final String FORGET_PWD_MOBILE_FAIL         = "FORGET_PWD_000001";
    
    // 忘记密码手机号不存在
    public static final String UPDATE_PWD_NO_CONSISTENT       = "UPDATE_PWD_000001";
    
    // 当前不是超级用户不能转移
    public static final String TRANSFER_SUPER_NOT_SUPER     = "TRANSFER_SUPER_USER_FAIL_000001";

    // 设置Blob字段出现错误
    public static final String SET_BLOB_ERROR               = "SET_BLOB_ERROR_000001";

    // 加载默认模板错误
    public static final String INIT_DEFAULT_TEMPLATE        = "INIT_DEFAULT_TEMPLATE_000001";
    
    //测试用户不存在错误
    public static final String TEST_USER_ERROR                    = "TEST_USER_ERROR";
    
    //在企业中用户不存在
    public static final String NOT_CORP_USER_VALID                = "NOT_CORP_USER_VALID";
    
    public static final String CHANGE_TYPE_FAIL             = "CHANGE_TYPE_FAIL";

}
