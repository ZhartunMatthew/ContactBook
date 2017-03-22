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

                phone.setPhoneID(Long.parseLong(object.get("id").toString()));
                phone.setCountryCode((String) object.get("countryCode"));
                phone.setOperatorCode((String) object.get("operatorCode"));
                phone.setNumber((String) object.get("number"));
                phone.setType(((Long) object.get("type")).intValue());
                phone.setComment((String) object.get("comment"));

                phones.add(phone);
            });
        } catch (ParseException | ClassCastException ex) {
            ex.printStackTrace();
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

                attachment.setFileID(Long.parseLong(object.get("id").toString()));
                attachment.setUploadDate(new Date(DateTime.now().getMillis()));
                attachment.setFilePath((String) object.get("name"));
                attachment.setComment((String) object.get("comment"));

                attachments.add(attachment);
            });

        } catch (ParseException | ClassCastException ex) {
            ex.printStackTrace();
        }
        return attachments;
    }
}
