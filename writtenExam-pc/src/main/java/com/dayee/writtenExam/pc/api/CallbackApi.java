
package com.dayee.writtenExam.pc.api;

import com.alibaba.fastjson.JSONObject;
import com.dayee.writtenExam.pc.service.apiService.CallbackService;
import com.dayee.writtenExam.restBaseService.BaseApi;
import com.dayee.writtenExam.restBaseService.RestRequest;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 15:15 2017/7/17
 * @Modified By:
 * @Version  
 */
@Path("/callback")
@Component
@Scope("request")
@Singleton
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
        MediaType.TEXT_HTML, MediaType.APPLICATION_OCTET_STREAM,
        MediaType.TEXT_PLAIN })
public class CallbackApi extends BaseApi {

    private static final Logger apiLogger = Logger
            .getLogger("apiLogger.CallbackApi");

    @Autowired
    private CallbackService     callbackService;

    /* 获取主观题答题结果，并带有账号信息 */
    @Path("/notice")
    @POST
    public String notice(String content,
                         @Context HttpServletRequest request,
                         @Context HttpServletResponse response) {

        if (apiLogger.isInfoEnabled()) {
            apiLogger.info("/callback/notice START");
        }

        if (apiLogger.isDebugEnabled()) {
            apiLogger.debug("content = " + content);
        }

        RestRequest restData = getRestRequest(content);
        if (restData != null) {
            Integer[] contentData = JSONObject
                    .parseObject(restData.getContent().toString(),
                                 Integer[].class);
            Integer accountId = contentData[0];
            Integer paperId = contentData[1];

            if (accountId == null || paperId == null) {
                return returnNULLStr();
            }

            callbackService.startSyncResultTask(accountId, paperId);

            return returnSUCCStr();
        } else {
            return returnNULLStr();
        }
    }
}
