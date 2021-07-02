package com.udacity.jwdnd.course1.cloudstorage.model;


import java.io.InputStream;

public class File {

    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userid;
    private InputStream fileData;

    public File(Integer fileId, String fileName, Integer userid) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = null;
        this.fileSize = null;
        this.userid = userid;
        this.fileData = null;
    }

    public File(Integer fileId, String fileName, String contentType, String fileSize, Integer userid, InputStream fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userid = userid;
        this.fileData = fileData;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public InputStream getFileData() {
        return fileData;
    }

    public void setFileData(InputStream fileData) {
        this.fileData = fileData;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", userid=" + userid +
                ", fileData=" + fileData +
                '}';
    }
}
