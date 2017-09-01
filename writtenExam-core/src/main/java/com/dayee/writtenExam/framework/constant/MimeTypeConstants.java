
package com.dayee.writtenExam.framework.constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MimeTypeConstants {
 
    public static final String        MIMETYPE_TEXT         = "text/plain";

    public static final String        MIMETYPE_TEXT_UTF_8   = "text/plafin;charset=utf-8";

    public static final String        MIMETYPE_HTML         = "text/html";

    public static final String        MIMETYPE_HTML_UTF_8   = "text/html;charset=utf-8";

    public static final String        MIMETYPE_XML          = "text/xml";

    public static final String        MIMETYPE_JSON         = "text/javascript";

    public static final String        MIMETYPE_ZIP          = "application/zip";

    public static final String        MIMETYPE_PDF          = "application/pdf";

    public static final String        MIMETYPE_RAR          = "application/rar";

    public static final String        MIMETYPE_HELP         = "application/mshelp";

    public static final String        MIMETYPE_7Z           = "application/x-7z-compressed";

    public static final String        MIMETYPE_MULTIPART    = "multipart/*";

    public static final String        MIMETYPE_MESSAGE      = "message/rfc822";

    public static final String        MIMETYPE_WORD_95      = "application/msword95";

    public static final String        MIMETYPE_WORD_2003    = "application/msword";

    public static final String        MIMETYPE_WORD_2007    = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    public static final String        MIMETYPE_EXCEL_2003   = "application/vnd.ms-excel";

    public static final String        MIMETYPE_EXCEL_2003_2 = "application/msexcel";

    public static final String        MIMETYPE_EXCEL_2007   = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static final String        MIMETYPE_STREAM       = "application/octet-stream";

    public static final String        MIMETYPE_JPEG         = "image/jpeg";

    public static final String        MIMETYPE_JPG          = "image/jpg";

    public static final String        MIMETYPE_PJPEG        = "image/pjpeg";

    public static final String        MIMETYPE_GIF          = "image/gif";

    public static final String        MIMETYPE_BMP          = "image/bmp";

    public static final String        MIMETYPE_ICO          = "image/ico";

    public static final String        MIMETYPE_GIF_1        = "gif";

    public static final String        MIMETYPE_PNG          = "image/png";

    public static final String        MIMETYPE_MHT          = "application/mht";

    public static final String        MIMETYPE_RTF          = "appication/rtf";

    public static final String        MIMETYPE_MSG          = "appication/msg";

    public static final String        MIMETYPE_XSD          = "appication/xsd";

    public static final String        MIMETYPE_DLL          = "appication/dll";

    public static final String        TEXT_FILE_SUFFIX      = ".txt";

    public static final String        HTML_FILE_SUFFIX      = ".html";

    public static final String        HTM_FILE_SUFFIX       = ".htm";

    public static final String        XML_FILE_SUFFIX       = ".xml";

    public static final String        ZIP_FILE_SUFFIX       = ".zip";

    public static final String        SEVEN_ZIP_FILE_SUFFIX = ".7z";

    public static final String        PDF_FILE_SUFFIX       = ".pdf";

    public static final String        RAR_FILE_SUFFIX       = ".rar";

    public static final String        HELP_FILE_SUFFIX      = ".chm";

    public static final String        WORD2003_FILE_SUFFIX  = ".doc";

    public static final String        WORD2007_FILE_SUFFIX  = ".docx";

    public static final String        EXCEL2003_FILE_SUFFIX = ".xls";

    public static final String        EXCEL2007_FILE_SUFFIX = ".xlsx";

    public static final String        JPG_FILE_SUFFIX       = ".jpg";

    public static final String        JPEG_FILE_SUFFIX      = ".jpeg";

    public static final String        GIF_FILE_SUFFIX       = ".gif";

    public static final String        MHT_FILE_SUFFIX       = ".mht";

    public static final String        RTF_FILE_SUFFIX       = ".rtf";

    public static final String        PNG_FILE_SUFFIX       = ".png";

    public static final String        EML_FILE_SUFFIX       = ".eml";

    public static final String        MSG_FILE_SUFFIX       = ".msg";

    public static final String        XSD_FILE_SUFFIX       = ".xsd";

    public static final String        ICO_FILE_SUFFIX       = ".ico";

    public static final String        BMP_FILE_SUFFIX       = ".bmp";

    public static final String        DLL_FILE_SUFFIX       = ".dll";

    public static final String        JS_FILE_SUFFIX        = ".js";

    public static final String        CSS_FILE_SUFFIX       = ".css";

    public static Set<String>         IMG_MIMETYPE          = new HashSet<String>();
    static {
        IMG_MIMETYPE.add(MIMETYPE_GIF);
        IMG_MIMETYPE.add(MIMETYPE_JPEG);
        IMG_MIMETYPE.add(MIMETYPE_JPG);
        IMG_MIMETYPE.add(MIMETYPE_BMP);
        IMG_MIMETYPE.add(MIMETYPE_ICO);
        IMG_MIMETYPE.add(MIMETYPE_PJPEG);
        IMG_MIMETYPE.add(MIMETYPE_PNG);
    }

    public static Set<String>         IMG_FILE_SUFFIX       = new HashSet<String>();
    static {
        IMG_FILE_SUFFIX.add(JPEG_FILE_SUFFIX);
        IMG_FILE_SUFFIX.add(JPG_FILE_SUFFIX);
        IMG_FILE_SUFFIX.add(GIF_FILE_SUFFIX);
        IMG_FILE_SUFFIX.add(ICO_FILE_SUFFIX);
        IMG_FILE_SUFFIX.add(BMP_FILE_SUFFIX);
        IMG_FILE_SUFFIX.add(PNG_FILE_SUFFIX);
    }

    public static Set<String>         CLEAR_FILE_TYPE       = new HashSet<String>();
    static {
        CLEAR_FILE_TYPE.add(MIMETYPE_DLL);
    }

    public static Map<String, String> FILE_SUFFIX_MIMETYPE  = new HashMap<String, String>();
    static {
        FILE_SUFFIX_MIMETYPE.put(TEXT_FILE_SUFFIX, MIMETYPE_TEXT);
        FILE_SUFFIX_MIMETYPE.put(HTML_FILE_SUFFIX, MIMETYPE_HTML);
        FILE_SUFFIX_MIMETYPE.put(HTM_FILE_SUFFIX, MIMETYPE_HTML);
        FILE_SUFFIX_MIMETYPE.put(XML_FILE_SUFFIX, MIMETYPE_XML);
        FILE_SUFFIX_MIMETYPE.put(ZIP_FILE_SUFFIX, MIMETYPE_ZIP);
        FILE_SUFFIX_MIMETYPE.put(SEVEN_ZIP_FILE_SUFFIX, MIMETYPE_7Z);
        FILE_SUFFIX_MIMETYPE.put(PDF_FILE_SUFFIX, MIMETYPE_PDF);
        FILE_SUFFIX_MIMETYPE.put(RAR_FILE_SUFFIX, MIMETYPE_RAR);
        // FILE_SUFFIX_MIMETYPE.put(HELP_FILE_SUFFIX, MIMETYPE_HELP);
        FILE_SUFFIX_MIMETYPE.put(WORD2003_FILE_SUFFIX, MIMETYPE_WORD_2003);
        FILE_SUFFIX_MIMETYPE.put(WORD2007_FILE_SUFFIX, MIMETYPE_WORD_2007);
        FILE_SUFFIX_MIMETYPE.put(EXCEL2003_FILE_SUFFIX, MIMETYPE_EXCEL_2003);
        FILE_SUFFIX_MIMETYPE.put(EXCEL2007_FILE_SUFFIX, MIMETYPE_EXCEL_2007);
        FILE_SUFFIX_MIMETYPE.put(JPG_FILE_SUFFIX, MIMETYPE_JPG);
        FILE_SUFFIX_MIMETYPE.put(JPEG_FILE_SUFFIX, MIMETYPE_JPEG);
        FILE_SUFFIX_MIMETYPE.put(GIF_FILE_SUFFIX, MIMETYPE_GIF);
        FILE_SUFFIX_MIMETYPE.put(MHT_FILE_SUFFIX, MIMETYPE_MHT);
        FILE_SUFFIX_MIMETYPE.put(RTF_FILE_SUFFIX, MIMETYPE_RTF);
        FILE_SUFFIX_MIMETYPE.put(EML_FILE_SUFFIX, MIMETYPE_MESSAGE);
        FILE_SUFFIX_MIMETYPE.put(MSG_FILE_SUFFIX, MIMETYPE_MSG);

        FILE_SUFFIX_MIMETYPE.put(XSD_FILE_SUFFIX, MIMETYPE_XSD);
        FILE_SUFFIX_MIMETYPE.put(ICO_FILE_SUFFIX, MIMETYPE_ICO);
        FILE_SUFFIX_MIMETYPE.put(BMP_FILE_SUFFIX, MIMETYPE_BMP);
        FILE_SUFFIX_MIMETYPE.put(PNG_FILE_SUFFIX, MIMETYPE_PNG);
        FILE_SUFFIX_MIMETYPE.put(DLL_FILE_SUFFIX, MIMETYPE_DLL);
    }

    public static Map<String, String> MIMETYPE_FILE_SUFFIX  = new HashMap<String, String>();
    static {
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_TEXT, TEXT_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_HTML, HTML_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_XML, XML_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_ZIP, ZIP_FILE_SUFFIX);
        FILE_SUFFIX_MIMETYPE.put(MIMETYPE_7Z, SEVEN_ZIP_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_PDF, PDF_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_RAR, RAR_FILE_SUFFIX);
        // MIMETYPE_FILE_SUFFIX.put(MIMETYPE_HELP, HELP_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_WORD_95, WORD2003_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_WORD_2003, WORD2003_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_WORD_2007, WORD2007_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_EXCEL_2003, EXCEL2003_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_EXCEL_2003_2, EXCEL2003_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_EXCEL_2007, EXCEL2007_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_JPEG, JPEG_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_PJPEG, JPEG_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_GIF, GIF_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_MHT, MHT_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_RTF, RTF_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_JPG, JPG_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_GIF_1, GIF_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_PNG, PNG_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_MESSAGE, EML_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_MSG, MSG_FILE_SUFFIX);

        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_XSD, XSD_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_ICO, ICO_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_BMP, BMP_FILE_SUFFIX);
        MIMETYPE_FILE_SUFFIX.put(MIMETYPE_DLL, DLL_FILE_SUFFIX);
    }

}
