/**
 * 
 */
package com.dayee.writtenExam.framework.export.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;


/**
 * @author Chinakite
 *
 */
public interface HeaderRowReader {
    /**
     * 
     * 
     * @param cell
     * @return
     */
    public String readHeaderRow(HSSFCell cell);
}
