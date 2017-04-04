package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.exception.WrongInputException;

public abstract class AbstractHandler {
    public abstract void handleField(Contact contact, String data) throws WrongInputException;
}
