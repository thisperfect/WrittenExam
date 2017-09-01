/*
 * IDEADP是IDEAMOMENT Develop Platform的缩写。
 * 由北京创想时刻信息技术有限公司研发。项目提供各种常见问题的最佳实践和解决方案，用于提高各产品和项目的开发效率。 Copyright @
 * www.ideamoment.com All right reserved.
 */

package com.dayee.writtenExam.framework.restful.json;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dayee.writtenExam.framework.exception.YuncaiMessage;

/**
 * @author Chinakite Zhang
 */
public class JsonData implements Serializable {

    private static final long       serialVersionUID         = -567524430420222607L;

    public static final String      SUCCESS_STATE            = "200";

    public static JsonData          SUCCESS                  = new JsonData(
                                                                     "{\"state\":\"200\",\"type\":\"success\"}");

    public static JsonData          ERROR                    = new JsonData(
                                                                     "{\"state\":\"300\",\"type\":\"error\",\"result\":\"error\"}");

    public static SerializerFeature circularReferenceFeature = SerializerFeature.DisableCircularReferenceDetect;

    public static SerializerFeature browserCompatibleFeature = SerializerFeature.BrowserCompatible;

    private String                  json;

    private Object                  data;

    public static JsonData success(Object data) {

        if (data == null) {
            return JsonData.SUCCESS;
        } else {

            StringBuffer buf = new StringBuffer();
            buf.append("{\"state\":\"200\",\"type\":\"success\",\"data\":");
            String dataJson = JSON
                    .toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss",
                                                circularReferenceFeature,
                                                browserCompatibleFeature);
            buf.append(dataJson);
            buf.append("}");

            return new JsonData(buf.toString());
        }
    }

    public static JsonData warning(String state, String msg, Object data) {

        if (data == null) {
            return JsonData.SUCCESS;
        } else {

            StringBuffer buf = new StringBuffer();
            buf.append("{\"state\":\"").append(state).append("\",\"msg\":\"")
                    .append(msg)
                    .append("\",\"type\":\"warning\",\"warningData\":");
            String dataJson = JSON
                    .toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss",
                                                circularReferenceFeature,
                                                browserCompatibleFeature);
            buf.append(dataJson);
            buf.append("}");

            return new JsonData(buf.toString());
        }
    }

    public static JsonData error(String state, String msg) {

        StringBuffer buf = new StringBuffer();
        buf.append("{\"type\":\"error\",\"state\":\"").append(state)
                .append("\",\"msg\":\"").append(msg).append("\"}");
        return new JsonData(buf.toString());
    }

    public static JsonData exception(String state, String msg) {

        StringBuffer buf = new StringBuffer();
        buf.append("{\"type\":\"waringAndException\",\"state\":\"").append(state)
                .append("\",\"msg\":\"").append(msg).append("\"}");
        return new JsonData(buf.toString());
    }

    public static JsonData warning(String state, String msg) {

        StringBuffer buf = new StringBuffer();
        buf.append("{\"type\":\"warning\",\"state\":\"").append(state)
                .append("\",\"msg\":\"").append(msg).append("\"}");
        return new JsonData(buf.toString());
    }

    public static JsonData warning(YuncaiMessage message) {

        StringBuffer buf = new StringBuffer();
        buf.append("{\"type\":\"warning\",\"state\":\"")
                .append(message.getState()).append("\",\"msg\":\"")
                .append(message.getMessage()).append("\"");
        if (message.getObj() != null) {
            buf.append(",\"data\":");
            String dataJson = JSON
                    .toJSONStringWithDateFormat(message.getObj(),
                                                "yyyy-MM-dd HH:mm:ss",
                                                circularReferenceFeature,
                                                browserCompatibleFeature);
            buf.append(dataJson);
        }
        buf.append("}");

        return new JsonData(buf.toString());
    }

    public static JsonData instance(int state, String msg) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"type\":\"error\",\"state\":");
        buffer.append(state);
        buffer.append(",\"msg\":\"");
        buffer.append(msg);
        buffer.append("\"}");
        return new JsonData(buffer.toString());
    }

    public JsonData(String jsonStr) {

        this.json = jsonStr;
    }

    public JsonData(Object obj) {

        this.data = obj;
    }

    public String getJson() {

        if (json != null) {
            return json;
        } else {
            return JSON.toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss",
                                                   circularReferenceFeature,
                                                   browserCompatibleFeature);
        }
    }

    public boolean isSuccess() {

        JSONObject jsonObject = JSONObject.parseObject(json);
        String state = jsonObject.getString("state");
        return state != null && state.equals(SUCCESS_STATE);
    }
}
