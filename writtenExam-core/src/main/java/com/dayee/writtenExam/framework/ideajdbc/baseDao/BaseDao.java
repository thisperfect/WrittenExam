
package com.dayee.writtenExam.framework.ideajdbc.baseDao;

import com.dayee.writtenExam.framework.ideajdbc.WrittenJdbc;
import com.dayee.writtenExam.framework.ideajdbc.baseEntity.BaseEntity;
import com.dayee.writtenExam.framework.util.CamelUnderlineUtils;
import com.dayee.writtenExam.framework.util.ParamTypeEnum;
import com.ideamoment.ideajdbc.action.Query;
import com.ideamoment.ideajdbc.description.JdbcEntityDescription;
import com.ideamoment.ideajdbc.description.JdbcEntityDescriptionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 9:57 2017/7/18
 * @Modified By:
 * @Version  
 */
@Repository
public class BaseDao {

    private String tableName = "";

    public void setTableName(String tableName) {

        this.tableName = tableName;
    }

    /* 根据条件判断是否存在数据 */
    public boolean isExistEntityByParams(Map<String, Object> conds) {

        String sql = getRunSql(conds, ParamTypeEnum.COUNT.getValue(), null);

        return getNumValue(sql, conds) > 0;
    }

    /* 获取表里最大的f_id */
    public Integer getMaxId() {

        String sql = getRunSql(null, ParamTypeEnum.MAXID.getValue(), null);

        return getNumValue(sql, null).intValue();
    }

    public Integer getIdByParams(Map<String, Object> conds) {

        List<String> fields = new ArrayList<String>();
        fields.add("F_ID");
        String sql = getRunSql(conds, ParamTypeEnum.PARAMS.getValue(), fields);

        return getNumValue(sql, conds).intValue();
    }

    public Object getUnitFieldById(Integer id, String fieldName) {

        Map<String, Object> conds = new HashMap<String, Object>();
        conds.put("F_ID", id);
        List<String> fields = new ArrayList<String>();
        fields.add(fieldName);
        String sql = getRunSql(conds, ParamTypeEnum.PARAMS.getValue(), fields);

        return getValue(sql, conds);
    }

    public Object getUnitFieldByParams(Map<String, Object> conds,
                                       List<String> fields) {

        String sql = getRunSql(conds, ParamTypeEnum.PARAMS.getValue(), fields);

        return getValue(sql, conds);
    }

    public <T> T getEntityById(Class<T> clazz, Integer id) {

        JdbcEntityDescription entityDesc = JdbcEntityDescriptionFactory
                .getInstance().getEntityDescription(clazz);
        String idDataItem = entityDesc.getIdDescription().getDataItem();

        String sql = "select " + entityDesc.allDataItemString()
                     + " from "
                     + entityDesc.getDataSet()
                     + " where "
                     + idDataItem
                     + " = :id";

        return (T) WrittenJdbc.query(sql).setParameter("id", id)
                .uniqueTo(clazz);
    }

    public <T> List<T> getEntitysById(Class<T> clazz, Integer[] ids) {

        JdbcEntityDescription entityDesc = JdbcEntityDescriptionFactory
                .getInstance().getEntityDescription(clazz);
        String idDataItem = entityDesc.getIdDescription().getDataItem();

        String sql = "select " + entityDesc.allDataItemString()
                     + " from "
                     + entityDesc.getDataSet()
                     + " where "
                     + idDataItem
                     + " in (:ids)";

        return WrittenJdbc.query(sql).setParameter("ids", ids).listTo(clazz);
    }

    public <T> List<T> getEntitysBySql(Class<T> clazz, String sql) {

        return WrittenJdbc.query(sql).listTo(clazz);
    }

    /* 如果存在，更新了；如果不存在，插入 */
    /* 返回Id */
    public Integer upsave(BaseEntity baseEntity, Map<String, Object> conds) {

        if (baseEntity == null) {
            return null;
        }

        Integer id = getIdByParams(conds);
        if (id > 0) {
            WrittenJdbc.update(baseEntity);
            return id;
        } else {
            return save(baseEntity);
        }
    }

    /* 保存数据 */
    public Integer save(BaseEntity baseEntity) {

        if (baseEntity == null) {
            return null;
        }
        if (baseEntity.getId() == null) {
            Integer _Id = getMaxId() + 1;
            baseEntity.setId(_Id);
        }
        BaseEntity baseTemp = (BaseEntity) WrittenJdbc.save(baseEntity);
        return baseTemp.getId();
    }

    /* 拼写执行的sql语句 */
    private String getRunSql(Map<String, Object> conds,
                             String paramType,
                             List<String> fields) {

        StringBuffer sql_buf = new StringBuffer();
        sql_buf.append("select ");
        sql_buf.append(getParamsSql(fields, paramType));
        sql_buf.append(" from ");
        sql_buf.append(tableName);
        sql_buf.append(getConditionSql(conds));
        return sql_buf.toString();
    }

    /* sql语句拼写要显示的字段 */
    private String getParamsSql(List<String> fields, String paramType) {

        StringBuffer param_buf = new StringBuffer();

        if (ParamTypeEnum.ALL.getValue().equals(paramType)) {
            param_buf.append(" * ");
        } else if (ParamTypeEnum.COUNT.getValue().equals(paramType)) {
            param_buf.append(" count(1) ");
        } else if (ParamTypeEnum.MAXID.getValue().equals(paramType)) {
            param_buf.append(" max(f_id) ");
        } else if (ParamTypeEnum.PARAMS.getValue().equals(paramType)) {
            if (CollectionUtils.isEmpty(fields)) {
                param_buf.append(" * ");
            } else {
                param_buf.append(fields.get(0));
                for (int i = 1; i < fields.size(); i++) {
                    param_buf.append(" , ");
                    param_buf.append(fields.get(i));
                }
            }
        }
        return param_buf.toString();
    }

    /* sql语句拼写参数 */
    private String getConditionSql(Map<String, Object> conds) {

        if (CollectionUtils.isEmpty(conds)) {
            return "";
        }
        StringBuffer cond_buf = new StringBuffer();
        cond_buf.append(" where 1=1 ");
        for (String key : conds.keySet()) {
            cond_buf.append(" and ");
            cond_buf.append(key);
            cond_buf.append("=:");
            cond_buf.append(CamelUnderlineUtils.underlineToCamel(key));
        }
        return cond_buf.toString();
    }

    /* query对象里设置参数 */
    private Query setSqlParameter(Query query, Map<String, Object> conds) {

        for (Map.Entry<String, Object> entry : conds.entrySet()) {
            query.setParameter(CamelUnderlineUtils
                    .underlineToCamel(entry.getKey()), entry.getValue());
        }
        return query;
    }

    /* sql执行返回单个数据值 */
    private Object getValue(String sql, Map<String, Object> conds) {

        Query query = WrittenJdbc.query(sql);
        if (!CollectionUtils.isEmpty(conds)) {
            setSqlParameter(query, conds);
        }
        List list = query.listValue();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /* sql执行返回单个数据值 */
    private Long getNumValue(String sql, Map<String, Object> conds) {

        Object result = getValue(sql, conds);
        if (result == null) {
            return 0L;
        }
        return ((Number) result).longValue();
    }
}
