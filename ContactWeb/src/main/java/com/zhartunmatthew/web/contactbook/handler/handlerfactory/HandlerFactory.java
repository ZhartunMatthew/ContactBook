package com.zhartunmatthew.web.contactbook.handler.handlerfactory;

import com.zhartunmatthew.web.contactbook.handler.handlers.*;

public class HandlerFactory {
    public static AbstractHandler getFieldHandler(String inputFieldName) {
        AbstractHandler abstractHandler = null;

        switch (inputFieldName){
            case "first-name":
                abstractHandler = new FirstNameHandler();
                break;
            case "last-name":
                abstractHandler = new LastNameHandler();
                break;
            case "patronymic":
                abstractHandler = new PatronymicHandler();
                break;
            case "birth-date":
                abstractHandler = new BirthDateHandler();
                break;
            case "marital-status":
                abstractHandler = new MaritalStatusHandler();
                break;
            case "sex":
                abstractHandler = new SexHandler();
                break;
            case "nationality":
                abstractHandler = new NationalityHandler();
                break;
            case "website":
                abstractHandler = new WebsiteHandler();
                break;
            case "email":
                abstractHandler = new EmailHandler();
                break;
            case "job":
                abstractHandler = new JobHandler();
                break;
            case "country":
                abstractHandler = new CountryHandler();
                break;
            case "city":
                abstractHandler = new CityHandler();
                break;
            case "street":
                abstractHandler = new StreetHandler();
                break;
            case "house-number":
                abstractHandler = new HouseHandler();
                break;
            case "flat":
                abstractHandler = new FlatHandler();
                break;
            case "postcode":
                abstractHandler = new PostcodeHandler();
                break;
            case "old-phones":
                abstractHandler = new OldPhonesHandler();
                break;
            case "old-attachments":
                abstractHandler = new OldAttachmentHandler();
                break;
            case "new-phones":
                abstractHandler = new NewPhonesHandler();
                break;
            case "new-attachments":
                abstractHandler = new NewAttachmentHandler();
                break;
        }
        return abstractHandler;
    }
}
