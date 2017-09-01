
package com.dayee.writtenExam.framework.mail;

import java.io.InputStream;
import java.sql.Blob;

import com.dayee.writtenExam.framework.exception.YuncaiBaseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

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
                throw new YuncaiBaseException(e.getMessage(), e);
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
                throw new YuncaiBaseException(e.getMessage(), e);
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
    }

}
