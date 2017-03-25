package com.zhartunmatthew.web.contactbook.command.showviewcommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.entity.Country;
import com.zhartunmatthew.web.contactbook.entity.MaritalStatus;
import com.zhartunmatthew.web.contactbook.entity.Nationality;
import com.zhartunmatthew.web.contactbook.services.UtilService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class ShowContactSearchCommand implements AbstractCommand {

    private final static String COMMAND_URL = "contact_search.jsp";

    @Override
    public String execute(HttpServletRequest request) {
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
