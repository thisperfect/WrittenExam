package com.dayee.writtenExam.framework.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tang on 2017/2/7.
 */
public class LogConstants {

    public static final Integer STATUS_SUCCESS = 0;

    public static final Integer STATUS_FAILED = 1;

    public static final String OPERATE_NAME_BIND_EMAIL = "bindEmail";

    public static final String OPERATE_NAME_BIND_CHANNEL = "bindChannel";

    public static final String OPERATE_NAME_SHARE_RESUME = "shareResume";

    public static final String OPERATE_NAME_DOWNLOAD_RESUME = "downloadResume";

    public static final String OPERATE_NAME_COMMENT_RESUME = "commentResume";

    public static final String CONTENT_BIND_EMAIL = "绑定邮箱${address}";

    public static final String CONTENT_BIND_CHANNEL = "绑定${channelDicName}渠道${channelMember}${channelAccount}";

    public static final String CONTENT_SHARE_RESUME = "共享了${resumeName}的简历";

    public static final String CONTENT_DOWNLOAD_RESUME = "下载了${shareUser}共享的${resumeName}的简历";

    public static final String CONTENT_COMMENT_RESUME = "评论了${shareUser}共享的${resumeName}的简历";


    public static Map<String, String> NAME_CONTENT_MAP = new HashMap<>();

    static {
        NAME_CONTENT_MAP.put(OPERATE_NAME_BIND_CHANNEL, CONTENT_BIND_CHANNEL);
        NAME_CONTENT_MAP.put(OPERATE_NAME_BIND_EMAIL, CONTENT_BIND_EMAIL);
        NAME_CONTENT_MAP.put(OPERATE_NAME_COMMENT_RESUME, CONTENT_COMMENT_RESUME);
        NAME_CONTENT_MAP.put(OPERATE_NAME_DOWNLOAD_RESUME, CONTENT_DOWNLOAD_RESUME);
        NAME_CONTENT_MAP.put(OPERATE_NAME_SHARE_RESUME, CONTENT_SHARE_RESUME);
    }
}
