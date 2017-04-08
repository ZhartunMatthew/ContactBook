package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import com.zhartunmatthew.web.contactbook.dto.search.SearchCriteria;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;

public class SearchContactsCommand implements AbstractCommand {

    private final static String COMMAND_URL = "contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            SearchCriteria searchParameters = new SearchCriteria();

            searchParameters.setFirstName(trimIfNotNull(request.getParameter("first-name")));
            searchParameters.setLastName(trimIfNotNull(request.getParameter("last-name")));
            searchParameters.setPatronymic(trimIfNotNull(request.getParameter("patronymic")));

            String sex = request.getParameter("sex");
            searchParameters.setSex(sex != null && !sex.equals("X") ? sex : null);

            String maritalStatus = request.getParameter("marital-status");
            searchParameters.setMaritalStatus(maritalStatus == null ? 0 : Integer.parseInt(maritalStatus));

            String nationality = request.getParameter("nationality");
            searchParameters.setNationality((nationality == null ? 0 : Integer.parseInt(nationality)));

            String country = request.getParameter("country");
            searchParameters.setCountry(country == null ? 0 : Integer.parseInt(country));

            searchParameters.setPostcode(trimIfNotNull(request.getParameter("postcode")));
            searchParameters.setCity(trimIfNotNull(request.getParameter("city")));
            searchParameters.setStreet(trimIfNotNull(request.getParameter("street")));
            searchParameters.setHouse(trimIfNotNull(request.getParameter("house-number")));
            searchParameters.setFlat(trimIfNotNull(request.getParameter("flat")));

            String fromDay = request.getParameter("from-birth-date-day");
            String fromMonth = request.getParameter("from-birth-date-month");
            String fromYear = request.getParameter("from-birth-date-year");
            searchParameters.setFromDate(string2Date(fromDay, fromMonth, fromYear));

            String toDay = request.getParameter("to-birth-date-day");
            String toMonth = request.getParameter("to-birth-date-month");
            String toYear = request.getParameter("to-birth-date-year");
            searchParameters.setToDate(string2Date(toDay, toMonth, toYear));

            ContactService contactService = new ContactService();
            ArrayList<Contact> contacts = contactService.findAllByParameters(searchParameters);

            request.setAttribute("contacts", contacts);
            request.setAttribute("contactsCount", contacts != null ? contacts.size() : 0);
        } catch (ServiceException ex) {
            throw new CommandException("Can't execute command SearchContacts", ex);
        }
        return COMMAND_URL;
    }

    private Date string2Date(String day, String month, String year) throws CommandException {
        if (checkDate(day, month, year)) {
            DateTime dateTime = new DateTime();
            try {
                dateTime = dateTime.withDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
                return new Date(dateTime.getMillis());
            } catch (NumberFormatException ex){
                throw new CommandException("Can't parse date ", ex);
            }
        } else {
            return null;
        }
    }

    private boolean checkDate(String day, String month, String year) {
        return StringUtils.isNotEmpty(day) && StringUtils.isNotEmpty(month) && StringUtils.isNotEmpty(year);
    }

    private String trimIfNotNull(String value) {
        if(!StringUtils.isEmpty(value)) {
            return value.trim();
        } else {
            return null;
        }
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
