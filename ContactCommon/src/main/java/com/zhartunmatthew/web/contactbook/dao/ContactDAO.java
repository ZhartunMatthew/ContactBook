package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.entityfactory.EntityFactory;
import com.zhartunmatthew.web.contactbook.entity.search.SearchParameters;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class ContactDAO extends AbstractDAO<Long, Contact> {

    private static Logger log = Logger.getLogger(ContactDAO.class);

    private static final String SELECT_ALL_CONTACTS =
            "SELECT contacts.id AS id, first_name, last_name, patronymic, " +
            "birth_date, sex, marital_status.id_marital_status AS marital_status, " +
            "nationality.id_nationality AS nationality, " +
            "countries.id_country AS country, addresses.city AS city, " +
            "addresses.street AS street, addresses.house_number AS house, " +
            "addresses.flat AS flat, addresses.postcode AS postcode, " +
            "website, email, photo_path, job " +
            "FROM contacts " +
            "LEFT JOIN nationality ON nationality.id_nationality = contacts.nationality_id " +
            "LEFT JOIN marital_status ON marital_status.id_marital_status = contacts.marital_status_id " +
            "LEFT JOIN countries ON countries.id_country = contacts.country_id " +
            "LEFT JOIN addresses ON addresses.contact_id = contacts.id ORDER BY last_name;";

    private static final String SELECT_CONTACT_BY_ID =
            "SELECT contacts.id AS id, first_name, last_name, patronymic, " +
                    "birth_date, sex, marital_status.id_marital_status AS marital_status, " +
                    "nationality.id_nationality AS nationality, " +
                    "countries.id_country AS country, addresses.city AS city, " +
                    "addresses.street AS street, addresses.house_number AS house, " +
                    "addresses.flat AS flat, addresses.postcode AS postcode, " +
                    "website, email, photo_path, job " +
                    "FROM contacts " +
                    "LEFT JOIN nationality ON nationality.id_nationality = contacts.nationality_id " +
                    "LEFT JOIN marital_status ON marital_status.id_marital_status = contacts.marital_status_id " +
                    "LEFT JOIN countries ON countries.id_country = contacts.country_id " +
                    "LEFT JOIN addresses ON addresses.contact_id = contacts.id WHERE id = ? LIMIT 1;";

    private static final String SELECT_CERTAIN_COUNT =
            "SELECT contacts.id AS id, first_name, last_name, patronymic, " +
                    "birth_date, sex, marital_status.id_marital_status AS marital_status, " +
                    "nationality.id_nationality AS nationality, " +
                    "countries.id_country AS country, addresses.city AS city, " +
                    "addresses.street AS street, addresses.house_number AS house, " +
                    "addresses.flat AS flat, addresses.postcode AS postcode, " +
                    "website, email, photo_path, job " +
                    "FROM contacts " +
                    "LEFT JOIN nationality ON nationality.id_nationality = contacts.nationality_id " +
                    "LEFT JOIN marital_status ON marital_status.id_marital_status = contacts.marital_status_id " +
                    "LEFT JOIN countries ON countries.id_country = contacts.country_id " +
                    "LEFT JOIN addresses ON addresses.contact_id = contacts.id ORDER BY last_name LIMIT ? OFFSET ?;";

    private static final String INSERT_CONTACT_QUERY =
            "INSERT INTO contacts (first_name, last_name, patronymic, birth_date, " +
                    "sex, marital_status_id, nationality_id, country_id, website, email, " +
                    "photo_path, job) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String INSERT_CONTACT_ADDRESS_QUERY =
            "INSERT INTO addresses (contact_id, city, street, house_number, flat, postcode)" +
                    " VALUES (?, ?, ?, ?, ?, ?);";

    private static final String DELETE_CONTACT =
            "DELETE FROM contacts WHERE id = ?";

    private static final String DELETE_CONTACTS_ADDRESS =
            "DELETE FROM addresses WHERE contact_id = ?";

    private static final String GET_COUNT = "SELECT COUNT(*) AS count FROM contactbook.contacts";

    private static final String GET_LAST_ID = "SELECT last_insert_id() AS last_id FROM contacts";

    private static final String PARAMS_QUERY =
            "SELECT contacts.id AS id, first_name, last_name, patronymic, " +
            "birth_date, sex, marital_status.id_marital_status AS marital_status, " +
            "nationality.id_nationality AS nationality, " +
            "countries.id_country AS country, addresses.city AS city, " +
            "addresses.street AS street, addresses.house_number AS house, " +
            "addresses.flat AS flat, addresses.postcode AS postcode, " +
            "website, email, photo_path, job " +
            "FROM contacts " +
            "LEFT JOIN nationality ON nationality.id_nationality = contacts.nationality_id " +
            "LEFT JOIN marital_status ON marital_status.id_marital_status = contacts.marital_status_id " +
            "LEFT JOIN countries ON countries.id_country = contacts.country_id " +
            "LEFT JOIN addresses ON addresses.contact_id = contacts.id WHERE TRUE ";


    public ContactDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Contact> readAll() {
        ArrayList<Contact> contacts = new ArrayList<>();

        ResultSet contactResultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CONTACTS)){
            contactResultSet = statement.executeQuery();
            while(contactResultSet.next()) {
                Contact tempContact = (Contact)
                        EntityFactory.createEntityFromResultSet(contactResultSet, Contact.class);
                tempContact.setPhones(getContactPhones(tempContact.getId()));
                tempContact.setAttachments(getContactAttachments(tempContact.getId()));
                contacts.add(tempContact);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contacts;
    }

    private ArrayList<Phone> getContactPhones(long i) {
        ArrayList<Phone> phones = null;
        PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.createDAO(PhoneDAO.class, connection);
        try {
            phones = phoneDAO.readContactPhones(i);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return phones;
    }

    private ArrayList<Attachment> getContactAttachments(long i) {
        ArrayList<Attachment> attachments = null;
        AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.createDAO(AttachmentDAO.class, connection);
        try {
            attachments = attachmentDAO.readContactAttachments(i);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return attachments;
    }

    public ArrayList<Contact> readCertainCount(long from, long count) {
        ArrayList<Contact> contacts = new ArrayList<>();

        ResultSet contactResultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CERTAIN_COUNT)){
            statement.setLong(1, count);
            statement.setLong(2, from);
            contactResultSet = statement.executeQuery();
            while(contactResultSet.next()) {
                log.info("ONE MORE");
                Contact tempContact = (Contact)
                        EntityFactory.createEntityFromResultSet(contactResultSet, Contact.class);
                tempContact.setPhones(getContactPhones(tempContact.getId()));
                tempContact.setAttachments(getContactAttachments(tempContact.getId()));
                contacts.add(tempContact);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contacts;
    }

    public Long getContactCount() {
        ResultSet resultSet;
        Long count = 0L;
        try (PreparedStatement statement = connection.prepareStatement(GET_COUNT)){
            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public void insert(Contact val) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CONTACT_QUERY)) {
            statement.setString(1, val.getFirstName());
            statement.setString(2, val.getLastName());
            statement.setString(3, val.getPatronymic());

            if(val.getBirthDate() == null) {
                statement.setNull(4, Types.DATE);
            } else {
                statement.setDate(4, val.getBirthDate());
            }

            statement.setString(5, val.getSex());
            if(val.getMaritalStatus() == null || val.getMaritalStatus().equals(0L)) {
                statement.setNull(6, Types.INTEGER);
            } else {
                statement.setLong(6, val.getMaritalStatus());
            }

            if(val.getNationality() == null || val.getNationality().equals(0L)) {
                statement.setNull(7, Types.INTEGER);
            } else {
                statement.setLong(7, val.getNationality());
            }

            if( val.getCountry() == null || val.getCountry().equals(0L)) {
                statement.setNull(8, Types.INTEGER);
            } else {
                statement.setLong(8, val.getCountry());
            }
            statement.setString(9, val.getWebsite());
            statement.setString(10, val.getEmail());
            statement.setString(11, val.getPhotoPath());
            statement.setString(12, val.getJob());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertContactAddress(Contact val, Long contactId) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CONTACT_ADDRESS_QUERY)) {
            statement.setLong(1, contactId);
            statement.setString(2, val.getCity());
            statement.setString(3, val.getStreet());
            statement.setString(4, val.getHouseNumber());
            statement.setString(5, val.getFlat());
            statement.setString(6, val.getPostCode());

            statement.executeUpdate();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Long getLastInsertedId() {
        ResultSet resultSet;
        Long lastId = 0L;
        try (PreparedStatement statement = connection.prepareStatement(GET_LAST_ID)){
            resultSet = statement.executeQuery();
            lastId = resultSet.next() ? resultSet.getLong("last_id") : -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }

    @Override
    public Contact read(Long l) {
        Contact contact = new Contact();
        ResultSet contactResultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CONTACT_BY_ID)) {
            statement.setLong(1, l);
            contactResultSet = statement.executeQuery();
            while(contactResultSet.next()) {
                contact = (Contact) EntityFactory.createEntityFromResultSet(contactResultSet, Contact.class);
                contact.setPhones(getContactPhones(contact.getId()));
                contact.setAttachments(getContactAttachments(contact.getId()));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contact;
    }

    @Override
    public void update(Long l, Contact val) {

    }

    @Override
    public void delete(Long l) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT)){
            statement.setLong(1, l);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContactAddress(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CONTACTS_ADDRESS)){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Contact> searchUserByParameters(SearchParameters parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(buildQuery(parameters, PARAMS_QUERY));

        ResultSet contactResult = statement.executeQuery();
        ArrayList<Contact> contacts = new ArrayList<>();
        while (contactResult.next()) {
            Contact contact = new Contact();
            contact.setId(contactResult.getLong("id"));
            contact.setFirstName(contactResult.getString("first_name"));
            contact.setLastName(contactResult.getString("last_name"));
            contact.setPatronymic(contactResult.getString("patronymic"));
            contact.setBirthDate(contactResult.getDate("birth_date"));
            contact.setSex(contactResult.getString("sex"));
            contact.setEmail(contactResult.getString("email"));
            contact.setWebsite(contactResult.getString("website"));
            contact.setNationality(contactResult.getLong("nationality"));
            contact.setMaritalStatus(contactResult.getLong("marital_status"));
            contact.setJob(contactResult.getString("job"));
            contact.setCountry(contactResult.getLong("country"));
            contact.setCity(contactResult.getString("city"));
            contact.setStreet(contactResult.getString("street"));
            contact.setHouseNumber(contactResult.getString("house"));
            contact.setFlat(contactResult.getString("flat"));

            contacts.add(contact);
        }
        return contacts;
    }

    private String buildQuery(SearchParameters parameters, String query) {

        if (!parameters.getFirstName().isEmpty()) {
            query += " AND first_name LIKE '" + parameters.getFirstName() + "'";
        }
        if (!parameters.getLastName().isEmpty()) {
            query += " AND last_name LIKE '" + parameters.getLastName() + "'";
        }
        if (!parameters.getPatronymic().equals("")) {
            query += " AND patronymic LIKE '" + parameters.getPatronymic() + "'";
        }
        if (!parameters.getSex().equals("X")) {
            query += " AND sex = '" + parameters.getSex() + "'";
        }
        if (parameters.getMaritalStatus() != 0) {
            query += " AND marital_status LIKE '" + parameters.getMaritalStatus() + "'";
        }
        if (parameters.getNationality() != 0) {
            query += " AND nationality LIKE '" + parameters.getNationality() + "'";
        }
        if (parameters.getCountry() != 0) {
            query += " AND state LIKE '" + parameters.getCountry() + "'";
        }
        if (!parameters.getCity().isEmpty()) {
            query += " AND city LIKE '" + parameters.getCity() + "'";
        }
        if (!parameters.getCity().isEmpty()) {
            query += " AND house LIKE '" + parameters.getCity() + "'";
        }
        if (parameters.getFlat() != 0) {
            query += " AND flat LIKE '" + parameters.getFlat() + "';";
        }

        return query;
    }
}
