package com.zhartunmatthew.web.contactbook.command.showviewcommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.pagination.Pagination;
import com.zhartunmatthew.web.contactbook.pagination.PaginationManager;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowContactListCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(ShowContactCommand.class);
    private final static String COMMAND_URL = "contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        ContactService contactService = new ContactService();
        long count = contactService.getContactCount();

        PaginationManager paginationManager = new PaginationManager(request, count);
        Pagination pagination = paginationManager.getPagination();

        int offset = paginationManager.getOffset();
        int contactsPerPage = paginationManager.getLimit();

        ArrayList<Contact> contacts = contactService.getCertainCount(offset, contactsPerPage);

        log.info("SHOW CONTACT LIST");
        log.info("ACTIVE PAGE = " + pagination.getActivePage());
        log.info("PAGE COUNT = " + pagination.getPageCount());
        log.info("OFFSET = " + offset);
        log.info("CONTACT PER PAGE = " + contactsPerPage);

        request.setAttribute("contacts", contacts);
        request.setAttribute("pagination", pagination);

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
