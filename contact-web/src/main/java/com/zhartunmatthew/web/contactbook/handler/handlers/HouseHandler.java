package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.exception.WrongInputException;
import com.zhartunmatthew.web.contactbook.validation.ValidationUtils;
import org.apache.commons.lang3.StringUtils;

public class HouseHandler implements AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) throws WrongInputException {
        if(!StringUtils.isEmpty(data)) {
            data = data.trim();
            if(ValidationUtils.checkLength(data, 10) &&
                    ValidationUtils.hasOnlyCharsAndDigits(data, " -")) {
                contact.setHouseNumber(data);
            } else {
                throw new WrongInputException("House is invalid");
            }
        } else {
            contact.setHouseNumber(null);
        }
    }
}
