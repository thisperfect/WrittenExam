
package com.dayee.writtenExam.scorePaper.controller;

import com.alibaba.fastjson.JSONObject;
import com.dayee.writtenExam.framework.restful.json.JsonData;
import com.dayee.writtenExam.scorePaper.model.entrance.AssignPaperPO;
import com.dayee.writtenExam.scorePaper.service.AssignPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 14:54 2017/7/25
 * @Modified By:
 * @Version  
 */
@Controller
@RequestMapping("/assignPaper")
public class AssignPaperController {

    @Autowired
    private AssignPaperService assignPaperService;

    @RequestMapping("/deal")
    public ModelAndView deal(String[] paperIds, String[] accountIds) {

        if (paperIds == null || accountIds == null) {
            return new ModelAndView("error");
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("paperIds", Arrays.asList(paperIds));
        data.put("accountIds", Arrays.asList(accountIds));

        return new ModelAndView("assignPaper", data);
    }

    @RequestMapping("/save")
    public JsonData save(String content) {

        AssignPaperPO assignPaperPO = JSONObject
                .parseObject(content, AssignPaperPO.class);

        assignPaperService.saveAssignPaper(assignPaperPO);

        return JsonData.success("succ");
    }
}
