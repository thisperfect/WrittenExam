package com.dayee.writtenExam.framework.util;

import java.io.IOException;
import java.io.Writer;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.MinimalHTMLWriter;

public class Rtf2HTMLWriter extends MinimalHTMLWriter {

    private String encodingChangeArray[];

    public String[] getEncodingChangeArray() {

        return encodingChangeArray;
    }

    public void setEncodingChangeArray(String[] encodingChangeArray) {

        this.encodingChangeArray = encodingChangeArray;
    }

    public Rtf2HTMLWriter(Writer w, StyledDocument doc, int pos, int len) {

        super(w, doc, pos, len);
    }

    public Rtf2HTMLWriter(Writer w, StyledDocument doc) {

        super(w, doc);
    }

    protected void writeHeader() throws IOException {

        writeStartTag("<head>");
        if (encodingChangeArray != null) {
            writeStartTag("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + encodingChangeArray[1]
                          + "\" />");
        }
        writeStartTag("<style>");
        writeStartTag("<!--");
        writeStyles();
        writeEndTag("-->");
        writeEndTag("</style>");
        writeEndTag("</head>");
    }

    protected void text(Element elem) throws IOException, BadLocationException {

        String contentStr = getText(elem);

        if ((contentStr.length() > 0) && (contentStr
                                                  .charAt(contentStr.length() - 1) == NEWLINE)) {
            contentStr = contentStr.substring(0, contentStr.length() - 1);
        }
        if (contentStr.length() > 0) {
            if (encodingChangeArray != null) {
                contentStr = new String(
                        contentStr.getBytes(encodingChangeArray[0]),
                        encodingChangeArray[1]);
            }

            write(contentStr);
        }
    }
}