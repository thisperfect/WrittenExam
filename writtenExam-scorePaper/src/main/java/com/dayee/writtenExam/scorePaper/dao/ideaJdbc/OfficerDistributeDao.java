
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
public class OfficerDistributeDao extends BaseDao {

    public OfficerDistributeDao() {
        super.setTableName("t_officer_distribute");
    }
}
