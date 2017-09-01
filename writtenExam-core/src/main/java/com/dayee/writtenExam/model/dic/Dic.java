
package com.dayee.writtenExam.model.dic;

import java.util.List;

import com.dayee.writtenExam.model.HistoriableEntity;
import com.ideamoment.ideadata.annotation.DataItemType;
import com.ideamoment.ideadata.annotation.Property;
import com.ideamoment.ideadata.annotation.Ref;

/**
 * @author cooltain
 *         <P>
 *         注：此类的id为assigned save之前需要设置id的值
 */
@SuppressWarnings("serial")
public abstract class Dic extends HistoriableEntity {

//    @Id(dataItem = "C_ID", generator = "assigned")
//    protected String  id;

    /**
     * 名称
     */
    @Property(dataItem = "C_NAME", type = DataItemType.VARCHAR, length = 200)
    private String    name;

    /**
     * 上级标识
     */
    @Property(dataItem = "C_PARENT_ID", type = DataItemType.VARCHAR, length = 32)
    private String    parentId;

    /**
     * 
     */
    @Property(dataItem = "C_CODE", type = DataItemType.VARCHAR, length = 200)
    private String    code;

    /**
     * 用于归类每一类的数据字典
     */
    @Property(dataItem = "C_STANDARDCODE", type = DataItemType.VARCHAR, length = 50)
    private String    standardCode;

    /**
     * 全拼/简拼，主要用于搜索的时候
     */
    @Property(dataItem = "C_PINYIN", type = DataItemType.VARCHAR, length = 200)
    private String    pinyin;

    /**
     * 类型
     */
    @Property(dataItem = "C_TYPE", type = DataItemType.INT)
    private Integer   type;

    /**
     * 顺序
     */
    @Property(dataItem = "C_ORDER", type = DataItemType.INT)
    private Integer   order;

    /**
     * 状态
     */
    @Property(dataItem = "C_STATUS", type = DataItemType.INT)
    private Integer   status;

    /**
     * 备注
     */
    @Property(dataItem = "C_REMARK", type = DataItemType.VARCHAR, length = 200)
    private String    remark;

    @Ref
    private List<Dic> children;

    /**
     * 是否使用控制台数据 如果是1则使用企业数据并且创建新企业的时候数据拷贝到企业
     */
    @Property(dataItem = "C_USE_CONSOLE", type = DataItemType.INT)
    private Integer useCosnole;

    public Integer getUseCosnole() {

        return useCosnole;
    }

    public void setUseCosnole(Integer useCosnole) {

        this.useCosnole = useCosnole;
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {

        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    @Override
    public void setId(String id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getParentId() {

        return parentId;
    }

    public void setParentId(String parentId) {

        this.parentId = parentId;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getStandardCode() {

        return standardCode;
    }

    public void setStandardCode(String standardCode) {

        this.standardCode = standardCode;
    }

    public String getPinyin() {

        return pinyin;
    }

    public void setPinyin(String pinyin) {

        this.pinyin = pinyin;
    }

    public Integer getType() {

        return type;
    }

    public void setType(Integer type) {

        this.type = type;
    }

    public Integer getOrder() {

        return order;
    }

    public void setOrder(Integer order) {

        this.order = order;
    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {

        this.status = status;
    }

    public String getRemark() {

        return remark;
    }

    public void setRemark(String remark) {

        this.remark = remark;
    }

    public List<Dic> getChildren() {

        return children;
    }

    public void setChildren(List<Dic> children) {

        this.children = children;
    }

	@Override
	public String toString() {
		return "Dic [name=" + name + ", parentId=" + parentId + ", code="
				+ code + ", standardCode=" + standardCode + ", pinyin="
				+ pinyin + ", type=" + type + ", order=" + order + ", status="
				+ status + ", remark=" + remark + ", children=" + children
				+ "]";
	}
    
    
    
}
