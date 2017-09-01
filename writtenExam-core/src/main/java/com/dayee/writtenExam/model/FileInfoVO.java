package com.dayee.writtenExam.model;



public class FileInfoVO {

    public static final int   TYPE_CONTRACT_PACT = 0;

    public static final int   TYPE_PHOTO         = 1;  // 照片

    public static final int TYPE_EMPLOYEE      = 2; // 档案附件 基本信息

    public static final int TYPE_CONTRACT      = 3; // 合同附件 合同信息

    public static final int TYPE_ENTRY         = 4; // 入职附件


    public static final int TYPE_formal        = 5; // 转正附件


    public static final int TYPE_CHANGE_JOB    = 6; // 调岗调级


    public static final int TYPE_CHANGE_SALARY = 7; // 调薪


    public static final int TYPE_VACATION      = 8; // 休假附件


    public static final int TYPE_ATTENDANCE    = 9; // 考勤-外勤图片


    public static final int TYPE_ACTIVITY      = 10; // 发布活动附件


    public static final int TYPE_NOTICE        = 11; // 发布公告附件


    public static final int TYPE_EDUCATION     = 12; // 教育信息


    public static final int TYPE_RESUME        = 13; // 履历信息


    public static final int TYPE_WORKED        = 14; // 任职信息


    public static final int TYPE_SALARY        = 15; // 薪资信息


    public static final int TYPE_TRAIN         = 16; // 培训信息


    public static final int TYPE_EXAMINE       = 17; // 考核档案


    public static final int TYPE_PRIZEINFO     = 18; // 奖惩信息


    public static final int TYPE_LEAVE         = 19; // 离职附件

    private String          oriName;                // 原始文件名

    private String          contentType;            // 文件类型

    private byte[]          content;

    public String getOriName() {

        return oriName;
    }

    public void setOriName(String oriName) {

        this.oriName = oriName;
    }

    public String getContentType() {

        return contentType;
    }

    public void setContentType(String contentType) {

        this.contentType = contentType;
    }

    public byte[] getContent() {

        return content;
    }

    public void setContent(byte[] content) {

        this.content = content;
    }
}
