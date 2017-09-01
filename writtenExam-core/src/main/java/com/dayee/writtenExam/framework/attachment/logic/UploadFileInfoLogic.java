
package com.dayee.writtenExam.framework.attachment.logic;

import java.util.Collection;

import com.dayee.writtenExam.framework.attachment.dao.UploadFileInfoDao;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 附件
 */
@Component
public class UploadFileInfoLogic {

    private static final Logger logger = LoggerFactory
                                               .getLogger(UploadFileInfoLogic.class);

    @Autowired
    private UploadFileInfoDao   uploadFileInfoDao;

    /**
     * 删除
     * 
     * @param corpCode
     * @param ownIds
     * @return
     */
    public int deleteUploadFileInfo(String corpCode, Collection<String> ownIds) {

        int num = 0;
        if (CollectionUtils.isNotEmpty(ownIds)) {

            num = uploadFileInfoDao.delete(corpCode, ownIds);
        }
        
        logger.debug("delete file's total is : " + num);
        
        return num;
    }
    
    
}
