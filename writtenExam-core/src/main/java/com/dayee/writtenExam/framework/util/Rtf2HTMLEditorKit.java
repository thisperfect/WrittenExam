package com.dayee.writtenExam.framework.util;

import java.io.IOException;
import java.io.Writer;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLWriter;

public class Rtf2HTMLEditorKit extends HTMLEditorKit {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String            encodingChangeArray[];

    public String[] getEncodingChangeArray() {

        return encodingChangeArray;
    }

    public void setEncodingChangeArray(String[] encodingChangeArray) {

        this.encodingChangeArray = encodingChangeArray;
    }

    public void write(Writer out, Document doc, int pos, int len)
            throws IOException, BadLocationException {

        if (doc instanceof HTMLDocument) {
            HTMLWriter w = new HTMLWriter(out, (HTMLDocument) doc, pos, len);
            w.write();
        } else if (doc instanceof StyledDocument) {
            Rtf2HTMLWriter w = new Rtf2HTMLWriter(out, (StyledDocument) doc,
                                                  pos, len);
            w.setEncodingChangeArray(encodingChangeArray);
            w.write();
        } else {
            super.write(out, doc, pos, len);
        }
    }
}

