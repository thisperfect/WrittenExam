package com.dayee.writtenExam.model.ideaJdbc;

import com.ideamoment.ideadata.annotation.Id;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/7.
 */
public class BaseEntity implements Serializable {

    @Id(dataItem="F_ID", generator="uuid")
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
