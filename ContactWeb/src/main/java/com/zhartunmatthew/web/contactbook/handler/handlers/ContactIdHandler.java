package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import org.apache.commons.lang3.StringUtils;

public class ContactIdHandler extends AbstractHandler{
    @Override
    public void handleField(Contact contact, String data) {
        if(!StringUtils.isEmpty(data)) {
            contact.setId(Long.parseLong(data));
        } else {
            contact.setId(null);
        }
    }
}
