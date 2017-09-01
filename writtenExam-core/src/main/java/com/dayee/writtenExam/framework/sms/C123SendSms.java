
package com.dayee.writtenExam.framework.sms;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.dayee.writtenExam.framework.config.YuncaiConfig;
import com.dayee.writtenExam.framework.constant.Constants;
import com.dayee.writtenExam.framework.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class C123SendSms {

    private static final Logger logger = LoggerFactory
                                               .getLogger(C123SendSms.class);
    
    private static final String sendOnce        = "sendOnce";

    private static final String getBalance      = "getBalance";

    private static final String action          = "action";

    // 企业账号
    private static final String ac              = "ac";

    // 密钥
    private static final String authkey         = "authkey";

    // 通道组
    private static final String cgid            = "cgid";

    // private static final String pcsid = "csid";

    // 内容
    private static final String c               = "c";

    // 号码
    private static final String m               = "m";

    // 1 操作成功
    // 0 帐户格式不正确(正确的格式为:员工编号@企业编号)
    // -1 服务器拒绝(速度过快、限时或绑定IP不对等)如遇速度过快可延时再发
    // -2 密钥不正确
    // -3 密钥已锁定
    // -4 参数不正确(内容和号码不能为空，手机号码数过多，发送时间错误等)
    // -5 无此帐户
    // -6 帐户已锁定或已过期
    // -7 帐户未开启接口发送
    // -8 不可使用该通道组
    // -9 帐户余额不足
    // -10 内部错误
    // -11 扣费失败
    public static final int     ERROR_0         = 0;

    public static final int     ERROR_1         = -1;

    public static final int     ERROR_2         = -2;

    public static final int     ERROR_3         = -3;

    public static final int     ERROR_4         = -4;

    public static final int     ERROR_5         = -5;

    public static final int     ERROR_6         = -6;

    public static final int     ERROR_7         = -7;

    public static final int     ERROR_8         = -8;

    public static final int     ERROR_9         = -9;

    public static final int     ERROR_10        = -10;

    public static final int     ERROR_11        = -11;

    public static final int     ERROR_12        = -12;

    public static final int     SUCCESS         = 1;

    public static final String  MESAGE_SUCCESS  = "C123_MESAGE_SUCCESS";

    public static final String  MESAGE_ERROR_0  = "C123_MESAGE_ERROR_0";

    public static final String  MESAGE_ERROR_1  = "C123_MESAGE_ERROR_1";

    public static final String  MESAGE_ERROR_2  = "C123_MESAGE_ERROR_2";

    public static final String  MESAGE_ERROR_3  = "C123_MESAGE_ERROR_3";

    public static final String  MESAGE_ERROR_4  = "C123_MESAGE_ERROR_4";

    public static final String  MESAGE_ERROR_5  = "C123_MESAGE_ERROR_5";

    public static final String  MESAGE_ERROR_6  = "C123_MESAGE_ERROR_6";

    public static final String  MESAGE_ERROR_7  = "C123_MESAGE_ERROR_7";

    public static final String  MESAGE_ERROR_8  = "C123_MESAGE_ERROR_8";

    public static final String  MESAGE_ERROR_9  = "C123_MESAGE_ERROR_9";

    public static final String  MESAGE_ERROR_10 = "C123_MESAGE_ERROR_10";

    public static final String  MESAGE_ERROR_11 = "C123_MESAGE_ERROR_11";

    public static final String  MESAGE_ERROR_12 = "C123_MESAGE_ERROR_12";

    public static final String  LABEL_YUAN      = "LABEL_YUAN";

    private static  SmsConfig  systemSmsConfig = null;
    
    public static SmsConfig getMessageConfigInfo() {

        systemSmsConfig = new SmsConfig();
        systemSmsConfig.setC123Account(YuncaiConfig.getC123Account());
        systemSmsConfig.setInterfaceKey(YuncaiConfig.getInterfaceKey());
        systemSmsConfig.setC123Url(YuncaiConfig.getC123Url());
        systemSmsConfig.setChannelGroup(YuncaiConfig.getChannelGroup());
        //
        //systemSmsConfig.setC123Account("1001@500774980084");
        //systemSmsConfig.setInterfaceKey("37EE3ECFAB4E4537061CF961F26E9057");
        //systemSmsConfig.setC123Url("http://smsapi.c123.cn/OpenPlatform/OpenApi");
        //systemSmsConfig.setChannelGroup("6410");
        return systemSmsConfig;
    }

    public static String getMessage(int result) {

        String message = "";
        switch (result) {
        case SUCCESS:
            message = "操作成功";
            break;
        case ERROR_0:
            message ="帐户格式不正确(正确的格式为:员工编号@企业编号)" ;
            break;
        case ERROR_1:
            message = "服务器拒绝(速度过快、限时或绑定IP不对等)如遇速度过快可延时再发";
            break;
        case ERROR_2:
            message = "密钥不正确";
            break;
        case ERROR_3:
            message = "密钥已锁定";
            break;
        case ERROR_4:
            message = "参数不正确(内容和号码不能为空，手机号码数过多，发送时间错误等)";
            break;
        case ERROR_5:
            message = "无此帐户";
            break;
        case ERROR_6:
            message = "帐户已锁定或已过期";
            break;
        case ERROR_7:
            message = "帐户未开启接口发送";
            break;
        case ERROR_8:
            message = "不可使用该通道组";
            break;
        case ERROR_9:
            message = "帐户余额不足";
            break;
        case ERROR_10:
            message = "内部错误";
            break;
        case ERROR_11:
            message = "扣费失败";
            break;
        case ERROR_12:
            message = "调用出现错误";
            break;
        }
        return message;
    }

    public static String getBlance(SmsConfig smsConfig) {

        String result = getMessage(ERROR_12);
        String c123Account = smsConfig.getC123Account();
        String c123Url = smsConfig.getC123Url();
        String interfaceKey = smsConfig.getInterfaceKey();
        if (StringUtils.isEmpty(c123Url) || StringUtils.isEmpty(c123Account)
                || StringUtils.isEmpty(interfaceKey)) {
            return result;
        }
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            if (!c123Url.startsWith(StringUtils.HTTP_DIAGONAL)) {
                c123Url = StringUtils.HTTP_DIAGONAL + c123Url;
            }
            HttpPost httpPost = new HttpPost(c123Url);
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            paramList.add(new BasicNameValuePair(action, getBalance));
            paramList.add(new BasicNameValuePair(ac, c123Account));
            paramList.add(new BasicNameValuePair(authkey, interfaceKey));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,
                                                                   Constants.DEFAULT_ENCODING);
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity resultEntity = httpResponse.getEntity();
            String xml = StringUtils.InputStreamToString(resultEntity
                                                         .getContent(), null);
            logger.debug("查询余额xml:" + xml);
            result = parseBalanceResult(xml);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("出错了", e);
        }
        return result;
    }

    public static String send(SmsConfig smsConfig, String phone, String content) {

        if(smsConfig==null){
            smsConfig = getMessageConfigInfo();
        }

        if (!smsConfig.canSend() || StringUtils.isEmpty(phone)
                || StringUtils.isEmpty(content)) {
            return String.valueOf(ERROR_12);
        }
        String result = String.valueOf(ERROR_12);
        try {
            logger.debug("通过c123发送短信开始");
            String c123Account = smsConfig.getC123Account();
            // String c123Pwd = smsConfig.getC123Pwd();
            String c123Url = smsConfig.getC123Url();
            String channelGroup = smsConfig.getChannelGroup();
            String interfaceKey = smsConfig.getInterfaceKey();
            // String csid = smsConfig.getCsid();
            DefaultHttpClient httpClient = new DefaultHttpClient();
            if (!c123Url.startsWith(StringUtils.HTTP_DIAGONAL)) {
                c123Url = StringUtils.HTTP_DIAGONAL + c123Url;
            }
            HttpPost httpPost = new HttpPost(c123Url);
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            paramList.add(new BasicNameValuePair(action, sendOnce));
            paramList.add(new BasicNameValuePair(ac, c123Account));
            paramList.add(new BasicNameValuePair(authkey, interfaceKey));
            paramList.add(new BasicNameValuePair(c, content));
            paramList.add(new BasicNameValuePair(m, phone));
            paramList.add(new BasicNameValuePair(cgid, channelGroup));
            // paramList.add(new BasicNameValuePair(pcsid, csid));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,
                                                                   Constants.DEFAULT_ENCODING);
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity resultEntity = httpResponse.getEntity();
            String xml = StringUtils.InputStreamToString(resultEntity
                                                         .getContent(), null);
            logger.debug("通过c123发送短信返回xml:" + xml);
            result = parseSendResult(xml);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("出错了", e);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        String result =  send(null, "13918455672", "测试HRSAAS SMS");
        System.out.println(result);
    }

    private static String parseSendResult(String xml) throws Exception {

        /*
         * 成功时返回响应: <xml name="sendOnce" result="1"> <Item cid="xxx" sid="xxx"
         * msgid="xxx" total="2" fee="0.10" remain="170.040"/> </xml>
         */
        /*
         * 失败时返回响应： <xml name="sendOnce" result="-1"/>
         */
        //
        if (StringUtils.hasLength(xml, true)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder
                    .parse(new InputSource(new StringReader(xml)));
            return doc.getFirstChild().getAttributes().getNamedItem("result")
                    .getNodeValue();
        }
        return xml;
    }

    private static String parseBalanceResult(String xml) throws Exception {

        /*
         * <xml name="getBalance" result="1"> <Item cid="xxx" sid="xxx"
         * remain="170.040"/> </xml>
         */
        /*
         * 失败时返回响应： <xml name="getBalance" result="-1"/>
         */
        //
        if (StringUtils.hasLength(xml, true)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder
                    .parse(new InputSource(new StringReader(xml)));
            String result = doc.getFirstChild().getAttributes().item(1)
                    .getTextContent();
            if (result.equals(String.valueOf(SUCCESS))) {
                String blance = doc.getFirstChild().getChildNodes().item(0)
                        .getAttributes().getNamedItem("remain").getNodeValue();
                return blance + "元";
            } else {
                return getMessage(Integer.parseInt(result));
            }
        }
        return xml;
    }
     
}
