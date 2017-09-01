
package com.dayee.writtenExam.scorePaper.service;

import com.alibaba.fastjson.JSONObject;
import com.dayee.writtenExam.framework.util.StringUtils;
import com.dayee.writtenExam.model.mongo.StaticQuesPO;
import com.dayee.writtenExam.model.mongo.StaticSubQuesPO;
import com.dayee.writtenExam.model.mongo.SubjectiveQuesPO;
import com.dayee.writtenExam.scorePaper.dao.ideaJdbc.AccountInfoDao;
import com.dayee.writtenExam.scorePaper.dao.ideaJdbc.AnswerResultDao;
import com.dayee.writtenExam.scorePaper.dao.ideaJdbc.OfficerDistributeDao;
import com.dayee.writtenExam.scorePaper.dao.ideaJdbc.SelectQuesDao;
import com.dayee.writtenExam.scorePaper.dao.mongo.MongoPaperQuesDao;
import com.dayee.writtenExam.scorePaper.dao.mongo.MongoQuesDao;
import com.dayee.writtenExam.scorePaper.model.ideaJdbc.AccountInfoEntity;
import com.dayee.writtenExam.scorePaper.model.ideaJdbc.AnswerResultEntity;
import com.dayee.writtenExam.scorePaper.model.ideaJdbc.OfficerDistributeEntity;
import com.dayee.writtenExam.scorePaper.model.outlet.ScorePaperPO;
import com.dayee.writtenExam.scorePaper.utils.Constants;
import com.ideamoment.ideajdbc.spring.IdeaJdbcTx;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 9:31 2017/7/28
 * @Modified By:
 * @Version  
 */
@Service
public class ScorePaperService {

    @Autowired
    private OfficerDistributeDao officerDistributeDao;

    @Autowired
    private SelectQuesDao        selectQuesDao;

    @Autowired
    private AccountInfoDao       accountInfoDao;

    @Autowired
    private AnswerResultDao      answerResultDao;

    @Autowired
    private MongoPaperQuesDao    mongoPaperQuesDao;

    @Autowired
    private MongoQuesDao         mongoQuesDao;

    @IdeaJdbcTx
    public ScorePaperPO getScorePaperInfo(Integer officerDistributeId) {

        OfficerDistributeEntity officerDistributeEntity = officerDistributeDao
                .getEntityById(OfficerDistributeEntity.class,
                               officerDistributeId);

        if (officerDistributeEntity == null) {
            return null;
        }

        Integer scoreType = officerDistributeEntity.getSendType();
        String accountIds = officerDistributeEntity.getSendPerson();
        Set<String> quesIds = new HashSet<String>();

        Map<String, List<Map<String, String>>> ques_map = new HashMap<String, List<Map<String, String>>>();

        if (scoreType == Constants.SCORE_PAPER_TYPE_BY_PERSON) {
            String paperIds = officerDistributeEntity.getPaperIds();
            List<SubjectiveQuesPO> subPapers = mongoPaperQuesDao
                    .findDocsByInRange("PAPER_ID", paperIds.split(","));

            for (SubjectiveQuesPO subPaper : subPapers) {
                if (!ques_map.containsKey(subPaper.getPaperId())) {
                    ques_map.put(subPaper.getPaperId(),
                                 new ArrayList<Map<String, String>>());
                }
                for (Map<String, String> tmp : subPaper.getSujectiveQues()
                        .values()) {
                    for (String quesId : tmp.keySet()) {
                        Map<String, String> _map = new HashMap<String, String>();
                        String subOrder = null;
                        if (quesId.contains(Constants.SYMBOL_COLON)) {
                            String[] _tmp = quesId
                                    .split(Constants.SYMBOL_COLON);
                            quesId = _tmp[0];
                            subOrder = _tmp[1];
                        }
                        quesIds.add(quesId);

                        _map.put("quesId", quesId);
                        _map.put("order", tmp.get(quesId));
                        _map.put("paperName", subPaper.getPaperName());
                        _map.put("subOrder", subOrder);

                        ques_map.get(subPaper.getPaperId()).add(_map);
                    }
                }
            }
        } else if (scoreType == Constants.SCORE_PAPER_TYPE_BY_QUES) {
            String quesStr = (String) selectQuesDao
                    .getUnitFieldById(officerDistributeEntity
                            .getSelectQuesRule(), "F_SELECT_CONTENT");
            quesStr = quesStr.replace("\"", "");

            List<String> paperIds = new ArrayList<String>();
            for (String str : quesStr.split(Constants.SYMBOL_COMMA)) {
                Map<String, String> _map = new HashMap<String, String>();
                String[] info = str.split(Constants.SYMBOL_UNDERLINE);
                String paperId = info[0];
                String quesId = info[1];
                String order = info[2];
                String paperName = "";
                String subOrder = null;

                if (!ques_map.containsKey(paperId)) {
                    Map<String, Object> conds = new HashMap<String, Object>();
                    conds.put("PAPER_ID", paperId);
                    paperName = mongoPaperQuesDao.findOneDocByParams(conds)
                            .getPaperName();
                    ques_map.put(paperId, new ArrayList<Map<String, String>>());
                }

                if (quesId.contains(Constants.SYMBOL_COLON)) {
                    String[] _tmp = quesId.split(Constants.SYMBOL_COLON);
                    quesId = _tmp[0];
                    subOrder = _tmp[1];
                }

                paperIds.add(paperId);
                quesIds.add(quesId);

                _map.put("quesId", quesId);
                _map.put("order", order);
                _map.put("paperName", paperName);
                _map.put("subOrder", subOrder);
                ques_map.get(paperId).add(_map);
            }
        }

        /* 当前需阅的试卷的题目信息 */
        List<StaticQuesPO> staticQuesList = mongoQuesDao
                .findDocsByIds(quesIds.toArray());

        Map<String, StaticQuesPO> staticQuesMap = new HashMap<String, StaticQuesPO>();
        for (StaticQuesPO staticQuesPO : staticQuesList) {
            staticQuesMap.put(staticQuesPO.getId(), staticQuesPO);
        }

        List<StaticQuesPO> _staticQuesList = new ArrayList<StaticQuesPO>();
        for (String paperId : ques_map.keySet()) {
            List<Map<String, String>> _cond = ques_map.get(paperId);

            for (Map<String, String> _cmap : _cond) {
                StaticQuesPO staticQuesPO = staticQuesMap
                        .get(_cmap.get("quesId"));
                StaticQuesPO _staticQuesPO = new StaticQuesPO();
                copyProperties(_staticQuesPO, staticQuesPO);
                _staticQuesPO.setPaperName(_cmap.get("paperName"));
                _staticQuesPO.setOrder(_cmap.get("order"));

                List<StaticSubQuesPO> _staticSubQuesList = new ArrayList<StaticSubQuesPO>();
                String subOrder = _cmap.get("subOrder");
                if (subOrder != null) {
                    List<StaticSubQuesPO> staticSubQuesList = _staticQuesPO
                            .getSsubQuesList();
                    for (StaticSubQuesPO staticSubQuesPO : staticSubQuesList) {
                        if (staticSubQuesPO.getOrder().toString()
                                .equals(subOrder)) {
                            _staticSubQuesList.add(staticSubQuesPO);
                            break;
                        }
                    }
                }
                _staticQuesPO.setSsubQuesList(_staticSubQuesList);

                _staticQuesList.add(_staticQuesPO);
            }
        }

        List<Integer> accountIdList = new ArrayList<Integer>();
        for (String accId : accountIds.split(Constants.SYMBOL_COMMA)) {
            accountIdList.add(Integer.parseInt(accId));
        }

        /* 当前阅卷的所有账号信息 */
        List<AccountInfoEntity> accountInfoEntityList = accountInfoDao
                .getEntitysById(AccountInfoEntity.class,
                                accountIdList.toArray(new Integer[0]));

        Map<Integer, AccountInfoEntity> _accountInfoEntityMap = new HashMap<Integer, AccountInfoEntity>();
        for (AccountInfoEntity accountInfoEntity : accountInfoEntityList) {
            _accountInfoEntityMap.put(accountInfoEntity.getId(),
                                      accountInfoEntity);
        }

        String sql = "select * from t_account_result a where a.F_ACCOUNT_ID in ("
                     + StringUtils.join(accountIdList, ",")
                     + ") and a.F_QUESTION_ID in ("
                     + StringUtils.join(quesIds, ",")
                     + ")";
        /* 当前阅卷的所有答题结果 */
        List<AnswerResultEntity> answerResultEntityList = answerResultDao
                .getEntitysBySql(AnswerResultEntity.class, sql);

        Map<Integer, Map<Integer, AnswerResultEntity>> _answerResultEntityMap = new HashMap<Integer, Map<Integer, AnswerResultEntity>>();
        for (AnswerResultEntity answerResultEntity : answerResultEntityList) {
            if (!_answerResultEntityMap
                    .containsKey(answerResultEntity.getAccountId())) {
                _answerResultEntityMap
                        .put(answerResultEntity.getAccountId(),
                             new HashMap<Integer, AnswerResultEntity>());
            }
            _answerResultEntityMap.get(answerResultEntity.getAccountId())
                    .put(answerResultEntity.getQuestionId(),
                         answerResultEntity);
        }

        ScorePaperPO scorePaperPO = new ScorePaperPO();
        scorePaperPO.setAccountInfoMap(_accountInfoEntityMap);
        scorePaperPO.setQuesInfoList(_staticQuesList);
        scorePaperPO.setResultInfoStr(JSONObject
                .toJSONString(_answerResultEntityMap));
        scorePaperPO.setSendType(scoreType);
        scorePaperPO.setSendNum(officerDistributeEntity.getSendNum());
        scorePaperPO.setPromptNum(officerDistributeEntity.getPromptNum());
        scorePaperPO.setAnonymous(officerDistributeEntity.getAnonymous());

        return scorePaperPO;
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
}
