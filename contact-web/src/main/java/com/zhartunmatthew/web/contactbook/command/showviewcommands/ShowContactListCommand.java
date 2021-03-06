package com.zhartunmatthew.web.contactbook.command.showviewcommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.pagination.Pagination;
import com.zhartunmatthew.web.contactbook.pagination.PaginationManager;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class ShowContactListCommand implements AbstractCommand {

    private final static String COMMAND_URL = "contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ContactService contactService = new ContactService();
            long count = contactService.getContactCount();

            PaginationManager paginationManager = new PaginationManager(request, count);
            Pagination pagination = paginationManager.getPagination();
            int offset = paginationManager.getOffset();
            int contactsPerPage = paginationManager.getLimit();

            ArrayList<Contact> contacts = contactService.getCertainCount(offset, contactsPerPage);
            request.setAttribute("contacts", contacts);
            request.setAttribute("pagination", pagination);
            HttpSession session = request.getSession();
            String actionName = (String) session.getAttribute("action-name");
            String actionDescription = (String) session.getAttribute("action-description");
            if(actionName != null && actionDescription != null) {
                session.removeAttribute("action-name");
                session.removeAttribute("action-description");
            }
            request.setAttribute("actionName", actionName);
            request.setAttribute("actionDescription", actionDescription);

        } catch (ServiceException | NumberFormatException ex) {
            throw new CommandException("Can't execute command ShowContactList", ex);
        }

        return COMMAND_URL;
    }
}
