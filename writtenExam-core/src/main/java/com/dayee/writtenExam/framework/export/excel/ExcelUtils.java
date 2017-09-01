/**
 * 
 */

package com.dayee.writtenExam.framework.export.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chinakite
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory
                                               .getLogger(ExcelUtils.class);

    protected static boolean isExcel2003(InputStream is) {

        try {
            return POIFSFileSystem.hasPOIFSHeader(is);
        } catch (Exception e) {
            return false;
        }
    }

    protected static boolean isExcel2007(InputStream is) {

        try {
            return POIXMLDocument.hasOOXMLHeader(is);
        } catch (Exception e) {
            return false;
        }
    }

    public static Workbook getWorkbook(InputStream input) {

        Workbook workbook = null;
        try {

            if (isExcel2003(input)) {
                workbook = new HSSFWorkbook(input);

            } else {
                workbook = new XSSFWorkbook(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 读取Excel中的一个sheet页
     * 
     * @param sheetName
     * @param workbook
     * @return
     */
    public static List<ExcelRow> readExcelSheet(String sheetName,
                                                Workbook workbook) {

        if (workbook instanceof HSSFWorkbook) {
            return HSSFExcelUtils.readExcelSheet(sheetName,
                                                 (HSSFWorkbook) workbook, null);
        } else {
            return XSSFExcelUtils.readExcelSheet(sheetName,
                                                 (XSSFWorkbook) workbook, null);
        }
    }
    
    /**
     * 读取Excel中的一个sheet页
     * 
     * @param sheetName
     * @param workbook
     * @return
     */
    public static List<ExcelRow> readTempExcelSheet(String sheetName,
                                                Workbook workbook) {

        if (workbook instanceof HSSFWorkbook) {
            return HSSFExcelUtils.readTempExcelSheet(sheetName,
                                                 (HSSFWorkbook) workbook, null);
        } else {
            return XSSFExcelUtils.readExcelSheet(sheetName,
                                                 (XSSFWorkbook) workbook, null);
        }
    }

    public static List<ExcelRow> readExcelSheet(String sheetName,
                                                Workbook workbook,
                                                Integer readRow) {

        if (workbook instanceof HSSFWorkbook) {
            return HSSFExcelUtils.readExcelSheet(sheetName,
                                                 (HSSFWorkbook) workbook,
                                                 readRow);

        } else {
            return XSSFExcelUtils.readExcelSheet(sheetName,
                                                 (XSSFWorkbook) workbook,
                                                 readRow);
        }
    }

    /**
     * 读取Excel中的一个sheet页
     * 
     * @param sheetName
     * @param input
     * @return
     */
    public static List<ExcelRow> readExcelSheet(String sheetName,
                                                InputStream input) {

        if (isExcel2003(input)) {
            return HSSFExcelUtils.readExcelSheet(sheetName, input, null);
        } else {
            return XSSFExcelUtils.readExcelSheet(sheetName, input, null);
        }
    }

    public static List<ExcelRow> readExcelSheet(String sheetName,
                                                InputStream input,
                                                Integer readRow) {

        if (isExcel2003(input)) {
            return HSSFExcelUtils.readExcelSheet(sheetName, input, readRow);
        } else {
            return XSSFExcelUtils.readExcelSheet(sheetName, input, readRow);
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param sheetName
     *            工作表的名称
     * @param sheetSize
     *            每个sheet中数据的行数,此数值必须小于65536
     * @param output
     *            java输出流
     */
    public static boolean exportExcel(List<List<ExcelRow>> datas,
                                      String sheetNames[],
                                      OutputStream output) {

        return HSSFExcelUtils.exportExcel(datas, sheetNames, output);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param sheetName
     *            工作表的名称
     * @param sheetSize
     *            每个sheet中数据的行数,此数值必须小于65536
     * @param output
     *            java输出流
     */
    public static boolean exportExcel2007(List<List<ExcelRow>> datas,
                                          String sheetNames[],
                                          OutputStream output) {

        return XSSFExcelUtils.exportExcel(datas, sheetNames, output);
    }

    protected static String[] columnNumber = { "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE",
            "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP",
            "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ", "BA",
            "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL",
            "BM", "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW",
            "BX", "BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH",
            "CI", "CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS",
            "CT", "CU", "CV", "CW", "CX", "CY", "CZ", "DA", "DB", "DC", "DD",
            "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO",
            "DP", "DQ", "DR", "DS", "DT", "DU", "DV", "DW", "DX", "DY", "DZ",
            "EA", "EB", "EC", "ED", "EE", "EF", "EG", "EH", "EI", "EJ", "EK",
            "EL", "EM", "EN", "EO", "EP", "EQ", "ER", "ES", "ET", "EU", "EV",
            "EW", "EX", "EY", "EZ", "FA", "FB", "FC", "FD", "FE", "FF", "FG",
            "FH", "FI", "FJ", "FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR",
            "FS", "FT", "FU", "FV", "FW", "FX", "FY", "FZ", "GA", "GB", "GC",
            "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN",
            "GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW", "GX", "GY",
            "GZ", "HA", "HB", "HC", "HD", "HE", "HF", "HG", "HH", "HI", "HJ",
            "HK", "HL", "HM", "HN", "HO", "HP", "HQ", "HR", "HS", "HT", "HU",
            "HV", "HW", "HX", "HY", "HZ", "IA", "IB", "IC", "ID", "IE", "IF",
            "IG", "IH", "II", "IJ", "IK", "IL", "IM", "IN", "IO", "IP", "IQ",
            "IR", "IS", "IT", "IU", "IV", "IW", "IX", "IY", "IZ", "JA", "JB",
            "JC", "JD", "JE", "JF", "JG", "JH", "JI", "JJ", "JK", "JL", "JM",
            "JN", "JO", "JP", "JQ", "JR", "JS", "JT", "JU", "JV", "JW", "JX",
            "JY", "JZ", "KA", "KB", "KC", "KD", "KE", "KF", "KG", "KH", "KI",
            "KJ", "KK", "KL", "KM", "KN", "KO", "KP", "KQ", "KR", "KS", "KT",
            "KU", "KV", "KW", "KX", "KY", "KZ", "LA", "LB", "LC", "LD", "LE",
            "LF", "LG", "LH", "LI", "LJ", "LK", "LL", "LM", "LN", "LO", "LP",
            "LQ", "LR", "LS", "LT", "LU", "LV", "LW", "LX", "LY", "LZ", "MA",
            "MB", "MC", "MD", "ME", "MF", "MG", "MH", "MI", "MJ", "MK", "ML",
            "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW",
            "MX", "MY", "MZ", "NA", "NB", "NC", "ND", "NE", "NF", "NG", "NH",
            "NI", "NJ", "NK", "NL", "NM", "NN", "NO", "NP", "NQ", "NR", "NS",
            "NT", "NU", "NV", "NW", "NX", "NY", "NZ", "OA", "OB", "OC", "OD",
            "OE", "OF", "OG", "OH", "OI", "OJ", "OK", "OL", "OM", "ON", "OO",
            "OP", "OQ", "OR", "OS", "OT", "OU", "OV", "OW", "OX", "OY", "OZ",
            "PA", "PB", "PC", "PD", "PE", "PF", "PG", "PH", "PI", "PJ", "PK",
            "PL", "PM", "PN", "PO", "PP", "PQ", "PR", "PS", "PT", "PU", "PV",
            "PW", "PX", "PY", "PZ", "QA", "QB", "QC", "QD", "QE", "QF", "QG",
            "QH", "QI", "QJ", "QK", "QL", "QM", "QN", "QO", "QP", "QQ", "QR",
            "QS", "QT", "QU", "QV", "QW", "QX", "QY", "QZ", "RA", "RB", "RC",
            "RD", "RE", "RF", "RG", "RH", "RI", "RJ", "RK", "RL", "RM", "RN",
            "RO", "RP", "RQ", "RR", "RS", "RT", "RU", "RV", "RW", "RX", "RY",
            "RZ", "SA", "SB", "SC", "SD", "SE", "SF", "SG", "SH", "SI", "SJ",
            "SK", "SL", "SM", "SN", "SO", "SP", "SQ", "SR", "SS", "ST", "SU",
            "SV", "SW", "SX", "SY", "SZ", "TA", "TB", "TC", "TD", "TE", "TF",
            "TG", "TH", "TI", "TJ", "TK", "TL", "TM", "TN", "TO", "TP", "TQ",
            "TR", "TS", "TT", "TU", "TV", "TW", "TX", "TY", "TZ", "UA", "UB",
            "UC", "UD", "UE", "UF", "UG", "UH", "UI", "UJ", "UK", "UL", "UM",
            "UN", "UO", "UP", "UQ", "UR", "US", "UT", "UU", "UV", "UW", "UX",
            "UY", "UZ", "VA", "VB", "VC", "VD", "VE", "VF", "VG", "VH", "VI",
            "VJ", "VK", "VL", "VM", "VN", "VO", "VP", "VQ", "VR", "VS", "VT",
            "VU", "VV", "VW", "VX", "VY", "VZ", "WA", "WB", "WC", "WD", "WE",
            "WF", "WG", "WH", "WI", "WJ", "WK", "WL", "WM", "WN", "WO", "WP",
            "WQ", "WR", "WS", "WT", "WU", "WV", "WW", "WX", "WY", "WZ", "XA",
            "XB", "XC", "XD", "XE", "XF", "XG", "XH", "XI", "XJ", "XK", "XL",
            "XM", "XN", "XO", "XP", "XQ", "XR", "XS", "XT", "XU", "XV", "XW",
            "XX", "XY", "XZ", "YA", "YB", "YC", "YD", "YE", "YF", "YG", "YH",
            "YI", "YJ", "YK", "YL", "YM", "YN", "YO", "YP", "YQ", "YR", "YS",
            "YT", "YU", "YV", "YW", "YX", "YY", "YZ", "ZA", "ZB", "ZC", "ZD",
            "ZE", "ZF", "ZG", "ZH", "ZI", "ZJ", "ZK", "ZL", "ZM", "ZN", "ZO",
            "ZP", "ZQ", "ZR", "ZS", "ZT", "ZU", "ZV", "ZW", "ZX", "ZY", "ZZ" };

}
