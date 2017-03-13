package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.dbmanager.WrappedConnection;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.entityfactory.EntityFactory;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ContactDAO extends AbstractDAO<Long, Contact> {

    private static Logger log = Logger.getLogger(ContactDAO.class);

    private static final String SELECT_ALL_CONTACTS =
            "SELECT contacts.id AS id, first_name, last_name, patronymic, " +
            "birth_date, sex, marital_status.marital_status_name AS marital_status, " +
            "nationality.nationality_name AS nationality, " +
            "countries.country_name AS country, addresses.city AS city, " +
            "addresses.street AS street, addresses.house_number AS house, " +
            "addresses.flat AS flat, addresses.postcode AS postcode, " +
            "website, email, photo_path, job " +
            "FROM contacts " +
            "LEFT JOIN nationality ON nationality.id_nationality = contacts.nationality_id " +
            "LEFT JOIN marital_status ON marital_status.id_marital_status = contacts.marital_status_id " +
            "LEFT JOIN countries ON countries.id_counrty = contacts.country_id " +
            "LEFT JOIN addresses ON addresses.contact_id = contacts.id;";

    public ContactDAO(WrappedConnection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Contact> readAll() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Statement statement = null;
        ResultSet contactResultSet = null;
        try {
            statement = connection.createStatement();
            contactResultSet = statement.executeQuery(SELECT_ALL_CONTACTS);
            while(contactResultSet.next()) {
                Contact tempContact = (Contact) EntityFactory.createEntityFromResultSet(contactResultSet, Contact.class);

                tempContact.setPhones(getContactPhones(tempContact.getId()));

                contacts.add(tempContact);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return contacts;
    }

    private ArrayList<Phone> getContactPhones(long i) {
        ArrayList<Phone> phones = null;
        try {
            PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.getDAO(PhoneDAO.class);
            phones = phoneDAO.readContactPhones(i);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return phones;
    }

    @Override
    public void insert(Contact val) {

    }

    @Override
    public Contact read(Long l) {
        return null;
    }

    @Override
    public void update(Long l, Contact val) {

    }

    @Override
    public void delete(Long l) {

    }
}
