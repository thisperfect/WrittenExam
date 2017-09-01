
package com.dayee.writtenExam.restBaseService;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class BaseApi {

    protected Log log = LogFactory.getLog(getClass());

    protected String returnStr(RestResponse restResponse) {

        if (restResponse != null) {
            String response = JSONObject.toJSONString(restResponse).toString();
            log.debug("response:" + response);
            return response;
        } else {
            JSONObject result = new JSONObject();
            result.put(Constants.returnCodeKey, Constants.API_CODE_500);
            result.put(Constants.returnDescKey, Constants.FAIL_DESC);
            log.debug("response:" + result.toString());
            return result.toString();
        }
    }

    protected String returnStr(String resturnCode,
                               Object returnValue,
                               String returnDes) {

        return returnStr(createRestResponse(resturnCode, returnValue,
                                            returnDes));
    }

    protected String returnStr(Object returnValue) {

        return returnStr(createRestResponse(returnValue));
    }

    protected String returnNULLStr() {

        return returnStr(createNULLResponse());
    }

    protected String returnFAILStr() {

        return returnStr(createFAILResponse());
    }

    protected String returnSUCCStr() {

        return returnStr(createSUCCResponse());
    }

    protected RestResponse createRestResponse(String resturnCode,
                                              Object returnValue,
                                              String returnDes) {

        RestResponse restResponse = new RestResponse();
        restResponse.setReturnCode(resturnCode);
        restResponse.setReturnValue(returnValue);
        restResponse.setReturnDes(returnDes);
        return restResponse;
    }

    protected RestResponse createRestResponse(Object returnValue) {

        return createRestResponse(Constants.SUCC_CODE, returnValue,
                                  Constants.SUCC_DESC);
    }

    protected RestResponse createNULLResponse() {

        return createRestResponse(Constants.API_CODE_499, null,
                                  Constants.NULL_DESC);
    }

    protected RestResponse createFAILResponse() {

        return createRestResponse(Constants.API_CODE_500, null,
                                  Constants.FAIL_DESC);
    }

    protected RestResponse createSUCCResponse() {

        return createRestResponse(Constants.SUCC_CODE, null,
                                  Constants.SUCC_DESC);
    }

    protected RestResponse createSUCC601Response() {

        return createRestResponse(Constants.API_CODE_601, null,
                                  Constants.SUCC_DESC_601);
    }

    protected RestRequest getRestRequest(String content) {

        if (StringUtils.hasLength(content)) {
            return JSONObject.parseObject(content, RestRequest.class);
        }
        return null;
    }
}
