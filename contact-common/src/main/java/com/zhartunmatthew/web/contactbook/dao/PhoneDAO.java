package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.entityfactory.EntityFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhoneDAO extends AbstractDAO<Long, Phone> {

    private static final String SELECT_CONTACT_PHONES =
        "SELECT phone_id AS id, contact_id, type AS phone_type, " +
        "country_code, operator_code, phone_number AS number, " +
        "comment FROM phones WHERE phones.contact_id = ?";

    private static final String INSERT_CONTACT_PHONES =
        "INSERT INTO phones (contact_id, country_code, " +
        "operator_code, phone_number, comment, type)" +
        "VALUES (?, ?, ?, ?, ?, ?);";

    private static final String DELETE_CONTACT_PHONE =
        "DELETE FROM phones WHERE contact_id = ?";

    private static final String UPDATE_PHONE =
        "UPDATE phones SET country_code = ?, " +
        "operator_code = ?, phone_number = ?, " +
        "type = ?, comment = ? WHERE phone_id = ?";

    private static final String DELETE_PHONE =
            "DELETE FROM phones WHERE phone_id = ?";

    public PhoneDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<Phone> readByContactId(Long contactId) throws DAOException {
        ArrayList<Phone> phones = new ArrayList<>();
        ResultSet phoneResultSet;
        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_CONTACT_PHONES)) {
            statement.setLong(1, contactId);
            phoneResultSet = statement.executeQuery();
            while (phoneResultSet.next()) {
                Phone phone = (Phone)
                        EntityFactory.createEntityFromResultSet(phoneResultSet, Phone.class);
                phones.add(phone);
            }
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't read phones by contact id = %d", contactId), ex);
        }
        return phones;
    }

    @Override
    public ArrayList<Phone> readAll() throws DAOException {
        return null;
    }

    @Override
    public void insert(Phone val) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CONTACT_PHONES)) {
            statement.setLong(1, val.getContactID());
            statement.setString(2, val.getCountryCode());
            statement.setString(3, val.getOperatorCode());
            statement.setString(4, val.getNumber());
            statement.setString(5, val.getComment());
            statement.setInt(6, val.getType());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            throw new DAOException("Can't insert new phone", ex);
        }
    }

    @Override
    public Phone read(Long id) throws DAOException {
        return null;
    }

    @Override
    public void update(Long id, Phone val) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PHONE)) {
            statement.setString(1, val.getCountryCode());
            statement.setString(2, val.getOperatorCode());
            statement.setString(3, val.getNumber());
            statement.setInt(4, val.getType());
            statement.setString(5, val.getComment());
            statement.setLong(6, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't update phone with id = %d", id), ex);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PHONE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't delete phone by id = %d", id), ex);
        }
    }

    public void deleteByContactId(Long id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT_PHONE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't delete phones by contact id = %d", id), ex);
        }
    }
}
