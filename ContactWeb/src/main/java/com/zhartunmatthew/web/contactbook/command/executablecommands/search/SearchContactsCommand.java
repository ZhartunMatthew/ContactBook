package com.zhartunmatthew.web.contactbook.command.executablecommands.search;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.dto.search.DateSearchType;
import com.zhartunmatthew.web.contactbook.dto.search.SearchParameters;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SearchContactsCommand implements AbstractCommand {

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

        searchParameters.setPostcode(request.getParameter("postcode"));
        searchParameters.setCity(request.getParameter("city"));
        searchParameters.setStreet(request.getParameter("street"));
        searchParameters.setHouse(request.getParameter("house-number"));
        searchParameters.setFlat(request.getParameter("flat"));

        String dateDay = request.getParameter("birth-date-day");
        String dateMonth = request.getParameter("birth-date-month");
        String dateYear = request.getParameter("birth-date-year");
        if(!StringUtils.isEmpty(dateDay) &&
                !StringUtils.isEmpty(dateMonth) && !StringUtils.isEmpty(dateYear)) {
            searchParameters.setDate(stringToDate(dateDay, dateMonth, dateYear));
            searchParameters.setDateSearchType(
                    DateSearchType.getType(Integer.parseInt(request.getParameter("date-type"))));
        }
        ContactService contactService = new ContactService();
        ArrayList<Contact> contacts = contactService.findAllByParameters(searchParameters);

        request.setAttribute("contacts", contacts);
        request.setAttribute("contactsCount", contacts != null ? contacts.size() : 0);
        return COMMAND_URL;
    }

    private Date stringToDate(String day, String month, String year) {
        Integer iDay = Integer.parseInt(day);
        Integer iMonth = Integer.parseInt(month);
        Integer iYear = Integer.parseInt(year);

        String dateString = iYear.toString();
        dateString += iMonth < 10 ? "-0" + iMonth.toString() : "-" + iMonth.toString();
        dateString += iDay < 10 ? "-0" + iDay.toString() : "-" + iDay.toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = null;
        try {
            if(!dateString.isEmpty()) {
                sqlDate = new java.sql.Date(dateFormat.parse(dateString).getTime());
            } else {
                sqlDate = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sqlDate;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
