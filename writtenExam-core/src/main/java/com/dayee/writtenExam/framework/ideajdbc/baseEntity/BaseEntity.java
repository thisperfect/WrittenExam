/**
 * 
 */

package com.dayee.writtenExam.framework.ideajdbc.baseEntity;

import com.ideamoment.ideadata.annotation.Id;

import java.io.Serializable;

/**
 * @author yq.song
 */
public class BaseEntity implements Serializable {

    /** 主键ID **/
    @Id(dataItem = "f_id")
    protected Integer id;

    /**
     * @return the id
     */
    public Integer getId() {

        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {

        this.id = id;
    }

}
