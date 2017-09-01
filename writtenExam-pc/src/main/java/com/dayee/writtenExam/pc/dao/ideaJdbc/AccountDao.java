
package com.dayee.writtenExam.pc.dao.ideaJdbc;

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
public class AccountDao extends BaseDao {

    public AccountDao() {
        super.setTableName("t_account_info");
    }
}
