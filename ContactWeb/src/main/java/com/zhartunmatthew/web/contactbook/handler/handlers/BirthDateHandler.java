package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BirthDateHandler extends AbstractHandler {
    @Override
    public void handleField(Contact contact, String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.sql.Date sqlDate = null;

        try {
            if(!data.isEmpty()) {
                date = dateFormat.parse(data);
                sqlDate = new java.sql.Date(date.getTime());
            } else {
                date = dateFormat.parse("2000-01-01");
                sqlDate = new java.sql.Date(date.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        contact.setBirthDate(sqlDate);
    }
}
