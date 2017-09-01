/**
 * 
 */

package com.dayee.writtenExam.framework.export.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dayee.writtenExam.framework.exception.YuncaiSystemException;
import com.dayee.writtenExam.framework.exception.YuncaiSystemExceptionCode;
import com.dayee.writtenExam.framework.util.CollectionUtils;
import com.dayee.writtenExam.framework.util.DateUtils;
import com.dayee.writtenExam.model.dic.Dic;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chinakite
 */
@SuppressWarnings("deprecation")
public class HSSFExcelUtils {

    private static final Logger logger = LoggerFactory
                                               .getLogger(HSSFExcelUtils.class);

    /**
     * 读取Excel中的一个sheet页
     * 
     * @param sheetName
     * @param readRow
     * @return
     */
    protected static List<ExcelRow> readTempExcelSheet(String sheetName,
                                                       HSSFWorkbook workbook,
                                                       Integer readRow) {

        HSSFSheet sheet = null;
        if (sheetName == null) {
            sheet = workbook.getSheetAt(0);
        } else if (!sheetName.trim().equals("")) {
            sheet = workbook.getSheet(sheetName);// 如果指定sheet名,则取指定sheet中的内容.
        }

        if (sheet == null) {
            sheet = workbook.getSheetAt(0); // 如果传入的sheet名不存在则默认指向第1个sheet.
        }
        int rows = sheet.getPhysicalNumberOfRows();

        List<ExcelRow> list = new ArrayList<ExcelRow>();

        if (rows > 0) {
            ExcelRow excelRow = new ExcelRow();
            HSSFRow row = sheet.getRow(0);
            HSSFCell cell = null;
            if (row != null) {
                cell = row.getCell(0);
            }
            ExcelCell excelCell = new ExcelCell();
            excelCell.setColumn(0);
            if (cell != null) {
                excelCell.setValue(getCellValue(cell));
            }
            excelRow.addExcelCell(excelCell);
            list.add(excelRow);
            List<ExcelRow> dataList = new ArrayList<ExcelRow>();
            if (rows >= 2) {
                int cellNum = sheet.getRow(1).getPhysicalNumberOfCells();
                dataList = readExcelSheet(sheet, 1, cellNum, readRow);
            }

            list.addAll(dataList);
        }

        return list;
    }

    /**
     * 读取Excel中的一个sheet页
     * 
     * @param sheetName
     * @param readRow
     * @return
     */
    protected static List<ExcelRow> readExcelSheet(String sheetName,
                                                   HSSFWorkbook workbook,
                                                   Integer readRow) {

        HSSFSheet sheet = null;
        if (sheetName == null) {
            sheet = workbook.getSheetAt(0);
        } else if (!sheetName.trim().equals("")) {
            sheet = workbook.getSheet(sheetName);// 如果指定sheet名,则取指定sheet中的内容.
        }

        if (sheet == null) {
            sheet = workbook.getSheetAt(0); // 如果传入的sheet名不存在则默认指向第1个sheet.
        }
        int rows = sheet.getPhysicalNumberOfRows();

        List<ExcelRow> list = new ArrayList<ExcelRow>();

        if (rows > 0) {
            int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();
            list = readExcelSheet(sheet, 0, cellNum, readRow);
        }
        return list;

    }

    /**
     * 读取Excel中的一个sheet页
     *
     * @param readRow
     * @return
     */
    protected static List<ExcelRow> readExcelSheet(HSSFSheet sheet,
                                                   Integer rowHeaderNum,
                                                   Integer cellNum,
                                                   Integer readRow) {

        List<ExcelRow> list = new ArrayList<ExcelRow>();

        if (null == sheet) {
            return list;
        }
        try {

            int rows = sheet.getPhysicalNumberOfRows();
            if (rows > 0) {// 有数据时才处理
                for (int i = rowHeaderNum; i < rows; i++) {
                    if (readRow != null && i >= readRow) {
                        break;
                    }
                    HSSFRow row = sheet.getRow(i);
                    ExcelRow excelRow = new ExcelRow();
                    if (rowHeaderNum == i) {
                        excelRow.setHeader(true);
                    }
                    for (int j = 0; j < cellNum; j++) {
                        HSSFCell cell = null;
                        if (row != null) {
                            cell = row.getCell(j);
                        }
                        ExcelCell excelCell = new ExcelCell();
                        excelCell.setColumn(j);
                        if (cell != null) {
                            excelCell.setValue(getCellValue(cell));
                        }
                        excelRow.addExcelCell(excelCell);

                    }

                    list.add(excelRow);
                }
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new YuncaiSystemException(YuncaiSystemExceptionCode.IO_ERROR,
                    e.getMessage());
        }
    }

    /**
     * 读取Excel中的一个sheet页
     * 
     * @param sheetName
     * @param input
     * @param readRow
     * @return
     */
    protected static List<ExcelRow> readExcelSheet(String sheetName,
                                                   InputStream input,
                                                   Integer readRow) {

        try {
            HSSFWorkbook workbook = new HSSFWorkbook(input);
            return readExcelSheet(sheetName, workbook, readRow);
        } catch (IOException e) {
            e.printStackTrace();
            throw new YuncaiSystemException(YuncaiSystemExceptionCode.IO_ERROR,
                    e.getMessage());
        }

    }

    /**
     * 获取 excel 单元格数据（各种类型转换为String）
     * 
     * @param cell
     * @return
     */
    private static String getCellValue(HSSFCell cell) {

        if (cell == null) {
            return null;
        }
        String ret = "";
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_BLANK:
            ret = "";
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            ret = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_ERROR:
            ret = null;
            break;
        case Cell.CELL_TYPE_FORMULA:
            // ret = "formula";
            try {
                ret = String.valueOf(cell.getNumericCellValue());
            } catch (IllegalStateException e) {
                ret = String.valueOf(cell.getRichStringCellValue());
            }
            break;
        case Cell.CELL_TYPE_NUMERIC:
            // Date date = HSSFDateUtil.getJavaDate( new Double(
            // cell.getNumericCellValue() ));
            if (DateUtil.isCellDateFormatted(cell)) {
                Date theDate = cell.getDateCellValue();
                ret = DateUtils.format(theDate);
            } else {
                ret = NumberToTextConverter.toText(cell.getNumericCellValue());
            }
            break;
        case Cell.CELL_TYPE_STRING:
            ret = cell.getRichStringCellValue().getString();
            break;
        default:
            ret = null;
        }
        return ret;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     */
    protected static boolean exportExcel(List<List<ExcelRow>> datas,
                                         String sheetNames[],
                                         OutputStream output) {

        if (datas.size() != sheetNames.length) {
            logger.error("Array length are not matched.");
            return false;
        }
        HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
        
        HSSFFont font = workbook.createFont();
        
        for (int i = 0; i < datas.size(); i++) {
            String sheetName = sheetNames[i];
            workbook.createSheet();// 产生工作表对象
            workbook.setSheetName(i, sheetName);
        }

        // 默认初始下拉关联行数
        int startDoubleNumber = 2;
        for (int i = 0; i < datas.size(); i++) {
            List<ExcelRow> excelSheet = datas.get(i);
            String sheetName = sheetNames[i];
            HSSFSheet sheet = workbook.getSheet(sheetName);
            // 设置列宽
            for (int j = 0; j < excelSheet.size(); j++) {
                HSSFRow row;
                HSSFCell cell;// 产生单元格
                /*
                 * HSSFCellStyle style = workbook.createCellStyle();
                 * style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
                 * style.
                 * setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
                 * HSSFCellStyle style1 = workbook.createCellStyle();
                 * style1.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
                 * style1
                 * .setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
                 * HSSFFont font = workbook.createFont();
                 * font.setColor(IndexedColors.RED.getIndex());
                 */
                // style.setFont(font);
                /*
                 * HSSFFont font1 = workbook.createFont();
                 * font1.setColor(IndexedColors.BLACK.getIndex());
                 */

                row = sheet.createRow(j);// 产生一行
                ExcelRow excelRow = excelSheet.get(j);
                if (null != excelRow.getRowHeight()) {
                    row.setHeightInPoints(excelRow.getRowHeight()); // 设置当前行的高度
                } else {
                    if (excelRow.isHeader()) {
                        row.setHeightInPoints(30);
                        // startDoubleNumber += j;
                        startDoubleNumber = j + 2;
                    }
                }
               
                for (int k = 0; k < excelRow.cellSize(); k++) {
                    ExcelCell excelCell = excelRow.getCell(k);
                    if (excelCell == null) {
                        continue;
                    }
                    int col = excelCell.getColumn();// 获得列号
                    cell = row.createCell(col);// 创建列
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
                    // 修改导出字体 by yyliu
                    HSSFCellStyle tempStyle = resetHSSFCellStyle(workbook,
                                                                 null,
                                                                 excelCell);
                    cell.setCellStyle(tempStyle);

                    // 设置列宽
                    if (null != excelCell.getColWidth()) {
                        sheet.setColumnWidth(k, excelCell.getColWidth());
                    }
                    Object value = excelCell.getValue();
                    if (value == null) {
                        // do nothing.
                        continue;
                    } else if (value instanceof String) {
                        cell.setCellValue((String) value);// 写入列名
                    } else if (value instanceof Date) {
                        if (excelCell.getDataType()
                                .equals(ExcelCellDataType.DATETIME)) {
                            cell.setCellValue(DateUtils
                                    .formatYMDHMS((Date) value));

                        } else {
                            // 默认 yyyyMMdd
                            cell.setCellValue(DateUtils.format((Date) value));
                        }
                    } else {
                        cell.setCellValue(value.toString());// 写入列名
                    }

                    // 设置单元格校验名称
                    if (excelCell.getCellFormulaName() == null) {
                        String cellFormulaName = "hidepoiCol" + i + col + "E";
                        excelCell.setCellFormulaName(cellFormulaName);
                    }

                    // 如果设置了combo属性则本列只能选择不能输入
                    if (excelCell.getCombo() != null && excelCell.getCombo().length > 0) {
                        setHSSFValidation(workbook, sheet, excelCell, 1, 1000,
                                          col, col);// 这里默认设了2-101列只能选择不能输入.
                    } else if (CollectionUtils.notEmpty(excelCell
                            .getDoubleSelectList())) {
                        // 是否要合并单元格，目前 header不合并
                        if (!excelRow.isHeader()) {
                            if (excelCell.getAcrossColumnsMerge()) {
                                sheet.addMergedRegion(new CellRangeAddress(0,
                                        (short) col, 0, (short) (col + 1)));
                            }
                        } else {
                            // 合并下一列单元格
                            sheet.addMergedRegion(new CellRangeAddress(0, (short) col, 0,
                                                                       (short) (col + 1)));
                        }
                        // 添加隐藏数据
                        addDoubleSelectToExcelHidePage(workbook, excelCell);
                        // 添加数据关联
                        col++;
                        for (int a = startDoubleNumber; a < 1000; a++) {
                            DataValidation data_validation_list1 = getDataValidationByFormula(excelCell
                                                                                                      .getCellFormulaName(),
                                                                                              a,
                                                                                              col);
                            sheet.addValidationData(data_validation_list1);

                            // 城市选项添加验证数据
                            DataValidation data_validation_list2 = getDataValidationByFormula("INDIRECT($" + ExcelUtils.columnNumber[col - 1]
                                                                                                      + "$"
                                                                                                      + a
                                                                                                      + ")",
                                                                                              a,
                                                                                              col + 1);
                            sheet.addValidationData(data_validation_list2);
                        }
                    } else if (CollectionUtils.notEmpty(excelCell.getRegionMap())) {
                        // 合并下一列单元格
                        sheet.addMergedRegion(new CellRangeAddress(0, (short) col,
                                excelCell.getRegionMap().get("startCol"),
                                excelCell.getRegionMap().get("endCol")));

                    } else {
                        if (excelCell.getPrompt() != null && !excelCell
                                    .getPrompt().trim().equals("")) {
                            setHSSFPrompt(sheet, "", excelCell.getPrompt()
                                    .trim(), 1, 1000, col, col);// 这里默认设了2-101列提示.
                        }
                    }
                    /*
                     * if (excelCell.getPrompt() != null) { if
                     * (excelCell.getPrompt().indexOf("必填") >= 0) {
                     * cell.setCellStyle(style); } else {
                     * cell.setCellStyle(style1); } } else {
                     * cell.setCellStyle(style1); }
                     */
                }
            }
        }

        try {
            output.flush();
            workbook.write(output);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Output is closed ");
            return false;
        }
    }

    /**
     * 设置字体、边框
     * 
     * @param workbook
     * @param style
     * @param excelCell
     * @return
     * @author yyliu
     */
    public static HSSFCellStyle resetHSSFCellStyle(HSSFWorkbook workbook,
                                                   HSSFCellStyle style,
                                                   ExcelCell excelCell) {

        if (null == style) {
            style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
        }
        HSSFFont font = workbook.createFont();

        if ("red".equals(excelCell.getFontColor())) {
            font.setColor(IndexedColors.RED.getIndex());
        }
        if ("grey80".equals(excelCell.getFontColor())) {
            font.setColor(IndexedColors.GREY_80_PERCENT.getIndex());
        }

        if (null != excelCell.getFontSize()) {
            font.setFontHeightInPoints(excelCell.getFontSize()); // 字体高度
        }

        if (null != excelCell.getFontName()) {
            font.setFontName(excelCell.getFontName()); // 字体
        }

        if (null != excelCell.getIsBold() && excelCell.getIsBold()) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽
        }
        // 设置字体
        style.setFont(font);

        // 设置居中

        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        if ("center".equals(excelCell.getAlign())) {
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

        } else if ("right".equals(excelCell.getAlign())) {
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 居右
        } else {
            style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 默认居左
        }

        // 自动换行
        style.setWrapText(excelCell.getIsWrap());// 设置自动换行

        // 设置编框
        if (excelCell.getIsBorder()) {
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);

            // 设置编框颜色
            style.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
            style.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
            style.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
            style.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        }

        // 设置背景色
        if (excelCell.getIsBgColor()) {
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);// 设置背景色
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        }

        return style;
    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
     * 
     * @param col
     */
    private static int getExcelCol(String col) {

        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }

    /**
     * 设置单元格上提示
     * 
     * @param sheet
     *            要设置的sheet.
     * @param promptTitle
     *            标题
     * @param promptContent
     *            内容
     * @param firstRow
     *            开始行
     * @param endRow
     *            结束行
     * @param firstCol
     *            开始列
     * @param endCol
     *            结束列
     * @return 设置好的sheet.
     */
    private static HSSFSheet setHSSFPrompt(HSSFSheet sheet,
                                           String promptTitle,
                                           String promptContent,
                                           int firstRow,
                                           int endRow,
                                           int firstCol,
                                           int endCol) {

        // 构造constraint对象
        DVConstraint constraint = DVConstraint
                .createCustomFormulaConstraint("DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList CellRangeAddresss = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_view = new HSSFDataValidation(
                CellRangeAddresss, constraint);
        data_validation_view.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     * 
     * @param sheet
     *            要设置的sheet.
     * @param excelCell
     *            下拉框显示的内容
     * @param firstRow
     *            开始行
     * @param endRow
     *            结束行
     * @param firstCol
     *            开始列
     * @param endCol
     *            结束列
     * @return 设置好的sheet.
     */
    private static HSSFSheet setHSSFValidation(HSSFWorkbook workbook,
                                               HSSFSheet sheet,
                                               ExcelCell excelCell,
                                               int firstRow,
                                               int endRow,
                                               int firstCol,
                                               int endCol) {

        String[] textlist = excelCell.getCombo();
        // 加载下拉列表内容
        HSSFSheet hideInfoSheet = workbook.getSheet(EXCEL_HIDE_SHEET_NAME);// 隐藏一些信息
        if (hideInfoSheet == null) {
            hideInfoSheet = workbook.createSheet(EXCEL_HIDE_SHEET_NAME);
        }
        int currentRow = hideInfoSheet.getPhysicalNumberOfRows();

        // 第1行设置省份名称列表
        Row provinceNameRow = hideInfoSheet.createRow(currentRow);
        creatRow(provinceNameRow, textlist);

        String formulaString = excelCell.getCellFormulaName();
        // 名称管理
        creatExcelNameList(workbook, formulaString, currentRow + 1,
                           textlist.length, null, false);

        DVConstraint constraint = DVConstraint
                .createFormulaListConstraint(formulaString);

        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList CellRangeAddresss = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(
                CellRangeAddresss, constraint);

        // 设置输入信息提示信息
        data_validation_list.createPromptBox("", excelCell.getPrompt());

        sheet.addValidationData(data_validation_list);

        // 设置隐藏页标志
        workbook.setSheetHidden(workbook.getSheetIndex(EXCEL_HIDE_SHEET_NAME),
                                true);
        return sheet;
    }

    private static final String EXCEL_HIDE_SHEET_NAME = "poihide";

    private static void addDoubleSelectToExcelHidePage(HSSFWorkbook workbook,
                                                       ExcelCell excelCell) {

        HSSFSheet hideInfoSheet = workbook.getSheet(EXCEL_HIDE_SHEET_NAME);// 隐藏一些信息
        if (hideInfoSheet == null) {
            hideInfoSheet = workbook.createSheet(EXCEL_HIDE_SHEET_NAME);
        }

        List<Dic> dicList = excelCell.getDoubleSelectList();

        int currentRow = hideInfoSheet.getPhysicalNumberOfRows();

        // 第1行设置省份名称列表
        Row provinceNameRow = hideInfoSheet.createRow(currentRow);
        creatRow(provinceNameRow, dicList);

        // 名称管理
        creatExcelNameList(workbook, excelCell.getCellFormulaName(),
                           currentRow + 1, dicList.size(),
                           excelCell.getSelectSameSheetNumber(), false);

        // 以下行设置城市名称列表
        for (Dic dic : dicList) {
            currentRow++;
            Row cityNameRow = hideInfoSheet.createRow(currentRow);
            creatRow(cityNameRow, dic.getChildren());
            // 设置子集下拉
            if (CollectionUtils.notEmpty(dic.getChildren())) {
                creatExcelNameList(workbook, dic.getName(), currentRow + 1, dic
                                           .getChildren().size(),
                                           excelCell.getSelectSameSheetNumber(), true);
            }
        }

        // 设置隐藏页标志
        workbook.setSheetHidden(workbook.getSheetIndex(EXCEL_HIDE_SHEET_NAME),
                                true);

    }

    private static void creatRow(Row currentRow, List<Dic> dicList) {

        if (dicList != null && dicList.size() > 0) {
            int i = 0;
            for (Dic dic : dicList) {
                Cell userNameLableCell = currentRow.createCell(i++);
                userNameLableCell.setCellValue(dic.getName());
            }
        }
    }

    private static void creatRow(Row currentRow, String[] textlist) {

        if (textlist != null && textlist.length > 0) {
            int i = 0;
            for (String text : textlist) {
                Cell userNameLableCell = currentRow.createCell(i++);
                userNameLableCell.setCellValue(text);
            }
        }
    }

    private static void creatExcelNameList(Workbook workbook,
                                           String nameCode,
                                           int order,
                                           int size,
                                           Integer sheetNumber,
                                           boolean cascadeFlag) {

        Name name;
        name = workbook.createName();
        if (null != sheetNumber) {
            name.setSheetIndex(sheetNumber);
        }
        name.setNameName(nameCode);
        String formula = EXCEL_HIDE_SHEET_NAME + "!"
                         + creatExcelNameList(order, size, cascadeFlag);
        System.out.println(nameCode + " ==  " + formula);
        name.setRefersToFormula(formula);
    }

    private static String creatExcelNameList(int order,
                                             int size,
                                             boolean cascadeFlag) {

        char start = 'A';
        if (cascadeFlag) {
            if (size <= 25) {
                char end = (char) (start + size - 1);
                return "$" + start + "$" + order + ":$" + end + "$" + order;
            } else {
                char endPrefix = 'A';
                char endSuffix = 'A';
                if ((size - 25) / 26 == 0 || size == 51) {// 26-51之间，包括边界（仅两次字母表计算）
                    if ((size - 25) % 26 == 0) {// 边界值
                        endSuffix = (char) ('A' + 25);
                    } else {
                        endSuffix = (char) ('A' + (size - 25) % 26 - 1);
                    }
                } else {// 51以上
                    if ((size - 25) % 26 == 0) {
                        endSuffix = (char) ('A' + 25);
                        endPrefix = (char) (endPrefix + (size - 25) / 26 - 1);
                    } else {
                        endSuffix = (char) ('A' + (size - 25) % 26 - 1);
                        endPrefix = (char) (endPrefix + (size - 25) / 26);
                    }
                }
                return "$" + start
                       + "$"
                       + order
                       + ":$"
                       + endPrefix
                       + endSuffix
                       + "$"
                       + order;
            }
        } else {
            if (size <= 26) {
                char end = (char) (start + size - 1);
                return "$" + start + "$" + order + ":$" + end + "$" + order;
            } else {
                char endPrefix = 'A';
                char endSuffix = 'A';
                if (size % 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                    if (size > 52 && size / 26 > 0) {
                        endPrefix = (char) (endPrefix + size / 26 - 2);
                    }
                } else {
                    endSuffix = (char) ('A' + size % 26 - 1);
                    if (size > 52 && size / 26 > 0) {
                        endPrefix = (char) (endPrefix + size / 26 - 1);
                    }
                }
                return "$" + start
                       + "$"
                       + order
                       + ":$"
                       + endPrefix
                       + endSuffix
                       + "$"
                       + order;
            }
        }
    }

    private static DataValidation getDataValidationByFormula(String formulaString,
                                                             int naturalRowIndex,
                                                             int naturalColumnIndex) {

        System.out.println("formulaString  " + formulaString);
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint
                .createFormulaListConstraint(formulaString);
        // 设置数据有效性加载在哪个单元格上。
        // 四个参数分别是：起始行、终止行、起始列、终止列
        int firstRow = naturalRowIndex - 1;
        int lastRow = naturalRowIndex - 1;
        int firstCol = naturalColumnIndex - 1;
        int lastCol = naturalColumnIndex - 1;
        CellRangeAddressList CellRangeAddresss = new CellRangeAddressList(firstRow,
                lastRow, firstCol, lastCol);
        // 数据有效性对象
        DataValidation data_validation_list = new HSSFDataValidation(CellRangeAddresss,
                constraint);
        data_validation_list.setEmptyCellAllowed(false);
        data_validation_list.setSuppressDropDownArrow(false);
        data_validation_list.setShowErrorBox(true);
        // 设置输入信息提示信息
        data_validation_list.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
        // 设置输入错误提示信息
        data_validation_list
                .createErrorBox("选择错误提示", "你输入的值未在备选列表中，请下拉选择合适的值！");
        return data_validation_list;
    }

}
