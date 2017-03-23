package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.entity.Country;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CountryDAO  extends AbstractDAO<Long, Country> {

    private static String SELECT_ALL = "SELECT countries.id_country, countries.country_name FROM countries";

    public CountryDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Country> readAll() {
        ArrayList<Country> countries = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                Country country = new Country();
                country.setId(resultSet.getLong("id_country"));
                country.setName(resultSet.getString("country_name"));
                countries.add(country);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return countries;
    }

    @Override
    public void insert(Country val) {

    }

    @Override
    public Country read(Long aLong) {
        return null;
    }

    @Override
    public void update(Long aLong, Country val) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
