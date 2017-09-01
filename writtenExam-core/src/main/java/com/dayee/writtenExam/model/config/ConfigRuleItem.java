
package com.dayee.writtenExam.model.config;

import com.dayee.writtenExam.model.CorpPartitionEntity;
import com.ideamoment.ideadata.annotation.DataItemType;
import com.ideamoment.ideadata.annotation.Entity;
import com.ideamoment.ideadata.annotation.Property;
import com.ideamoment.ideadata.annotation.Transient;

@Entity(dataSet = "T_CONFIG_RULE_ITEM")
public class ConfigRuleItem extends CorpPartitionEntity {

    @Transient
    private static final long serialVersionUID = -3484230241064199281L;

    @Property(dataItem = "C_KEY", type = DataItemType.VARCHAR, length = 50)
    private String            key;

    @Property(dataItem = "C_VALUE", type = DataItemType.VARCHAR, length = 200)
    private String            value;

    public String getKey() {

        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

}
