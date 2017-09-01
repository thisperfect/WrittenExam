/**
 * 
 */

package com.dayee.writtenExam.framework.export.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chinakite
 */
public class ExcelRow {

    private int             maxColumnNo = 0;

    private Integer         rowHeight;

    /**
     * 单元格列表
     */
    private List<ExcelCell> cells       = new ArrayList<ExcelCell>();

    /**
     * 是否表头
     */
    private boolean         header      = false;

    public List<ExcelCell> getCells() {

        return this.cells;
    }

    public ExcelCell getCell(int i) {

        if (cells == null || cells.size() == 0 || i >= cells.size()) {
            return null;
        } else {
            return cells.get(i);
        }
    }

    public int cellSize() {

        if (cells == null) {
            return 0;
        } else {
            return cells.size();
        }
    }

    public void addExcelCell(ExcelCell cell) {

        if (cell != null) {
            if (cell.getColumn() > maxColumnNo) {
                maxColumnNo = cell.getColumn();
            }
        }
        this.cells.add(cell);
    }

    public void addExcelCellAll(List<ExcelCell> cellList) {

        if (cellList != null) {
            for (ExcelCell cell : cellList) {
                addExcelCell(cell);
            }
        }
    }

    /**
     * @return the header
     */
    public boolean isHeader() {

        return header;
    }

    /**
     * @param header
     *            the header to set
     */
    public void setHeader(boolean header) {

        this.header = header;
    }

    public Integer getRowHeight() {

        return rowHeight;
    }

    public void setRowHeight(Integer rowHeight) {

        this.rowHeight = rowHeight;
    }
}
