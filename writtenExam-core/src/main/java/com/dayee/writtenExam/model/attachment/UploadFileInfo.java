
package com.dayee.writtenExam.model.attachment;

import com.dayee.writtenExam.model.CorpPartitionEntity;
import com.dayee.writtenExam.model.HistoriableEntity;
import com.ideamoment.ideadata.annotation.DataItemType;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;
import com.ideamoment.ideadata.annotation.Transient;

/**
 * 上传文件
 * 
 * @author william
 */
@Entity(dataSet = "T_UPLOAD_FILE_INFO")
public class UploadFileInfo extends HistoriableEntity {

    @Transient
    private static final long serialVersionUID   = 1L;

    @Property(dataItem = "C_FILE_PATH", type = DataItemType.VARCHAR, length = 500)
    private String            filePath;               // 文件路径

    @Property(dataItem = "C_ORI_NAME", type = DataItemType.VARCHAR, length = 500)
    private String            oriName;                // 原始文件名

    @Property(dataItem = "C_CONTENT_TYPE", type = DataItemType.VARCHAR, length = 100)
    private String            contentType;            // 文件类型

    @Property(dataItem = "C_OWN_ID", type = DataItemType.VARCHAR, length = 50)
    private String            ownId;                  // 文件归属者

    @Property(dataItem = "C_TYPE", type = DataItemType.INT)
    private Integer           type;                   // 文件类型

    @Property(dataItem = "C_SIZE", type = DataItemType.VARCHAR, length = 100)
    private String            size;                   // 文件类型

    @Property(dataItem = "C_PREVIEW_ID", type = DataItemType.VARCHAR, length = 50)
    private String            previewId;

    @Transient
    private byte[]            content;

    public byte[] getContent() {

        return content;
    }

    public void setContent(byte[] content) {

        this.content = content;
    }

    public String getOwnId() {

        return ownId;
    }

    public void setOwnId(String ownId) {

        this.ownId = ownId;
    }

    public String getFilePath() {

        return filePath;
    }

    public void setFilePath(String filePath) {

        this.filePath = filePath;
    }

    public String getOriName() {

        return oriName;
    }

    public void setOriName(String oriName) {

        this.oriName = oriName;
    }

    public String getContentType() {

        return contentType;
    }

    public void setContentType(String contentType) {

        this.contentType = contentType;
    }

    public Integer getType() {

        return type;
    }

    public void setType(Integer type) {

        this.type = type;
    }

    public String getSize() {

        return size;
    }

    public void setSize(String size) {

        this.size = size;
    }

    public String getPreviewId() {

        return previewId;
    }

    public void setPreviewId(String previewId) {

        this.previewId = previewId;
    }
}
