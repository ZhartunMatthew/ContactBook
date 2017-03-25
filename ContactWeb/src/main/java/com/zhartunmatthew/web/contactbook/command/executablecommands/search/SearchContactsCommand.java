package com.zhartunmatthew.web.contactbook.command.executablecommands.search;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.search.SearchParameters;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class SearchContactsCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(SearchContactsCommand.class);
    private final static String COMMAND_URL = "contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        SearchParameters searchParameters = new SearchParameters();

        searchParameters.setFirstName(request.getParameter("first-name").trim());
        searchParameters.setLastName(request.getParameter("last-name").trim());
        searchParameters.setPatronymic(request.getParameter("patronymic").trim());
        searchParameters.setSex(request.getParameter("sex").trim());
        searchParameters.setMaritalStatus(Integer.parseInt(request.getParameter("marital-status").trim()));
        searchParameters.setNationality(Integer.parseInt(request.getParameter("nationality").trim()));
        searchParameters.setCountry(Integer.parseInt(request.getParameter("country").trim()));
        searchParameters.setCity(request.getParameter("city"));
//        searchParameters.setHouse(Integer.parseInt(request.getParameter("house")));
//        searchParameters.setFlat(Integer.parseInt(request.getParameter("flat")));

        ContactService contactService = new ContactService();
        ArrayList<Contact> contacts = contactService.findAllByParameters(searchParameters);

        request.setAttribute("contacts", contacts);
        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
