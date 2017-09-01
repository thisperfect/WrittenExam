
package com.dayee.writtenExam.pc.controller;

import com.dayee.writtenExam.framework.restful.json.JsonData;
import com.dayee.writtenExam.pc.model.LoginUser;
import com.dayee.writtenExam.pc.service.LoginUserService;
import com.dayee.writtenExam.pc.service.QuesRepService;
import com.dayee.writtenExam.pc.service.apiService.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by wangchen on 2017/7/7.
 */
@Controller
public class TestController {

    @Autowired
    private QuesRepService   quesRepService;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private CallbackService  callbackService;

    @RequestMapping("testUrl")
    public JsonData testUrl() {

        return JsonData.success("testSuccess");
    }

    @RequestMapping("testMRI")
    public JsonData useMRI() {

        String str = quesRepService.testDubbo();
        return JsonData.success(str);
        // return JsonData.success("11");
    }

    @RequestMapping("testDb")
    public JsonData testDb() {

        List<LoginUser> users = loginUserService.getUsers();
        return JsonData.success(users);
    }

    @RequestMapping("/getPaper/{paperId}")
    public JsonData getPaper(@PathVariable("paperId") Integer paperId) {

        callbackService.getSyncPaper(paperId);
        return JsonData.success("111");
    }

    @RequestMapping("/getResult/{accountId}/{paperId}")
    public JsonData getResult(@PathVariable("accountId") Integer accountId,
                              @PathVariable("paperId") Integer paperId) {

        callbackService.getSyncResult(accountId, paperId);
        return JsonData.success("111");
    }

}
