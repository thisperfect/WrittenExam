
package com.dayee.writtenExam.framework.export.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayee.writtenExam.framework.export.excel.validate.ExcelValidate;

public class ImportPreviewExcel {

    // 列数最大的一个
    private int                 maxColumn   = 0;

    // (暂不考虑人员档案导入)
    // key: 列，value: 标题
    private Map<String, String> titleMap    = new HashMap<String, String>();

    // key: 列，value: 属性名
    private Map<String, String> propertyMap = new HashMap<String, String>();

    // 返回结果 （List类型建议放的是数组，excel原内容是什么，就返回什么）
    private List<?>             rowList;
    
    // 注：不要提供get方法（List类型是一个对象）
    private List<?>             entityList;

    // 警告信息
    private List<ExcelValidate> warrnings;

    // 错误信息
    private List<ExcelValidate> errors;

    public boolean hasWarrning() {

        if (warrnings != null && warrnings.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasError() {

        if (errors != null && errors.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public void addWarrning(ExcelValidate warrning) {

        if (warrning != null) {
            if (warrnings == null) {
                warrnings = new ArrayList<ExcelValidate>();
            }
            warrnings.add(warrning);
        }
    }
    
    public void addWarrning(int row, int col, String desc) {
        ExcelValidate error = ExcelValidate.instanceWarning(row, col, desc);
        if (warrnings == null) {
            warrnings = new ArrayList<ExcelValidate>();
        }
        warrnings.add(error);
    }

    public void addError(ExcelValidate error) {

        if (error != null) {
            if (errors == null) {
                errors = new ArrayList<ExcelValidate>();
            }
            errors.add(error);
        }
    }
    
    public void addError(int row, int col, String desc) {
        ExcelValidate error = ExcelValidate.instanceError(row, col, desc);
        if (errors == null) {
            errors = new ArrayList<ExcelValidate>();
        }
        errors.add(error);
    }

    public void addProperty(int column, String titleName, String propertyName) {

        titleMap.put(String.valueOf(column), titleName);
        propertyMap.put(String.valueOf(column), propertyName);
        if (column > maxColumn) {
            maxColumn = column;
        }
    }

    public int getMaxColumn() {

        return maxColumn;
    }

    public Map<String, String> getTitleMap() {

        return titleMap;
    }

    public Map<String, String> getPropertyMap() {

        return propertyMap;
    }

    public List getRowList() {

        return rowList;
    }

    public void setRowList(List rowList) {

        this.rowList = rowList;
    }

    public List<ExcelValidate> getWarrnings() {

        return warrnings;
    }

    public void setWarrnings(List<ExcelValidate> warrnings) {

        this.warrnings = warrnings;
    }

    public List<ExcelValidate> getErrors() {

        return errors;
    }

    public void setErrors(List<ExcelValidate> errors) {

        this.errors = errors;
    }
    
    public List<?> getEntityList() {
    
        return entityList;
    }

    
    public void setEntityList(List<?> entityList) {
    
        this.entityList = entityList;
    }

    public void convertExcelRowToArray(List<ExcelRow> excelRows) {

        List<Object[]> arrayList = new ArrayList<Object[]>();
        for (ExcelRow row : excelRows) {
            if (row.getCells() == null || row.getCells().size() == 0) {
                continue;
            }
            Object[] array = new Object[row.cellSize()];
            boolean hasValue = false;
            for (ExcelCell cell : row.getCells()) {
                if(!hasValue && cell.getValue()!=null){
                    hasValue = true;
                }
                array[cell.getColumn()] = cell.getValue();
            }
            if(hasValue){
                arrayList.add(array);
            }else{
                arrayList.add(null);
            }
        }
        
        // 从最后一条开始，删除空行纪录，直到出现任意一条就不再删除
        for(int i=arrayList.size()-1; i>=0; i--){
            Object array = arrayList.get(i);
            if(array==null){
                arrayList.remove(i);
            }else{
                break;
            }
        }
        rowList = arrayList;
    }
}
