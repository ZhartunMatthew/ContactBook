package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import org.apache.log4j.Logger;

public abstract class AbstractHandler {
    protected Logger log = Logger.getLogger(AbstractHandler.class);
    public abstract void handleField(Contact contact, String data);
}
