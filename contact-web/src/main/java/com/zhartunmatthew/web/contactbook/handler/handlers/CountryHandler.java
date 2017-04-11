package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;

public class CountryHandler implements AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) {
        Long id = Long.parseLong(data);
        if(id.equals(0L)) {
            contact.setCountry(null);
        } else {
            contact.setCountry(Long.parseLong(data));
        }
    }
}
