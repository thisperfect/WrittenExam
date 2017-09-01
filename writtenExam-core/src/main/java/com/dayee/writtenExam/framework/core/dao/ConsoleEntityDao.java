
package com.dayee.writtenExam.framework.core.dao;

import com.dayee.writtenExam.framework.ideajdbc.WrittenJdbc;
import com.dayee.writtenExam.model.BaseEntity;
import org.springframework.stereotype.Component;

/**
 * @author cooltain
 */
@Component
public abstract class ConsoleEntityDao {

    // TODO : 考虑是否有其它方式获取
     public static final String CONSOLE_DB = "ycc";

    @SuppressWarnings("rawtypes")
    protected abstract Class getClassType();

    protected abstract String getTableName();

    @SuppressWarnings("unchecked")
    public <E extends BaseEntity> E findOne(String uuid) {

        String sql = "select * from " + getTableName() + " where C_ID = :id";
        E e = (E) WrittenJdbc.consoleDb().query(sql).setParameter("id", uuid)
                .uniqueTo(getClassType());

        return e;
    }

    /**
     * 删除指定纪录
     * 
     * @param uuid
     * @return
     */
    public Integer deleteOne(String uuid) {

        int result = WrittenJdbc.consoleDb().delete(getClassType(), uuid);

        return result;
    }

    public Integer swapOrder(String uuid1, String uuid2) {

        // Mark. 需要执行4次sql ，考虑是否可以优化的空间

        String sql = "select C_ORDER from " + getTableName()
                     + " where C_ID = :id";
        Integer order1 = (Integer) WrittenJdbc.consoleDb().query(sql)
                .setParameter("id", uuid1).uniqueValue();
        Integer order2 = (Integer) WrittenJdbc.query(sql)
                .setParameter("id", uuid2).uniqueValue();
        if (order1 == null || order2 == null) {
            return 0;
        }

        int effectRows1 = WrittenJdbc.consoleDb().update(getClassType(), uuid1)
                .setProperty("order", order2).execute();
        int effectRows2 = WrittenJdbc.consoleDb().update(getClassType(), uuid2)
                .setProperty("order", order1).execute();

        return effectRows1 + effectRows2;
    }
}
