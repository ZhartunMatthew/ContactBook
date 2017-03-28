package com.zhartunmatthew.web.contactbook.services;

import com.zhartunmatthew.web.contactbook.dao.CountryDAO;
import com.zhartunmatthew.web.contactbook.dao.MaritalStatusDAO;
import com.zhartunmatthew.web.contactbook.dao.NationalityDAO;
import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionUtils;
import com.zhartunmatthew.web.contactbook.entity.Country;
import com.zhartunmatthew.web.contactbook.entity.MaritalStatus;
import com.zhartunmatthew.web.contactbook.entity.Nationality;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtilService {

    private static Logger log = Logger.getLogger(UtilService.class);

    public UtilService() {
    }

    public ArrayList<MaritalStatus> getMaritalStatuses() {
        ArrayList<MaritalStatus> maritalStatuses = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            MaritalStatusDAO maritalStatusDAO = new MaritalStatusDAO(connection);
            maritalStatuses = maritalStatusDAO.readAll();
        } catch (SQLException | DAOException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return maritalStatuses;
    }

    public ArrayList<Nationality> getNationalities() {
        ArrayList<Nationality> nationalities = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            NationalityDAO maritalStatusDAO = new NationalityDAO(connection);
            nationalities = maritalStatusDAO.readAll();
        } catch (SQLException | DAOException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return nationalities;
    }

    public ArrayList<Country> getCountries() {
        ArrayList<Country> countries = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            CountryDAO maritalStatusDAO = new CountryDAO(connection);
            countries = maritalStatusDAO.readAll();
        } catch (SQLException | DAOException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return countries;
    }
}
