package com.zhartunmatthew.web.contactbook.dao.daofactory;

import com.zhartunmatthew.web.contactbook.dao.AbstractDAO;
import com.zhartunmatthew.web.contactbook.dao.AttachmentDAO;
import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.PhoneDAO;
import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionManager;
import com.zhartunmatthew.web.contactbook.dbmanager.WrappedConnection;

public class DAOFactory {

    public static AbstractDAO getDAO(Class<? extends AbstractDAO> type) {
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
        return abstractDAO;
    }
}
