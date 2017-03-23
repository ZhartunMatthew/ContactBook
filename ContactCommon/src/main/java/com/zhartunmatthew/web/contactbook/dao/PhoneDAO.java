package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.entityfactory.EntityFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhoneDAO extends AbstractDAO<Long, Phone> {

    private Logger log = Logger.getLogger(PhoneDAO.class);

    private static final String SELECT_CONTACT_PHONES =
            "SELECT phone_id AS id, " +
            "contact_id, " +
            "type AS phone_type, " +
            "country_code, " +
            "operator_code, " +
            "phone_number AS number, " +
            "comment " +
            "FROM contactbook.phones " +
            "WHERE phones.contact_id = ?";

    private static final String INSERT_CONTACT_PHONES =
            "INSERT INTO phones (contact_id, country_code, operator_code, phone_number, comment, type)" +
            "VALUES (?, ?, ?, ?, ?, ?);";

    private static final String DELETE_PHONE =
            "DELETE FROM phones WHERE contact_id = ?";

    public PhoneDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<Phone> readContactPhones(Long contactId) {
        ArrayList<Phone> phones = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet phoneResultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_CONTACT_PHONES);
            statement.setLong(1, contactId);
            phoneResultSet = statement.executeQuery();
            while(phoneResultSet.next()) {
                Phone phone = (Phone) EntityFactory.createEntityFromResultSet(phoneResultSet, Phone.class);
                phones.add(phone);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return phones;
    }

    @Override
    public ArrayList<Phone> readAll() {
        return null;
    }

    @Override
    public void insert(Phone val) {

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_CONTACT_PHONES);

            statement.setLong(1, val.getContactID());
            statement.setString(2, val.getCountryCode());
            statement.setString(3, val.getOperatorCode());
            statement.setString(4, val.getNumber());
            statement.setString(5, val.getComment());
            statement.setInt(6, val.getType());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Phone read(Long id) {
        return null;
    }

    @Override
    public void update(Long id, Phone val) {

    }

    @Override
    public void delete(Long id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_PHONE);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
