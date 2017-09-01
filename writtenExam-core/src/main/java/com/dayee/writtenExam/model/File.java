package com.dayee.writtenExam.model;

import java.util.List;

/**
 * Created by tang on 2017/1/17.
 */
public class File {

    private String fileName;

    private String path;

    private String fileType;

    private Long size;

    private byte[] content;

    private List<File> attaList;

    private String     encoding;

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    public String getPath() {

        return path;
    }

    public void setPath(String path) {

        this.path = path;
    }

    public String getFileType() {

        return fileType;
    }

    public void setFileType(String fileType) {

        this.fileType = fileType;
    }

    public Long getSize() {

        return size;
    }

    public void setSize(Long size) {

        this.size = size;
    }

    public byte[] getContent() {

        return content;
    }

    public void setContent(byte[] content) {

        this.content = content;
    }

    public List<File> getAttaList() {

        return attaList;
    }

    public void setAttaList(List<File> attaList) {

        this.attaList = attaList;
    }

    public String getEncoding() {

        return encoding;
    }

    public void setEncoding(String encoding) {

        this.encoding = encoding;
    }
}
