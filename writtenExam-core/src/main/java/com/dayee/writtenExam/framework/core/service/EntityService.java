
package com.dayee.writtenExam.framework.core.service;

import com.dayee.writtenExam.framework.core.dao.EntityDao;
import com.dayee.writtenExam.framework.usercontext.UserContext;
import com.dayee.writtenExam.model.BaseEntity;
import com.ideamoment.ideajdbc.spring.IdeaJdbcTx;
import org.springframework.stereotype.Service;

/**
 * @author cooltain
 */
@Service
public abstract class EntityService {

    protected abstract EntityDao getEntityDao();

    protected String getCurrentCorpCode() {

        return UserContext.getCurrentContext().getCorpCode();
    }

    protected String getCurrentUserId() {

        return UserContext.getCurrentContext().getUser().getUserId();
    }

    /**
     * 根据唯一标识来获取指定的实体
     * 
     * @param uuid
     * @return
     */
    @IdeaJdbcTx(readOnly = true)
    public <E extends BaseEntity> E findOne(String uuid) {

        String corpCode = getCurrentCorpCode();

        return getEntityDao().findOne(corpCode, uuid);
    }

    /**
     * 删除一条数据
     * 
     * @param uuid
     * @return
     */
    @IdeaJdbcTx
    public int deleteOne(String uuid) {

        String corpCode = getCurrentCorpCode();

        return getEntityDao().deleteOne(corpCode, uuid);
    }

    /**
     * 改变状态
     * 
     * @param uuid
     * @param status
     * @return
     */
    @IdeaJdbcTx
    public Integer changeStatus(String uuid, Integer status) {

        String corpCode = getCurrentCorpCode();
        String userId = getCurrentUserId();

        return getEntityDao().changeStatus(corpCode, userId, uuid, status);
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

        String corpCode = getCurrentCorpCode();

        if (uuid1 == null || uuid2 == null) {
            return 0;
        }
        return getEntityDao().swapOrder(corpCode, uuid1, uuid2);
    }

}
