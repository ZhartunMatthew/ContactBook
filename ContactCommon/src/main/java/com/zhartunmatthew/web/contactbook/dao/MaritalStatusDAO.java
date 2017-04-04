package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.entity.MaritalStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MaritalStatusDAO extends AbstractDAO<Long, MaritalStatus> {

    private static String SELECT_ALL = "SELECT id_marital_status, marital_status_name FROM marital_status";

    public MaritalStatusDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<MaritalStatus> readAll() throws DAOException {
        ArrayList<MaritalStatus> maritalStatuses = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MaritalStatus maritalStatus = new MaritalStatus();
                maritalStatus.setId(resultSet.getInt("id_marital_status"));
                maritalStatus.setName(resultSet.getString("marital_status_name"));
                maritalStatuses.add(maritalStatus);
            }
        } catch (Exception ex) {
            throw new DAOException("Can't read all marital statuses", ex);
        }
        return maritalStatuses;
    }

    @Override
    public void insert(MaritalStatus val) throws DAOException {

    }

    @Override
    public MaritalStatus read(Long id) throws DAOException {
        return null;
    }

    @Override
    public void update(Long id, MaritalStatus val) throws DAOException {

    }

    @Override
    public void delete(Long id) throws DAOException {

    }
}
