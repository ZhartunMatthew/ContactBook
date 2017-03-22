package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.jsonbuilder.JSONBuilder;

import java.util.ArrayList;

public class NewPhonesHandler extends AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) {
        ArrayList<Phone> phones = JSONBuilder.buildPhoneListFromJSON(data);
        contact.setPhones(phones);
    }
}
