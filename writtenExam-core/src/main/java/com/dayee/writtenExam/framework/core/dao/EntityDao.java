
package com.dayee.writtenExam.framework.core.dao;

import java.util.Date;
import java.util.List;

import com.dayee.writtenExam.framework.ideajdbc.WrittenJdbc;
import com.dayee.writtenExam.framework.util.StringUtils;
import com.dayee.writtenExam.model.BaseEntity;

/**
 * @author cooltain
 */
public abstract class EntityDao extends CommonDao {

    @SuppressWarnings("rawtypes")
    protected abstract Class getClassType();

    protected abstract String getTableName();

    /**
     * 根据唯一标识返回一条纪录
     * <ul>
     * <li>要求要有corpCode属性</li>
     * </ul>
     * 
     * @param corpCode
     * @param uuid
     * @return
     */
    @SuppressWarnings("unchecked")
    public <E extends BaseEntity> E findOne(String corpCode, String uuid) {

        String sql = "select * from " + getTableName()
                     + " where C_CORPCODE = :corpCode and C_ID = :id";
        E e = (E) WrittenJdbc.query(sql).setParameter("corpCode", corpCode)
                .setParameter("id", uuid).uniqueTo(getClassType());

        return e;
    }

    /**
     * 根据唯一标识返回一条纪录
     * <ul>
     * <li>要求要有corpCode属性</li>
     * </ul>
     * 
     * @param corpCode
     * @param uuid
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <E extends BaseEntity> E findOne(String corpCode,
                                            String uuid,
                                            Class clazz) {

        String sql = "select * from " + getTableName()
                     + " where C_CORPCODE = :corpCode and C_ID = :id";
        E e = (E) WrittenJdbc.query(sql).setParameter("corpCode", corpCode)
                .setParameter("id", uuid).uniqueTo(clazz);

        return e;
    }

    /**
     * 删除指定纪录
     * 
     * @param uuid
     * @return
     */
    public Integer deleteOne(String corpCode, String uuid) {

        int result = WrittenJdbc.delete(getClassType(), uuid, corpCode);

        return result;
    }

    /**
     * 更改状态
     * <ul>
     * <li>要求要有status，modifier，modifyTimed三个属性</li>
     * </ul>
     * 
     * @param corpCode
     * @param userId
     * @param uuid
     * @param status
     * @return
     */
    public Integer changeStatus(String corpCode,
                                String userId,
                                String uuid,
                                Integer status) {

        Date optTime = new Date();
        int effectRows = WrittenJdbc.updateCorp(getClassType(), uuid, corpCode)
                .setProperty("status", status).setProperty("modifier", userId)
                .setProperty("modifyTime", optTime).execute();
        return effectRows;
    }

    /**
     * 更换指定的两条纪录的顺序
     * <ul>
     * <li>要求要有corpCode，order属性</li>
     * </ul>
     * 
     * @param corpCode
     * @param uuid1
     * @param uuid2
     * @return
     */
    public Integer swapOrder(String corpCode, String uuid1, String uuid2) {

        // Mark. 需要执行4次sql ，考虑是否可以优化的空间

        String sql = "select C_ORDER from " + getTableName()
                     + " where C_CORPCODE = :corpCode and C_ID = :id";
        Integer order1 = (Integer) WrittenJdbc.query(sql)
                .setParameter("corpCode", corpCode).setParameter("id", uuid1)
                .uniqueValue();
        Integer order2 = (Integer) WrittenJdbc.query(sql)
                .setParameter("corpCode", corpCode).setParameter("id", uuid2)
                .uniqueValue();
        if (order1 == null || order2 == null) {
            return 0;
        }

        int effectRows1 = WrittenJdbc.updateCorp(getClassType(), uuid1, corpCode)
                .setProperty("order", order2).execute();
        int effectRows2 = WrittenJdbc.updateCorp(getClassType(), uuid2, corpCode)
                .setProperty("order", order1).execute();

        return effectRows1 + effectRows2;
    }

    public <E extends List> E findAll(String corpCode, String order) {

        String sql = "select * from " + getTableName()
                     + " where C_CORPCODE = :corpCode";
        if (StringUtils.hasLength(order)) {
            sql = sql = " order by  " + order;
        }
        E e = (E) WrittenJdbc.query(sql).setParameter("corpCode", corpCode)
                .listTo(getClassType());
        return e;
    }

}
