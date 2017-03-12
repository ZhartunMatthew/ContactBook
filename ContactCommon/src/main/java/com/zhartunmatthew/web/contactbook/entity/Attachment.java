package com.zhartunmatthew.web.contactbook.entity;

import java.sql.Time;

public class Attachment {

    private long fileID;
    private long contactID;
    private String filePath;
    private String comment;
    private Time uploadDate;

    public long getFileID() {
        return fileID;
    }

    public void setFileID(long fileID) {
        this.fileID = fileID;
    }

    public long getContactID() {
        return contactID;
    }

    public void setContactID(long contactID) {
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
