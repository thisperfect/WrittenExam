
package com.dayee.writtenExam.model;

import java.io.InputStream;
import java.sql.Blob;

import com.ideamoment.ideadata.annotation.Transient;
import com.ideamoment.ideajdbc.util.LobUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractPic extends CorpPartitionEntity {

    /**
     * 
     */
    @Transient
    private static final long serialVersionUID = 1L;

    @Transient
    protected byte[]          pic;

    @Transient
    protected String          currentDb;

    // 具体entity配置具体字段名称
    // @Property(dataItem = "C_LOGO", type = DataItemType.BLOB)
    // private Blob picBlob;
    public boolean isExistPic() {

        return pic != null;
    }

    public Blob getPicBlob() {

        if (StringUtils.isEmpty(currentDb)) {
            throw new RuntimeException("设置Blob字段出现错误，需要db");
        }
        try {
            return pic == null ? null : LobUtil.createBlob(pic, currentDb);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void setPicBlob(Blob picBlob) {

        if (picBlob != null) {
            InputStream is = null;
            try {
                is = picBlob.getBinaryStream();
                pic = IOUtils.toByteArray(is);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
    }

    public String getCurrentDb() {

        return currentDb;
    }

    public void setCurrentDb(String currentDb) {

        this.currentDb = currentDb;
    }

    public byte[] getPic() {

        return pic;
    }

    public void setPic(byte[] pic) {

        this.pic = pic;
    }

}
