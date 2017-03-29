package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

public class MonthOfBirthHandler extends AbstractHandler {
    @Override
    public void handleField(Contact contact, String value) {
        if (!StringUtils.isEmpty(value)) {
            try {
                int month = Integer.parseInt(value);
                if (month != 0) {
                    if (contact.getBirthDate() == null) {
                        contact.setBirthDate(new Date(0));
                    }
                    contact.setBirthDate(setDateMonth(contact.getBirthDate(), month));
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

    private Date setDateMonth(Date date, int month) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withMonthOfYear(month);
        return new Date(dateTime.getMillis());
    }
}
