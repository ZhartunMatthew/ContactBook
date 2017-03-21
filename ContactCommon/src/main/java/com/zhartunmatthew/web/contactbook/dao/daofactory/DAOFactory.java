package com.zhartunmatthew.web.contactbook.dao.daofactory;

import com.zhartunmatthew.web.contactbook.dao.*;
import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionManager;
import com.zhartunmatthew.web.contactbook.dbmanager.WrappedConnection;

public class DAOFactory {

    public static AbstractDAO createDAO(Class<? extends AbstractDAO> type) {
        AbstractDAO abstractDAO = null;
        WrappedConnection connection = ConnectionManager.getConnection();

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


        return abstractDAO;
    }
}
