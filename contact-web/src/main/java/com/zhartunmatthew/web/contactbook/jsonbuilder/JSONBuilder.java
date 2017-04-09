package com.zhartunmatthew.web.contactbook.jsonbuilder;

import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.abstractions.ContactEntity;
import com.zhartunmatthew.web.contactbook.validation.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.ArrayList;

public class JSONBuilder {

    private final static Logger LOG = LoggerFactory.getLogger(JSONBuilder.class);

    public static ArrayList<Phone> buildPhoneListFromJSON(String jsonPhones) {
        ArrayList<Phone> phones = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(jsonPhones);
            jsonArray.forEach(item -> {
                JSONObject object = (JSONObject) item;
                Phone phone = new Phone();

                Object id = object.get("id");
                idSetter(phone, id);
                phone.setCountryCode((String) object.get("countryCode"));
                phone.setOperatorCode((String) object.get("operatorCode"));
                phone.setNumber((String) object.get("number"));
                phone.setType(((Long) object.get("type")).intValue());
                commentSetter(phone, object.get("comment"));

                phones.add(phone);
            });
        } catch (ParseException | ClassCastException ex) {
            LOG.error("JSON phones error", ex);
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

                idSetter(attachment, object.get("id"));
                attachment.setUploadDate(new Date(DateTime.now().getMillis()));
                attachment.setFileName((String) object.get("fileName"));
                commentSetter(attachment, object.get("comment"));

                attachments.add(attachment);
            });

        } catch (ParseException | ClassCastException ex) {
            LOG.error("JSON attachments error", ex);
        }
        return attachments;
    }

    private static void idSetter(ContactEntity entity, Object id) {
        if(id != null) {
            entity.setId(Long.parseLong(id.toString()));
        } else {
            entity.setId(null);
        }
    }

    private static void commentSetter(ContactEntity entity, Object comment) {
        if(!StringUtils.isEmpty((String) comment)) {
            String commentStr = (String) comment;
            if(!ValidationUtils.checkLength(commentStr, 160)) {
                commentStr = commentStr.substring(0, 160);
            }
            entity.setComment(commentStr);
        } else {
            entity.setComment(null);
        }
    }
}
