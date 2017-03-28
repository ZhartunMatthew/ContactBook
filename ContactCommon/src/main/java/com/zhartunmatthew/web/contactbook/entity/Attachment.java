package com.zhartunmatthew.web.contactbook.entity;

import java.sql.Date;

public class Attachment implements Entity {

    private Long id;
    private Long contactID;
    private String fileName;
    private String comment;
    private Date uploadDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactID() {
        return contactID;
    }

    public void setContactID(Long contactID) {
        this.contactID = contactID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
                "id=" + id +
                ", contactID=" + contactID +
                ", fileName='" + fileName + '\'' +
                ", comment='" + comment + '\'' +
                ", uploadDate=" + uploadDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attachment that = (Attachment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        return uploadDate != null ? uploadDate.equals(that.uploadDate) : that.uploadDate == null;

    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
