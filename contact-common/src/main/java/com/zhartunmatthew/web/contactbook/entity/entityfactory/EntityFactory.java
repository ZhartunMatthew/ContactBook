package com.zhartunmatthew.web.contactbook.entity.entityfactory;

import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.abstractions.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityFactory {

    private static Logger LOG = LoggerFactory.getLogger(EntityFactory.class);

    public static Entity createEntityFromResultSet(ResultSet resultSet, Class type) {
        Entity entity = null;
        try {
            if (type == Contact.class) {
                entity = createContact(resultSet);
            }
            if (type == Phone.class) {
                entity = createPhone(resultSet);
            }
            if (type == Attachment.class) {
                entity = createAttachment(resultSet);
            }
        } catch (SQLException ex) {
            LOG.error("Can't build Entity ", ex);
        }
        return entity;
    }

    private static Contact createContact(ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong("id"));
        contact.setFirstName(resultSet.getString("first_name"));
        contact.setLastName(resultSet.getString("last_name"));
        contact.setPatronymic(resultSet.getString("patronymic"));
        contact.setBirthDate(resultSet.getDate("birth_date"));
        contact.setSex(resultSet.getString("sex"));
        contact.setEmail(resultSet.getString("email"));
        contact.setWebsite(resultSet.getString("website"));
        contact.setNationality(resultSet.getLong("nationality"));
        contact.setMaritalStatus(resultSet.getLong("marital_status"));
        contact.setJob(resultSet.getString("job"));
        contact.setCountry(resultSet.getLong("country"));
        contact.setCity(resultSet.getString("city"));
        contact.setStreet(resultSet.getString("street"));
        contact.setHouseNumber(resultSet.getString("house"));
        contact.setFlat(resultSet.getString("flat"));
        contact.setPostCode(resultSet.getString("postcode"));
        contact.setPhotoPath(resultSet.getString("photo_path"));
        return contact;
    }

    private static Phone createPhone(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone();
        phone.setId(resultSet.getLong("id"));
        phone.setContactID(resultSet.getLong("contact_id"));
        phone.setType(resultSet.getInt("phone_type"));
        phone.setCountryCode(resultSet.getString("country_code"));
        phone.setOperatorCode(resultSet.getString("operator_code"));
        phone.setNumber(resultSet.getString("number"));
        phone.setComment(resultSet.getString("comment"));
        return phone;
    }

    private static Attachment createAttachment(ResultSet resultSet) throws SQLException {
        Attachment attachment = new Attachment();
        attachment.setId(resultSet.getLong("id"));
        attachment.setContactID(resultSet.getLong("contact_id"));
        attachment.setFileName(resultSet.getString("file_name"));
        attachment.setComment(resultSet.getString("comment"));
        attachment.setUploadDate(resultSet.getDate("upload_date"));
        return attachment;
    }
}
