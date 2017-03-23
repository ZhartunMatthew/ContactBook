package com.zhartunmatthew.web.contactbook.dao.daofactory;

import com.zhartunmatthew.web.contactbook.dao.*;
import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionManager;

import java.sql.Connection;

public class DAOFactory {

    public static AbstractDAO createDAO(Class<? extends AbstractDAO> type) {
        AbstractDAO abstractDAO = null;
        ConnectionManager manager = ConnectionManager.getInstance();
        Connection connection = manager.getConnection();

        if(type == AttachmentDAO.class) {
            abstractDAO = new AttachmentDAO(connection);
        }

        if(type == ContactDAO.class) {
            abstractDAO = new ContactDAO(connection);
        }

        if(type == PhoneDAO.class) {
            abstractDAO = new PhoneDAO(connection);
        }

        if(type == MaritalStatusDAO.class) {
            abstractDAO = new MaritalStatusDAO(connection);
        }

        if(type == NationalityDAO.class) {
            abstractDAO = new NationalityDAO(connection);
        }

        if(type == CountryDAO.class) {
            abstractDAO = new CountryDAO(connection);
        }

        return abstractDAO;
    }
}
