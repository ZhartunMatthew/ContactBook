package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.exception.WrongInputException;
import com.zhartunmatthew.web.contactbook.validation.ValidationUtils;
import org.apache.commons.lang3.StringUtils;

public class CityHandler extends AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) throws WrongInputException {
        if(!StringUtils.isEmpty(data)) {
            if(ValidationUtils.checkLength(data, 35) && ValidationUtils.hasOnlyChars(data, "-")) {
                contact.setCity(data);
            } else {
                throw new WrongInputException("Invalid city name");
            }
        } else {
            contact.setCity(null);
        }
    }
}
