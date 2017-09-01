
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
@Entity(dataSet = "T_DIC_CONSOLE")
public class ConsoleDic extends Dic {

    @Transient
    private static final long serialVersionUID = -597085859268919486L;
    
    @Id(dataItem = "C_ID", generator = "assigned")
    protected String  id;
    
    /**
     * 是否使用控制台数据 如果是1则使用企业数据并且创建新企业的时候数据拷贝到企业
     */
    @Property(dataItem = "C_USE_CONSOLE", type = DataItemType.INT)
    private Integer           useCosnole;

    public Integer getUseCosnole() {

        return useCosnole;
    }

    public void setUseCosnole(Integer useCosnole) {

        this.useCosnole = useCosnole;
    }
    


}
