
package com.dayee.writtenExam.model.config;

import java.sql.Blob;

import com.dayee.writtenExam.model.AbstractPic;
import com.ideamoment.ideadata.annotation.DataItemType;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;
import com.ideamoment.ideadata.annotation.Transient;

@Entity(dataSet = "t_corp_basic_info")
public class CorpLogo extends AbstractPic {

    /**
     * 
     */
    @Transient
    private static final long serialVersionUID = 1L;

    @Property(dataItem = "C_LOGO", type = DataItemType.BLOB)
    private Blob              picBlob;
}
