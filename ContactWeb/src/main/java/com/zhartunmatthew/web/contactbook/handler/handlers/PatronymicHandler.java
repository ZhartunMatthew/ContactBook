package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.exception.WrongInputException;
import com.zhartunmatthew.web.contactbook.validation.ValidationUtils;
import org.apache.commons.lang3.StringUtils;

public class PatronymicHandler extends AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) throws WrongInputException {
        if(!StringUtils.isEmpty(data)) {
            data = data.trim();
            if(data.length() > 0) {
                if (ValidationUtils.checkLength(data, 35) && ValidationUtils.hasOnlyChars(data, "- ")) {
                    contact.setPatronymic(data);
                } else {
                    throw new WrongInputException("Patronymic is invalid");
                }
            } else {
                contact.setPatronymic(null);
            }
        } else {
            contact.setPatronymic(null);
        }
    }
}
