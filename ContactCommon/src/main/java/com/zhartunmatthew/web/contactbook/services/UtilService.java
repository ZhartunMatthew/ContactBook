package com.zhartunmatthew.web.contactbook.services;

import com.zhartunmatthew.web.contactbook.dao.CountryDAO;
import com.zhartunmatthew.web.contactbook.dao.MaritalStatusDAO;
import com.zhartunmatthew.web.contactbook.dao.NationalityDAO;
import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionUtils;
import com.zhartunmatthew.web.contactbook.entity.Country;
import com.zhartunmatthew.web.contactbook.entity.MaritalStatus;
import com.zhartunmatthew.web.contactbook.entity.Nationality;

import java.sql.Connection;
import java.util.ArrayList;

public class UtilService {

    public UtilService() {}

    public ArrayList<MaritalStatus> getMaritalStatuses() {
        ArrayList<MaritalStatus> maritalStatuses = null;
        try (Connection connection = ConnectionUtils.getConnection()){
            MaritalStatusDAO maritalStatusDAO = (MaritalStatusDAO)
                    DAOFactory.createDAO(MaritalStatusDAO.class, connection);
            maritalStatuses = maritalStatusDAO.readAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return maritalStatuses;
    }

    public ArrayList<Nationality> getNationalities() {
        ArrayList<Nationality> nationalities = null;
        try(Connection connection = ConnectionUtils.getConnection()) {
            NationalityDAO maritalStatusDAO = (NationalityDAO)
                    DAOFactory.createDAO(NationalityDAO.class, connection);
            nationalities = maritalStatusDAO.readAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nationalities;
    }

    public ArrayList<Country> getCountries() {
        ArrayList<Country> countries = null;
        try(Connection connection = ConnectionUtils.getConnection()) {
            CountryDAO maritalStatusDAO = (CountryDAO)
                    DAOFactory.createDAO(CountryDAO.class, connection);
            countries = maritalStatusDAO.readAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return countries;
    }
}
