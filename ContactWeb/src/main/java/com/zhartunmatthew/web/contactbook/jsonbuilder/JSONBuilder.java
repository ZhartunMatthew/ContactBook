package com.zhartunmatthew.web.contactbook.jsonbuilder;

import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Date;
import java.util.ArrayList;

public class JSONBuilder {

    private static Logger log = Logger.getLogger(JSONBuilder.class);

    public static ArrayList<Phone> buildPhoneListFromJSON(String jsonPhones) {
        ArrayList<Phone> phones = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(jsonPhones);
            jsonArray.forEach(item -> {
                JSONObject object = (JSONObject) item;
                Phone phone = new Phone();

                Object id = object.get("id");
                if(id != null) {
                    phone.setId(Long.parseLong(id.toString()));
                } else {
                    phone.setId(null);
                }
                phone.setCountryCode((String) object.get("countryCode"));
                phone.setOperatorCode((String) object.get("operatorCode"));
                phone.setNumber((String) object.get("number"));
                phone.setType(((Long) object.get("type")).intValue());
                phone.setComment((String) object.get("comment"));

                phones.add(phone);
            });
        } catch (ParseException | ClassCastException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return phones;
    }

    public static ArrayList<Attachment> buildAttachmentListFromJSON(String jsonAttachments) {
        ArrayList<Attachment> attachments = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(jsonAttachments);
            jsonArray.forEach(item -> {
                JSONObject object = (JSONObject) item;
                Attachment attachment = new Attachment();

                Object id = object.get("id");
                if(id != null) {
                    attachment.setId(Long.parseLong(id.toString()));
                } else {
                    attachment.setId(null);
                }

                attachment.setUploadDate(new Date(DateTime.now().getMillis()));
                attachment.setFileName((String) object.get("fileName"));
                attachment.setComment((String) object.get("comment"));

                attachments.add(attachment);
            });

        } catch (ParseException | ClassCastException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return attachments;
    }
}
