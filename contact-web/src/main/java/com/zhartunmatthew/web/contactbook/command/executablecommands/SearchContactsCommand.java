package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import com.zhartunmatthew.web.contactbook.dto.search.SearchCriteria;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;

public class SearchContactsCommand implements AbstractCommand {

    private final static String COMMAND_URL = "contact_list.jsp";
    private final static Logger LOG = LoggerFactory.getLogger(SearchContactsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            SearchCriteria criteria = new SearchCriteria();

            criteria.setFirstName(trimIfNotNull(request.getParameter("first-name")));
            criteria.setLastName(trimIfNotNull(request.getParameter("last-name")));
            criteria.setPatronymic(trimIfNotNull(request.getParameter("patronymic")));

            String sex = request.getParameter("sex");
            criteria.setSex(sex != null && !sex.equals("X") ? sex : null);

            String maritalStatus = request.getParameter("marital-status");
            criteria.setMaritalStatus(maritalStatus == null ? 0 : Integer.parseInt(maritalStatus));

            String nationality = request.getParameter("nationality");
            criteria.setNationality((nationality == null ? 0 : Integer.parseInt(nationality)));

            String country = request.getParameter("country");
            criteria.setCountry(country == null ? 0 : Integer.parseInt(country));

            criteria.setPostcode(trimIfNotNull(request.getParameter("postcode")));
            criteria.setCity(trimIfNotNull(request.getParameter("city")));
            criteria.setStreet(trimIfNotNull(request.getParameter("street")));
            criteria.setHouse(trimIfNotNull(request.getParameter("house-number")));
            criteria.setFlat(trimIfNotNull(request.getParameter("flat")));

            String fromDay = request.getParameter("from-birth-date-day");
            String fromMonth = request.getParameter("from-birth-date-month");
            String fromYear = request.getParameter("from-birth-date-year");
            criteria.setFromDate(string2Date(fromDay, fromMonth, fromYear));

            String toDay = request.getParameter("to-birth-date-day");
            String toMonth = request.getParameter("to-birth-date-month");
            String toYear = request.getParameter("to-birth-date-year");
            criteria.setToDate(string2Date(toDay, toMonth, toYear));

            LOG.info("Searching contacts with criteria {}", criteria.toArray());

            ContactService contactService = new ContactService();
            ArrayList<Contact> contacts = contactService.findAllByParameters(criteria);

            request.setAttribute("contacts", contacts);
            request.setAttribute("criteria", criteria.toArray());
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
                dateTime = dateTime.withDate(Integer.parseInt(year),
                        Integer.parseInt(month), Integer.parseInt(day));
                return new Date(dateTime.getMillis());
            } catch (NumberFormatException ex){
                throw new CommandException("Can't parse date ", ex);
            }
        } else {
            return null;
        }
    }

    private boolean checkDate(String day, String month, String year) {
        return  StringUtils.isNotEmpty(day) &&
                StringUtils.isNotEmpty(month) &&
                StringUtils.isNotEmpty(year);
    }

    private String trimIfNotNull(String value) {
        if(!StringUtils.isEmpty(value)) {
            return value.trim();
        } else {
            return null;
        }
    }
}
