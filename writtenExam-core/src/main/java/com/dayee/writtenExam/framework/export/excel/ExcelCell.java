/**
 * 
 */

package com.dayee.writtenExam.framework.export.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayee.writtenExam.model.dic.Dic;

/**
 * @author Chinakite
 */
public class ExcelCell {

    /**
     * 列数（第*列）
     */
    private Integer            column             = null;

    private Object             value              = null;

    private Integer            dataType           = ExcelCellDataType.STRING;

    /**
     * 提示
     */
    private String             prompt             = null;

    private String[]           combo              = null;

    private List<Dic>          doubleSelectList   = null;

    private Map<String, Short> regionMap          = null;

    // 下拉行数
    private Integer            selectSameSheetNumber;

    private String             cellFormulaName;

    /**
     * 列宽
     */
    private Short              colWidth;

    /**
     * 字体颜色
     */
    private String             fontColor;

    /**
     * 字体大写
     */
    private Short              fontSize;

    /**
     * 字体
     */
    private String             fontName;

    /**
     * 加粗
     */
    private Boolean            isBold             = false;

    private Boolean            isBorder           = false;

    /**
     * 背景颜色
     */
    private Boolean            isBgColor          = false;

    private Boolean            isWrap             = false;

    /**
     * 对齐方式
     */
    private String             align              = null;

    /**
     * 跨列合并
     */
    private Boolean            acrossColumnsMerge = false;

    public ExcelCell() {

    }

    public ExcelCell(String fontColor,
                     Short fontSize,
                     String fontName,
                     Boolean isBold,
                     Boolean isBorder,
                     Boolean isBgColor,
                     Boolean isWrap,
                     String align) {

        this.fontColor = fontColor;
        this.fontSize = fontSize;
        this.fontName = fontName;
        this.isBold = isBold;
        this.isBorder = isBorder;
        this.isBgColor = isBgColor;
        this.isWrap = isWrap;
        this.align = align;
    }

    public String getCellFormulaName() {

        return cellFormulaName;
    }

    public void setCellFormulaName(String cellFormulaName) {

        this.cellFormulaName = cellFormulaName;
    }

    public List<Dic> getDoubleSelectList() {

        return doubleSelectList;
    }

    public void setDoubleSelectList(List<Dic> doubleSelectList) {

        this.doubleSelectList = doubleSelectList;
    }

    /**
     * @return the column
     */
    public Integer getColumn() {

        return column;
    }

    /**
     * @param column
     *            the column to set
     */
    public void setColumn(Integer column) {

        this.column = column;
    }

    /**
     * @return the value
     */
    public Object getValue() {

        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(Object value) {

        this.value = value;
    }

    /**
     * @return the prompt
     */
    public String getPrompt() {

        return prompt;
    }

    /**
     * @param prompt
     *            the prompt to set
     */
    public void setPrompt(String prompt) {

        this.prompt = prompt;
    }

    /**
     * @return the combo
     */
    public String[] getCombo() {

        return combo;
    }

    /**
     * @param combo
     *            the combo to set
     */
    public void setCombo(String[] combo) {

        this.combo = combo;
    }

    public Integer getDataType() {

        return dataType;
    }

    public void setDataType(Integer dataType) {

        this.dataType = dataType;
    }

    public Short getColWidth() {

        return colWidth;
    }

    public void setColWidth(Short colWidth) {

        this.colWidth = colWidth;
    }

    public String getFontColor() {

        return fontColor;
    }

    public void setFontColor(String fontColor) {

        this.fontColor = fontColor;
    }

    public Short getFontSize() {

        return fontSize;
    }

    public void setFontSize(Short fontSize) {

        this.fontSize = fontSize;
    }

    public String getFontName() {

        return fontName;
    }

    public void setFontName(String fontName) {

        this.fontName = fontName;
    }

    public Boolean getIsBold() {

        return isBold;
    }

    public void setIsBold(Boolean isBold) {

        this.isBold = isBold;
    }

    public Boolean getIsBorder() {

        return isBorder;
    }

    public void setIsBorder(Boolean isBorder) {

        this.isBorder = isBorder;
    }

    public Boolean getIsBgColor() {

        return isBgColor;
    }

    public void setIsBgColor(Boolean isBgColor) {

        this.isBgColor = isBgColor;
    }

    public Boolean getIsWrap() {

        return isWrap;
    }

    public void setIsWrap(Boolean isWrap) {

        this.isWrap = isWrap;
    }

    public String getAlign() {

        return align;
    }

    public void setAlign(String align) {

        this.align = align;
    }

    public Map<String, Short> getRegionMap() {

        return regionMap;
    }

    public void setRegionMap(Map<String, Short> regionMap) {

        this.regionMap = regionMap;
    }

    public void setRegionMap(Short startCol, Short endCol) {

        if (null == regionMap) {
            regionMap = new HashMap<String, Short>();
        }
        regionMap.put("startCol", startCol);
        regionMap.put("endCol", endCol);
    }

    /**
     * @return the acrossColumnsMerge
     */
    public Boolean getAcrossColumnsMerge() {

        return acrossColumnsMerge;
    }

    /**
     * @param acrossColumnsMerge
     *            the acrossColumnsMerge to set
     */
    public void setAcrossColumnsMerge(Boolean acrossColumnsMerge) {

        this.acrossColumnsMerge = acrossColumnsMerge;
    }

    public Integer getSelectSameSheetNumber() {

        return selectSameSheetNumber;
    }

    public void setSelectSameSheetNumber(Integer selectSameSheetNumber) {

        this.selectSameSheetNumber = selectSameSheetNumber;
    }

}
