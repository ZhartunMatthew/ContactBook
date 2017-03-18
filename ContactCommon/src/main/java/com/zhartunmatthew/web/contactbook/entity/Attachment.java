package com.zhartunmatthew.web.contactbook.entity;

import java.sql.Timestamp;

public class Attachment implements Entity {

    private Long fileID;
    private Long contactID;
    private String filePath;
    private String comment;
    private Timestamp uploadDate;

    public Long getFileID() {
        return fileID;
    }

    public void setFileID(Long fileID) {
        this.fileID = fileID;
    }

    public Long getContactID() {
        return contactID;
    }

    public void setContactID(Long contactID) {
        this.contactID = contactID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }
}
