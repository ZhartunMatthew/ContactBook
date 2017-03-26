package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.entity.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CountryDAO  extends AbstractDAO<Long, Country> {

    private static String SELECT_ALL = "SELECT countries.id_country, countries.country_name FROM countries";

    public CountryDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Country> readAll() throws DAOException {
        ArrayList<Country> countries = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Country country = new Country();
                country.setId(resultSet.getLong("id_country"));
                country.setName(resultSet.getString("country_name"));
                countries.add(country);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return countries;
    }

    @Override
    public void insert(Country val) throws DAOException {

    }

    @Override
    public Country read(Long aLong) throws DAOException {
        return null;
    }

    @Override
    public void update(Long aLong, Country val) throws DAOException {

    }

    @Override
    public void delete(Long aLong) throws DAOException {

    }
}
