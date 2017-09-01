
package com.dayee.writtenExam.quesRep.controller;

import com.alibaba.fastjson.JSONObject;
import com.dayee.writtenExam.framework.restful.json.JsonData;
import com.dayee.writtenExam.model.mongo.UserPO;
import com.dayee.writtenExam.quesRep.service.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 14:40 2017/7/13
 * @Modified By:
 * @Version  
 */
@Controller
public class MongoTestController {

    @Autowired
    private MongoUserService mongoUserService;

    @RequestMapping("/saveUser")
    public JsonData saveUser() {

        mongoUserService.saveUser();
        return JsonData.success("success");
    }

    @RequestMapping("/getUser/{id}")
    @ResponseBody
    public String getUserById(@PathVariable String id) {

        // UserPO userPO = mongoUserService.getUserById(id);
        UserPO userPO = mongoUserService.getUserByParams();
        return JSONObject.toJSONString(userPO);
    }
}
