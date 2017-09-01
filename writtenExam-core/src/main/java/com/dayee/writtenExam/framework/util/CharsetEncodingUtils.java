
package com.dayee.writtenExam.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import com.dayee.writtenExam.framework.constant.Constants;
import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CharsetEncodingUtils {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory
                                               .getLogger(CharsetEncodingUtils.class);
    
    private static final String UTF8_CODE   = "%";

    private static final String MY_CODE     = ",";

    private static final String NEED_DECODE = "true";

    private static final String AT          = "&";

    private static final String EQ          = "=";

    private static final String QU          = "?";

    private static final String REQUEST_KEY = "needDecode";

    public static final String  ISO_8859_1  = "ISO-8859-1";

    public static final String  GBK         = "GBK";

    public static final String  GB2312      = "GB2312";

    public static final String  UTF_8       = "UTF-8";
    
    public static CodepageDetectorProxy detector = null;

    static {
        detector = CodepageDetectorProxy.getInstance();
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        detector.add(new ParsingDetector(false));
    }

    public static String stringToUnicode(String s) {

        StringBuffer buffer = new StringBuffer();
        for (char ch : s.toCharArray()) {
            if (CharacterUtils.isChineseLetter(ch)) {
                buffer.append("\\u").append(Integer.toHexString(ch));
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(str);

        char ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            str = str.replace(matcher.group(1), String.valueOf(ch));

        }

        return str;

    }

    public static boolean isNeedDecode(ServletRequest request) {

        String needDecode = request.getParameter(REQUEST_KEY);
        return StringUtils.hasLength(needDecode) ? NEED_DECODE
                .equalsIgnoreCase(needDecode) : false;
    }

    public static String addNeedDecodeAtParam(String url) {

        return url + AT + REQUEST_KEY + EQ + NEED_DECODE;
    }

    public static String addNeedDecode(String url) {

        return url + QU + REQUEST_KEY + EQ + NEED_DECODE;
    }

    public static String encode(String str) throws Exception {

        if (StringUtils.hasLength(str)) {
            str = URLEncoder.encode(str, Constants.DEFAULT_ENCODING);
            str = str.replace(UTF8_CODE, MY_CODE);
        }
        return str;
    }

    public static String decode(String str) throws Exception {

        if (StringUtils.hasLength(str) && str.startsWith(MY_CODE)) {
            str = str.replace(MY_CODE, UTF8_CODE);
            str = URLDecoder.decode(str, Constants.DEFAULT_ENCODING);
        }
        return str;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void decode(ServletRequest request) throws ServletException {

        try {
            Map map = request.getParameterMap();
            if (CollectionUtils.notEmpty(map)) {
                for (Object key : map.keySet()) {
                    Object values = map.get(key);

                    if (values instanceof String[]) {
                        String[] oldArray = (String[]) values;
                        String[] newArray = new String[oldArray.length];
                        for (int i = 0; i < oldArray.length; i++) {
                            String newValue = decode(oldArray[i]);
                            newArray[i] = newValue;
                        }

                        map.put(key, newArray);
                    } else if (values instanceof String) {
                        String newValue = decode((String) values);
                        map.put(key, newValue);
                    }
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ServletException(e.getMessage(), e);
        }
    }

 

    public static final String          VOID     = "void";

 

 

    public static String getEncodingContent(String content) throws Exception {

        double md = 0;
        String encodingStr = null;
        for (String encoding : CHARSET_ARRAY) {
            for (String newencoding : CHARSET_ARRAY) {
                // if (!encoding.equals(newencoding)) {
                String str = new String(content.getBytes(encoding), newencoding);

                double d = getEncodingMatchingDegree(str);
                if (d >= 100) {
                    return str;
                } else if (d > 0 && (md == 0 || d > md)) {
                    md = d;
                    encodingStr = str;
                }

            }
        }

        return encodingStr;
    }
    
    public static String getEncoding(InputStream in, int length)
            throws Exception {

        Charset charset = detector.detectCodepage(in, length);
        String encoding = charset.name();
       
        if (StringUtils.isEmpty(encoding) || VOID.equals(encoding)) {
            encoding = guessEncoding(in, null, 0);
        } else {
            double match = getEncodingMatchingDegree(in, encoding);

            if (match < 100) {
                encoding = guessEncoding(in, encoding, match);
            }
        }
        if (encoding != null && encoding.equalsIgnoreCase(GB2312)) {
            encoding = GBK;
        }
        return encoding;
    }

    public static String[] getEncoding(String content) throws Exception {

        double md = 0;
        String mdEncoding = null;
        String mdNewEncoding = null;
        for (String encoding : CHARSET_ARRAY) {
            for (String newencoding : CHARSET_ARRAY) {
                // if (!encoding.equals(newencoding)) {

                String str = new String(content.getBytes(encoding), newencoding);

                double d = getEncodingMatchingDegree(str);
                // System.out.println("--------------------");
                // System.out.println("newencoding=="+newencoding);
                // System.out.println("encoding=="+encoding);
                // System.out.println("d=="+d);
                // System.out.println("str=="+str);
                if (d >= 100) {
                    return new String[] { encoding, newencoding };
                } else if (d > 0 && (md == 0 || d > md)) {
                    md = d;
                    mdEncoding = encoding;
                    mdNewEncoding = newencoding;
                }

                // }
            }
        }
        if (md > 0) {
            return new String[] { mdEncoding, mdNewEncoding };
        }

        return null;
    }

    public static String[] CHARSET_ARRAY = { "ISO-8859-1", "GBK", "UTF-8",
            "US-ASCII"                  };

    // private static Set<String> CHARSET_ARRAY = Charset.availableCharsets()
    // .keySet();

    private static String guessEncoding(InputStream is,
                                        String exceptEncode,
                                        double match) throws Exception {

        double md = match;
        String encodingStr = exceptEncode;
        for (String encoding : CHARSET_ARRAY) {

            if (exceptEncode == null || !exceptEncode.equals(encoding)) {

                double d = getEncodingMatchingDegree(is, encoding);

                if (d >= 100) {
                    return encoding;
                } else if (d > 0 && (md == 0 || d > md)) {
                    md = d;
                    encodingStr = encoding;
                }

            }
        }
        return encodingStr;
    }

    public static boolean isRightEncoding(String content) {

        return getEncodingMatchingDegree(content) >= 90;
    }

    private static double getEncodingMatchingDegree(InputStream is,
                                                    String encoding)
            throws Exception {

        String content = IOUtils.toString(is, encoding);

        try {
            is.reset();
        } catch (IOException ioex) {
            throw new IllegalArgumentException(
                    "More than the given length had to be read and the given stream could not be reset. Undetermined state for this detection.");

        }

        return getEncodingMatchingDegree(content);
    }

    public static double getEncodingMatchingDegree(String content) {

        // for (char c : SET) {
        // System.out.print(c);
        // }
        content = StringUtils.toDBC(content);
        // System.out.println("---------------------");
        char[] charArray = content.toCharArray();
        int hanziEncodeNum = 0;
        int normailHanziEncodeNum = 0;
        int canNotEncodeNum = 0;
        int qMarkNum = 0;
        // Set<Character> canNotEncodeChar = new HashSet<Character>();
        // Map<Character, Integer> numMap = new HashMap<Character, Integer>();

        Map<Character, Integer> hanziNumMap = new HashMap<Character, Integer>();

        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];

            // Integer num = numMap.get(ch);
            // if (num == null) {
            // num = 1;
            // } else {
            // num++;
            // }
            // numMap.put(ch, num);
            if (ch == 63) {
                // ?
                qMarkNum++;
            } else if (ch == 161 || ch == 165
                       || ch == 166
                       || ch == 168
                       || ch == 169
                       || ch == 171
                       || ch == 177
                       || ch == 183
                       || ch == 215
                       || (ch >= 8192 && ch <= 8231)
                       || (ch >= 8544 && ch <= 8555)
                       || (ch >= 8560 && ch <= 8571)
                       || ch == 9670
                       || ch == 12289
                       || ch == 12290
                       || ch == 12298
                       || ch == 12299
                       || ch == 12302
                       || ch == 12303
                       || ch == 12304
                       || ch == 12305
                       || ch == 65509) {

                // ·:183
                // ×:215
                // ‘:8216
                // “:8220
                // ”:8221
                // …:8230
                // ◆ 9670
                // 、:12289
                // 。:12290
                // 《:12298
                // 》:12299
                // 『:12302
                // 』:12303
                // 【:12304
                // 】:12305
                // ￥:65509
                // punctEncodeNum++;
            } else if (ch >= 65519) {
                canNotEncodeNum++;
                // canNotEncodeChar.add(ch);
            } else if (!CharacterUtils.isSpaceLetter(ch)) {
                if (CharacterUtils.isChineseLetter(ch)) {
                    Integer hanziNum = hanziNumMap.get(ch);
                    if (hanziNum == null) {
                        hanziNum = 1;
                    } else {
                        hanziNum++;
                    }
                    hanziNumMap.put(ch, hanziNum);

                    hanziEncodeNum++;
                    // if(!GARB_WORD_SET.contains(ch)&&!COMMON_WORD_SET.contains(ch))
                    // {
                    // canNotEncodeChar.add(ch);
                    // }
                    if (PatternUtils.isChineseLetter(ch)) {
                        normailHanziEncodeNum++;
                    }
                } else if (CharacterUtils.isEnglishLetter(ch)) {
                    // engEncodeNum++;
                } else if (CharacterUtils.isArabicNumber(ch)) {
                    // numEncodeNum++;
                } else if (PatternUtils.isPunct(ch)) {
                    // punctEncodeNum++;
                } else {
                    if (i > 0) {
                        // System.out.println("canNotEncode:[" + ch
                        // + "]"
                        // + (int) ch);
                        // int bi = i - 10;
                        // if (bi < 0) {
                        // bi = 0;
                        // }
                        // for (int j = bi; j < i; j++) {
                        // System.out.print(charArray[j]);
                        // }
                        //
                        // System.out.print("[" + ch + "]");
                        // int ei = i + 10;
                        // if (ei > charArray.length) {
                        // ei = charArray.length - 1;
                        // }
                        // for (int j = i; j <= ei; j++) {
                        // System.out.print(charArray[j]);
                        // }
                        // System.out.println();
                        canNotEncodeNum++;
                        // canNotEncodeChar.add(ch);
                    }
                }
            }

        }
        // System.out.println(canNotEncodeChar);
        // System.out.println("-------------------------");
        // for (char c : notHanziChar) {
        // System.out.println("notHanziChar:[" + c + "][" + (int) c + "]");
        // }

        // System.out.println("-------------------------");
        // for (char c : canNotEncodeChar) {
        // System.out.println("canNotEncode:[" + c + "][" + (int) c + "]");
        // }

        // System.out.println("-------------------------");
        // for (Map.Entry<Character, Integer> entry : numMap.entrySet()) {
        // System.out.println("canNotEncode:[" + entry.getKey()
        // + "]["
        // + (int) entry.getKey()
        // + "]="
        // + entry.getValue());
        // }

        if (canNotEncodeNum == 0) {
            if (hanziEncodeNum > 0 && hanziEncodeNum > canNotEncodeNum) {
                int maxNum = 0;
                boolean haveGrad = false;
                int commonHanzi = 0;
                int garbHanzi = 0;
                for (Map.Entry<Character, Integer> entry : hanziNumMap
                        .entrySet()) {
                    char ch = entry.getKey();
                    Integer num = entry.getValue();
                    if (num > 25 && !COMMON_WORD_SET.contains(ch)) {
                        maxNum++;
                        if (!haveGrad && GARB_WORD_SET.contains(ch)) {
                            haveGrad = true;
                        }

                        // System.out.println("ccc==" + ch + "=" + num);
                        // System.out.print(ch);
                    }

                    if (COMMON_WORD_SET.contains(ch)) {
                        commonHanzi++;
                    } else if (GARB_WORD_SET.contains(ch)) {
                        garbHanzi++;
                    }
                }
                // System.out.println("maxNum=" + maxNum);
                if (maxNum > 20) {
                    return 0;
                } else if (maxNum > 10 && haveGrad) {
                    return 0;
                } else if (garbHanzi > commonHanzi) {
                    return 0;
                }

            }

            if (qMarkNum > 200) {
                return 0;
            }
            return 100;
        }

        if (hanziEncodeNum > 0 && hanziEncodeNum > canNotEncodeNum) {
            double d = (normailHanziEncodeNum * 100 / hanziEncodeNum);
            return d;
        }

        return 0;
    }

    // private static Set<Character> SET = new HashSet<Character>();

    private static final String         COMMON_WORD     = "编多邮监将本墙员验也核防口安完代于使加火器应跟记录题过视觉把遇阶下环试宁检介简云互四首测次师点似控优席相竞站每收前段苏做渠置道算查款线端来终同这根台审付息单离功类时信括决头距处拟领内出规笔网解足准资扩差源才商具满入着针西我战自架析究构被层咨就裁程探接某潜直配问术值增告牵服级络后绿材持续向认订训讨书城培义之询该标药求型队参评张专且可不价化电任定包仪区客医容念家得交略急性导融对总力低助并施面势虚需估色伴研伙分创节球支到碳效意副整深供有金播撰证汽京传度大策写报护关全提稿案学北车媒划夫观览现所售展职推尔销合际高博场广动活市众新人工文项部公管的行调经各议及业司与文办团务作企会政理计的议等料文筑办述设品路位团年务方通描作图国体主流开发及旅中熟业司营和目企技酒会上与海万宋机执心工间集门政生理物建月制库店项法人统系部行组调产协经负公责地各管数成事能要是户据进为个一件块以实用在了";

    private static final Set<Character> COMMON_WORD_SET = new HashSet<Character>();
    static {
        for (char ch : COMMON_WORD.toCharArray()) {
            COMMON_WORD_SET.add(ch);
        }
    }

    private static final String         GARB_WORD       = "脦脤脨脗脛脨脠脌脧脦脙脮脿脜脟脝脭脕脢脰脫脳脼脪脺脷脴脥脡脻脵脹";

    private static final Set<Character> GARB_WORD_SET   = new HashSet<Character>();
    static {
        for (char ch : GARB_WORD.toCharArray()) {
            GARB_WORD_SET.add(ch);
        }
    }

    public static void main(String[] args) {

        for (int i = 1; i <= 120; i++) {
            char ch = (char) i;
            System.out.println("canNotEncodeNum==" + ch + "[" + (int) ch + "]");
        }
        // char ch = (char) 8213;
        // System.out.println("canNotEncodeNum==" + ch + "[" + (int) ch + "]");
        // ch = (char) 8212;
        // System.out.println("canNotEncodeNum==" + ch + "[" + (int) ch + "]");
        // ch = (char) 8211;
        // System.out.println("canNotEncodeNum==" + ch + "[" + (int) ch + "]");
    }

    // public static void main(String[] args) throws Exception {
    //
    // byte[] c = FileUtils
    // .readFileToByteArray(new File(
    // "d:/MyDocuments/temp/resume/问题简历/01不被识别为简历/2012-02-22/3389740.txt"));
    //
    // // "ISO-8859-1", "GBK", "UTF-8", "US-ASCII"
    // String str = new String(c, "ISO-8859-1");
    // System.out.println(isRightEncoding2(str));
    // System.out.println(str);
    //
    // }
    //
    // public static boolean isRightEncoding2(String content) {
    //
    // char[] charArray = content.toCharArray();
    // int canEncodeNum = 0;
    // int canNotEncodeNum = 0;
    // for (char ch : charArray) {
    // if (!CharacterUtils.isSpaceLetter(ch) && !PatternUtils.isPunct(ch)
    // && !CharacterUtils.isChineseLetter(ch)
    // && !CharacterUtils.isEnglishLetter(ch)
    // && !CharacterUtils.isArabicNumber(ch)) {
    //
    // // System.out.println(ch);
    // canNotEncodeNum++;
    // }
    // }
    // return canNotEncodeNum < 0;
    //
    // }

    // public static final Pattern COMMON_CHINESE_WORD = Pattern
    // .compile("时间|问题|明显|关闭|即时|明星|即是|开关|显然|既然|明了|题目|关键|曼谷|开机|时机|影本|日本|问卷|时光|日报|明白|昵称|照顾|巴黎|日后|最后|开启|关节|问答|暴动|关税|星座|题库|影视|暴力|最初|影片|照片|晶片|是非|日历|显示|明确|明天|昨天|即可|是否|阑珊|星辰|巴西|日子|开发|星际|冒险|开会|晚会|景气|暑假|关系|即使|时候|开创|影像|时代|明年|关心|关切|关怀|开拓|间接|阅览|早已|显著|日期|早期|星期|时期|日前|日益|景观|影响|最好|即将|是以|开始|阅读|开课|开设|门诊|开办|开放|日文|最近|早上|晚上|时刻|日志|日语|日记|早餐|关於|同时|则是|眼睛|冠军|刚刚|服用|具体|目录|赚钱|眼镜|同样|目标|用来|股东|用水|肝炎|月光|贩卖|用地|同志|赠与|同学|然后|体系|体重|购物|目的|眼科|胜利|体制|用户|婴儿|月底|目次|助教|军事|具有|朋友|肿瘤|月初|同一|股票|助理|受理|财政|然而|见到|月刊|胜负|删除|了解|财务|服务|彩色|体会|具备|服饰|股价|赔偿|受伤|同仁|肌肉|用心|爱心|爱情|爱恨|县民|用品|赠品|争取|购屋|体验|县长|同期|辞典|目前|财经|服装|购买|购置|周围|股市|体育|用途|同步|脚步|争议|赠送|脸上|财产|同意|周边|县立|见证|录影|金门|却是|公开|公用|分钟|钢铁|公分|金钱|爸爸|钟表|分机|分析|分类|公报|公所|公斤|分行|银行|公告|键盘|公寓|金额|公车|分割|鉴定|公布|分布|分数|镜头|却不|金融|公平|钢琴|公元|分配|分隔|分子|却又|分解|公务|公会|销售|公顷|分手|公吨|分别|公路|铁路|弟兄|录取|金属|公尺|公司|分区|分局|公共|针对|分散|公益|分级|公约|分组|曾经|公里|父母|公园|录音|分享|分离|公文|错误|错过|公立|父亲|却让|标题|相关|机关|树脂|橡胶|相同|整体|板桥|机构|森林|相机|根本|检查|本来|查核|构想|机械|来源|想法|标准|刺激|检测|概况|模糊|材料|扎营|本省|相当|本土|机场|材质|本身|植物|来的|标的|本科|机制|权利|检举|查看|校庆|核准|格式|模式|本次|才能|核能|机能|构成|检视|来宾|档案|检索|机车|橱窗|核定|检定|校友|权力|本书|相片|棒球|机票|标示|整理|模型|相互|东亚|核可|来电|机电|想要|东西|样子|核发|档名|校务|本人|机会|想像|概念|来信|相信|整个|整合|核心|根据|模拟|想把|榜单|东路|机器|样品|检验|来临|东区|校区|校长|林业|相对|权益|模组|林园|校园|桃园|查询|皮肤|概论|东京|检讨|相连|机率|本文|本部|东部|禁止|本处|标志|构造|来访|东方|液晶|温暖|测量|流量|清晰|流星|澳门|海关|海军|准则|减肥|清楚|清水|潜水|泪水|淡水|流水|澄清|浪潮|深深|游泳|活泼|浪漫|清洁|水源|水准|港澳|渐渐|澳洲|水泥|海洋|渔港|河流|流浪|减少|染料|温馨|海报|海域|决策|法师|水质|温泉|注重|流程|注射|浮生|水利|流行|消失|消息|法律|渔船|活动|滑鼠|浓度|深度|温度|水库|酒店|法案|汽车|减轻|测定|决定|法定|没有|消瘦|治疗|潜力|活力|消费|浪费|河川|漫画|油画|激烈|油压|水平|水电|清除|消除|温柔|涉及|水陆|消防|染色|法院|海外|法人|渔会|准备|渡假|深入|法令|混合|演奏|法规|演员|清单|满足|渔民|测验|浏览|渔业|海芋|演艺|清华|沿革|海岸|演出|水果|法国|洽询|演讲|流通|沟通|决议|水族|测试|水产|海上|深刻|满意|注意|漂亮|当日|光明|当时|当然|精彩|常见|光源|糊涂|判决|常常|烘焙|燃烧|灿烂|当地|光学|光复|荣誉|劳动|省府|精神|尚未|火车|精密|荣获|当中|当初|少数|光碟|烦忧|类型|料理|当天|劳工|精灵|光电|爆发|营建|当做|类似|当代|少年|当年|当作|烦恼|掌握|类别|精品|光临|当局|省长|萤幕|营业|光华|精华|当前|营养|少女|精致|判断|光纤|常识|当选|精选|营运|党部|尝试|营造|精诚|省立|声明|执照|热门|喜爱|帮助|土木|地板|起来|台湾|土地|场地|垃圾|地址|趋势|走势|土壤|超越|地质|场所|执行|报告|走向|增资|幸福|填写|越南|报考|增加|地球|热烈|地理|地形|地政|地面|封面|报到|均可|鼓励|地震|地下|去除|增强|寿险|报名|超人|报价|去年|地位|热心|热情|赶快|帮忙|报表|地区|嘉义|喜欢|培养|超级|城乡|报纸|地点|地图|声音|城市|培育|报请|增设|增进|报导|幸运|培训|地上|地产|超过|地方|生日|房间|黎明|风暴|风景|重量|简易|贸易|反映|卫星|算是|顾问|邮购|自然|皇冠|失败|利用|启用|自助|赞助|身体|看见|科目|节目|行销|节录|学分|身分|师父|看板|怎样|动机|学校|积极|往来|后来|从来|白朴|看来|稽核|风格|生涯|看法|税法|血液|生活|香港|各类|种类|鸟类|欣赏|物料|节省|学报|简报|兴趣|失去|八卦|船舶|律师|牧师|简称|复兴|特质|物质|升学|留学|科学|学系|科系|各种|往往|特种|种种|行程|动物|生物|等待|等等|自我|台风|觉得|觉得|所得|学科|特徵|先生|师生|学生|卫生|启航|学术|彷佛|管制|自行|举行|自从|八德|动向|徘徊|血管|启动|移动|自动|行动|各自|看看|师资|邮资|制度|印度|徵求|程度|重庆|行为|怎麼|各式|程式|秩序|程序|动态|反应|生态|重视|徐福|学者|笔者|答案|特写|移转|特教|冲突|垂直|学士|航空|启事|往事|从事|制定|特定|稳定|我家|冲击|邮寄|顾客|重大|所有|生存|所在|动力|魅力|重复|动画|自由|第一|各项|特殊|反覆|篮球|答覆|启示|物理|生理|的确|管理|血型|风云|和平|白天|邮政|行政|反而|后面|反正|学历|季刊|得到|等到|系列|行列|帅哥|循环|重要|留下|儿子|种子|房子|自强|反弹|印象|风险|重建|兴建|签名|税务|学院|特色|白色|户外|等人|私人|学会|我俩|特价|租赁|生命|简介|从今|范例|我们|生化|学年|复健|物件|邮件|各个|告知|得知|从何|符合|须知|动作|制作|各位|学位|身心|行情|风情|特性|质疑|后悔|射手|科技|学员|委员|简单|特别|科别|行踪|移民|物品|制品|符号|学习|房屋|所属|印刷|印尼|特区|邮局|自己|学长|生长|所长|千万|学期|船期|与其|行业|反对|先前|往前|从前|利益|看出|后续|看好|升级|等级|特约|签约|得奖|徵收|得以|所以|留恋|系统|签署|各界|各国|德国|我国|范围|特点|策略|重点|待遇|所谓|先进|儿童|学童|自主|利率|翻译|简章|简讯|举办|航运|重新|生产|身上|从此|彼此|委托|留言|笑话|制造|身高|身边|拜访|私立|签证|笔记|管道|告诉|等於|之间|能量|或是|参阅|感冒|感受|应用|求助|参见|资金|求才|资本|成本|资格|废水|资深|感染|能源|资源|魔法|冷漠|台湾|广泛|麻烦|废料|资料|广场|参与|之后|礼物|应徵|福利|广告|祷告|厨房|感动|视觉|感觉|态度|戚戚|庆祝|冷冻|祝福|或者|视窗|参考|广大|庞大|威胁|库存|参加|能力|厂牌|参数|次数|成型|麻醉|能否|成功|底下|能够|之外|成人|社会|为何|感情|广播|成员|礼品|求职|视听|社区|成长|尤其|之前|参观|成绩|良好|底线|神经|成果|社团|视野|福音|参选|成熟|凋谢|感谢|允许|资讯|视讯|或许|成交|成效|为止|资产|应该|厂商|度过|感言|凭证|成立|成就|永远|空间|专题|容量|考量|容易|轻易|暂时|专门|真是|都是|老板|家具|宝贝|突然|家用|实用|专用|轮胎|协助|辅助|软体|十分|士林|教材|客栈|专校|完整|审查|审核|考核|未来|专栏|乾净|宪法|教法|老婆|乾燥|栽培|穿越|空白|老师|教师|实质|教学|宠物|事物|真的|辐射|专科|考生|专利|转移|宣告|客户|事先|密度|家庭|都能|完成|车辆|写真|宝宝|事实|真实|专案|教室|专辑|转载|宗教|寂寞|事故|博士|事宜|真空|故事|协定|安定|客家|专家|南韩|宇宙|宣布|实在|实力|空中|宝贵|家里|南非|宁愿|事项|突破|密码|真理|转型|完工|真正|专刊|直到|家电|实现|车子|实际|蠹鱼|索引|实务|事务|家人|老人|协会|都会|教会|室内|定价|密集|空气|安全|完全|专任|输入|宣传|支付|守候|它们|实例|暂停|案例|古代|事件|案件|宿舍|写信|写作|定位|宗旨|害怕|事情|真情|密切|支援|教授|支持|转换|直接|安排|古迹|官员|专员|实践|容器|字号|实习|索取|家属|轻松|实验|考验|专区|家长|专长|穿著|定期|古典|宝典|实业|专业|事业|定义|完美|完善|输出|支出|专线|教练|故乡|安装|字母|完毕|韩国|都市|教育|容颜|辅选|实施|协议|宣导|审议|辅导|教导|客运|审计|家族|南部|干部|考试|截止|输赢|考虑|故意|协商|协调|空调|车站|南方|官方|专访|究竟|力量|大量|有时|加盟|有关|犯贱|大同|架构|有机|带来|大概|大楼|疗法|加油|大地|有趣|存款|大师|力学|大学|获得|独特|有的|大千|获利|有利|套房|带动|大盘|独自|大厦|加州|大成|大专|太空|有空|大事|大家|大安|疾病|疼痛|癫痫|存在|太太|癌症|左右|大力|病患|大型|太平|大致|加工|大哥|在下|大小|大陆|太阳|加强|大多|有了|大名|有限|病人|有人|大会|带领|大气|加入|病例|疲惫|套件|奈何|疲倦|友情|有情|奇怪|病毒|痕迹|在职|驾驶|痛苦|疫苗|大约|病变|症状|加以|大纲|套装|大众|有点|犯罪|架设|希望|有效|加速|有些|有意|独立|在於|贺卡|中间|背景|尽量|数量|费用|补助|书目|畅销|版权|版本|书法|非洲|中油|非常|申报|书籍|中兴|中学|数学|背后|中等|顺利|中和|复制|画廊|顺序|书店|患者|贵宾|数字|画家|蝴蝶|初版|中央|中原|中天|版面|画面|里面|书面|中正|片名|悲伤|中午|顺便|数值|数位|中心|划拨|中毒|数据|北区|中区|书局|中医|初期|中共|中华|中美|中山|中奖|中国|申请|北京|复杂|初步|费率|中文|北部|中部|由此|补充|悲哀|由於|申诉|不易|电影|一时|平时|要闻|一旦|可是|不是|正是|工具|玩具|可爱|一股|不受|天然|不然|理财|而且|原则|否则|一则|不用|一同|不同|硬体|可见|不见|项目|电脑|五金|现金|不错|型录|云林|一样|天才|电梯|电机|原本|一本|再来|原来|下来|不想|理想|互相|不满|电池|一波|电源|亚洲|西洋|政治|现况|平常|正常|政党|天堂|不少|票券|至少|原料|一半|露营|不当|天地|一堆|现场|一块|下去|一声|不幸|平均|一起|环境|破坏|政策|面积|三季|一般|一段|工程|刊物|下的|不得|平等|雷射|牙科|列印|再生|一生|平衡|现行|一律|一笔|一向|不管|互动|电动|原先|示范|一番|可靠|融资|再度|需求|要求|一座|政府|工厂|到底|型式|形式|正式|下次|功能|可能|型态|不能|形态|形成|电视|不良|珠宝|现实|确实|二专|列车|天空|一直|瑞士|硕士|理事|研究|一定|一家|玩家|确定|攻击|形容|平安|不大|再有|原有|不在|现在|正在|一套|更加|压力|电力|珍贵|票数|页数|历史|一片|碟片|磁片|理由|五百|一再|不再|玻璃|不愿|码头|硬碟|环球|要不|原理|天王|一班|电压|正确|玫瑰|灵魂|天天|一天|一致|不敢|一面|下面|平面|正面|霹雳|更正|下列|不到|可否|哥哥|不可|电玩|西元|不要|需要|天下|一下|王子|电子|研发|不多|一阵|残障|不久|形象|现象|下降|理解|破解|一名|不够|刊登|不会|配备|百货|天气|环保|确保|电气|不佳|现任|列入|一份|下午|天使|不仅|不但|现今|至今|不像|现代|理念|石化|元件|百年|配件|零件|历年|一个|配合|电信|零售|融合|工作|一个|耐心|可惜|恐怕|不懂|一切|不必|两性|理性|可怜|二手|歌手|平静|干扰|元素|更换|研拟|列表|球员|下跌|不足|一路|电器|电路|武器|型号|一号|一层|研习|更改|一群|面临|而已|牙医|原著|百万|工业|一对|工艺|面对|正义|瓦斯|面前|歌曲|玉山|两岸|三峡|压缩|不好|班级|形状|不断|可以|原始|不如|不妨|平装|原因|王国|配置|一点|要点|不论|理论|顶端|甄选|票选|可望|一步|研讨|亚运|电讯|原文|天文|贡献|一部|更新|一些|甄试|到处|歌剧|否认|确认|愿意|恐龙|到达|不过|致词|电话|歌词|一边|西方|死亡|至於|发明|阳明|及时|小时|随时|登门|又是|乃是|承受|色彩|引用|张贴|名册|外销|登录|附录|小弟|建构|建材|阶梯|危机|飞机|解析|解决|预测|防治|防火|鱼类|蛋糕|多少|阵营|发烧|阳光|登场|引起|名称|建筑|小学|阶段|随后|及第|建物|外科|解释|象徵|发生|强制|发行|防制|限制|飞行|预告|解答|预算|小儿|外资|强度|角度|陶瓷|名额|名字|预定|危害|子宫|阵容|发布|免疫|孤独|强大|附加|强力|免费|鸳鸯|函数|多数|除非|强烈|发票|小型|外面|陈列|障碍|发现|小小|孩子|解除|引发|勉强|多多|危险|多久|小孩|多怨|了解|预防|角色|名人|盈余|随便|延伸|降低|强化|多年|附件|发信|预估|小心|务必|弹性|急性|负责|防毒|发挥|负担|发表|名单|小品|外汇|附属|延长|发展|院长|预期|引擎|隐藏|外观|登山|发出|子女|陆续|预约|多变|小姐|小组|予以|发给|外界|建国|外国|建设|防护|附设|引进|急诊|建议|引导|预计|承办|外交|发文|附近|防止|预订|承认|名词|建造|外商|外语|强调|小站|发言|承诺|建立|小说|登记|解说|假日|今日|每日|人间|休闲|依照|尽量|含量|何时|入门|像是|仍是|但是|便是|内阁|今晚|仁爱|饱腹|仍然|依然|作用|使用|信用|全体|人体|气体|内销|促销|人权|人才|年来|价格|合格|人格|内涵|做法|合法|作法|依法|无法|侦测|货币|人类|债券|缺少|饲料|饮料|会场|位址|住址|领域|付款|贷款|条款|优势|作坊|入学|化学|佛学|似乎|今后|何种|全身|人物|食物|货物|值得|优待|使得|内科|付邮|侨生|人生|便利|修行|缺乏|仁德|休息|倾向|尽管|保管|优秀|优先|信箱|停留|保留|年资|健康|年度|作为|人为|什麼|年底|每次|供应|饭店|合成|优良|住宿|何者|作者|传真|余额|个案|保守|停车|佛教|便宜|年轻|人士|传输|人事|作家|人家|优惠|住宅|伤害|内容|侵害|伟大|仅有|只有|含有|储存|保存|传奇|全力|人力|集中|年初|人数|企划|领袖|全球|售票|代码|伦理|合理|代理|今天|每天|全面|化工|修正|人工|繁殖|做到|便可|位元|例子|保障|保险|气象|命名|知名|债务|任务|住院|无限|内外|个人|他人|令人|人人|每人|仓储|入会|年内|家俱|售价|低价|健保|人气|健全|保全|介入|气氛|命令|促使|气候|仲介|信仰|价值|他们|你们|人们|条例|偶像|年代|全年|今年|伙伴|保健|信件|条件|每年|任何|集合|创作|合作|合并|每位|饮食|内心|信心|何必|年代|个性|保持|短打|占据|依据|智慧|条规|代表|传播|入口|人口|人员|会员|个别|全民|人民|仪器|作品|食品|货品|代号|信号|舞蹈|任职|修习|领取|修改|无聊|会长|假期|任期|短期|创业|企业|作业|信义|保养|杰出|付出|年级|短线|合约|假如|介绍|例如|供给|传统|年纪|仔细|优异|集团|全国|位置|优点|焦点|缺点|企图|知识|何谓|保育|无论|舒适|人选|叙述|假设|保护|会谈|备注|促进|年龄|领导|会议|合计|命运|估计|货运|会计|伦敦|传送|人文|条文|内部|全部|全新|创新|停止|何处|修订|创意|任意|信托|创造|双方|创立|保证|传递|传说|他说|知道|午餐|位於|世间|也是|疑问|情爱|必然|忽然|心脏|性格|情深|情况|快报|情报|情趣|情势|必须|性质|恢复|比重|心得|懂得|心扉|心底|心态|性能|情感|比赛|比较|心中|心里|情愿|心理|情形|必需|心灵|情歌|必要|怀孕|情人|也会|必修|比例|世代|包含|心情|慢慢|悻悻|憔悴|愉快|慢性|怀疑|包括|怀抱|性别|疑难|情绪|快乐|情丝|情缘|世纪|包装|世界|忽略|也许|比率|快速|按照|投影|摄影|打开|提升|采购|接受|规则|采用|手册|撤销|按钮|投标|招标|指标|授权|规模|表格|规格|表演|扮演|技师|哲学|春风|招生|手术|技术|抑制|排行|控制|推行|损失|搭乘|推动|打算|提升|规范|挽留|投资|推广|抢救|技能|按摩|提案|探索|搜索|撰写|指南|指教|规定|指定|搬家|拟定|打击|夫妻|扩大|拥有|携带|指数|规画|规划|表示|投票|指示|提示|春天|表面|提醒|找到|排列|技巧|捉弄|表现|搭配|摘要|按下|排除|批发|接触|指引|排名|提名|扩张|夫人|提供|投保|担保|责任|担任|投入|指令|替代|青年|操作|担心|毒性|青春|把握|拍摄|指挥|折扣|拥抱|扫描|挑战|挂号|采取|找寻|搜寻|拓展|推展|接著|推荐|技艺|提前|抱歉|插花|指出|推出|提出|手续|持续|接续|契约|接收|拒绝|泰国|据点|描述|授课|措施|抗议|指导|探讨|规章|批评|捷运|排放|播放|接近|表达|抵达|誓言|提高|采访|扩充|民间|只是|战争|虽然|口腔|只见|兄弟|器材|严格|跳蚤|民营|呼吁|品质|品种|严重|路径|民生|只能|战士|另有|只有|唯有|单独|哪里|品牌|唱片|唯一|单一|号码|跳票|员工|呈现|单元|只要|跟随|吸引|足够|另外|别人|只会|单价|叫做|民俗|单位|喇叭|呼吸|跳跃|呼叫|呵呵|哈哈|咖啡|兽医|跟著|只好|路线|吸收|足以|单纯|民国|民众|战略|噪音|距离|民主|民族|哪些|路上|民意|别让|聪明|届时|那时|临时|那是|召开|展开|联盟|居然|成败|凹凸|区分|马桶|那样|尺寸|职棒|取消|司法|欧洲|监测|区域|职称|医师|耳朵|医学|取得|医生|驱动|临床|寻求|长度|那麼|层次|改良|联系|监事|居家|长大|医疗|那里|屋顶|展示|聊天|那天|层面|圣灵|展现|耶稣|紧急|职务|医院|紧张|聚会|居住|取代|联合|那个|习惯|属性|坚持|寻找|联邦|联招|职员|居民|展览|长期|职业|医药|改革|欧美|改善|展出|改变|已经|圣经|联结|联络|听讲|展望|改进|汇率|局部|圣诞|联谊|那些|马上|马达|改造|验证|监督|听说|那就|属於|期间|前景|若是|茫然|塑胶|并且|善用|共同|基金|美金|黄金|对错|基本|对本|前来|梦想|观测|观赏|观光|基地|农地|农场|恭喜|黄昏|对策|前后|前往|尊重|药物|期待|美术|艺术|观看|首先|薪资|甚麼|其次|观摩|养成|华视|业者|其实|差额|落实|并未|草案|警察|观察|董事|美容|花卉|共有|英雄|其中|丛书|前辈|并非|前项|华夏|甚至|若干|前面|义工|养殖|期刊|基础|美丽|美元|普及|对象|著名|义务|业务|蓝色|期限|华人|艺人|农会|华侨|期货|其他|其余|搜集|英俊|兼任|革命|观念|著作|美食|前世|对手|藉口|农民|药品|基层|莅临|药局|首长|蔬菜|农药|农业|菩萨|慈善|典藏|蔚蓝|荷兰|兰屿|美女|业绩|美好|欢乐|梦幻|差异|苹果|基因|业界|美国|英国|观众|观点|花园|共识|观音|敬请|普通|普遍|前述|共享|前进|期望|共计|英文|艺文|散文|欢迎|某些|难忘|前言|对话|美商|英语|对方|基督|难道|基於|对於|出来|出炉|出去|出的|出生|出租|出席|丰富|出版|出现|出发|出货|出售|出口|山区|山庄|出国|街道|总是|恋爱|将军|如同|奖助|媒体|细胞|编目|奖金|经销|乡镇|纪录|收录|结构|结束|以来|将来|幻想|装潢|状况|经济|经常|经营|乡土|缘起|经贸|几乎|以后|以往|终身|女生|编制|变动|收盘|女儿|幼儿|纳税|以为|状态|组成|变成|总额|收支|编辑|幼教|女士|约翰|终究|约定|灾害|好友|努力|女中|好书|经费|收费|变数|绘画|结帐|经由|统一|编码|绝不|经理|以致|变更|经历|编列|奖励|好玩|纲要|以下|女子|以及|给予|好久|姓名|以免|绿色|红色|以外|女人|总会|装备|收集|维修|纳入|缘份|收入|给付|以便|装做|她们|如今|好像|纪念|变化|几个|几何|如何|综合|组合|结合|装作|女性|维持|网路|线路|丝路|乐器|编号|练习|练习|经验|组长|总共|经典|细菌|绝对|以前|收藏|乐观|妇女|纺织|组织|结婚|汇编|缴纳|缠绵|继续|好好|姊妹|娱乐|妹妹|妈妈|纤维|纷纷|纽约|婚姻|姻缘|总统|始终|如果|结果|乐团|装置|乐园|绘图|总论|结论|红颜|维护|绝望|总计|编译|统计|绩效|缴交|总部|以上|好处|装订|如此|经过|变迁|终於|黑暗|国军|回眸|团体|罚锾|回来|思想|异常|买卖|园地|回去|黑白|回顾|累积|四季|男生|众生|回答|因为|回应|因应|图案|回家|固定|国家|思考|国大|国中|图书|图片|回覆|图示|图形|因而|界面|回电|国小|男子|国际|众多|团队|国防|黑色|国外|国人|男人|众人|国会|国内|回馈|图像|国代|思念|夥伴|回信|男性|回忆|困扰|因素|默契|国民|罗马|园区|毕业|困难|园艺|男女|回收|围绕|累计|国文|图文|国产|因此|回话|国语|国立|毕竟|母亲|近日|夜间|透明|证明|注明|说明|文明|主题|议题|话题|试题|课题|护照|剂量|产量|评量|交易|此时|新闻|立即|部门|就是|还是|於是|请问|询问|访问|离开|放开|订阅|岁月|选购|订购|文具|竞争|享受|衣服|竟然|童军|商用|运用|适用|试用|认同|立体|注册|意见|产销|连锁|记录|充分|部分|处分|办公|评鉴|这样|商标|主权|主机|透析|调整|调查|进来|过来|近来|远东|充满|导演|立法|办法|语法|说法|方法|逐渐|主流|交流|上涨|通常|证券|谈判|适当|通报|周报|剧场|商场|市场|立场|过去|论坛|放款|造势|新竹|导师|讲师|高兴|新兴|文学|违反|章程|过程|课程|文物|高等|记得|赢得|放射|诊所|产生|途径|诞生|新生|商行|进行|游行|施行|旅行|遗失|哀愁|熟悉|道德|讯息|选举|旋律|主管|导向|迈向|方向|主动|运动|上升|就算|运算|计算|这儿|亲自|主委|立委|订制|主席|速度|进度|过度|请求|高度|追求|认为|讲座|旅社|市府|这麼|方式|上次|这次|产能|适应|还能|餐厅|效能|效应|商店|造成|达成|译者|读者|记者|充实|竞赛|证实|认真|文字|方案|运转|遵守|频宽|旋转|逻辑|记载|请教|文教|运输|肯定|订定|注定|设定|认定|游客|访客|旅客|评审|放大|享有|还有|交友|还在|主力|高中|证书|读书|文书|新书|运费|新版|这里|文史|卡片|计画|计划|意愿|上班|造型|处理|护理|办理|敲碎|市政|导致|施政|上面|方面|桌面|施工|周刊|遇到|达到|许可|认可|迷雾|戏弄|主要|就要|上下|高阶|离子|主角|许多|过了|新鲜|商务|避免|颜色|戏院|主张|意外|此外|主人|诗人|敌人|还会|议会|设备|评价|交集|退休|文集|主任|进入|进修|选修|放入|遗传|上午|过敏|方便|产值|迄今|就像|餐饮|近代|文化|周年|证件|近年|文件|这个|通信|通知|适合|评估|认知|新知|旅馆|运作|部位|方位|主旨|剧情|商情|记忆|选手|高手|新手|主持|选择|颤抖|交换|虚拟|证据|违规|连接|迎接|牵挂|进口|议员|选战|订单|追踪|市民|选民|商品|产品|通路|道路|讯号|上层|高职|选取|读取|试验|步骤|族群|市区|选区|市长|站长|部长|议长|逾期|近期|商业|就业|产业|课业|主义|讲义|意义|文艺|豪华|部落|辛苦|效益|高峰|进出|岁出|音响|回响|连续|游乐|育乐|音乐|上线|连线|高级|训练|诊断|连结|连络|详细|效果|剧团|帝国|处置|设置|放置|意思|返回|谘询|辨识|认识|语音|意识|卡通|上市|上帝|交通|遭遇|邀请|辩论|评论|言论|讨论|上述|就读|竞选|上课|导游|设施|放弃|旅游|逐步|进步|主导|督导|游戏|谢谢|主办|通讯|速率|频率|设计|效率|文章|音效|论文|语文|文献|高速|迅速|这些|立刻|戏剧|杂志|巡回|透过|通过|邂逅|谘商|词语|语言|说话|造访|虔诚|旁边|市立|设立|认证|忘记|频道|话说|就让|衰退 ");

    public static BASE64Encoder mailEnc = new BASE64Encoder();

    private static final String          UTF_    = "=?UTF-8?B?";

    private static final String          _UTF    = "?=";

    public static String mailEncodeBASE64(String str)
            throws UnsupportedEncodingException {

        String result = UTF_ + mailEnc.encode(str.getBytes(UTF_8)) + _UTF;
        return result;
    }
    
    public static String getEncodingNotGuess(InputStream in, int length)
            throws Exception {

        Charset charset = detector.detectCodepage(in, length);
        String encoding = charset.name();
        if (VOID.equals(encoding)) {
            return null;
        }
        if (encoding != null && encoding.equalsIgnoreCase(GB2312)) {
            encoding = GBK;
        }
        return encoding;

    }

}
