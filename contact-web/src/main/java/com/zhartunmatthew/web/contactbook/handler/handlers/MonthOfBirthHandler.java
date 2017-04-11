package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.exception.WrongInputException;
import com.zhartunmatthew.web.contactbook.validation.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

public class MonthOfBirthHandler implements AbstractHandler {
    @Override
    public void handleField(Contact contact, String data) throws WrongInputException {
        if (!StringUtils.isEmpty(data)) {
            try {
                data = data.trim();
                if(ValidationUtils.isNumber(data)) {
                    int month = Integer.parseInt(data);
                    if (month != 0) {
                        if (contact.getBirthDate() == null) {
                            contact.setBirthDate(new Date(0));
                        }
                        contact.setBirthDate(setDateMonth(contact.getBirthDate(), month));
                    } else {
                        contact.setBirthDate(null);
                    }
                } else {
                    throw new WrongInputException("Month is invalid");
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
