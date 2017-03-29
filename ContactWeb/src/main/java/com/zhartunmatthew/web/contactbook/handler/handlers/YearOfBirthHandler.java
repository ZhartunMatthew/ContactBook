package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

public class YearOfBirthHandler extends AbstractHandler {
    @Override
    public void handleField(Contact contact, String value) {
        if (!StringUtils.isEmpty(value)) {
            try {
                int year = Integer.parseInt(value);
                if(year != 0) {
                    if (contact.getBirthDate() == null) {
                        contact.setBirthDate(new Date(0));
                    }
                    contact.setBirthDate(setDateYear(contact.getBirthDate(), year));
                } else {
                    contact.setBirthDate(null);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            contact.setBirthDate(null);
        }
    }

    private Date setDateYear(Date date, int year) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withYear(year);
        return new Date(dateTime.getMillis());
    }
}
