package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;

public class HouseHandler extends AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) {
        contact.setHouseNumber(data);
    }
}
