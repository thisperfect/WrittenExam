
package com.dayee.writtenExam.pc.dao.ideaJdbc;

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
public class ResultDao extends BaseDao {

    public ResultDao() {
        super.setTableName("t_account_result");
    }
}
