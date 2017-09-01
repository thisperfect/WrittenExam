
package com.dayee.writtenExam.framework.attachment;

import java.text.DecimalFormat;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.dayee.writtenExam.framework.util.PropertiesEnum;
import com.ideamoment.ideadata.annotation.Transient;

/**
 * 文件信息
 */
public class FileInfo {

    private String fileId;

    private String fileName;

    private String fileExt;

    @JSONField(format = "yyyy-MM-dd")
    private Date   addDate;

    private Long   fileSize; // 文件大小

    @Transient
    private String niceSize;  // 文件大小友好显示

    @Transient
    private String remoteUrl; // 链接地址

    public FileInfo() {

    }

    public FileInfo(String fileId,
                    String fileName,
                    String fileExt,
                    Long fileSize,
                    Date addDate) {

        this.fileId = fileId;
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.addDate = addDate;
        this.fileSize = fileSize;
    }

    public String getFileId() {

        return fileId;
    }

    public void setFileId(String fileId) {

        this.fileId = fileId;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    public String getFileExt() {

        return fileExt;
    }

    public void setFileExt(String fileExt) {

        this.fileExt = fileExt;
    }

    public Date getAddDate() {

        return addDate;
    }

    public void setAddDate(Date addDate) {

        this.addDate = addDate;
    }

    public String getRemoteUrl() {

        if (remoteUrl == null) {
            this.remoteUrl = PropertiesEnum.RESUME_CONFIG.getProperty("local.host") + "/attach/downloadFile?fileId="
                             + fileId
                             ;
        }

        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {

        this.remoteUrl = remoteUrl;
    }

    public Long getFileSize() {

        return fileSize;
    }

    public void setFileSize(Long fileSize) {

        this.fileSize = fileSize;
    }

    public String getNiceSize() {

        if (this.fileSize == null) {
            niceSize = "";
        } else if (this.fileSize < 1024L) {
            niceSize = this.fileSize + "字节";
        } else if (this.fileSize < 1048576) {
            niceSize = this.fileSize / 1024L + "KB";
        } else {
            double a = this.fileSize / 1048576;
            DecimalFormat df = new DecimalFormat("0.00");
            niceSize = df.format(a) + "M";
        }
        return niceSize;
    }

    public void setNiceSize(String niceSize) {

        this.niceSize = niceSize;
    }
}
