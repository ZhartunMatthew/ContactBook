package com.zhartunmatthew.web.contactbook.command;

import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.CountryDAO;
import com.zhartunmatthew.web.contactbook.dao.MaritalStatusDAO;
import com.zhartunmatthew.web.contactbook.dao.NationalityDAO;
import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Country;
import com.zhartunmatthew.web.contactbook.entity.MaritalStatus;
import com.zhartunmatthew.web.contactbook.entity.Nationality;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Objects;

public class ShowContactCommand implements AbstractCommand {

    private Logger log = Logger.getLogger(ShowContactCommand.class);
    private final static String COMMAND_URL = "contact.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String id = request.getParameter("contact_id");
        Contact contact;
        if(!Objects.isNull(id)) {
            try (ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class)) {
                contact = contactDAO.read(Long.parseLong(id));
                request.setAttribute("contact", contact);
            }
        }
            ArrayList<MaritalStatus> maritalStatuses;
            try (MaritalStatusDAO maritalStatusDAO = (MaritalStatusDAO) DAOFactory.createDAO(MaritalStatusDAO.class)) {
                maritalStatuses = maritalStatusDAO.readAll();
            }

            ArrayList<Nationality> nationalities;
            try (NationalityDAO nationalityDAO = (NationalityDAO) DAOFactory.createDAO(NationalityDAO.class)) {
                nationalities = nationalityDAO.readAll();
            }

            ArrayList<Country> countries;
            try (CountryDAO countryDAO = (CountryDAO) DAOFactory.createDAO(CountryDAO.class)) {
                countries = countryDAO.readAll();
            }

            request.setAttribute("nationalities", nationalities);
            request.setAttribute("countries", countries);
            request.setAttribute("martialStatuses", maritalStatuses);

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
