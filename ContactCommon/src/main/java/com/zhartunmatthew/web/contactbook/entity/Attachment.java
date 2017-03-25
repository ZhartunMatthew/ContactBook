package com.zhartunmatthew.web.contactbook.entity;

import java.sql.Date;

public class Attachment implements Entity {

    private Long fileID;
    private Long contactID;
    private String filePath;
    private String comment;
    private Date uploadDate;

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

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "fileID=" + fileID +
                ", contactID=" + contactID +
                ", filePath='" + filePath + '\'' +
                ", comment='" + comment + '\'' +
                ", uploadDate=" + uploadDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attachment that = (Attachment) o;

        if (fileID != null ? !fileID.equals(that.fileID) : that.fileID != null) return false;
        if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        return uploadDate != null ? uploadDate.equals(that.uploadDate) : that.uploadDate == null;

    }

    @Override
    public int hashCode() {
        int result = filePath != null ? filePath.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
