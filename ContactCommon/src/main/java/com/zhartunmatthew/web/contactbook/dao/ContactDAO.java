package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.dto.search.SearchParameters;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.entityfactory.EntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class ContactDAO extends AbstractDAO<Long, Contact> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ContactDAO.class);

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

    private static final String SELECT_BY_BIRTH_DATE =
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
            "LEFT JOIN addresses ON addresses.contact_id = contacts.id " +
            "WHERE MONTH(birth_date) = MONTH(CURRENT_DATE()) AND DAY(birth_date) = DAY(CURRENT_DATE());";

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

    private static final String UPDATE_CONTACT_PHOTO = "UPDATE contacts SET photo_path = ? WHERE id = ?;";

    private static final String DELETE_CONTACT = "DELETE FROM contacts WHERE id = ?";

    private static final String DELETE_CONTACTS_ADDRESS = "DELETE FROM addresses WHERE contact_id = ?";

    private static final String GET_COUNT = "SELECT COUNT(*) AS count FROM contacts";

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
            "LEFT JOIN addresses ON addresses.contact_id = contacts.id WHERE TRUE";

    private static final String UPDATE_CONTACT =
            "UPDATE contacts SET " +
            "first_name = ?, last_name = ?, patronymic = ?, birth_date = ?, sex = ?, marital_status_id = ?, " +
            "nationality_id = ?, website = ?, email = ?, photo_path = ?, job = ?, country_id = ? WHERE id = ?";

    private static final String UPDATE_CONTACT_ADDRESS =
            "UPDATE addresses SET city = ?, street = ?, house_number = ?, flat = ?, postcode = ? WHERE contact_id = ?";

    public ContactDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Contact> readAll() throws DAOException {
        ArrayList<Contact> contacts = new ArrayList<>();
        ResultSet contactResultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CONTACTS)) {
            contactResultSet = statement.executeQuery();
            while (contactResultSet.next()) {
                Contact tempContact = (Contact)
                        EntityFactory.createEntityFromResultSet(contactResultSet, Contact.class);
                tempContact.setPhones(getContactPhones(tempContact.getId()));
                tempContact.setAttachments(getContactAttachments(tempContact.getId()));
                contacts.add(tempContact);
            }
        } catch (SQLException ex) {
            throw new DAOException("Can't read all contacts", ex);
        }
        return contacts;
    }

    private ArrayList<Phone> getContactPhones(long i) throws DAOException {
        ArrayList<Phone> phones = null;
        PhoneDAO phoneDAO = new PhoneDAO(connection);
        try {
            phones = phoneDAO.readByContactId(i);
        } catch (Exception ex) {
            throw new DAOException(String.format("Can't get Phones with contact id = %d", i), ex);
        }
        return phones;
    }

    private ArrayList<Attachment> getContactAttachments(long i) throws DAOException {
        ArrayList<Attachment> attachments = null;
        AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
        try {
            attachments = attachmentDAO.readByContactId(i);
        } catch (Exception ex) {
            throw new DAOException(String.format("Can't get Attachments with contact id = %d", i), ex);
        }
        return attachments;
    }

    public ArrayList<Contact> readCertainCount(long from, long count) throws DAOException {
        ArrayList<Contact> contacts = new ArrayList<>();
        ResultSet contactResultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CERTAIN_COUNT)) {
            statement.setLong(1, count);
            statement.setLong(2, from);
            contactResultSet = statement.executeQuery();
            while (contactResultSet.next()) {
                Contact tempContact = (Contact)
                        EntityFactory.createEntityFromResultSet(contactResultSet, Contact.class);
                tempContact.setPhones(getContactPhones(tempContact.getId()));
                tempContact.setAttachments(getContactAttachments(tempContact.getId()));
                contacts.add(tempContact);
            }
        } catch (SQLException ex) {
            throw new DAOException("Can't read certain count of contacts", ex);
        }
        return contacts;
    }

    public Long getContactCount() throws DAOException {
        ResultSet resultSet;
        Long count = 0L;
        try (PreparedStatement statement = connection.prepareStatement(GET_COUNT)) {
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Can't get contact count", ex);
        }
        return count;
    }

    @Override
    public void insert(Contact val) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CONTACT_QUERY)) {
            statement.setString(1, val.getFirstName());
            statement.setString(2, val.getLastName());
            statement.setString(3, val.getPatronymic());

            if (val.getBirthDate() == null) {
                statement.setNull(4, Types.DATE);
            } else {
                statement.setDate(4, val.getBirthDate());
            }

            statement.setString(5, val.getSex());
            if (val.getMaritalStatus() == null || val.getMaritalStatus().equals(0L)) {
                statement.setNull(6, Types.INTEGER);
            } else {
                statement.setLong(6, val.getMaritalStatus());
            }

            if (val.getNationality() == null || val.getNationality().equals(0L)) {
                statement.setNull(7, Types.INTEGER);
            } else {
                statement.setLong(7, val.getNationality());
            }

            if (val.getCountry() == null || val.getCountry().equals(0L)) {
                statement.setNull(8, Types.INTEGER);
            } else {
                statement.setLong(8, val.getCountry());
            }
            statement.setString(9, val.getWebsite());
            statement.setString(10, val.getEmail());
            statement.setString(11, val.getPhotoPath());
            statement.setString(12, val.getJob());

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Can't insert new contact", ex);
        }
    }

    public void insertContactAddress(Contact val, Long contactId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CONTACT_ADDRESS_QUERY)) {
            statement.setLong(1, contactId);
            statement.setString(2, val.getCity());
            statement.setString(3, val.getStreet());
            statement.setString(4, val.getHouseNumber());
            statement.setString(5, val.getFlat());
            statement.setString(6, val.getPostCode());

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Cant insert Address", ex);
        }
    }

    public Long getLastInsertedId() throws DAOException {
        ResultSet resultSet;
        Long lastId = 0L;
        try (PreparedStatement statement = connection.prepareStatement(GET_LAST_ID)) {
            resultSet = statement.executeQuery();
            lastId = resultSet.next() ? resultSet.getLong("last_id") : -1;
        } catch (SQLException ex) {
            throw new DAOException("Can't get last id", ex);
        }
        return lastId;
    }

    @Override
    public Contact read(Long l) throws DAOException {
        Contact contact = new Contact();
        ResultSet contactResultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CONTACT_BY_ID)) {
            statement.setLong(1, l);
            contactResultSet = statement.executeQuery();
            while (contactResultSet.next()) {
                contact = (Contact) EntityFactory.createEntityFromResultSet(contactResultSet, Contact.class);
                contact.setPhones(getContactPhones(contact.getId()));
                contact.setAttachments(getContactAttachments(contact.getId()));
            }
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't read contact with id = %d", l), ex);
        }
        return contact;
    }

    @Override
    public void update(Long l, Contact val) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT)) {
            statement.setString(1, val.getFirstName());
            statement.setString(2, val.getLastName());
            statement.setString(3, val.getPatronymic());
            statement.setDate(4, val.getBirthDate());
            stringSetter(statement, 5, val.getSex());
            longSetter(statement, 6, val.getMaritalStatus());
            longSetter(statement, 7, val.getNationality());
            statement.setString(8, val.getWebsite());
            statement.setString(9, val.getEmail());
            statement.setString(10, val.getPhotoPath());
            statement.setString(11, val.getJob());
            longSetter(statement, 12, val.getCountry());
            statement.setLong(13, l);
            statement.executeUpdate();
            updateContactAddress(l, val);
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't update contact with id = %d", l), ex);
        }
    }

    private void longSetter(PreparedStatement statement, int index, Long value) throws SQLException {
        if(value == null) {
            statement.setNull(index, Types.INTEGER);
        } else {
            statement.setLong(index, value);
        }
    }

    private void stringSetter(PreparedStatement statement, int index, String value) throws SQLException {
        if(value == null) {
            statement.setNull(index, Types.VARCHAR);
        } else {
            statement.setString(index, value);
        }
    }

    private void updateContactAddress(Long l, Contact val) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_ADDRESS)) {
            statement.setString(1, val.getCity());
            statement.setString(2, val.getStreet());
            statement.setString(3, val.getHouseNumber());
            statement.setString(4, val.getFlat());
            statement.setString(5, val.getPostCode());
            statement.setLong(6, val.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't update Address with id = %d", l), ex);
        }
    }

    public void updateContactPhoto(Long id, String photoPath) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_PHOTO)) {
            statement.setString(1, photoPath);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't update contact photo with id = %d", id), ex);
        }
    }

    @Override
    public void delete(Long l) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT)) {
            statement.setLong(1, l);
            statement.execute();
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't delete contact with id = %d", l), ex);
        }
    }

    public void deleteContactAddress(Long id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CONTACTS_ADDRESS)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't delete contact address with id = %d", id), ex);
        }
    }

    public ArrayList<Contact> searchUserByParameters(SearchParameters parameters) throws DAOException, SQLException {
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
        query += buildStringQueryPart("first_name", parameters.getFirstName());
        query += buildStringQueryPart("last_name", parameters.getLastName());
        query += buildStringQueryPart("patronymic", parameters.getPatronymic());
        query += buildSexQueryPart(parameters.getSex());
        query += buildIntQueryPart("marital_status.id_marital_status", parameters.getMaritalStatus());
        query += buildIntQueryPart("nationality.id_nationality", parameters.getNationality());
        query += buildIntQueryPart("countries.id_country", parameters.getCountry());
        query += buildStringQueryPart("postcode", parameters.getPostcode());
        query += buildStringQueryPart("city", parameters.getCity());
        query += buildStringQueryPart("street", parameters.getStreet());
        query += buildStringQueryPart("house_number", parameters.getHouse());
        query += buildStringQueryPart("flat", parameters.getFlat());
        query += buildDateQueryPart(parameters.getFromDate(), ">=");
        query += buildDateQueryPart(parameters.getToDate(), "<=");
        query += " ORDER BY last_name;";

        LOGGER.info(query);

        return query;
    }

    public ArrayList<Contact> readByBirthDate() throws DAOException {
        ArrayList<Contact> contacts = new ArrayList<>();
        ResultSet contactResultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_BIRTH_DATE)) {
            contactResultSet = statement.executeQuery();
            while (contactResultSet.next()) {
                Contact tempContact = (Contact)
                        EntityFactory.createEntityFromResultSet(contactResultSet, Contact.class);
                contacts.add(tempContact);
            }
        } catch (SQLException ex) {
            throw new DAOException("Can't get contact by birth date", ex);
        }
        return contacts;
    }

    private String buildStringQueryPart(String parameter, String value) {
        return value != null && !value.isEmpty() ? String.format(" AND %s LIKE '%s'", parameter, value) : "";
    }

    private String buildSexQueryPart(String value) {
        return value != null && !value.equals("X") ? String.format(" AND sex = '%s'", value) : "";
    }

    private String buildIntQueryPart(String parameter, int value) {
        if(value != 0) {
            return String.format(" AND %s LIKE '%d'", parameter, value);
        } else {
            return "";
        }
    }

    private String buildDateQueryPart(Date date, String sign) {
        if(date != null) {
            return " AND birth_date " + sign + "'" + date + "'";
        } else {
            return "";
        }
    }
}
