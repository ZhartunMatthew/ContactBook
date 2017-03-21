package com.zhartunmatthew.web.contactbook.jsonbuilder;

import com.zhartunmatthew.web.contactbook.entity.Phone;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class JSONBuilder {

    private static Logger log = Logger.getLogger(JSONBuilder.class);

    public static ArrayList<Phone> buildPhoneListFromJSON(String jsonPhones) {
        ArrayList<Phone> phones = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(jsonPhones);
            for(JSONObject object : (Iterable<JSONObject>) jsonArray) {
                Phone phone = new Phone();

                phone.setPhoneID(Long.parseLong(object.get("id").toString()));
                phone.setCountryCode((String) object.get("countryCode"));
                phone.setOperatorCode((String) object.get("operatorCode"));
                phone.setNumber((String) object.get("number"));
                phone.setType((String) object.get("type"));
                phone.setComment((String) object.get("comment"));

                phones.add(phone);
            }
        } catch (ParseException | ClassCastException ex) {
            ex.printStackTrace();
        }
        return phones;
    }
}
