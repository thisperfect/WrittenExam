
package com.dayee.writtenExam.framework.core.service;

import com.dayee.writtenExam.framework.core.dao.ConsoleEntityDao;
import com.dayee.writtenExam.model.BaseEntity;
import com.ideamoment.ideajdbc.spring.IdeaJdbcTx;
import org.springframework.stereotype.Service;

/**
 * @author cooltain
 */
@Service
public abstract class ConsoleEntityService {

    protected abstract ConsoleEntityDao getConsoleEntityDao();

    /**
     * 根据唯一标识来获取指定的实体
     * 
     * @param uuid
     * @return
     */
    @IdeaJdbcTx(readOnly = true)
    public <E extends BaseEntity> E findOne(String uuid) {

        return getConsoleEntityDao().findOne(uuid);
    }

    /**
     * 删除一条数据
     * 
     * @param uuid
     * @return
     */
    @IdeaJdbcTx
    public int deleteOne(String uuid) {

        return getConsoleEntityDao().deleteOne(uuid);
    }

    /**
     * 交换两个的顺序
     * 
     * @param uuid1
     * @param uuid2
     * @return
     */
    @IdeaJdbcTx
    public Integer swapOrder(String uuid1, String uuid2) {

        if (uuid1 == null || uuid2 == null) {
            return 0;
        }
        return getConsoleEntityDao().swapOrder(uuid1, uuid2);
    }
}
