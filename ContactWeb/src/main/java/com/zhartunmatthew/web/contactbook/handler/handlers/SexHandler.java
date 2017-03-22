package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;

public class SexHandler  extends AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) {
        log.info("SEX: " + data);

        contact.setSex(data);

    }
}
