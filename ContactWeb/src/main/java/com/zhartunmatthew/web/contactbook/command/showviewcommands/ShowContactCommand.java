package com.zhartunmatthew.web.contactbook.command.showviewcommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.entity.Country;
import com.zhartunmatthew.web.contactbook.entity.MaritalStatus;
import com.zhartunmatthew.web.contactbook.entity.Nationality;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.UtilService;
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

        if(!Objects.isNull(id)) {
            ContactService contactService = new ContactService();
            request.setAttribute("contact", contactService.getContactById(Long.parseLong(id)));
        }

        UtilService utilService = new UtilService();
        ArrayList<MaritalStatus> maritalStatuses = utilService.getMaritalStatuses();
        ArrayList<Nationality> nationalities = utilService.getNationalities();
        ArrayList<Country> countries = utilService.getCountries();

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
