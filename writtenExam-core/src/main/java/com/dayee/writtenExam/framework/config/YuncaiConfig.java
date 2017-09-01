/**
 * 
 */

package com.dayee.writtenExam.framework.config;

import com.ideamoment.config.IdeaConfiguration;

/**
 * @author Chinakite
 */
public class YuncaiConfig {

    private static IdeaConfiguration config                   = YuncaiConfigManage.config;

    private static final String      FILSER_SERVER_URL        = "yuncai.fileServer.url";

    private static final String      FILSER_SERVER_UPLOAD_URL = "yuncai.fileServer.uploadUrl";

    private static final String      PARTITION_CREATENUM      = "yuncai.db.partition.maxNum";

    private static final String      CONSOLE_CORP_CODE        = "yuncai.db.console.corpCode";

    private static final String      SYSTEM_URL               = "yuncai.url";

    private static final String      LIMIT_USER_NUM           = "yuncai.corp.limit.userNum";

    private static final String      REGIST_SMS_LIMIT_NUM     = "yuncai.register.sms.limitNum";

    private static final String      REGIST_SMS_VALID_SECONDS = "yuncai.register.sms.validSeconds";

    private static final String      MAIL_URL                 = "yuncai.url.mail";

    private static final String      MSG_CENTER_URL           = "yuncai.msgcenter.url";
    
    private static final String      TEST_USER_HR           = "yuncai.testUser.hr";
    
    private static final String      TEST_USER_SENIOR           = "yuncai.testUser.senior";
    
    private static final String      TEST_USER_PASSWORD           = "yuncai.testUser.passWord";
    
    private static final String      APP_LINK_URL             = "yuncai.applink.url";

    private static final String      C_123_ACCOUNT            = "yuncai.message.c123Account";

    private static final String      INTERFACE_KEY            = "yuncai.message.interfaceKey";

    private static final String      C_123_URL                = "yuncai.message.c123Url";

    private static final String      CHANNEL_GROUP            = "yuncai.message.channelGroup";
    
    private static final String      TASK_SERVER_URL          ="yuncai.taskServer.url";

    public static String getConsoleCorpCode() {

        return config.get(CONSOLE_CORP_CODE, "console");
    }
    
    public static String getApplinkUrl() {

        return config.get(APP_LINK_URL, "");
    }

    public static String getTestUserHr(){
    	 return config.get(TEST_USER_HR, "");
    }
    
    public static String getTestUserSenior(){
    	 return config.get(TEST_USER_SENIOR, "");
    }
    
    public static String getTestUserPassWord(){
    	 return config.get(TEST_USER_PASSWORD, "");
    }
    
    public static String getC123Account() {

        return config.get(C_123_ACCOUNT, "");
    }

    public static String getInterfaceKey() {

        return config.get(INTERFACE_KEY, "");
    }

    public static String getC123Url() {

        return config.get(C_123_URL, "");
    }

    public static String getChannelGroup() {

        return config.get(CHANNEL_GROUP, "");
    }

    public static int getSmsValidSeconds() {

        return config.getInt(REGIST_SMS_VALID_SECONDS, 0);
    }

    public static int getLimitSmsNum() {

        return config.getInt(REGIST_SMS_LIMIT_NUM, 0);
    }

    public static int getMaxPartitionCreateNum() {

        return config.getInt(PARTITION_CREATENUM, 0);
    }

    public static int getLimitUserNum() {

        return config.getInt(LIMIT_USER_NUM, 0);
    }

    public static String getFileServerUrl() {

        String url = "";

        try {
            url = config.get(FILSER_SERVER_URL, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (url == null || "".equals(url)) {
            url = "http://localhost/fileServer";
        }
        return url;
    }

    public static String getFileServerUploadUrl() {

        String url = config.get(FILSER_SERVER_UPLOAD_URL, "");
        if (url == null || "".equals(url)) {
            url = "http://localhost/fileServer/upload";
        }
        return url;
    }

    public static String getMailUrl() {

        return config.get(MAIL_URL, "");
    }

    public static String getSystemUrl() {

        String url = config.get(SYSTEM_URL, "");
        if (url == null || "".equals(url)) {
            url = "http://localhost:8080/yuncai";
        }
        return url;
    }

    public static String getMsgCenterUrl() {

        return config.get(MSG_CENTER_URL, "");
    }

    /**
     * Return a String property with a default value.
     */
    public static String get(String key, String defaultValue) {

        if (!config.isInited()) {
            YuncaiConfigManage.initConfig();
        }
        return config.get(key, defaultValue);
    }

    /**
     * Return a int property with a default value.
     */
    public static int getInt(String key, int defaultValue) {

        if (!config.isInited()) {
            YuncaiConfigManage.initConfig();
        }
        return config.getInt(key, defaultValue);
    }

    /**
     * Return a long property with a default value.
     */
    public static long getLong(String key, long defaultValue) {

        if (!config.isInited()) {
            YuncaiConfigManage.initConfig();
        }
        return config.getLong(key, defaultValue);
    }

    /**
     * Return a boolean property with a default value.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {

        if (!config.isInited()) {
            YuncaiConfigManage.initConfig();
        }
        return config.getBoolean(key, defaultValue);
    }
    public static void main(String[] args) {
    	System.out.println(getTestUserHr());
	}
    
    public static String getTaskServerUrl() {

        String url = "";

        try {
            url = config.get(TASK_SERVER_URL, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (url == null || "".equals(url)) {
            url = "http://localhost:8080/task";
        }
        return url;
    }
}
