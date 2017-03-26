package com.zhartunmatthew.web.contactbook.entity;

public class Phone implements Entity {

    private Long id;
    private Long contactID;
    private String countryCode;
    private String operatorCode;
    private String number;
    private int type;
    private String comment;

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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", contactID=" + contactID +
                ", countryCode='" + countryCode + '\'' +
                ", operatorCode='" + operatorCode + '\'' +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone = (Phone) o;

        if (type != phone.type) return false;
        if (id != null ? !id.equals(phone.id) : phone.id != null) return false;
        if (countryCode != null ? !countryCode.equals(phone.countryCode) : phone.countryCode != null) return false;
        if (operatorCode != null ? !operatorCode.equals(phone.operatorCode) : phone.operatorCode != null) return false;
        if (number != null ? !number.equals(phone.number) : phone.number != null) return false;
        return comment != null ? comment.equals(phone.comment) : phone.comment == null;

    }

    @Override
    public int hashCode() {
        int result = countryCode != null ? countryCode.hashCode() : 0;
        result = 31 * result + (operatorCode != null ? operatorCode.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
