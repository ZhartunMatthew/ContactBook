package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.entity.Nationality;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NationalityDAO extends AbstractDAO<Long, Nationality> {

    private static String SELECT_ALL =
            "SELECT nationality.id_nationality, nationality.nationality_name FROM nationality";

    private static String SELECT =
            "SELECT nationality.id_nationality, nationality.nationality_name FROM nationality WHERE id_nationality = ?";

    public NationalityDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Nationality> readAll() throws DAOException {
        ArrayList<Nationality> nationalities = new ArrayList<>();

        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Nationality nationality = new Nationality();
                nationality.setId(resultSet.getLong("id_nationality"));
                nationality.setName(resultSet.getString("nationality_name"));
                nationalities.add(nationality);
            }
        } catch (Exception ex) {
            throw new DAOException("Can't read all nationalities", ex);
        }
        return nationalities;
    }

    @Override
    public void insert(Nationality val) throws DAOException {

    }

    @Override
    public Nationality read(Long aLong) throws DAOException {
        Nationality nationality = null;
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT)) {
            statement.setLong(1, aLong);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                nationality = new Nationality();
                nationality.setId(resultSet.getLong("id_nationality"));
                nationality.setName(resultSet.getString("nationality_name"));
            }
        } catch (Exception ex) {
            throw new DAOException("Can't read all nationality by id", ex);
        }
        return nationality;
    }

    @Override
    public void update(Long aLong, Nationality val) throws DAOException {

    }

    @Override
    public void delete(Long aLong) throws DAOException {

    }
}
