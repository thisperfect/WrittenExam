
package com.dayee.writtenExam.scorePaper.controller;

import com.dayee.writtenExam.framework.restful.json.JsonData;
import com.dayee.writtenExam.scorePaper.service.SelectQuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 19:52 2017/7/17
 * @Modified By:
 * @Version  
 */
@Controller
@RequestMapping("/selectQues")
public class SelectQuesController {

    @Autowired
    private SelectQuesService selectQuesService;

    @RequestMapping("/deal")
    public ModelAndView getName(String[] paperIds) {

        if (paperIds == null) {
            return new ModelAndView("error");
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("result", selectQuesService.selectQues(paperIds));

        return new ModelAndView("selectQues", data);
    }

    @RequestMapping("/save")
    public JsonData saveSelectedQues(String corpCode, String selectedQues) {

        Integer ruleId = selectQuesService.saveSelectQues(corpCode,
                                                          selectedQues);

        return JsonData.success(ruleId);
    }
}
