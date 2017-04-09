package com.zhartunmatthew.web.contactbook.command.showviewcommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import com.zhartunmatthew.web.contactbook.entity.Country;
import com.zhartunmatthew.web.contactbook.entity.MaritalStatus;
import com.zhartunmatthew.web.contactbook.entity.Nationality;
import com.zhartunmatthew.web.contactbook.services.UtilService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowContactSearchCommand implements AbstractCommand {

    private final static String COMMAND_URL = "contact_search.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UtilService utilService = new UtilService();
            ArrayList<MaritalStatus> maritalStatuses = utilService.getMaritalStatuses();
            ArrayList<Nationality> nationalities = utilService.getNationalities();
            ArrayList<Country> countries = utilService.getCountries();

            request.setAttribute("nationalities", nationalities);
            request.setAttribute("countries", countries);
            request.setAttribute("martialStatuses", maritalStatuses);
        } catch (ServiceException ex) {
            throw new CommandException("Can't execute command ShowContactSearch", ex);
        }
        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
