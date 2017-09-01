
package com.dayee.writtenExam.scorePaper.service;

import com.dayee.writtenExam.framework.util.CollectionUtils;
import com.dayee.writtenExam.framework.util.StringUtils;
import com.dayee.writtenExam.model.mongo.SubjectiveQuesPO;
import com.dayee.writtenExam.scorePaper.dao.ideaJdbc.DistributionDetailDao;
import com.dayee.writtenExam.scorePaper.dao.ideaJdbc.OfficerDistributeDao;
import com.dayee.writtenExam.scorePaper.dao.ideaJdbc.ScoreOfficerDao;
import com.dayee.writtenExam.scorePaper.dao.ideaJdbc.SelectQuesDao;
import com.dayee.writtenExam.scorePaper.dao.mongo.MongoPaperQuesDao;
import com.dayee.writtenExam.scorePaper.model.entrance.AssignGroupPO;
import com.dayee.writtenExam.scorePaper.model.entrance.AssignOfficerPO;
import com.dayee.writtenExam.scorePaper.model.entrance.AssignPaperPO;
import com.dayee.writtenExam.scorePaper.model.ideaJdbc.DistributionDetailEntity;
import com.dayee.writtenExam.scorePaper.model.ideaJdbc.OfficerDistributeEntity;
import com.dayee.writtenExam.scorePaper.model.ideaJdbc.ScoreOfficerEntity;
import com.ideamoment.ideajdbc.spring.IdeaJdbcTx;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 14:54 2017/7/25
 * @Modified By:
 * @Version  
 */
@Service
public class AssignPaperService {

    @Autowired
    private MongoPaperQuesDao     mongoPaperQuesDao;

    @Autowired
    private ScoreOfficerDao       scoreOfficerDao;

    @Autowired
    private OfficerDistributeDao  officerDistributeDao;

    @Autowired
    private SelectQuesDao         selectQuesDao;

    @Autowired
    private DistributionDetailDao distributionDetailDao;

    /**
     * 当分配阅卷方式为按候选人阅卷时，分配给阅卷官的为整套试卷的题目 当分配阅卷方式为按题阅卷时，分配给阅卷官的为特定规则下的题目
     */
    @IdeaJdbcTx
    public void saveAssignPaper(AssignPaperPO assignPaperPO) {

        if (assignPaperPO == null) {
            return;
        }
        if (CollectionUtils.isEmpty(assignPaperPO.getGroups())) {
            return;
        }

        List<String> accountIds = assignPaperPO.getAccunntIds();

        List<AssignGroupPO> assignGroupPOList = assignPaperPO.getGroups();

        /* 记录分配的账号每组该有多少个 */
        Map<Integer, Integer> map_1 = new HashMap<Integer, Integer>();

        /* 记录分配的账号的剩余个数 */
        Map<Integer, Integer> map_2 = new HashMap<Integer, Integer>();

        /* 分完组后的账号 */
        Map<Integer, List<List<String>>> result;

        int groupSize = assignGroupPOList.size();
        if (groupSize == 1) {
            int officerNum = assignGroupPOList.get(0).getOfficers().size();

            result = getShuffleAccountIdsOneGroup(accountIds, officerNum);

            map_2.put(null, 0);
        } else {
            for (AssignGroupPO assignGroupPO : assignGroupPOList) {
                if (assignGroupPO != null && CollectionUtils
                        .isNotEmpty(assignGroupPO.getOfficers())) {
                    map_2.put(assignGroupPO.getNumber(), 0);
                    if (map_1.containsKey(assignGroupPO.getNumber())) {
                        map_1.put(assignGroupPO
                                .getNumber(),
                                  map_1.get(assignGroupPO.getNumber())
                                              + assignGroupPO.getOfficers()
                                                      .size());
                    } else {
                        map_1.put(assignGroupPO.getNumber(),
                                  assignGroupPO.getOfficers().size());
                    }
                }
            }
            result = getShuffleAccountIds(accountIds, map_1);
        }

        for (AssignGroupPO assignGroupPO : assignGroupPOList) {
            if (assignGroupPO != null
                && CollectionUtils.isNotEmpty(assignGroupPO.getOfficers())) {

                Integer officerId;
                for (AssignOfficerPO assignOfficerPO : assignGroupPO
                        .getOfficers()) {
                    officerId = saveOfficer(assignOfficerPO);

                    int progress = map_2.get(assignGroupPO.getNumber());
                    List<String> officers = result
                            .get(assignGroupPO.getNumber()).get(progress);

                    saveOfficerDistribute(assignPaperPO, officerId,
                                          officers.size(), officers);

                    map_2.put(assignGroupPO.getNumber(), progress + 1);
                }
            }
        }
    }

    private void copyProperties(Object dest, Object orig) {

        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Map<Integer, List<List<String>>> getShuffleAccountIds(List<String> accountIds,
                                                                  Map<Integer, Integer> groupMap) {

        Collections.shuffle(accountIds);

        int progress = 0;
        Map<Integer, List<List<String>>> result = new HashMap<Integer, List<List<String>>>();
        for (Map.Entry<Integer, Integer> entry : groupMap.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            List<List<String>> list_temp = new ArrayList<List<String>>();

            for (int i = 0; i < value; i++) {
                List<String> list_unit = new ArrayList<String>();
                for (int j = 0; j < key; j++) {
                    list_unit.add(accountIds.get(progress));
                    progress += 1;
                }
                list_temp.add(list_unit);
            }
            result.put(key, list_temp);
        }

        return result;
    }

    private Map<Integer, List<List<String>>> getShuffleAccountIdsOneGroup(List<String> accountIds,
                                                                          Integer officerNum) {

        Collections.shuffle(accountIds);

        Integer accountNum = accountIds.size();
        int unitNum = accountNum / officerNum;
        int overageNum = accountNum % officerNum;
        int progress = 0;
        Map<Integer, List<List<String>>> result = new HashMap<Integer, List<List<String>>>();
        result.put(null, new ArrayList<List<String>>());

        for (int i = 0; i < officerNum; i++) {
            int tmp = unitNum;
            if (overageNum > 0) {
                tmp++;
                overageNum--;
            }

            List<String> list_unit = new ArrayList<String>();
            for (int j = 0; j < tmp; j++) {
                list_unit.add(accountIds.get(progress));
                progress++;
            }
            result.get(null).add(list_unit);
        }

        return result;
    }

    @IdeaJdbcTx
    private Integer saveOfficer(AssignOfficerPO assignOfficerPO) {

        ScoreOfficerEntity scoreOfficerEntity = new ScoreOfficerEntity();
        copyProperties(scoreOfficerEntity, assignOfficerPO);
        scoreOfficerEntity.setStatus(0);
        Map<String, Object> conds = new HashMap<String, Object>();
        conds.put("F_EMAIL", scoreOfficerEntity.getEmail());
        return scoreOfficerDao.upsave(scoreOfficerEntity, conds);
    }

    @IdeaJdbcTx
    private void saveOfficerDistribute(AssignPaperPO assignPaperPO,
                                       Integer officerId,
                                       Integer groupNum,
                                       List<String> persons) {

        OfficerDistributeEntity officerDistributeEntity = new OfficerDistributeEntity();
        officerDistributeEntity.setScoreOfficerId(officerId);
        officerDistributeEntity.setPaperIds(StringUtils
                .join(assignPaperPO.getPaperIds(), ","));
        officerDistributeEntity
                .setSelectQuesRule(assignPaperPO.getSelectRuleId());
        officerDistributeEntity.setSendNum(groupNum);
        officerDistributeEntity.setSendTime(new Date());
        officerDistributeEntity.setSendType(assignPaperPO.getScoreType());
        officerDistributeEntity.setAnonymous(assignPaperPO.getAnonymous());
        officerDistributeEntity.setSendSms(assignPaperPO.getSms());
        officerDistributeEntity.setEmailInfo(assignPaperPO.getEmailInfo());
        officerDistributeEntity.setSendPerson(StringUtils.join(persons, ","));

        Integer id = officerDistributeDao.save(officerDistributeEntity);

        if (assignPaperPO.getScoreType() == 0) {
            saveDistributionDetailByPerson(officerId, id,
                                           assignPaperPO.getPaperIds(),
                                           persons);
        } else {
            saveDistributionDetailByQues(assignPaperPO.getSelectRuleId(),
                                         officerId, id, persons);
        }
    }

    private void saveDistributionDetailByPerson(Integer officerId,
                                                Integer externalId,
                                                List<String> paperIds,
                                                List<String> persons) {

        List<SubjectiveQuesPO> subjectiveQuesPOList = mongoPaperQuesDao
                .findDocsByInRange("PAPER_ID", paperIds.toArray());

        if (CollectionUtils.isEmpty(subjectiveQuesPOList)) {
            return;
        }

        for (SubjectiveQuesPO subjectiveQuesPO : subjectiveQuesPOList) {
            for (String accountId : persons) {
                for (Map<String, String> map : subjectiveQuesPO
                        .getSujectiveQues().values()) {
                    for (String quesId : map.keySet()) {

                        DistributionDetailEntity distributionDetailEntity = new DistributionDetailEntity();
                        distributionDetailEntity.setAccountId(accountId);
                        distributionDetailEntity
                                .setPaperId(subjectiveQuesPO.getPaperId());
                        distributionDetailEntity.setQuestionId(quesId);
                        distributionDetailEntity.setScoreOfficerId(officerId);
                        distributionDetailEntity.setPromptStatus(0);
                        distributionDetailEntity.setExternalId(externalId);

                        distributionDetailDao.save(distributionDetailEntity);
                    }
                }
            }
        }
    }

    private void saveDistributionDetailByQues(Integer selectRuleId,
                                              Integer officerId,
                                              Integer externalId,
                                              List<String> persons) {

        Map<String, Object> conds = new HashMap<String, Object>();
        conds.put("F_ID", selectRuleId);
        List<String> fields = new ArrayList<String>();
        fields.add("F_SELECT_CONTENT");
        String quesIds = (String) selectQuesDao.getUnitFieldByParams(conds,
                                                                     fields);
        String[] quesIdArray = quesIds.split(",");

        for (String accountId : persons) {
            for (String quesId : quesIdArray) {
                String[] paper_ques = quesId.replace("\"", "").split("_");

                DistributionDetailEntity distributionDetailEntity = new DistributionDetailEntity();
                distributionDetailEntity.setAccountId(accountId);
                distributionDetailEntity.setPaperId(paper_ques[0]);
                distributionDetailEntity.setQuestionId(paper_ques[1]);
                distributionDetailEntity.setScoreOfficerId(officerId);
                distributionDetailEntity.setPromptStatus(0);
                distributionDetailEntity.setExternalId(externalId);

                distributionDetailDao.save(distributionDetailEntity);
            }
        }
    }
}
