package com.dayee.writtenExam.scorePaper.dao.ideaJdbc;

import com.dayee.writtenExam.framework.ideajdbc.baseDao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 14:56 2017/7/28
 * @Modified By:
 * @Version  
 */
@Repository
public class AccountInfoDao extends BaseDao {

    public AccountInfoDao() {
        super.setTableName("t_account_info");
    }
}
