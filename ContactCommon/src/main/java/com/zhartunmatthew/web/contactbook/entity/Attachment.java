package com.zhartunmatthew.web.contactbook.entity;

import java.sql.Time;

public class Attachment implements Entity {

    private Long fileID;
    private Long contactID;
    private String filePath;
    private String comment;
    private Time uploadDate;

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

    public Time getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Time uploadDate) {
        this.uploadDate = uploadDate;
    }
}
