
package com.dayee.writtenExam.model.dic;

import com.ideamoment.ideadata.annotation.DataItemType;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Id;
import com.ideamoment.ideadata.annotation.Property;
import com.ideamoment.ideadata.annotation.Transient;

/**
 * @author cooltain
 *         <P>
 *         注：此类的id为assigned save之前需要设置id的值
 */
@Entity(dataSet = "T_DIC")
public class CorpDic extends Dic {
	
	@Id(dataItem="C_ID", generator="assigned")
	protected String id;
	
	
    @Transient
    private static final long serialVersionUID = -2734598632785870160L;

    /**
     * 企业代码
     */
    @Property(dataItem = "C_CORPCODE", type = DataItemType.VARCHAR, length = 32)
    protected String          corpCode;

    /**
     * @return the corpCode
     */
    public String getCorpCode() {

        return corpCode;
    }

    /**
     * @param corpCode
     *            the corpCode to set
     */
    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

}
