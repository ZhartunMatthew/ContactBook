package com.zhartunmatthew.web.contactbook.handler.handlerfactory;

import com.zhartunmatthew.web.contactbook.handler.handlers.*;

import java.util.HashMap;
import java.util.Map;

public class HandlerFactory {
    
    private static Map<String, AbstractHandler> handlerMap = new HashMap<>();
    static {
        handlerMap.put("id", new ContactIdHandler());
        handlerMap.put("first-name", new FirstNameHandler());
        handlerMap.put("last-name", new LastNameHandler());
        handlerMap.put("patronymic", new PatronymicHandler());
        handlerMap.put("birth-date", new BirthDateHandler());
        handlerMap.put("marital-status", new MaritalStatusHandler());
        handlerMap.put("sex", new SexHandler());
        handlerMap.put("nationality", new NationalityHandler());
        handlerMap.put("website", new WebsiteHandler());
        handlerMap.put("email", new EmailHandler());
        handlerMap.put("job", new JobHandler());
        handlerMap.put("country", new CountryHandler());
        handlerMap.put("city", new CityHandler());
        handlerMap.put("street", new StreetHandler());
        handlerMap.put("house-number", new HouseHandler());
        handlerMap.put("flat", new FlatHandler());
        handlerMap.put("postcode", new PostcodeHandler());
        handlerMap.put("old-phones", new PhonesHandler());
        handlerMap.put("old-attachments", new AttachmentHandler());
        handlerMap.put("new-phones", new PhonesHandler());
        handlerMap.put("new-attachments", new AttachmentHandler());
    }
    
    public static AbstractHandler getFieldHandler(String inputFieldName) {
        return handlerMap.get(inputFieldName);
    }
}
