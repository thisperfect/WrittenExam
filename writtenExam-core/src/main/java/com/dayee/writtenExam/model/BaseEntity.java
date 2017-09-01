/**
 * 
 */
package com.dayee.writtenExam.model;

import java.io.Serializable;

import com.ideamoment.ideadata.annotation.Id;


/**
 * @author yq.song
 *
 */
public class BaseEntity implements Serializable {
    
    /**主键ID**/
    @Id(dataItem="C_ID", generator="uuid")
    protected String id;
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
}
