package com.zhartunmatthew.web.contactbook.jsonbuilder;

import com.zhartunmatthew.web.contactbook.entity.Phone;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class JSONBuilder {

    private static Logger log = Logger.getLogger(JSONBuilder.class);
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public static ArrayList<Phone> buildPhoneListFromJSON(String jsonPhones) {
        ArrayList<Phone> phones = new ArrayList<>();


        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(jsonPhones);
            for(JSONObject object : (Iterable<JSONObject>) jsonArray) {
                Phone phone = new Phone();

                log.info("Id = " + Long.parseLong(object.get("id").toString()));
                phone.setPhoneID(Long.parseLong(object.get("id").toString()));

                log.info("Country code = " + object.get("countryCode"));
                phone.setCountryCode((String) object.get("countryCode"));

                log.info("Operator code = " + object.get("operatorCode"));
                phone.setOperatorCode((String) object.get("operatorCode"));

                log.info("Number = " + object.get("number"));
                phone.setNumber((String) object.get("number"));

                log.info("Type = " + object.get("type"));
                phone.setType((String) object.get("type"));

                log.info("Comment = " + object.get("comment"));
                phone.setComment((String) object.get("comment"));

                phones.add(phone);
            }
        } catch (ParseException | ClassCastException ex) {
            ex.printStackTrace();
        }
        return phones;
    }
}
