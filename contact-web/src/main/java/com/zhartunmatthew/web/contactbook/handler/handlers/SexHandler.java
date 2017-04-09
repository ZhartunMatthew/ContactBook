package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;

import java.util.Objects;

public class SexHandler  extends AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) {
        if(!Objects.equals(data, "X")) {
            contact.setSex(data);
        } else {
            contact.setSex(null);
        }
    }
}
