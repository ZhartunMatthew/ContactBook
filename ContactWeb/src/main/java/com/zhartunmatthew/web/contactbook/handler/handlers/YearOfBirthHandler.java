package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.exception.WrongInputException;
import com.zhartunmatthew.web.contactbook.validation.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

public class YearOfBirthHandler extends AbstractHandler {
    @Override
    public void handleField(Contact contact, String data) throws WrongInputException {
        if (!StringUtils.isEmpty(data)) {
            try {
                data = data.trim();
                if(ValidationUtils.isNumber(data)) {
                    int year = Integer.parseInt(data);
                    if (year != 0) {
                        if (contact.getBirthDate() == null) {
                            contact.setBirthDate(new Date(0));
                        }
                        contact.setBirthDate(setDateYear(contact.getBirthDate(), year));
                    } else {
                        contact.setBirthDate(null);
                    }
                } else {
                    throw new WrongInputException("Year is invalid");
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
