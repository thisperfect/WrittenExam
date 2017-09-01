package com.dayee.writtenExam.quesRep.controller;

import com.dayee.writtenExam.framework.restful.json.JsonData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangchen on 2017/7/3.
 */
@Controller
public class TestController {


    @RequestMapping("/showJsonData")
    public JsonData showJsonData(){
        return JsonData.success("success");
    }

    @RequestMapping("/showRMI")
    public JsonData showRMI(){
        return JsonData.success("ttt");
    }

}
