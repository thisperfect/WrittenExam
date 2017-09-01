package com.dayee.writtenExam.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.dayee.writtenExam.framework.constant.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tang on 2016/9/23.
 */
public class HttpRequestClient {

    private static Logger logger = LoggerFactory
            .getLogger(HttpRequestClient.class);

    public static String post(String url, Map<String, String> params) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String body = null;

        logger.info("create httppost:{}", url);
        HttpPost post = postForm(url, params);

        body = invoke(httpclient, post);

        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    public static String post(String url, JSONObject params) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String body = null;

        logger.info("create httppost:{}", url);
        HttpPost post = postForm(url, params);

        body = invoke(httpclient, post);

        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    public static void post(String url, Map<String, String> params, HttpServletResponse response, String fileName, String fileType) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        logger.info("create httppost:{}", url);
        HttpPost post = postForm(url, params);

        HttpResponse httpResponse = sendRequest(httpclient, post);

        InputStream is = httpResponse.getEntity().getContent();

        OutputStream os = response.getOutputStream();
        response.setContentType(fileType);
        response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder
                .encode(fileName,
                        Constants.DEFAULT_ENCODING));
//        response.setCharacterEncoding("utf-8");

        BufferedInputStream br = new BufferedInputStream(is);
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = br.read(buf)) > 0)
            os.write(buf, 0, len);
//        httpResponse.getEntity().writeTo(os);

        os.flush();
        br.close();
        os.close();
        //response.flushBuffer();

    }

    public static String get(String url) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String body = null;

        logger.info("create httppost:{}", url);
        HttpGet get = new HttpGet(url);
        body = invoke(httpclient, get);

        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    public static String simpleGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        logger.debug("Sending 'GET' request to URL : " + url);
        logger.debug("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(),"utf-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        logger.debug("Response result: {}", response.toString());

        return response.toString();
    }

    public static String postFile(String url, File file) {
        String result = null;
        ContentBody fbody = new FileBody(file);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        MultipartEntity mpEntity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE, null,
                Charset.forName("UTF-8"));
        mpEntity.addPart("file", fbody);
        post.setEntity(mpEntity);

        try {
            HttpResponse response = httpclient.execute(post);

            result = paseResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static String invoke(HttpClient httpclient,
                                 HttpUriRequest httpost) {

        HttpResponse response = sendRequest(httpclient, httpost);
        String body = paseResponse(response);

        return body;
    }

    private static String paseResponse(HttpResponse response) {

        logger.debug("get response from http server..");
        HttpEntity entity = response.getEntity();

        logger.info("response status: {}", response.getStatusLine());
//        String charset =  ContentType.getOrDefault(entity).getCharset().displayName();
//        logger.debug(charset);

        String body = null;
        try {

            // Header[] headers = response.getAllHeaders();
            logger.debug("show the header >>>");
            logger.debug("header-Location : {}", response.getHeaders("Location"));
            body = EntityUtils.toString(entity);
            logger.debug("show the body >>>");
            logger.debug(body);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    private static HttpResponse sendRequest(HttpClient httpclient,
                                            HttpUriRequest httpost) {

        logger.debug("execute post...");
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpPost postForm(String url, Map<String, String> params) {

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            logger.debug("set utf-8 form entity to httppost");
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }

    private static HttpPost postForm(String url, JSONObject params) {

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, (String) params.get(key)));
        }

        try {
            logger.debug("set utf-8 form entity to httppost");
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }
}
