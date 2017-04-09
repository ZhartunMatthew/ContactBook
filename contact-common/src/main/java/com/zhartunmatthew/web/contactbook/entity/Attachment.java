package com.zhartunmatthew.web.contactbook.entity;

import com.zhartunmatthew.web.contactbook.entity.abstractions.ContactEntity;

import java.sql.Date;
import java.util.Calendar;

public class Attachment implements ContactEntity {
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

    public String getDateString() {
        if(uploadDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(uploadDate);
            int iDay = calendar.get(Calendar.DAY_OF_MONTH);
            int iMonth = calendar.get(Calendar.MONTH) + 1;
            int iYear = calendar.get(Calendar.YEAR);
            String day = iDay < 10 ? "0" + Integer.toString(iDay) : Integer.toString(iDay);
            String month = iMonth < 10 ? "0" + Integer.toString(iMonth) : Integer.toString(iMonth);
            String year = Integer.toString(iYear);

            return day + "." + month + "." + year;
        } else {
            return "";
        }
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
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Attachment that = (Attachment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) {
            return false;
        }
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) {
            return false;
        }
        return uploadDate != null ? uploadDate.equals(that.uploadDate) : that.uploadDate == null;
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
