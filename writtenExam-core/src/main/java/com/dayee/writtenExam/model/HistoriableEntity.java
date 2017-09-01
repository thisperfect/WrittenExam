/**
 * 
 */
package com.dayee.writtenExam.model;

import java.util.Date;

import com.ideamoment.ideadata.annotation.Property;


/**
 * @author yq.song
 *
 */
public class HistoriableEntity extends BaseEntity {
    
    /**创建人**/
    @Property(dataItem="C_CREATOR")
    protected String creator;
    
    /**创建时间**/
    @Property(dataItem="C_CREATE_TIME")
    protected Date createTime;
    
    /**修改人**/
    @Property(dataItem="C_MODIFIER")
    protected String modifier;
    
    /**修改时间**/
    @Property(dataItem="C_MODIFY_TIME")
    protected Date modifyTime;

    
    /**
     * @return the creator
     */
    public String getCreator() {
        return creator;
    }

    
    /**
     * @param creator the creator to set
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    
    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    
    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    /**
     * @return the modifier
     */
    public String getModifier() {
        return modifier;
    }

    
    /**
     * @param modifier the modifier to set
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    
    /**
     * @return the modifyTime
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    
    /**
     * @param modifyTime the modifyTime to set
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    
}
