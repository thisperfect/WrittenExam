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
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chinakite
 */
@SuppressWarnings("deprecation")
public class XSSFExcelUtils {

    private static final Logger logger = LoggerFactory
                                               .getLogger(XSSFExcelUtils.class);


    /**
     * 读取Excel中的一个sheet页
     * @return
     */
    protected static List<ExcelRow> readExcelSheet(String sheetName,
                                                   XSSFWorkbook workbook,
                                                   Integer readRow) {

        List<ExcelRow> list = new ArrayList<ExcelRow>();

        try {
            XSSFSheet sheet = null;// workbook.getSheet(sheetName);
            if (sheetName == null) {
                sheet = workbook.getSheetAt(0);
            } else if (!sheetName.trim().equals("")) {
                sheet = workbook.getSheet(sheetName);// 如果指定sheet名,则取指定sheet中的内容.
            }

            if (sheet == null) {
                sheet = workbook.getSheetAt(0); // 如果传入的sheet名不存在则默认指向第1个sheet.
            }

            int rows = sheet.getPhysicalNumberOfRows();
            int cellNum = 0;
            if (rows > 0) {// 有数据时才处理
                for (int i = 0; i < rows; i++) {// 从第1行开始取数据,默认第一行是表头.
                    if (readRow != null && i >= readRow) {
                        break;
                    }
                    XSSFRow row = sheet.getRow(i);

                    ExcelRow excelRow = new ExcelRow();
                    if (i == 0) {
                        excelRow.setHeader(true);
                        cellNum = row.getPhysicalNumberOfCells();
                    }
                    for (int j = 0; j < cellNum; j++) {
                        XSSFCell cell = null;
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
            XSSFWorkbook workbook = new XSSFWorkbook(input);
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
    private static String getCellValue(XSSFCell cell) {

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
            ret = "formula";
            break;
        case Cell.CELL_TYPE_NUMERIC:
            // Date date = XSSFDateUtil.getJavaDate( new Double(
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
     */
    protected static boolean exportExcel(List<List<ExcelRow>> datas,
                                         String sheetNames[],
                                         OutputStream output) {

        if (datas.size() != sheetNames.length) {
            logger.error("Array length are not matched.");
            return false;
        }
        XSSFWorkbook workbook = new XSSFWorkbook();// 产生工作薄对象

        for (int i = 0; i < datas.size(); i++) {
            String sheetName = sheetNames[i];
            workbook.createSheet();// 产生工作表对象
            workbook.setSheetName(i, sheetName);
        }

        for (int i = 0; i < datas.size(); i++) {
            List<ExcelRow> excelSheet = datas.get(i);
            String sheetName = sheetNames[i];
            XSSFSheet sheet = workbook.getSheet(sheetName);
            for (int j = 0; j < excelSheet.size(); j++) {
                XSSFRow row;
                XSSFCell cell;// 产生单元格
                XSSFCellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
                style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
                row = sheet.createRow(j);// 产生一行

                ExcelRow excelRow = excelSheet.get(j);

                for (int k = 0; k < excelRow.cellSize(); k++) {
                    ExcelCell excelCell = excelRow.getCell(k);
                    if (excelCell == null) {
                        continue;
                    }
                    int col = excelCell.getColumn();// 获得列号
                    cell = row.createCell(col);// 创建列
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型

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
                        setXSSFValidation(workbook, sheet, excelCell, 1, 1000,
                                          col, col);// 这里默认设了2-101列只能选择不能输入.
                    } else if (CollectionUtils.notEmpty(excelCell
                            .getDoubleSelectList())) {
                        // 合并下一列单元格
                        sheet.addMergedRegion(new CellRangeAddress(0, col, 0,
                                col + 1));

                        // 添加隐藏数据
                        addDoubleSelectToExcelHidePage(workbook, excelCell);

                        // 添加数据关联
                        col++;
                        for (int a = 2; a < 1000; a++) {
                            DataValidation data_validation_list1 = getDataValidationByFormula(sheet,
                                                                                              excelCell
                                                                                                      .getCellFormulaName(),
                                                                                              a,
                                                                                              col);
                            sheet.addValidationData(data_validation_list1);

                            // 城市选项添加验证数据
                            DataValidation data_validation_list2 = getDataValidationByFormula(sheet,
                                                                                              "INDIRECT($" + ExcelUtils.columnNumber[col - 1]
                                                                                                      + "$"
                                                                                                      + a
                                                                                                      + ")",
                                                                                              a,
                                                                                              col + 1);
                            sheet.addValidationData(data_validation_list2);
                        }
                    } else {
                        if (excelCell.getPrompt() != null && !excelCell
                                    .getPrompt().trim().equals("")) {
                            setXSSFPrompt(sheet, "", excelCell.getPrompt()
                                    .trim(), 1, 1000, col, col);// 这里默认设了2-101列提示.
                        }
                    }

                    cell.setCellStyle(style);
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
    private static XSSFSheet setXSSFPrompt(XSSFSheet sheet,
                                           String promptTitle,
                                           String promptContent,
                                           int firstRow,
                                           int endRow,
                                           int firstCol,
                                           int endCol) {

        // 构造constraint对象
        XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(
                ValidationType.ANY, "DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidationHelper help = new XSSFDataValidationHelper(sheet);
        DataValidation validation = help.createValidation(constraint, regions);
        validation.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(validation);
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
    private static XSSFSheet setXSSFValidation(XSSFWorkbook workbook,
                                               XSSFSheet sheet,
                                               ExcelCell excelCell,
                                               int firstRow,
                                               int endRow,
                                               int firstCol,
                                               int endCol) {

        String[] textlist = excelCell.getCombo();
        // 加载下拉列表内容
        XSSFSheet hideInfoSheet = workbook.getSheet(EXCEL_HIDE_SHEET_NAME);// 隐藏一些信息
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
                           textlist.length, false);

        XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(
                ValidationType.ANY, formulaString);

        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidationHelper help = new XSSFDataValidationHelper(sheet);
        DataValidation validation = help.createValidation(constraint, regions);

        // 设置输入信息提示信息
        validation.createPromptBox("", excelCell.getPrompt());

        sheet.addValidationData(validation);

        // 设置隐藏页标志
        workbook.setSheetHidden(workbook.getSheetIndex(EXCEL_HIDE_SHEET_NAME),
                                true);
        return sheet;
    }

    private static final String EXCEL_HIDE_SHEET_NAME = "poihide";

    private static void addDoubleSelectToExcelHidePage(XSSFWorkbook workbook,
                                                       ExcelCell excelCell) {

        XSSFSheet hideInfoSheet = workbook.getSheet(EXCEL_HIDE_SHEET_NAME);// 隐藏一些信息
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
                           currentRow + 1, dicList.size(), false);

        // 以下行设置城市名称列表
        for (Dic dic : dicList) {
            currentRow++;
            Row cityNameRow = hideInfoSheet.createRow(currentRow);
            creatRow(cityNameRow, dic.getChildren());
            // 设置子集下拉
            if (CollectionUtils.notEmpty(dic.getChildren())) {
                creatExcelNameList(workbook, dic.getName(), currentRow + 1, dic
                        .getChildren().size(), true);
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
                                           boolean cascadeFlag) {

        Name name;
        name = workbook.createName();
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

    private static DataValidation getDataValidationByFormula(XSSFSheet sheet,
                                                             String formulaString,
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
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                lastRow, firstCol, lastCol);
        // 数据有效性对象
        // DataValidation data_validation_list = new XSSFDataValidation(regions,
        // constraint);

        DataValidationHelper help = new XSSFDataValidationHelper(sheet);
        DataValidation validation = help.createValidation(constraint, regions);

        validation.setEmptyCellAllowed(false);
        if (validation instanceof XSSFDataValidation) {
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
        } else {
            validation.setSuppressDropDownArrow(false);
        }
        // 设置输入信息提示信息
        validation.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
        // 设置输入错误提示信息
        validation.createErrorBox("选择错误提示", "你输入的值未在备选列表中，请下拉选择合适的值！");
        return validation;
    }

}
