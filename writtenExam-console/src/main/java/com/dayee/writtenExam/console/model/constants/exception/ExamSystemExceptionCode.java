package com.dayee.writtenExam.console.model.constants.exception;

/**
 * Created by Administrator on 2017/8/7.
 */
public class ExamSystemExceptionCode {

    public static final String REPORT_CONFIG_INIT_DUPLICATE = "SYS-00001";

    public static final String JSP_TAG_ERROR                = "SYS-00002";

    public static final String IO_ERROR                     = "SYS-00003";

    public static final String NOT_HAVE_TABLE_ERROR         = "SYS-00004";                   // 未对应表

    public static final String DATE_FORMAT_ERROR            = "SYS-00005";                   // 日期转换错误

    // 设置Blob字段出现错误
    public static final String SET_BLOB_ERROR               = "SET_BLOB_ERROR_000001";

    // 加载默认模板错误
    public static final String INIT_DEFAULT_TEMPLATE        = "INIT_DEFAULT_TEMPLATE_000001";

    // 测试用户不存在错误
    public static final String TEST_USER_ERROR              = "TEST_USER_ERROR";

    public static final String CHANGE_TYPE_FAIL             = "CHANGE_TYPE_FAIL";

}
