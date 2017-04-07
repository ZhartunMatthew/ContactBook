package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.exception.WrongInputException;
import com.zhartunmatthew.web.contactbook.validation.ValidationUtils;
import org.apache.commons.lang3.StringUtils;

public class FirstNameHandler extends AbstractHandler {
    @Override
    public void handleField(Contact contact, String data) throws WrongInputException {
        if(!StringUtils.isEmpty(data)) {
            data = data.trim();
            if(ValidationUtils.hasOnlyChars(data, "- ") && ValidationUtils.checkLength(data, 35)) {
                contact.setFirstName(data);
            } else {
                throw new WrongInputException("First name is invalid");
            }
        } else {
            throw new WrongInputException("First name is invalid");
        }
    }
}
