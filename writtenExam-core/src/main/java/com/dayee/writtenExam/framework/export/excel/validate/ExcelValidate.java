
package com.dayee.writtenExam.framework.export.excel.validate;

public class ExcelValidate {

    private int    row;      // 行

    private int    col;      // 列

    private int    type = 0; // 0waring, 1error

    private String desc = ""; // 描述

    private ExcelValidate(int row, int col, int type, String desc) {

        super();
        this.row = row;
        this.col = col;
        this.type = type;
        this.desc = desc;
    }

    public static ExcelValidate instanceWarning(int row, int col, String desc) {

        return new ExcelValidate(row, col, 0, desc);
    }

    public static ExcelValidate instanceError(int row, int col, String desc) {

        return new ExcelValidate(row, col, 1, desc);
    }

    public int getRow() {

        return row;
    }

    public void setRow(int row) {

        this.row = row;
    }

    public int getCol() {

        return col;
    }

    public void setCol(int col) {

        this.col = col;
    }

    public int getType() {

        return type;
    }

    public void setType(int type) {

        this.type = type;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {

        this.desc = desc;
    }

}