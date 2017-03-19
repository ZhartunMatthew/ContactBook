package com.zhartunmatthew.web.contactbook.command;

import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.pagination.Pagination;
import com.zhartunmatthew.web.contactbook.pagination.PaginationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class ShowContactListCommand implements AbstractCommand {

    private final static String COMMAND_URL = "contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request) {

        int offset = PaginationManager.getOffset(request);
        int contactsPerPage = PaginationManager.getLimit();
        ArrayList<Contact> contacts;
        try (ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class)) {
            contacts = contactDAO.readCertainCount(offset, contactsPerPage);
        }

        Pagination pagination = new Pagination();
        pagination.setActivePage(PaginationManager.getActivePage(request));
        pagination.setPageCount(PaginationManager.getPageCount());

        request.setAttribute("contacts", contacts);
        request.setAttribute("pagination", pagination);
        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
