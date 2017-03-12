package com.zhartunmatthew.web.contactbook.entity;

public class Phone {
    private long phoneID;
    private long contactID;
    private String countryCode;
    private String operatorCode;
    private String number;
    private String type;
    private String commentary;

    public long getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(long phoneID) {
        this.phoneID = phoneID;
    }

    public long getContactID() {
        return contactID;
    }

    public void setContactID(long contactID) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}
