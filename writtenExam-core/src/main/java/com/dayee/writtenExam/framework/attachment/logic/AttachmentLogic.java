
package com.dayee.writtenExam.framework.attachment.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.dayee.writtenExam.framework.attachment.UploadState;
import com.dayee.writtenExam.framework.attachment.utils.AttachmentUtils;
import com.dayee.writtenExam.framework.config.YuncaiConfig;
import com.dayee.writtenExam.framework.constant.Constants;
import com.dayee.writtenExam.framework.constant.MimeTypeConstants;
import com.dayee.writtenExam.framework.exception.YuncaiBaseException;
import com.dayee.writtenExam.framework.ideajdbc.WrittenJdbc;
import com.dayee.writtenExam.framework.usercontext.UserContext;
import com.dayee.writtenExam.framework.util.CollectionUtils;
import com.dayee.writtenExam.framework.util.EntityUtils;
import com.dayee.writtenExam.framework.util.PropertiesEnum;
import com.dayee.writtenExam.framework.util.RequestUtils;
import com.dayee.writtenExam.framework.util.StringUtils;
import com.dayee.writtenExam.model.attachment.UploadFileInfo;
import com.ideamoment.ideajdbc.action.Query;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 附件Service
 */
@SuppressWarnings("deprecation")
@Component
public class AttachmentLogic {

    private static final Logger logger = LoggerFactory
                                               .getLogger(AttachmentLogic.class);

    private static final Pattern IFRAME_BEGIN_PATTERN   = Pattern.compile("<iframe", Pattern.CASE_INSENSITIVE);

    private static final Pattern IFRAME_END_PATTERN     = Pattern.compile("</iframe>", Pattern.CASE_INSENSITIVE);

    private static final Pattern SCRIPT_BEGIN_PATTERN   = Pattern.compile("<script", Pattern.CASE_INSENSITIVE);

    private static final Pattern SCRIPT_END_PATTERN     = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);

    /**
     * 文件列表
     * 
     * @param ownId
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<UploadFileInfo> listFileInfo(String ownId,
                                             List<Integer> typeList) {

        if (!StringUtils.hasLength(ownId, true)) {
            return null;
        }
        String corpCode = UserContext.getCurrentCorpCode();
        String sql = "select * from t_upload_file_info where c_corpCode=:corpCode and c_own_id=:ownId ";
        if (CollectionUtils.notEmpty(typeList)) {
            sql += " and c_type in (:typeList)";
        }
        Query query = WrittenJdbc.query(sql).setParameter("corpCode", corpCode)
                .setParameter("ownId", ownId);
        if (CollectionUtils.notEmpty(typeList)) {
            query.setParameter("typeList", typeList);
        }

        return query.listTo(UploadFileInfo.class);
    }

    /**
     * 查找文件
     * 
     * @param id
     * @return
     */
    public UploadFileInfo getFileInfo(String id) {

        if (!StringUtils.hasLength(id, true)) {
            return null;
        }
        UploadFileInfo file = WrittenJdbc.find(UploadFileInfo.class, id);
        if (file != null) {
            String filePath = file.getFilePath();
            String fileUrl = YuncaiConfig.getFileServerUrl() + filePath;
            byte[] bytes = AttachmentUtils.getBytesFromUrl(fileUrl);
            file.setContent(bytes);
        }
        return file;
    }

    /**
     * 查找文件信息(不含原文信息)
     */
    public UploadFileInfo getFileInfoWithoutContent(String id) {
        if (!StringUtils.hasLength(id, true)) {
            return null;
        }
        return WrittenJdbc.find(UploadFileInfo.class, id);
    }

    /**
     * 获取原文信息
     */
    public byte[] getFileContent(UploadFileInfo file) {

        if (file != null) {
            String filePath = file.getFilePath();
            String fileUrl = YuncaiConfig.getFileServerUrl() + filePath;
            return AttachmentUtils.getBytesFromUrl(fileUrl);
        }

        return null;
    }

    /**
     * 删除文件
     * 
     * @param id
     * @return
     */
    public void deleteFile(String id) {

        if (!StringUtils.hasLength(id, true)) {
            return;
        }
        String corpCode = UserContext.getCurrentContext().getCorpCode();
        UploadFileInfo file = WrittenJdbc.find(UploadFileInfo.class, id,
                                              corpCode);
        if (file != null) {
            String filePath = file.getFilePath();
            String url = YuncaiConfig.getFileServerUrl() + "/delete";
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("filePath", filePath);
            try {
                String content = RequestUtils.sendSimplePostRequest(url,
                                                                    paramMap);
                UploadState upState = JSONObject.parseObject(content,
                                                             UploadState.class);
                // 删除成功
                if (upState != null && upState.isSuccess()) {
                    // 删除文件记录
                    WrittenJdbc.delete(UploadFileInfo.class, id, corpCode);
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
    }

    /**
     * 删除文件
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public void deleteFileByType(String ownId, Integer type) {

        if (!StringUtils.hasLength(ownId, true) || type == null) {
            return;
        }
        String corpCode = UserContext.getCurrentCorpCode();
        String sql = "select * from T_UPLOAD_FILE_INFO where  c_corpCode=:corpCode and  c_own_id=:ownId and c_type=:type  ";
        List<UploadFileInfo> fileList = WrittenJdbc.query(sql)
                .setParameter("corpCode", corpCode).setParameter(ownId, ownId)
                .setParameter(type, type).listTo(UploadFileInfo.class);
        if (CollectionUtils.notEmpty(fileList)) {
            for (UploadFileInfo file : fileList) {
                String filePath = file.getFilePath();
                String url = YuncaiConfig.getFileServerUrl() + "/delete";
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("filePath", filePath);
                try {
                    String content = RequestUtils
                            .sendSimplePostRequest(url, paramMap);
                    UploadState upState = JSONObject
                            .parseObject(content, UploadState.class);
                    // 删除成功
                    if (upState != null && upState.isSuccess()) {
                        // 删除文件记录
                        WrittenJdbc.delete(UploadFileInfo.class, file.getId(),
                                          corpCode);
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 上传图片
     * 
     * @param is
     * @imgName 图片名称
     * @ownId 归属者Id
     * @type 文件类型(1 照片,2 档案,3 合同)
     * @return
     * @throws UnsupportedEncodingException
     */
    public UploadState uploadImg(InputStream is,
                                 String imgName,
                                 String ownId,
                                 Integer type,
                                 Long size) throws UnsupportedEncodingException {

        MultipartEntity mpEntity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE, null,
                Charset.forName("UTF-8"));
        String corpCode = UserContext.getCurrentCorpCode();
        mpEntity.addPart("corpCode", new StringBody(corpCode));
        mpEntity.addPart("img", new InputStreamBody(is, imgName));

        logger.debug("ready to uploading file : " + imgName);

        return uploadFile(imgName, mpEntity, ownId, type, size);
    }

    /**
     * 上传文件
     * 
     * @param is
     * @param fileName
     * @ownId 归属者Id
     * @type 文件类型(1 照片,2 档案,3 合同,4 简历, 5 简历预览文件)
     * @size 文件大小
     * @return
     */
    public UploadState uploadFile(InputStream is,
                                  String fileName,
                                  String ownId,
                                  Integer type,
                                  Long size) {

        if (!MimeTypeConstants.FILE_SUFFIX_MIMETYPE.containsKey(AttachmentUtils
                .getExt(fileName).toLowerCase())) {
            return new UploadState(UploadState.ERROR, "不支持的格式");
        }

        MultipartEntity mpEntity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE, null,
                Charset.forName("UTF-8"));
        mpEntity.addPart("file", new InputStreamBody(is, fileName));

        return uploadFile(fileName, mpEntity, ownId, type, size);
    }

    @SuppressWarnings("resource")
    private UploadState uploadFile(String fileName,
                                   MultipartEntity mpEntity,
                                   String ownId,
                                   Integer type,
                                   Long size) {

        String name = AttachmentUtils.getExt(fileName).toLowerCase();
        String contentType = MimeTypeConstants.FILE_SUFFIX_MIMETYPE.get(name);
        if (!StringUtils.hasLength(contentType, true)) {
            throw new YuncaiBaseException("UPLOAD_FILE_TYPE_ERROR",
                    "不支持上传该文件类型");
        }

        HttpPost post = new HttpPost(YuncaiConfig.getFileServerUploadUrl());
        post.setEntity(mpEntity);

        HttpClient httpclient = new DefaultHttpClient();
        // 请求超时
        httpclient.getParams()
                .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                              30000 * 10);
        // 读取超时
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                                            30000 * 10);
        httpclient.getParams()
                .setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
                              Charset.forName("UTF-8"));
        HttpResponse httResponse = null;
        try {
            httResponse = httpclient.execute(post);
            HttpEntity entity = httResponse.getEntity();
            InputStream respIs = entity.getContent();

            logger.debug("back result : " + respIs.toString());

            String content = IOUtils.toString(respIs);

            logger.debug("back content : " + content);
            UploadState upState = JSONObject.parseObject(content,
                                                         UploadState.class);

            // 上传成功保存
            if (upState != null && upState.isSuccess()) {
                UploadFileInfo upFile = new UploadFileInfo();
                upFile.setContentType(contentType);
                upFile.setOriName(fileName);
                upFile.setFilePath(upState.getFileId());
                upFile.setOwnId(ownId);
                upFile.setType(type);
                upFile.setSize(AttachmentUtils.getFileSizeStr(size));
                EntityUtils.initCreateInfo(upFile, ownId);
                WrittenJdbc.save(upFile);

                String uploadId = upFile.getId();
                upState.setFileId(uploadId); // 更换id
            }

            return upState;
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return new UploadState(UploadState.ERROR, "上传失败");
    }

    /**
     * 上传照片
     * 
     * @param file
     * @ownId 归属者Id
     * @type 文件类型(1 照片,2 档案,3 合同)
     * @return
     * @throws FileNotFoundException
     */
    public UploadState uploadImg(File file,
                                 String ownId,
                                 Integer type,
                                 Long size) throws FileNotFoundException {

        InputStream is = new FileInputStream(file);

        MultipartEntity mpEntity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE, null,
                Charset.forName("UTF-8"));

        mpEntity.addPart("img", new InputStreamBody(is, file.getName()));

        return uploadFile(file.getName(), mpEntity, ownId, type, size);
    }

    
    /****
     * 获取文件路径
     * @param corpCode
     * @param fileId
     * @return
     */
    public String getFileUrl(String corpCode, String fileId){
    	UploadFileInfo fileInfo = getFileInfo(fileId);
    	if(fileInfo != null){
    		String fileUrl = YuncaiConfig.getFileServerUrl()+fileInfo.getFilePath();
    		return fileUrl;
    	}
    	return null;
    }

    /**
     * 拼接获取简历云文件url
     */
    public String getLocalFileUrl(String fileId) {

        if (StringUtils.isNotBlank(fileId)) {
            return new StringBuffer(PropertiesEnum.RESUME_CONFIG.getProperty(Constants.COMMON.LOCAL_HOST))
                    .append("/writtenExam/attach/file?fileId=")
                    .append(fileId)
                    .toString();
        }

        return null;
    }

    /**
     * 拼接获取简历云文件预览url
     */
    public String getFilePreviewUrl(String fileId) {

        if (StringUtils.isNotBlank(fileId)) {
            return new StringBuffer()
                    .append("/writtenExam/attach/file?isDownload=false&fileId=")
                    .append(fileId)
                    .toString();
        }

        return null;
    }

    /**
     * 拼接获取简历云图片url
     */
    public String getLocalPhotoUrl(String photoId) {

        if (StringUtils.isNotBlank(photoId)) {
            return new StringBuffer(PropertiesEnum.RESUME_CONFIG.getProperty(Constants.COMMON.LOCAL_HOST))
                    .append("/writtenExam/attach/img?fileId=")
                    .append(photoId)
                    .toString();
        }

        return null;
    }

    /**
     * 将文件转化为html或pdf格式
     */
    /*public JSONObject convertToHtmlOrPdf(UploadFileInfo fileInfo) {

        JSONObject result = new JSONObject();

        if ((MimeTypeConstants.MIMETYPE_HTML.equals(fileInfo.getContentType()) && FileUtils.isHtml(fileInfo.getContent()))
                || (MimeTypeConstants.MIMETYPE_PDF.equals(fileInfo.getContentType()) && FileUtils.isPdf(fileInfo.getContent()))) {
            result.put("type", fileInfo.getContentType());
            result.put("url",  getFilePreviewUrl(fileInfo.getId()));

            return result;
        }

        if (MimeTypeConstants.MIMETYPE_TEXT.equals(fileInfo.getType()) || (fileInfo.getOriName() != null && fileInfo.getOriName().trim().endsWith(".txt"))) {

            try {
                String htmlStr = FileConvertUtils.textToHtml(fileInfo.getContent());

                if (StringUtils.isNotBlank(htmlStr)) {
                    InputStream inputStream = IOUtils.toInputStream(htmlStr);

                    UploadState state = uploadFile(inputStream, fileInfo.getId() + MimeTypeConstants.HTML_FILE_SUFFIX, fileInfo.getOwnId(), Constants.FILE.TYPE_RESUME_PREVIEW, (long) inputStream.available());

                    if (state.isSuccess()) {
                        String previewId = state.getFileId();
                        fileInfo.setPreviewId(previewId);

                        try {
                            WrittenJdbc.update(fileInfo);
                        } catch (Exception e) {
                            logger.error("设置预览文件id失败！", e);
                        }

                        result.put("type", MimeTypeConstants.MIMETYPE_HTML);
                        result.put("url",  getFilePreviewUrl(previewId));

                        return result;
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        } else if (FileUtils.isMht(fileInfo.getContent())) {

            try {
                Map<String, com.dayee.writtenExam.model.File> map = FileConvertUtils
                        .mhtToHtml(fileInfo.getContent());

                Map<String, String> imgUrlMap = new HashMap<>();
                if (map.containsKey("keyHtml")) {

                    for (Map.Entry<String, com.dayee.writtenExam.model.File> entry : map.entrySet()) {

                        com.dayee.writtenExam.model.File img = entry.getValue();
                        byte[] bytes = img.getContent();
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

                        UploadState state = uploadFile(byteArrayInputStream, img.getFileName(), fileInfo.getOwnId(), Constants.FILE.TYPE_PHOTO, (long) byteArrayInputStream.available());
                        if (state.isSuccess()) {
                            imgUrlMap.put(entry.getKey(), getLocalPhotoUrl(state.getFileId()));
                        }
                    }

                    com.dayee.writtenExam.model.File html = map.get("keyHtml");

                    String htmlStr = new String(html.getContent(), StringUtils.isEmpty(html.getEncoding()) ? Constants.DEFAULT_ENCODING : html.getEncoding());

                    for (Map.Entry<String, String> entry : imgUrlMap.entrySet()) {

                        htmlStr = htmlStr.replace(FileUtils.CID_NAME + entry.getKey(), entry.getValue());
                    }
                    System.out.println(htmlStr);
                    InputStream inputStream = IOUtils.toInputStream(htmlStr, html.getEncoding());

                    UploadState state = uploadFile(inputStream, fileInfo.getId() + MimeTypeConstants.HTML_FILE_SUFFIX, fileInfo.getOwnId(), Constants.FILE.TYPE_RESUME_PREVIEW, (long) inputStream.available());

                    if (state.isSuccess()) {
                        String previewId = state.getFileId();
                        fileInfo.setPreviewId(previewId);

                        try {
                            WrittenJdbc.update(fileInfo);
                        } catch (Exception e) {
                            logger.error("设置预览文件id失败！", e);
                        }

                        result.put("type", MimeTypeConstants.MIMETYPE_HTML);
                        result.put("url", getFilePreviewUrl(previewId));

                        return result;
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        } else {
            File tempFile = null;
            try {
                tempFile = File.createTempFile(fileInfo.getId(), FileUtils.getFileNameSuffix(fileInfo.getOriName()));

                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                IOUtils.write(fileInfo.getContent(), fileOutputStream);
                fileOutputStream.flush();

                byte[] bytes = FileConvertUtils.word2PdfByLibreOffice(tempFile);

                System.out.println(IOUtils.toString(bytes));

                if (FileUtils.isPdf(bytes)) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                    UploadState state = uploadFile(byteArrayInputStream, fileInfo.getId() + MimeTypeConstants.PDF_FILE_SUFFIX, fileInfo.getOwnId(), Constants.FILE.TYPE_RESUME_PREVIEW, (long) byteArrayInputStream.available());

                    if (state.isSuccess()) {
                        String previewId = state.getFileId();
                        fileInfo.setPreviewId(previewId);

                        try {
                            WrittenJdbc.update(fileInfo);
                        } catch (Exception e) {
                            logger.error("设置预览文件id失败！", e);
                        }

                        result.put("type", MimeTypeConstants.MIMETYPE_PDF);
                        result.put("url",  getFilePreviewUrl(previewId));

                        return result;
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {

                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }

        return result;
    }*/

    private static String clearHtmlContent(String content) throws Exception {

        StringBuffer buffer = new StringBuffer(content);

        String beginTag = "<![if !vml]><img";
        String endTag = "<![endif]>";
        int deleteBeginTagLen = 12;
        int beingIndex = buffer.indexOf(beginTag);
        while (beingIndex > -1) {
            int endIndex = buffer.indexOf(endTag, beingIndex);
            if (endIndex > 0) {

                buffer.delete(beingIndex, beingIndex + deleteBeginTagLen);
                buffer.delete(endIndex - deleteBeginTagLen,
                              endIndex + endTag.length() - deleteBeginTagLen);

                beingIndex = buffer.indexOf(beginTag, beingIndex);
            } else {
                break;
            }
        }

        removeContent(buffer, "<?xml", ">", true, true);
        deleteIframeContent(buffer);
        deleteScriptContent(buffer);

        removeIfContent(buffer, "<!--[if", "<![endif]-->", true, true);
        removeIfContent(buffer, "<![if", "<![endif]>", true, true);

        String htmlContent = buffer.toString();

        return htmlContent;

    }

    private static void removeContent(StringBuffer buffer,
                                      String beginTag,
                                      String endTag,
                                      boolean removeBeginTag,
                                      boolean removeEndTag) {

        int beingIndex = buffer.indexOf(beginTag);
        while (beingIndex > -1) {

            int endIndex = buffer.indexOf(endTag, beingIndex);
            if (endIndex > 0) {
                if (!removeBeginTag) {
                    beingIndex = beingIndex + beginTag.length();
                }
                if (removeEndTag) {
                    endIndex = endIndex + endTag.length();
                }
                buffer.delete(beingIndex, endIndex);

                beingIndex = buffer.indexOf(beginTag, beingIndex);
            } else {
                break;
            }
        }
    }

    private static void removeIfContent(StringBuffer buffer,
                                        String beginTag,
                                        String endTag,
                                        boolean removeBeginTag,
                                        boolean removeEndTag) {

        int beingIndex = buffer.indexOf(beginTag);
        while (beingIndex > -1) {
            int rIndex = buffer.indexOf("]", beingIndex);
            if (rIndex > 0) {
                String tag = buffer.substring(beingIndex, rIndex);
                if (tag.contains(" IE ")) {
                    beingIndex = buffer.indexOf(beginTag, rIndex);
                    continue;
                }
            }
            int endIndex = buffer.indexOf(endTag, beingIndex);
            if (endIndex > 0) {
                if (!removeBeginTag) {
                    beingIndex = beingIndex + beginTag.length();
                }
                if (removeEndTag) {
                    endIndex = endIndex + endTag.length();
                }
                buffer.delete(beingIndex, endIndex);

                beingIndex = buffer.indexOf(beginTag, beingIndex);
            } else {
                break;
            }
        }
    }

    public static StringBuffer deleteIframeContent(StringBuffer buffer) {

        // StringBuffer buffer = new StringBuffer(str);

        Matcher bm = IFRAME_BEGIN_PATTERN.matcher(buffer);
        while (bm.find()) {

            Matcher em = IFRAME_END_PATTERN.matcher(buffer);

            if (em.find(bm.end())) {
                buffer.delete(bm.start(), em.end());
                bm = IFRAME_BEGIN_PATTERN.matcher(buffer);
            } else {
                break;
            }
        }

        return buffer;
    }

    public static StringBuffer deleteScriptContent(StringBuffer buffer) {

        // StringBuffer buffer = new StringBuffer(str);

        Matcher bm = SCRIPT_BEGIN_PATTERN.matcher(buffer);
        while (bm.find()) {

            Matcher em = SCRIPT_END_PATTERN.matcher(buffer);

            if (em.find(bm.end())) {
                buffer.delete(bm.start(), em.end());

                bm = SCRIPT_BEGIN_PATTERN.matcher(buffer);
            } else {
                break;
            }
        }

        return buffer;
    }
}
