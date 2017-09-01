
package com.dayee.writtenExam.framework.util;

import java.io.*;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import com.dayee.writtenExam.framework.constant.Constants;
import com.dayee.writtenExam.framework.constant.MimeTypeConstants;
import com.dayee.writtenExam.framework.exception.YuncaiBaseException;
import com.dayee.writtenExam.framework.usercontext.UserContext;
import com.dayee.writtenExam.model.FileInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadFilesUtils {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory
                                               .getLogger(DownloadFilesUtils.class);
    
    public static final String Content_Disposition = "Content-Disposition";

    public static final String ATTACHMENT_FILENAME = "attachment;filename=";

    public static final String INLINE_FILENAME = "inline;filename=";

    public static final String DEFAUL_FILE_NAME    = "download";

    public static void downloadFile(String file, String fileName) {

        try {

            if (!StringUtils.isEmpty(file)) {
                HttpServletResponse response = UserContext.getCurrentContext()
                        .getResponse();
                response.setContentType(MimeTypeConstants.MIMETYPE_STREAM);

                fileName = URLEncoder.encode(fileName,
                                             Constants.DEFAULT_ENCODING);
                response.addHeader(Content_Disposition,
                                   ATTACHMENT_FILENAME + fileName);

                OutputStream out = response.getOutputStream();
                IOUtils.write(file, out, Constants.DEFAULT_ENCODING);
                out.flush();
                out.close();
                out = null;
                response.flushBuffer();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new YuncaiBaseException(e.getMessage(), e);
        }
    }

    public static void downloadFile(File file) {

        InputStream is = null;
        OutputStream out = null;
        try {

            if (file != null && file.exists()) {
                HttpServletResponse response = UserContext.getCurrentContext()
                        .getResponse();
                response.setContentType(MimeTypeConstants.MIMETYPE_STREAM);
                String fileName = file.getName();
                fileName = URLEncoder.encode(fileName,
                                             Constants.DEFAULT_ENCODING);
                response.addHeader(Content_Disposition,
                                   ATTACHMENT_FILENAME + fileName);

                out = response.getOutputStream();
                is = new FileInputStream(file);
                IOUtils.copy(is, out);

                // out.flush();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new YuncaiBaseException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(is);

        }
    }

    public static void downloadFile(File file, String contentType) {

        InputStream is = null;
        OutputStream out = null;
        try {

            if (file != null && file.exists()) {
                HttpServletResponse response = UserContext.getCurrentContext()
                        .getResponse();
                response.setContentType(contentType);
                String fileName = file.getName();
                fileName = URLEncoder
                        .encode(fileName,
                                com.dayee.writtenExam.framework.constant.Constants.DEFAULT_ENCODING);
                response.addHeader(Content_Disposition,
                                   ATTACHMENT_FILENAME + fileName);

                out = response.getOutputStream();
                is = new FileInputStream(file);
                IOUtils.copy(is, out);

                // out.flush();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new YuncaiBaseException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(is);

        }
    }

    public static final String CACHE_CONTROL = "Cache-Control";

    public static final String NO_CACHE      = "no-cache";

    public static final String EXPIRES       = "Expires";

    public static void downloadImg(byte[] img, HttpServletResponse response) {

        OutputStream out = null;
        try {
            if (img != null && img.length > 0) {
                response.setContentType(MimeTypeConstants.MIMETYPE_JPEG);
                response.setHeader(CACHE_CONTROL, NO_CACHE);
                response.setDateHeader(EXPIRES, 0);
                out = response.getOutputStream();
                out.write(img);
                out.flush();
                out.close();
                out = null;
                response.flushBuffer();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new YuncaiBaseException(e.getMessage(), e);
        }
    }

    public static void downloadOrPreviewAttachment(FileInfoVO attachment, boolean isDownload) {

        InputStream is = null;
        OutputStream out = null;
        try {
            HttpServletResponse response = UserContext.getCurrentContext()
                    .getResponse();
            String fileType = attachment.getContentType();
            if (StringUtils.hasLength(fileType)) {
                response.setContentType(fileType);
            } else {
                response.setContentType(MimeTypeConstants.MIMETYPE_STREAM);
            }
            String fileName = attachment.getOriName();
            fileName = URLEncoder.encode(fileName, Constants.DEFAULT_ENCODING);
            response.addHeader(Content_Disposition,
                               (isDownload ? ATTACHMENT_FILENAME : INLINE_FILENAME) + fileName);

            out = response.getOutputStream();
            is = new ByteArrayInputStream(attachment.getContent());

            if (!isDownload && fileType.equals(MimeTypeConstants.MIMETYPE_HTML)) {

                response.setCharacterEncoding(CharsetEncodingUtils.getEncoding(is, attachment.getContent().length));
            }
            IOUtils.copy(is, out);
            out.close();

            // out.flush();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new YuncaiBaseException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static void downloadAttachment(FileInfoVO attachment) {

        downloadOrPreviewAttachment(attachment, true);
    }

    public static void previewAttachment(FileInfoVO attachment) {

        downloadOrPreviewAttachment(attachment, false);
    }
}
