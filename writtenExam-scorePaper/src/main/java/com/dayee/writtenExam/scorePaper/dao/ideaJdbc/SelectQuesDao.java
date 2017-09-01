
package com.dayee.writtenExam.scorePaper.dao.ideaJdbc;

import com.dayee.writtenExam.framework.ideajdbc.baseDao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 19:23 2017/7/18
 * @Modified By:
 * @Version  
 */
@Repository
public class SelectQuesDao extends BaseDao {

    public SelectQuesDao() {
        super.setTableName("t_select_ques_rule");
    }
}
