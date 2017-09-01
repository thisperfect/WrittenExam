
package com.dayee.writtenExam.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtils {

    private static final Logger logger = LoggerFactory
                                               .getLogger(RequestUtils.class);
    
    private static final String X_Forwarded_For      = "X-Forwarded-For";

    private static final String Proxy_Client_IP      = "Proxy-Client-IP";

    private static final String WL_Proxy_Client_IP   = "WL-Proxy-Client-IP";

    private static final String HTTP_CLIENT_IP       = "HTTP_CLIENT_IP";

    private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    private static final String unknown              = "unknown";

    /**
     * simple form 提交 key value
     * 
     * @param url
     * @param paramMap
     * @return
     * @throws Exception
     */
    public static String sendSimplePostRequest(String url,
                                               Map<String, Object> paramMap)
            throws Exception {

        try {
            HttpPost httppost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 参数
            if (CollectionUtils.notEmpty(paramMap)) {
                Set<String> set = paramMap.keySet();
                for (String s1 : set) {
                    NameValuePair np = new BasicNameValuePair(s1,
                            paramMap.get(s1) + "");
                    nvps.add(np);
                }
                httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            }

            HttpClient httpclient = new DefaultHttpClient();
            // 请求超时
            httpclient.getParams()
                    .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                                  100000);
            // 读取超时
            httpclient.getParams()
                    .setParameter(CoreConnectionPNames.SO_TIMEOUT, 100000);
            httpclient.getParams()
                    .setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
                                  Charset.forName("UTF-8"));

            HttpResponse httResponse = httpclient.execute(httppost);

            HttpEntity entity = httResponse.getEntity();
            InputStream is = entity.getContent();
            byte[] jsonConent = IOUtils.toByteArray(is);

            if (jsonConent != null) {
                String json = new String(jsonConent, "UTF-8");
                return json;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return url;
    }

    public static String getClientIpAddress(HttpServletRequest request) {

        String ip = StringUtils.EMPTY_SPACE;

        if (request != null) {

            ip = request.getHeader(X_Forwarded_For);
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader(Proxy_Client_IP);
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader(WL_Proxy_Client_IP);
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader(HTTP_CLIENT_IP);
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader(HTTP_X_FORWARDED_FOR);
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }

        return ip;
    }
    
    // 发送json
    public static String sendJsonRequest(String url, String jsonStr)
            throws ClientProtocolException, IOException {

        HttpPost post = new HttpPost(url);
        post.addHeader("content-type", "application/json");

        post.setEntity(new StringEntity(jsonStr, "UTF-8"));

        HttpClient httpclient = new DefaultHttpClient();
        // 请求超时
        httpclient.getParams()
                .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        // 读取超时
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                                            30000);
        httpclient.getParams()
                .setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
                              Charset.forName("UTF-8"));
        HttpResponse httResponse = httpclient.execute(post);
        if (httResponse == null) {
            return "error";
        } else {
            HttpEntity entity = httResponse.getEntity();
            InputStream respIs = entity.getContent();

            byte[] xmlConent = IOUtils.toByteArray(respIs);
            String content = new String(xmlConent, "UTF-8");
            logger.debug("返回内容:" + content);
            return content;
        }
    }
    
    
    // 发送json
    public static String sendGetJsonRequest(String url)
            throws ClientProtocolException, IOException {

        HttpGet post = new HttpGet(url);
        post.addHeader("content-type", "application/json");
        HttpClient httpclient = new DefaultHttpClient();
        // 请求超时
        httpclient.getParams()
                .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        // 读取超时
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                                            30000);
        httpclient.getParams()
                .setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
                              Charset.forName("UTF-8"));
        HttpResponse httResponse = httpclient.execute(post);
        if (httResponse == null) {
            return "error";
        } else {
            HttpEntity entity = httResponse.getEntity();
            InputStream respIs = entity.getContent();
            byte[] xmlConent = IOUtils.toByteArray(respIs);
            String content = new String(xmlConent, "UTF-8");
            logger.debug("返回内容:" + content);
            return content;
        }
    }


    /**
     * 判断ajax请求
     * 
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {

        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest"
                .equals(request.getHeader("X-Requested-With").toString()));
    }
}
