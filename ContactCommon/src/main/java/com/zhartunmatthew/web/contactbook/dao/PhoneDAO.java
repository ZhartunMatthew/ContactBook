package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dbmanager.WrappedConnection;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.entityfactory.EntityFactory;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            "WHERE phones.contact_id = ";

    public PhoneDAO(WrappedConnection connection) {
        super(connection);
    }

    public ArrayList<Phone> readContactPhones(Long contactId) {
        ArrayList<Phone> phones = new ArrayList<>();
        Statement statement = null;
        ResultSet phoneResultSet = null;
        try {
            statement = connection.createStatement();
            phoneResultSet = statement.executeQuery(SELECT_CONTACT_PHONES + contactId.longValue() + ";");
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

    }

    @Override
    public Phone read(Long aLong) {
        return null;
    }

    @Override
    public void update(Long aLong, Phone val) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
