/**
 * 
 */
package com.dayee.writtenExam.model;

import com.ideamoment.ideadata.annotation.DataItemType;
import com.ideamoment.ideadata.annotation.Property;


/**
 * @author Chinakite
 *
 */
public class CorpPartitionEntity extends HistoriableEntity implements ICorpable {
    /**
     * 企业代码
     */
    @Property(dataItem="C_CORPCODE", type=DataItemType.VARCHAR, length=32)
    protected String corpCode;

    
    /**
     * @return the corpCode
     */
    public String getCorpCode() {
        return corpCode;
    }

    /**
     * @param corpCode the corpCode to set
     */
    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }
    
}
