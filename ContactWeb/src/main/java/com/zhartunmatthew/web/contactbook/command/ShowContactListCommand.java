package com.zhartunmatthew.web.contactbook.command;

import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.entity.Contact;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class ShowContactListCommand implements AbstractCommand {

    private final static String COMMAND_URL = "contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        ArrayList<Contact> contacts;
        try (ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class)) {
            contacts = contactDAO.readAll();
        }
        request.setAttribute("contacts", contacts);
        return COMMAND_URL;
    }
}
