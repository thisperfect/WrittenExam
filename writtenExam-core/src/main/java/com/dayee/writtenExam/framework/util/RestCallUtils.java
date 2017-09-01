
package com.dayee.writtenExam.framework.util;

import com.alibaba.fastjson.JSONObject;
import com.dayee.writtenExam.restBaseService.Constants;
import com.dayee.writtenExam.restBaseService.RestRequest;
import com.dayee.writtenExam.restBaseService.RestResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 13:54 2017/7/19
 * @Modified By:
 * @Version  
 */
public class RestCallUtils {

    public static String initPostRestCall(String restUrl,
                                          Object contentParams) {

        RestRequest restRequest = new RestRequest();
        restRequest.setContent(contentParams);

        WebResource r = new Client().resource(restUrl);

        String content = r.post(String.class,
                                JSONObject.toJSONString(restRequest));

        RestResponse restResponse = JSONObject.parseObject(content,
                                                           RestResponse.class);

        if (restResponse.getReturnCode().equals(Constants.SUCC_CODE)) {
            return String.valueOf(restResponse.getReturnValue());
        } else {
            return "error";
        }
    }

    public static String initGetRestCall(String restUrl, Object contentParams) {

        RestRequest restRequest = new RestRequest();
        restRequest.setContent(contentParams);

        WebResource r = new Client().resource(restUrl);

        r.setProperty("content", JSONObject.toJSONString(restRequest));

        String content = r.entity(JSONObject.toJSONString(restRequest))
                .get(String.class);

        RestResponse restResponse = JSONObject.parseObject(content,
                                                           RestResponse.class);

        if (restResponse.getReturnCode().equals(Constants.SUCC_CODE)) {
            return String.valueOf(restResponse.getReturnValue());
        } else {
            return "error";
        }
    }
}
