
package com.dayee.writtenExam.pc.service.apiService;

import com.alibaba.fastjson.JSONObject;
import com.dayee.writtenExam.framework.util.RestCallUtils;
import com.dayee.writtenExam.pc.dao.ideaJdbc.AccountDao;
import com.dayee.writtenExam.pc.dao.ideaJdbc.CandidateDao;
import com.dayee.writtenExam.pc.dao.ideaJdbc.ResultDao;
import com.dayee.writtenExam.pc.dao.mongo.MongoPaperDao;
import com.dayee.writtenExam.pc.dao.mongo.MongoPaperQuesDao;
import com.dayee.writtenExam.pc.dao.mongo.MongoQuesDao;
import com.dayee.writtenExam.pc.model.accPaper.AccountEntity;
import com.dayee.writtenExam.pc.model.accPaper.CandidateEntity;
import com.dayee.writtenExam.pc.model.accPaper.ResultEntity;
import com.dayee.writtenExam.pc.model.result.AccountInfoPO;
import com.dayee.writtenExam.pc.model.result.AccountPaperPO;
import com.dayee.writtenExam.pc.model.result.ResultPO;
import com.dayee.writtenExam.pc.model.result.UserInfoPO;
import com.dayee.writtenExam.pc.model.statics.*;
import com.dayee.writtenExam.pc.thread.ResultDealQueue;
import com.ideamoment.ideajdbc.spring.IdeaJdbcTx;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 11:44 2017/7/20
 * @Modified By:
 * @Version  
 */
@Service
public class CallbackService {

    @Autowired
    private MongoPaperDao     mongoPaperDao;

    @Autowired
    private MongoQuesDao      mongoQuesDao;

    @Autowired
    private MongoPaperQuesDao mongoPaperQuesDao;

    @Autowired
    private AccountDao        accountDao;

    @Autowired
    private ResultDao         resultDao;

    @Autowired
    private CandidateDao      candidateDao;

    @Value("${writtenExam.callback.getResult.url}")
    private String            getResultUrl;

    @Value("${writtenExam.callback.getPaper.url}")
    private String            getPaperUrl;

    /* 启动获取答题结果的线程 */
    public void startSyncResultTask(Integer accountId, Integer paperId) {

        ResultDealQueue resultDealQueue = ResultDealQueue.getInstance();
        resultDealQueue.addTaskWithParam(accountId, paperId);
    }

    /* 同步答题结果 */
    @IdeaJdbcTx
    public void getSyncResult(Integer accountId, Integer paperId) {

        String result = RestCallUtils
                .initGetRestCall(getResultUrl,
                                 new Integer[] { accountId, paperId });

        if (result == null) {
            return;
        }

        AccountInfoPO accountInfoPO = JSONObject
                .parseObject(result, AccountInfoPO.class);

        if (CollectionUtils.isEmpty(accountInfoPO.getExamPaperList())) {
            return;
        }

        AccountPaperPO accountPaperPO = accountInfoPO.getExamPaperList().get(0);

        if (CollectionUtils.isEmpty(accountPaperPO.getResultList())) {
            return;
        }

        AccountEntity accountEntity = new AccountEntity();
        copyProperties(accountEntity, accountInfoPO);
        accountEntity.setId(accountInfoPO.getMgrAccountId());
        accountEntity.setPaperId(accountPaperPO.getPaperId().toString());
        accountEntity.setCompleteRate(accountPaperPO.getCompleteRate());
        accountEntity.setPaperName(accountPaperPO.getPaperAliasName());
        accountEntity.setScore(accountPaperPO.getScore());
        accountDao.save(accountEntity);

        if (accountInfoPO.getUserInfo() == null) {
            return;
        }

        UserInfoPO userInfoPO = accountInfoPO.getUserInfo();
        CandidateEntity candidateEntity = new CandidateEntity();
        copyProperties(candidateEntity, userInfoPO);
        candidateEntity.setCorpCode(accountInfoPO.getCorpCode());
        candidateEntity.setAccountId(accountInfoPO.getMgrAccountId());
        candidateDao.save(candidateEntity);

        List<ResultPO> resultPOList = accountPaperPO.getResultList();
        for (ResultPO resultPO : resultPOList) {
            if (resultPO == null) {
                continue;
            }

            ResultEntity resultEntity = new ResultEntity();
            copyProperties(resultEntity, resultPO);
            resultEntity.setProjectId(accountPaperPO.getMgrProjectId());
            resultEntity.setPaperId(accountPaperPO.getPaperId());
            resultEntity.setStoreQuesId(resultPO.getStoreQuesId());

            resultEntity.setIsScore(0);
            if (resultPO.getQuestionType() == 3) {
                resultEntity.setIsScore(1);
            }
            resultDao.save(resultEntity);
        }
    }

    public void getSyncPaper(Integer paperId) {

        String result = RestCallUtils.initPostRestCall(getPaperUrl, paperId);
        if (result == null) {
            return;
        }

        Map<String, Map<String, String>> subjectiveQuesMap = new HashMap<String, Map<String, String>>();

        StaticPaperPO staticPaperPO = JSONObject
                .parseObject(result, StaticPaperPO.class);

        String corpCode = staticPaperPO.getCorpCode();

        List<StaticPagePO> staticPagePOList = staticPaperPO.getSpageList();

        if (CollectionUtils.isEmpty(staticPagePOList)) {
            return;
        }

        for (StaticPagePO staticPagePO : staticPagePOList) {
            List<StaticPartPO> staticPartPOList = staticPagePO.getSpartList();
            if (CollectionUtils.isEmpty(staticPartPOList)) {
                break;
            }
            for (StaticPartPO staticPartPO : staticPartPOList) {

                List<StaticQuesPO> staticQuesPOList = staticPartPO
                        .getSquesList();
                if (CollectionUtils.isEmpty(staticQuesPOList)) {
                    break;
                }
                for (StaticQuesPO staticQuesPO : staticQuesPOList) {
                    staticQuesPO.setPaperId(paperId.toString());
                    staticQuesPO.setCorpCode(corpCode);
                    staticQuesPO.setFactorContent(staticPartPO.getTitle());

                    /* 默认设置为非大小题 */
                    staticQuesPO.setIsSuperior(1);

                    setSubjectiveQues(staticQuesPO, subjectiveQuesMap,
                                      staticPartPO.getTitle());

                    saveStoreQues(staticQuesPO);
                }

            }
        }
        saveStorePaper(staticPaperPO);
        saveSubjectPaperQues(subjectiveQuesMap, staticPaperPO.getMgrPaperId(),
                             staticPaperPO.getPaperName(),
                             staticPaperPO.getCorpCode());
    }

    /* 保存试卷信息 */
    private void saveSubjectPaperQues(Map<String, Map<String, String>> subjectiveQuesMap,
                                      String paperId,
                                      String paperName,
                                      String corpCode) {

        if (CollectionUtils.isEmpty(subjectiveQuesMap)) {
            return;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("PAPER_ID", paperId);
        SubjectPaperQuesPO temp = mongoPaperQuesDao.findOneDocByParams(params);
        if (temp == null) {
            SubjectPaperQuesPO subjectPaperQuesPO = new SubjectPaperQuesPO();
            subjectPaperQuesPO.setPaperId(paperId);
            subjectPaperQuesPO.setPaperName(paperName);
            subjectPaperQuesPO.setCorpCode(corpCode);
            subjectPaperQuesPO.setSujectiveQues(subjectiveQuesMap);
            mongoPaperQuesDao.insertDoc(subjectPaperQuesPO);
        }
    }

    /* 保存试卷信息 */
    private void saveStorePaper(StaticPaperPO staticPaperPO) {

        mongoPaperDao.upsertDocById(staticPaperPO,
                                    staticPaperPO.getMgrPaperId());
    }

    /* 保存题目信息 */
    private void saveStoreQues(StaticQuesPO staticQuesPO) {

        StaticQuesSinglePO single = new StaticQuesSinglePO();
        copyProperties(single, staticQuesPO);

        mongoQuesDao.upsertDocById(single, staticQuesPO.getStoreQuesId());
    }

    /* 试卷对应主观题内容填充 */
    private void setSubjectiveQues(StaticQuesPO staticQuesPO,
                                   Map<String, Map<String, String>> subjectiveQuesMap,
                                   String title) {

        Integer quesType = staticQuesPO.getQuesType();
        Integer quesLevel = staticQuesPO.getQuesLevel();
        String storeQuesId = staticQuesPO.getStoreQuesId();

        if (quesLevel == 0) {
            staticQuesPO.setIsSuperior(0);
        }
        if (quesType == 3 && storeQuesId != null) {
            if (!subjectiveQuesMap.containsKey(title)) {
                subjectiveQuesMap.put(title, new HashMap<String, String>());
            }

            /* 如果题目为大小题，则将小题也计算入内 */
            if (quesLevel == 0) {
                List<StaticSubQuesPO> subQuesList = staticQuesPO
                        .getSsubQuesList();

                for (StaticSubQuesPO staticSubQuesPO : subQuesList) {

                    subjectiveQuesMap.get(title)
                            .put(storeQuesId + ":" + staticSubQuesPO.getOrder(),
                                 staticQuesPO.getNumber());
                }
            } else {
                subjectiveQuesMap.get(title).put(storeQuesId,
                                                 staticQuesPO.getNumber());
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
}
