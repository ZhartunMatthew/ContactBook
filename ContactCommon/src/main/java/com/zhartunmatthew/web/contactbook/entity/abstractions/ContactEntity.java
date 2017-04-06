package com.zhartunmatthew.web.contactbook.entity.abstractions;

public interface ContactEntity extends Entity {
    Long getContactID();

    String getComment();
    void setComment(String comment);
}
