
package com.dayee.writtenExam.scorePaper.service;

import com.dayee.writtenExam.model.mongo.StaticQuesPO;
import com.dayee.writtenExam.model.mongo.SubjectiveQuesPO;
import com.dayee.writtenExam.scorePaper.dao.ideaJdbc.SelectQuesDao;
import com.dayee.writtenExam.scorePaper.dao.mongo.MongoPaperQuesDao;
import com.dayee.writtenExam.scorePaper.dao.mongo.MongoQuesDao;
import com.dayee.writtenExam.scorePaper.model.ideaJdbc.SelectQuesEntity;
import com.dayee.writtenExam.scorePaper.model.outlet.SelectQuesPO;
import com.dayee.writtenExam.scorePaper.model.outlet.SelectQuesUnitPO;
import com.ideamoment.ideajdbc.spring.IdeaJdbcTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 12:36 2017/7/24
 * @Modified By:
 * @Version  
 */
@Service
public class SelectQuesService {

    @Autowired
    private MongoPaperQuesDao mongoPaperQuesDao;

    @Autowired
    private MongoQuesDao      mongoQuesDao;

    @Autowired
    private SelectQuesDao     selectQuesDao;

    public List<SelectQuesPO> selectQues(String[] paperIds) {

        List<SelectQuesPO> selectQuesPOList = new ArrayList<SelectQuesPO>();

        List<SubjectiveQuesPO> subjectiveQuesPOList = mongoPaperQuesDao
                .findDocsByInRange("PAPER_ID", paperIds);

        Set<String> quesIdList = new HashSet<String>();

        if (!CollectionUtils.isEmpty(subjectiveQuesPOList)) {
            for (SubjectiveQuesPO subjectiveQuesPO : subjectiveQuesPOList) {
                for (Map<String, String> map : subjectiveQuesPO
                        .getSujectiveQues().values()) {
                    for (String quesId : map.keySet()) {
                        if (quesId.contains(":")) {
                            quesId = quesId.split(":")[0];
                        }
                        quesIdList.add(quesId);
                    }
                }
            }
        }

        Map<String, StaticQuesPO> staticQuesPOMap = new HashMap<String, StaticQuesPO>();
        List<StaticQuesPO> staticQuesPOList = mongoQuesDao
                .findDocsByIds(quesIdList.toArray());

        for (StaticQuesPO staticQuesPO : staticQuesPOList) {
            staticQuesPOMap.put(staticQuesPO.getId(), staticQuesPO);
        }

        Map<String, Set<SelectQuesUnitPO>> selectQuesUnits = new HashMap<String, Set<SelectQuesUnitPO>>();
        for (SubjectiveQuesPO subjectiveQuesPO : subjectiveQuesPOList) {
            String paperId = subjectiveQuesPO.getPaperId();
            String paperName = subjectiveQuesPO.getPaperName();
            String corpCode = subjectiveQuesPO.getCorpCode();
            String paperShortName = paperName;

            if (paperName.length() > 6) {
                paperShortName = paperName.substring(0, 3) + "..."
                                 + paperName.substring(paperName.length() - 2);
            }

            Map<String, Map<String, String>> sujectiveQues = subjectiveQuesPO
                    .getSujectiveQues();

            for (String factorName : sujectiveQues.keySet()) {
                if (!selectQuesUnits.containsKey(factorName)) {
                    selectQuesUnits.put(factorName,
                                        new HashSet<SelectQuesUnitPO>());
                }

                Map<String, String> quesMap = sujectiveQues.get(factorName);
                for (Map.Entry<String, String> ques : quesMap.entrySet()) {
                    String quesId = ques.getKey();
                    if (quesId.contains(":")) {
                        quesId = quesId.split(":")[0];
                    }

                    SelectQuesUnitPO selectQuesUnitPO = new SelectQuesUnitPO();
                    selectQuesUnitPO.setQuesId(quesId);
                    selectQuesUnitPO.setNumber(ques.getValue());
                    selectQuesUnitPO.setPaperId(paperId);
                    selectQuesUnitPO.setPaperName(paperName);
                    selectQuesUnitPO.setPaperShortName(paperShortName);
                    selectQuesUnitPO.setCorpCode(corpCode);
                    selectQuesUnitPO.setContent(staticQuesPOMap.get(quesId)
                            .getContent());

                    Integer subNum = staticQuesPOMap.get(quesId)
                            .getSsubQuesList() == null ? 0 : staticQuesPOMap
                                    .get(quesId).getSsubQuesList().size();

                    selectQuesUnitPO.setSubquesNum(subNum);
                    selectQuesUnits.get(factorName).add(selectQuesUnitPO);
                }
            }

            for (Map.Entry<String, Set<SelectQuesUnitPO>> temp : selectQuesUnits
                    .entrySet()) {
                SelectQuesPO selectQuesPO = new SelectQuesPO();
                selectQuesPO.setFactorName(temp.getKey());
                selectQuesPO.setQuesUnits(new ArrayList<>(temp.getValue()));
                selectQuesPOList.add(selectQuesPO);
            }

            for (SelectQuesPO selectQuesPO : selectQuesPOList) {
                Collections.sort(selectQuesPO.getQuesUnits(),
                                 new Comparator<SelectQuesUnitPO>() {

                                     @Override
                                     public int compare(SelectQuesUnitPO o1,
                                                        SelectQuesUnitPO o2) {

                                         return Integer.parseInt(o1.getNumber())
                                                - Integer.parseInt(o2
                                                        .getNumber());
                                     }
                                 });
            }
        }
        return selectQuesPOList;
    }

    @IdeaJdbcTx
    public Integer saveSelectQues(String corpCode, String selectedQues) {

        SelectQuesEntity selectQuesEntity = new SelectQuesEntity();
        selectQuesEntity.setCorpCode(corpCode);
        selectQuesEntity.setSelectQues(selectedQues
                .substring(1, selectedQues.length() - 1));
        selectQuesEntity.setAddDate(new Date());
        return selectQuesDao.save(selectQuesEntity);
    }
}
