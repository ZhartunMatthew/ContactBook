package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;

public class ContactIdHandler extends AbstractHandler{
    @Override
    public void handleField(Contact contact, String data) {
        if(data != null && !data.isEmpty()) {
            contact.setId(Long.parseLong(data));
        } else {
            contact.setId(null);
        }
    }
}
