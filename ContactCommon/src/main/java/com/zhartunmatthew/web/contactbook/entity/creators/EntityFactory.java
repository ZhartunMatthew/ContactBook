package com.zhartunmatthew.web.contactbook.entity.creators;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityFactory {
    public static Entity createEntityFromResultSet(ResultSet resultSet, Class type) {
        Entity entity = null;
        try {
            if (type == Contact.class) {
                entity = createContact(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    private static Contact createContact(ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong("id"));
        contact.setFirstName(resultSet.getString("first_name"));
        contact.setLastName(resultSet.getString("last_name"));
        contact.setPatronymic(resultSet.getString("patronymic"));
        contact.setBirthDate(resultSet.getTimestamp("birth_date"));
        contact.setSex(resultSet.getString("sex"));
        contact.setEmail(resultSet.getString("email"));
        contact.setWebsite(resultSet.getString("website"));
        contact.setNationality(resultSet.getString("nationality"));
        contact.setMaritalStatus(resultSet.getString("marital_status"));
        contact.setJob(resultSet.getString("job"));
        contact.setCountry(resultSet.getString("country"));
        contact.setCity(resultSet.getString("city"));
        contact.setStreet(resultSet.getString("street"));
        contact.setHouseNumber(resultSet.getString("house"));
        contact.setFlat(resultSet.getString("flat"));
        contact.setPostCode(resultSet.getString("postcode"));
        contact.setPhotoPath(resultSet.getString("photo_path"));
        return contact;
    }
}