
package com.dayee.writtenExam.model.dic;

import java.util.HashSet;
import java.util.Set;

public class DicConstants {

    /** 根目录结点 */
    public static final String ROOT_ID       = "0";

    /** 分隔符 */
    public static final String CODE_SEPARATE = "/";

    /** 归类的文件夹,例如 4001，4002，4003 */
    public static final int    TYPE_FOLD     = 0;

    /** 实际使用的码表,例如 4001下的节点 */
    public static final int    TYPE_CODE     = 1;

    /** 控制台数据 */
    public static final int    USE_COSNOLE             = 0;

    /** 非控制台数据 */
    public static final int    NOT_USE_COSNOLE         = 1;

    /** 职位序列 (4001) */
    public static final String ZWXL          = "ZWXL";

    /** 职位等级 (4002) */
    public static final String ZWDJ          = "ZWDJ";

    /** 业务条线 (4003) */
    public static final String YWTX          = "YWTX";


    /** 性别 */
    public static final String SC_GENDER               = "E/HB";

    /** 婚姻状况MaritalStatus */
    public static final String SC_MARITAL_STATUS       = "HK";

    /** 学历 */
    public static final String SC_EDUCATION            = "G/JD";

    /** 学位 */
    public static final String SC_DEGREE               = "JE";

    /** 工作类型 */
    public static final String SC_WORK_TYPE            = "RD";

    /** 证件类型 */
    public static final String SC_ID_TYPE              = "HI";

    /** 国籍/地区 */
    public static final String SC_NATIONALITY          = "HD";

    /** 户口 */
    public static final String SC_HUKOU                = "HE";

    /** 民族 */
    public static final String SC_NATION               = "HF";

    /** 政治面貌 */
    public static final String SC_POLITICAL            = "HH";

    /** 工作年限 */
    public static final String Y_HM                    = "Y/HM";

    /** 血型 */
    public static final String SC_BLOOD_TYPE           = "HHF";

    /** 期望从事职业/职位 */
    public static final String SC_DESIRED_POSITION     = "HS/IF";

    /** 工作地点/目前所在城市/期望工作地点 */
    public static final String SC_WORK_PLACE           = "D/HP/HR";

    /** 外语语种 */
    public static final String LA                      = "LA";

    /** 外语水平 */
    public static final String LB                      = "LB";

    /** 英语考试 */
    public static final String QA                      = "QA";

    /** 考试成绩 */
    public static final String QB                      = "QB";

    /** 行业 */
    public static final String ID                      = "ID";

    /** 工作类型 */
    public static final String RD                      = "RD";

    /** 公司性质 */
    public static final String IC                      = "IC";

    /** 目前年薪/期望年薪 */
    public static final String HN_HT_PF                = "HN/HT/PF";

    /** 招聘专业/专业 */
    public static final String F_JC                    = "F/JC";

    /** 离职所需时间 */
    public static final String PE                      = "PE";

    /** 期望从事职业/职位 */
    public static final String HS_IF                   = "HS/IF";

    /** 技能水平 */
    public static final String MB                      = "MB";

    /** 与本人关系 */
    public static final String TC                      = "TC";

    /** 职位类型 */
    public static final String ZWLX                    = "ZWLX";

    /** 目前薪酬/期望薪酬 */
    public static final String XC                      = "XC";

    /** 语种:语言等级 */
    public static final String LALEL                   = "LALEL";

    /** 险种 */
    public static final String INSURANCE               = "XZ";

    /** 合作模式 */
    public static final String COOPERATIONMODE         = "COOPERATIONMODE";

    /** 培训现场 */
    public static final String TRAINNING_SCENE         = "TRAINNING_SCENE";

    /** 职位级别 */
    public static final String JOBLEVEL                = "JOBLEVEL";

    /** 公司规模 */
    public static final String JOB_CS                  = "JOB/CS";

    /** 入库原因 */
    public static final String INTO_TALENT_POOL_REASON = "INTO_TALENT_POOL_REASON";

    /** 变更类型 */
    public static final String BGLX                    = "BGLX";

    /** 成本分类 */
    public static final String CBFL                    = "CBFL";

    /** 调查类型 */
    public static final String DCLX                    = "DCLX";

    /** 合同类型 */
    public static final String HTLX                    = "HTLX";

    /** 合同业务类型 */
    public static final String HTYWLX                  = "HTYWLX";

    /** 汇总项目 */
    public static final String HZXM                    = "HZXM";

    /** 解除原因 */
    public static final String JCYY                    = "JCYY";

    /** 健康状况 */
    public static final String JKZK                    = "JKZK";

    /** 考勤期间 */
    public static final String KQQJ                    = "KQQJ";

    /** 可享合计 */
    public static final String KXHJ                    = "KXHJ";

    /** 离职类型 */
    public static final String LZLX                    = "LZLX";

    /** 离职日期 */
    public static final String LZRQ                    = "LZRQ";

    /** 离职原因 */
    public static final String LZYX                    = "LZYX";

    /** 签订次数 */
    public static final String QDCS                    = "QDCS";

    /** 人事业务 */
    public static final String RSYW                    = "RSYW";

    /** 人员类别 */
    public static final String RYLB                    = "RYLB";

    /** 任职方式 */
    public static final String RZFS                    = "RZFS";

    /** 入职日期 */
    public static final String RZRQ                    = "RZRQ";

    /** 剩余合计 */
    public static final String SYHJ                    = "SYHJ";

    /** 剩余天数 */
    public static final String SYTS                    = "SYTS";

    /** 题目类型 */
    public static final String TMLX                    = "TMLX";

    /** 调资原因 */
    public static final String TZYY                    = "TZYY";

    /** 休假类别 */
    public static final String XJLB                    = "XJLB";

    /** 血型 */
    public static final String XX                      = "XX";

    /** 学习方式 */
    public static final String XXFS                    = "XXFS";

    /** 协议类型 */
    public static final String XYLX                    = "XYLX";

    /** 薪资项目 */
    public static final String XZXM                    = "XZXM";

    /** 用工形式 */
    public static final String YGXS                    = "YGXS";

    /** 业务主题 */
    public static final String YWZT                    = "YWZT";

    /** 资质类型 */
    public static final String ZZLX                    = "ZZLX";

    /** 转正日期 */
    public static final String ZZRQ                    = "ZZRQ";

    /** 企业级码表对应的SD */
    private static Set<String> corpStandCode  = new HashSet<String>();
    static {
        corpStandCode.add(ZWXL);
        corpStandCode.add(ZWDJ);
        corpStandCode.add(YWTX);
    }

    /**
     * 判断一个SD码，是否是企业级码表
     * @param stanardCode
     * @return
     */
    public static boolean checkIsCorpSD(String stanardCode){
        return corpStandCode.contains(stanardCode);
    }
}
