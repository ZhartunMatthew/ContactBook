package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.entity.MaritalStatus;

import java.sql.*;
import java.util.ArrayList;

public class MaritalStatusDAO extends AbstractDAO <Long, MaritalStatus> {

    private static String SELECT_ALL = "SELECT id_marital_status, marital_status_name FROM marital_status";

    public MaritalStatusDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<MaritalStatus> readAll() {
        ArrayList<MaritalStatus> maritalStatuses = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)){
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MaritalStatus maritalStatus = new MaritalStatus();
                maritalStatus.setId(resultSet.getInt("id_marital_status"));
                maritalStatus.setName(resultSet.getString("marital_status_name"));
                maritalStatuses.add(maritalStatus);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return maritalStatuses;
    }

    @Override
    protected void insert(MaritalStatus val) {

    }

    @Override
    protected MaritalStatus read(Long id) {
        return null;
    }

    @Override
    protected void update(Long id, MaritalStatus val) {

    }

    @Override
    protected void delete(Long id) {

    }
}
