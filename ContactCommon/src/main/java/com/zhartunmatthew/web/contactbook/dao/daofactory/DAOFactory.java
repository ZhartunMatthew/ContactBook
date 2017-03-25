package com.zhartunmatthew.web.contactbook.dao.daofactory;

import com.zhartunmatthew.web.contactbook.dao.*;

import java.sql.Connection;

public class DAOFactory {

    public static AbstractDAO createDAO(Class<? extends AbstractDAO> type, Connection connection) {
        AbstractDAO abstractDAO = null;

        if(type == AttachmentDAO.class) {
            abstractDAO = new AttachmentDAO(connection);
            return abstractDAO;
        }

        if(type == ContactDAO.class) {
            abstractDAO = new ContactDAO(connection);
            return abstractDAO;
        }

        if(type == PhoneDAO.class) {
            abstractDAO = new PhoneDAO(connection);
            return abstractDAO;
        }

        if(type == MaritalStatusDAO.class) {
            abstractDAO = new MaritalStatusDAO(connection);
            return abstractDAO;
        }

        if(type == NationalityDAO.class) {
            abstractDAO = new NationalityDAO(connection);
            return abstractDAO;
        }

        if(type == CountryDAO.class) {
            abstractDAO = new CountryDAO(connection);
            return abstractDAO;
        }

        return abstractDAO;
    }
}
