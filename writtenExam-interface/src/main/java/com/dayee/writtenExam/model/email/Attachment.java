package com.dayee.writtenExam.model.email;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.sql.Blob;

/**
 * Created by Administrator on 2017/8/10.
 */
public class Attachment {

    private byte[] content;

    private String name;

    private String type;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public boolean isHaveContent() {

        return content != null;
    }

    public byte[] getContent() {

        return content;
    }

    public void setContent(byte[] content) {

        this.content = content;
    }

    public void setContentSource(java.io.File contentSource) {

        if (contentSource != null) {
            try {

                content = FileUtils.readFileToByteArray(contentSource);

            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    public void setContentBlob(Blob contentBlob) {

        if (contentBlob != null) {
            InputStream is = null;
            try {

                is = contentBlob.getBinaryStream();
                content = IOUtils.toByteArray(is);

            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
    }
}
