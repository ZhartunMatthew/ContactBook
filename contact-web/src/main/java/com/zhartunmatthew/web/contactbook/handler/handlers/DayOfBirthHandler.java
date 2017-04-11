package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.exception.WrongInputException;
import com.zhartunmatthew.web.contactbook.validation.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

public class DayOfBirthHandler implements AbstractHandler{
    @Override
    public void handleField(Contact contact, String data) throws WrongInputException {
        if (!StringUtils.isEmpty(data)) {
            try {
                data = data.trim();
                if(ValidationUtils.isNumber(data)) {
                    int day = Integer.parseInt(data);
                    if (day != 0) {
                        if (contact.getBirthDate() == null) {
                            contact.setBirthDate(new Date(0));
                        }
                        contact.setBirthDate(setDateDay(contact.getBirthDate(), day));
                    } else {
                        contact.setBirthDate(null);
                    }
                } else {
                    throw new WrongInputException("Day of birth invalid");
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        } else {
            contact.setBirthDate(null);
        }
    }

    private Date setDateDay(Date date, int day) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withDayOfMonth(day);
        return new Date(dateTime.getMillis());
    }
}
