package com.zhartunmatthew.web.contactbook.command.executablecommands.search;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.search.SearchParameters;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class SearchContactsCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(SearchContactsCommand.class);
    private final static String COMMAND_URL = "contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        SearchParameters searchParameters = new SearchParameters();

        searchParameters.setFirstName(request.getParameter("first-name"));
        searchParameters.setLastName(request.getParameter("last-name"));
        searchParameters.setPatronymic(request.getParameter("patronymic"));
        searchParameters.setSex(request.getParameter("sex"));

        String maritalStatus = request.getParameter("marital-status");
        searchParameters.setMaritalStatus(maritalStatus == null ? 0 : Integer.parseInt(maritalStatus));

        String nationality = request.getParameter("nationality");
        searchParameters.setNationality((nationality == null ? 0 : Integer.parseInt(nationality)));

        String country = request.getParameter("country");
        searchParameters.setCountry(country == null ? 0 : Integer.parseInt(country));
        searchParameters.setCity(request.getParameter("city"));

        String house = request.getParameter("house-number");
        searchParameters.setHouse(house == null || house.isEmpty() ? 0 : Integer.parseInt(house));

        String flat = request.getParameter("flat");
        searchParameters.setFlat(flat == null || flat.isEmpty() ? 0 : Integer.parseInt(flat));

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
