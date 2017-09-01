
package com.dayee.writtenExam.scorePaper.dao.ideaJdbc;

import com.dayee.writtenExam.framework.ideajdbc.baseDao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 9:05 2017/7/19
 * @Modified By:
 * @Version  
 */
@Repository
public class AnswerResultDao extends BaseDao {

    public AnswerResultDao() {
        super.setTableName("t_account_result");
    }
}
