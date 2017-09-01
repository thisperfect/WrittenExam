package com.dayee.writtenExam.framework.util;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.POIXMLException;
import org.apache.poi.xwpf.model.XWPFCommentsDecorator;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTEmpty;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPTab;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Word2007ToHtml {

    private static final String SHAPE       = "shape";

    private static final String TAB         = "w:tab";

    private static final String CR          = "w:cr";

    private static final String BR          = "w:br";

    private static final String SELECT_PATH = "./*";

    private static final String INSTR_TEXT  = "w:instrText";

    public static void main(String[] args) throws IOException {
        XWPFDocument document = new XWPFDocument(
                Files.newInputStream(Paths.get("/Users/tang/Desktop/51job_田予诺_社交媒体运营专员.docx")));
        System.out.println(toHtml(document));
    }

    public static String toHtml(XWPFDocument document) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("<html><head><title>");
        buffer.append("</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head><body>");

        List<XWPFHeader> headerList = document.getHeaderList();
        if (CollectionUtils.notEmpty(headerList)) {
            for (XWPFHeader header : headerList) {
                List<IBodyElement> eleList = header.getBodyElements();
                if (CollectionUtils.notEmpty(eleList)) {
                    for (IBodyElement e : eleList) {
                        buffer.append(bodyElement2Html(document, e));
                        buffer.append(StringUtils.ADD_CONTENT_SEPARATOR);
                    }
                }
            }
        }

        List<IBodyElement> eleList = document.getBodyElements();
        if (CollectionUtils.notEmpty(eleList)) {
            for (IBodyElement e : eleList) {
                buffer.append(bodyElement2Html(document, e));
                buffer.append(StringUtils.ADD_CONTENT_SEPARATOR);
            }
        }

        List<XWPFFooter> footerList = document.getFooterList();
        if (CollectionUtils.notEmpty(footerList)) {
            for (XWPFFooter footer : footerList) {
                List<IBodyElement> bodyList = footer.getBodyElements();
                if (CollectionUtils.notEmpty(bodyList)) {
                    for (IBodyElement e : bodyList) {
                        buffer.append(bodyElement2Html(document, e));
                        buffer.append(StringUtils.ADD_CONTENT_SEPARATOR);
                    }
                }
            }
        }
        buffer.append("</body></html>");
        return buffer.toString();
    }

    private static String bodyElement2Html(XWPFDocument document,
                                           IBodyElement element) {

        StringBuffer buffer = new StringBuffer();

        if (element.getElementType() == BodyElementType.TABLE) {

            XWPFTable table = (XWPFTable) element;
            buffer.append(table2Html(document, table));
        } else if (element.getElementType() == BodyElementType.PARAGRAPH) {
            XWPFParagraph paragraph = (XWPFParagraph) element;
            buffer.append(paragraph2Html(document, paragraph));
        }
        return buffer.toString();
    }

    private static String table2Html(XWPFDocument document, XWPFTable table) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("<table>");
        List<XWPFTableRow> rowList = (List<XWPFTableRow>) table.getRows();
        if (CollectionUtils.notEmpty(rowList)) {

            for (XWPFTableRow row : rowList) {
                List<XWPFTableCell> cellList = (List<XWPFTableCell>) row
                        .getTableCells();
                if (CollectionUtils.notEmpty(cellList)) {
                    buffer.append("<tr>");

                    for (XWPFTableCell cell : cellList) {

                        List<IBodyElement> pList = cell.getBodyElements();
                        if (CollectionUtils.notEmpty(pList)) {
                            buffer.append("<td>");
                            for (IBodyElement xt : pList) {
                                buffer.append(bodyElement2Html(document, xt));
                            }
                            buffer.append("</td>");
                        }
                    }
                }
                buffer.append("</tr>");
            }
        }
        buffer.append("</table>");

        return buffer.toString();
    }

    private static String paragraph2Html(XWPFDocument document,
                                         XWPFParagraph paragraph) {

        StringBuffer buffer = new StringBuffer();
        try {
            CTSectPr ctSectPr = null;
            if (paragraph.getCTP().getPPr() != null) {
                ctSectPr = paragraph.getCTP().getPPr().getSectPr();
            }

            XWPFHeaderFooterPolicy headerFooterPolicy = null;

            if (ctSectPr != null) {
                headerFooterPolicy = new XWPFHeaderFooterPolicy(document,
                                                                ctSectPr);
                buffer.append(extractHeaders(document, headerFooterPolicy));
            }

            for (XWPFRun run : paragraph.getRuns()) {
                CTR ctr = run.getCTR();

                XmlCursor c = ctr.newCursor();
                c.selectPath(SELECT_PATH);
                StringBuffer text = new StringBuffer();
                while (c.toNextSelection()) {

                    XmlObject o = c.getObject();

                    if (o instanceof CTText) {
                        String tagName = o.getDomNode().getNodeName();
                        if (!INSTR_TEXT.equals(tagName)) {
                            String s = ((CTText) o).getStringValue();
                            text.append(s);
                        }
                    } else if (o instanceof CTPTab) {
                        text.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                    } else if (o instanceof CTEmpty) {
                        String tagName = o.getDomNode().getNodeName();

                        if (TAB.equals(tagName)) {
                            text.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                        } else if (BR.equals(tagName)) {
                            text.append(StringUtils.ADD_CONTENT_SEPARATOR);
                        } else if (CR.equals(tagName)) {
                            text.append(StringUtils.ADD_CONTENT_SEPARATOR);
                        }
                    } else if (o instanceof CTBr) {
                        text.append(StringUtils.ADD_CONTENT_SEPARATOR);
                    } else if (o instanceof CTPicture) {

                        XmlObject array[] = o
                                .selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//w:p");

                        if (CollectionUtils.notEmpty(array)) {
                            for (XmlObject t : array) {
                                CTP ctp = CTP.Factory.parse(t.xmlText());
                                XWPFParagraph p = new XWPFParagraph(ctp,
                                                                    document);
                                text.append(paragraph2Html(document, p));
                                text.append(StringUtils.ADD_CONTENT_SEPARATOR);
                            }
                        }

                        Node shapeNode = getChildNode(o.getDomNode(), SHAPE);
                        if (shapeNode != null) {

                            // 鑹烘湳瀛�
                            Node textNode = getChildNode(shapeNode, "textpath");
                            if (textNode != null) {
                                Node idNode = getAttribute(textNode, "string");
                                if (idNode != null) {
                                    String nodeValue = idNode.getNodeValue();

                                    buffer.append(text.toString());
                                    buffer.append(StringUtils.ADD_CONTENT_SEPARATOR);

                                    buffer.append(nodeValue);
                                    text = new StringBuffer();
                                }
                            }

                        }

                    }
                }
                String str = text.toString();
                str = formatToHtml(str);

                buffer.append("<span style=\"font-family:" + run
                        .getFontFamily()
                              + ";font-size:"
                              + run.getFontSize()
                              // / 2
                              + "px;");

                if (run.isBold()) {
                    buffer.append("font-weight:bold;");
                }
                if (run.isItalic()) {
                    buffer.append("font-style:italic;");
                }
                String underline = run.getUnderline().name();
                if (!UnderlinePatterns.NONE.name().equals(underline)) {
                    buffer.append("text-decoration:underline;");
                }
                buffer.append("\">");
                buffer.append(str).append("</span>");

                c.dispose();

            }

            XWPFCommentsDecorator decorator = new XWPFCommentsDecorator(
                    paragraph, null);
            String commentText = decorator.getCommentText();
            if (StringUtils.hasLength(commentText)) {
                buffer.append(decorator.getCommentText());
                buffer.append(StringUtils.ADD_CONTENT_SEPARATOR);
            }

            String footnameText = paragraph.getFootnoteText();
            if (footnameText != null && footnameText.length() > 0) {
                buffer.append(decorator.getCommentText());
                buffer.append(StringUtils.ADD_CONTENT_SEPARATOR);
            }

            if (ctSectPr != null) {
                buffer.append(extractFooters(document, headerFooterPolicy));
            }
        } catch (Exception e) {
            throw new POIXMLException(e);
        }

        return buffer.toString();
    }

    private static String extractHeaders(XWPFDocument document,
                                         XWPFHeaderFooterPolicy hfPolicy) {

        StringBuffer buffer = new StringBuffer();
        if (hfPolicy.getFirstPageHeader() != null) {
            XWPFHeader header = hfPolicy.getFirstPageHeader();
            List<IBodyElement> eleList = header.getBodyElements();
            if (CollectionUtils.notEmpty(eleList)) {
                for (IBodyElement e : eleList) {
                    buffer.append(bodyElement2Html(document, e));
                    buffer.append(StringUtils.ADD_CONTENT_SEPARATOR);
                }
            }
        }

        if (hfPolicy.getEvenPageHeader() != null) {
            XWPFHeader header = hfPolicy.getEvenPageHeader();
            List<IBodyElement> eleList = header.getBodyElements();
            if (CollectionUtils.notEmpty(eleList)) {
                for (IBodyElement e : eleList) {
                    buffer.append(bodyElement2Html(document, e));
                    buffer.append(StringUtils.ADD_CONTENT_SEPARATOR);
                }
            }
        }

        if (hfPolicy.getDefaultHeader() != null) {
            XWPFHeader header = hfPolicy.getDefaultHeader();
            List<IBodyElement> eleList = header.getBodyElements();
            if (CollectionUtils.notEmpty(eleList)) {
                for (IBodyElement e : eleList) {
                    buffer.append(bodyElement2Html(document, e));
                    buffer.append(StringUtils.ADD_CONTENT_SEPARATOR);
                }
            }
        }

        return buffer.toString();
    }

    private static String extractFooters(XWPFDocument document,
                                         XWPFHeaderFooterPolicy hfPolicy) {

        StringBuffer buffer = new StringBuffer();
        if (hfPolicy.getFirstPageFooter() != null) {
            XWPFFooter footer = hfPolicy.getFirstPageFooter();
            List<IBodyElement> eleList = footer.getBodyElements();
            if (CollectionUtils.notEmpty(eleList)) {
                for (IBodyElement e : eleList) {
                    buffer.append(bodyElement2Html(document, e));
                    buffer.append(StringUtils.LINE_SEPARATOR);
                }
            }
        }

        if (hfPolicy.getEvenPageFooter() != null) {
            XWPFFooter footer = hfPolicy.getEvenPageFooter();
            List<IBodyElement> eleList = footer.getBodyElements();
            if (CollectionUtils.notEmpty(eleList)) {
                for (IBodyElement e : eleList) {
                    buffer.append(bodyElement2Html(document, e));
                    buffer.append(StringUtils.LINE_SEPARATOR);
                }
            }
        }

        if (hfPolicy.getDefaultFooter() != null) {
            XWPFFooter footer = hfPolicy.getDefaultFooter();
            List<IBodyElement> eleList = footer.getBodyElements();
            if (CollectionUtils.notEmpty(eleList)) {
                for (IBodyElement e : eleList) {
                    buffer.append(bodyElement2Html(document, e));
                    buffer.append(StringUtils.LINE_SEPARATOR);
                }
            }
        }

        return buffer.toString();
    }

    private static Node getAttribute(Node node, String attr) {

        NamedNodeMap map = node.getAttributes();
        if (map != null) {
            int mapSize = map.getLength();
            for (int k = 0; k < mapSize; k++) {
                Node mapNode = map.item(k);
                String mapNodeName = mapNode.getNodeName();
                if (StringUtils.hasLength(mapNodeName) && (mapNodeName
                                                                   .endsWith(StringUtils.COLON + attr) || mapNodeName
                                                                   .equalsIgnoreCase(attr))) {

                    return mapNode;
                }
            }
        }
        return null;
    }

    private static Node getChildNode(Node node, String childName) {

        NodeList nodeList = node.getChildNodes();
        if (nodeList != null) {
            int size = nodeList.getLength();
            for (int i = 0; i < size; i++) {
                Node childNode = nodeList.item(i);
                String name = childNode.getNodeName();
                if (StringUtils.hasLength(name) && (name
                                                            .endsWith(StringUtils.COLON + childName) || name
                                                            .equalsIgnoreCase(childName))) {
                    return childNode;
                }
            }
        }
        return null;
    }

    protected static String formatToHtml(String str) {

        if (StringUtils.isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);
            if (ch == 56269 || ch == 57102) {
                buffer.append("&nbsp;");
            } else if (Character.isSpaceChar(ch)) {
                buffer.append("&nbsp;");
            } else if (Character.isWhitespace(ch)) {
                if (ch == Character.DIRECTIONALITY_BOUNDARY_NEUTRAL) {
                    buffer.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                    // } else if (ch ==
                    // Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR || ch ==
                    // Character.DIRECTIONALITY_OTHER_NEUTRALS) {
                    // buffer.append("<br>");
                } else {
                    buffer.append("<br>");
                }
            } else if (!CharacterUtils.isSpaceLetter(ch) && (Character
                                                                     .isIdentifierIgnorable(ch) || Character
                                                                     .isISOControl(ch))) {
                if (Character.isISOControl(ch)) {
                    // chs[count++] = 32;
                } else if (Character.isIdentifierIgnorable(ch)) {
                    buffer.append("&nbsp;");
                }
            } else {
                buffer.append(str.charAt(i));
            }
        }

        return buffer.toString();

    }
}