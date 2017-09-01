
package com.dayee.writtenExam.scorePaper.controller;

import com.dayee.writtenExam.scorePaper.model.outlet.ScorePaperPO;
import com.dayee.writtenExam.scorePaper.service.ScorePaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 9:30 2017/7/28
 * @Modified By:
 * @Version  
 */
@Controller
@RequestMapping("/scorePaper")
public class ScorePaperController {

    @Autowired
    private ScorePaperService scorePaperService;

    @RequestMapping("/deal")
    public ModelAndView deal(Integer id) {

        ScorePaperPO scorePaperPO = scorePaperService.getScorePaperInfo(id);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", scorePaperPO);

        return new ModelAndView("scorePaperByPer", data);
    }
}
