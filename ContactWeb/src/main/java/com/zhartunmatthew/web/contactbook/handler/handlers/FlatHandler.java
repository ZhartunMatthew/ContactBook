package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;

public class FlatHandler extends AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) {
        contact.setFlat(data);
    }
}
