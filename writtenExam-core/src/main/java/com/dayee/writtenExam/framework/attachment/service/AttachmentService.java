
package com.dayee.writtenExam.framework.attachment.service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dayee.writtenExam.framework.attachment.UploadState;
import com.dayee.writtenExam.framework.attachment.logic.AttachmentLogic;
import com.dayee.writtenExam.framework.usercontext.UserContext;
import com.dayee.writtenExam.framework.util.StringUtils;
import com.dayee.writtenExam.model.attachment.UploadFileInfo;
import com.ideamoment.ideajdbc.spring.IdeaJdbcTx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 附件Service
 */
@Service
public class AttachmentService {

    private static final Logger logger = LoggerFactory
                                               .getLogger(AttachmentService.class);
    
    @Autowired
    private AttachmentLogic     attachmentLogic;

    /**
     * 文件列表
     * @param ownId
     * @return
     */
    @IdeaJdbcTx(readOnly = true)
    public List<UploadFileInfo> listFileInfo(String ownId,
                                              List<Integer> typeList) {

        return attachmentLogic.listFileInfo(ownId, typeList);
    }
    
    /**
     * 查找文件
     * @param id
     * @return
     */
    @IdeaJdbcTx(readOnly = true)
    public UploadFileInfo getFileInfo(String id, String corpCode) {

        return attachmentLogic.getFileInfo(id);
    }
    
    /**
     * 删除文件
     * @param id
     * @return
     */
    @IdeaJdbcTx
    public void deleteFile(String id) {
        
        attachmentLogic.deleteFile(id);
    }
    

    /**
     * 上传图片
     * @param is
     * @imgName 图片名称
     * @ownId  归属者Id
     * @type   文件类型(1 照片,2 档案,3 合同)
     * @return
     * @throws UnsupportedEncodingException 
     */
    @IdeaJdbcTx
    public UploadState uploadImg(InputStream is,
                                  String imgName,
                                  String ownId,
                                  Integer type,
                                  Long size)
            throws UnsupportedEncodingException {

        return attachmentLogic.uploadImg(is, imgName, ownId, type, size);
    }

    /**
     * 上传文件
     * @param is
     * @param fileName
     * @ownId  归属者Id
     * @type   文件类型(1 照片,2 档案,3 合同)
     * @size   文件大小
     * @return
     */
    @IdeaJdbcTx
    public UploadState uploadFile(InputStream is,
                                   String fileName,
                                   String ownId,
                                   Integer type,
                                   Long size) {

        return attachmentLogic.uploadFile(is, fileName, ownId, type, size);

    }

    /**
     * 在线预览文件
     */
    @IdeaJdbcTx
    public JSONObject previewFile(String fileId) {

        String userId = UserContext.getCurrentUserId();

        UploadFileInfo fileInfo = attachmentLogic.getFileInfoWithoutContent(fileId);

        if (fileInfo == null || StringUtils.isEmpty(userId) || !userId.equals(fileInfo.getOwnId())) {
            return null;
        }

        if (StringUtils.isEmpty(fileInfo.getPreviewId())) { // 判断是否已生成了预览文件
//            byte[] bytes = attachmentLogic.getFileContent(fileInfo);
//            fileInfo.setContent(bytes);
//
//            JSONObject result = attachmentLogic.convertToHtmlOrPdf(fileInfo);
//
//            if (result == null || result.isEmpty()) {
//                return null;
//            }
//
//            return result;
        } else {
            String previewId = fileInfo.getPreviewId();

            UploadFileInfo previewFile = attachmentLogic.getFileInfoWithoutContent(previewId);

            if (previewFile != null) {
                JSONObject result = new JSONObject();

                result.put("type", previewFile.getContentType());
                result.put("url", attachmentLogic.getFilePreviewUrl(previewFile.getId()));

                return result;
            }
        }

        return null;

    }
}
