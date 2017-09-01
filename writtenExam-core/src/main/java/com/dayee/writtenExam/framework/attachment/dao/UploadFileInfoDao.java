
package com.dayee.writtenExam.framework.attachment.dao;

import java.util.Collection;

import com.dayee.writtenExam.framework.core.dao.CommonDao;
import com.dayee.writtenExam.framework.ideajdbc.WrittenJdbc;
import com.dayee.writtenExam.model.attachment.UploadFileInfo;
import org.springframework.stereotype.Component;

@Component
public class UploadFileInfoDao extends CommonDao {

    //private static String QUERY_FILE_SQL = "select F.* from T_UPLOAD_FILE_INFO F ";

    /**
     * 新增
     * 
     * @param uploadFileInfo
     * @return
     */
    public UploadFileInfo save(UploadFileInfo uploadFileInfo) {

        return (UploadFileInfo) WrittenJdbc.save(uploadFileInfo);
    }

    /**
     * 修改
     * 
     * @param uploadFileInfo
     * @return
     */
    public int update(UploadFileInfo uploadFileInfo) {

        return WrittenJdbc.update(uploadFileInfo);
    }

    /**
     * 删除
     * 
     * @param corpCode
     * @param ownIds
     * @return
     */
    public int delete(String corpCode, Collection<String> ownIds) {

        String sql = "delete from T_UPLOAD_FILE_INFO where c_corpCode = :corpCode and C_OWN_ID in ( :ownIds ) ";

        return WrittenJdbc.sql(sql).setParameter("corpCode", corpCode)
                .setParameter("ownIds", ownIds).execute();
    }

}
