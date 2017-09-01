package com.dayee.writtenExam.console.model.config;

import com.ideamoment.config.IdeaConfiguration;

/**
 * Created by Administrator on 2017/8/7.
 */
public class WrittenExamConfig {
    private static IdeaConfiguration config              = WrittenExamConfigManage.config;

    private static final String      PARTITION_CREATENUM = "yuncai.db.partition.maxNum";

    private static final String      CONSOLE_CORP_CODE   = "yuncai.db.console.corpCode";

    private static final String      SYSTEM_URL          = "yuncai.url";

    private static final String      MAIL_URL            = "yuncai.url.mail";

    private static final String      C_123_ACCOUNT       = "report.sms.c123.c123Account";

    private static final String      INTERFACE_KEY       = "report.sms.c123.interfaceKey";

    private static final String      C_123_URL           = "report.sms.c123.c123Url";

    private static final String      CHANNEL_GROUP       = "report.sms.c123.channelGroup";

    public static String getConsoleCorpCode() {

        return config.get(CONSOLE_CORP_CODE, "console");
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

    public static int getMaxPartitionCreateNum() {

        return config.getInt(PARTITION_CREATENUM, 0);
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

    /**
     * Return a String property with a default value.
     */
    public static String get(String key, String defaultValue) {

        if (!config.isInited()) {
            WrittenExamConfigManage.initConfig();
        }
        return config.get(key, defaultValue);
    }

    /**
     * Return a int property with a default value.
     */
    public static int getInt(String key, int defaultValue) {

        if (!config.isInited()) {
            WrittenExamConfigManage.initConfig();
        }
        return config.getInt(key, defaultValue);
    }

    /**
     * Return a long property with a default value.
     */
    public static long getLong(String key, long defaultValue) {

        if (!config.isInited()) {
            WrittenExamConfigManage.initConfig();
        }
        return config.getLong(key, defaultValue);
    }

    /**
     * Return a boolean property with a default value.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {

        if (!config.isInited()) {
            WrittenExamConfigManage.initConfig();
        }
        return config.getBoolean(key, defaultValue);
    }

}
