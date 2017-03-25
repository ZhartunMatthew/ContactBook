package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.entity.Nationality;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NationalityDAO extends AbstractDAO<Long, Nationality> {

    private static String SELECT_ALL =
            "SELECT nationality.id_nationality, nationality.nationality_name FROM nationality";

    public NationalityDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Nationality> readAll() {
        ArrayList<Nationality> nationalities = new ArrayList<>();

        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL) ){
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Nationality nationality = new Nationality();
                nationality.setId(resultSet.getLong("id_nationality"));
                nationality.setName(resultSet.getString("nationality_name"));
                nationalities.add(nationality);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nationalities;
    }

    @Override
    public void insert(Nationality val) {

    }

    @Override
    public Nationality read(Long aLong) {
        return null;
    }

    @Override
    public void update(Long aLong, Nationality val) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
