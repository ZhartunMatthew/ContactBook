package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.exception.WrongInputException;
import com.zhartunmatthew.web.contactbook.validation.ValidationUtils;
import org.apache.commons.lang3.StringUtils;

public class LastNameHandler extends AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) throws WrongInputException {
        if(!StringUtils.isEmpty(data)) {
            if(ValidationUtils.hasOnlyChars(data, "-") && ValidationUtils.checkLength(data, 35)) {
                contact.setLastName(data);
            } else {
                throw new WrongInputException("Last name is invalid");
            }
        } else {
            throw new WrongInputException("Last name is invalid");
        }
    }
}
