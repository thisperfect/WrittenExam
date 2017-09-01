
package com.dayee.writtenExam.framework.core.dao;

import java.util.List;

import com.dayee.writtenExam.framework.exception.YuncaiSystemException;
import com.dayee.writtenExam.framework.exception.YuncaiSystemExceptionCode;
import com.dayee.writtenExam.framework.ideajdbc.WrittenJdbc;
import com.ideamoment.ideajdbc.action.Query;
import com.ideamoment.ideajdbc.description.JdbcEntityDescription;
import com.ideamoment.ideajdbc.description.JdbcEntityDescriptionFactory;

public class CommonDao {

    public List exsit(String corpCode, Class clazz) {

        JdbcEntityDescription entityDescription = JdbcEntityDescriptionFactory
                .getInstance().getEntityDescription(clazz);
        if (entityDescription == null) {
            throw new YuncaiSystemException(
                    YuncaiSystemExceptionCode.NOT_HAVE_TABLE_ERROR, "未对应表");
        }
        String tableName = entityDescription.getDataSet();
        if (tableName == null) {
            throw new YuncaiSystemException(
                    YuncaiSystemExceptionCode.NOT_HAVE_TABLE_ERROR, "未对应表");
        }
        String sql = "select c_id  from " + tableName
                     + " where  c_corpcode=:corpCode ";
        Query query = WrittenJdbc.query(sql);
        query.setParameter("corpCode", corpCode);
        List list = query.list();
        return list;
//        if (CollectionUtils.isNotEmpty(list)) {
//            Map<String, Object> map = (HashMap<String, Object>) list.get(0);
//            Long value = (Long) map.get("count");
//            if (value.intValue() > 0) {
//                return true;
//            }
//        }
//        return false;
    }
}
