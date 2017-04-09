package com.zhartunmatthew.web.contactbook.command.showviewcommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Country;
import com.zhartunmatthew.web.contactbook.entity.MaritalStatus;
import com.zhartunmatthew.web.contactbook.entity.Nationality;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.UtilService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import com.zhartunmatthew.web.contactbook.validation.ValidationUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShowContactCommand implements AbstractCommand {

    private final static String COMMAND_URL = "contact.jsp";
    private final static String REDIRECTED_URL = "controller";
    private boolean isRedirected;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            isRedirected = false;
            String id = request.getParameter("contact_id");
            if (!Objects.isNull(id)) {
                if (!ValidationUtils.isNumber(id)) {
                    isRedirected = true;
                    return REDIRECTED_URL;
                }
                ContactService contactService = new ContactService();
                Contact contact = contactService.getContactById(Long.parseLong(id));
                if (contact.getId() == null) {
                    isRedirected = true;
                    return REDIRECTED_URL;
                }
                request.setAttribute("contact", contact);
                if (contact.getBirthDate() != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(contact.getBirthDate());
                    request.setAttribute("day", calendar.get(Calendar.DAY_OF_MONTH));
                    request.setAttribute("month", calendar.get(Calendar.MONTH) + 1);
                    request.setAttribute("year", calendar.get(Calendar.YEAR));
                }
                String path = ResourceBundle.getBundle("directories").getString("files-directory") +
                        "contact_" + contact.getId() + File.separator;
                if (contact.getPhotoPath() != null && !contact.getPhotoPath().isEmpty()) {
                    request.setAttribute("contactPhoto", path + contact.getPhotoPath());
                }
            }

            UtilService utilService = new UtilService();
            ArrayList<MaritalStatus> maritalStatuses = utilService.getMaritalStatuses();
            ArrayList<Nationality> nationalities = utilService.getNationalities();
            ArrayList<Country> countries = utilService.getCountries();

            request.setAttribute("nationalities", nationalities);
            request.setAttribute("countries", countries);
            request.setAttribute("martialStatuses", maritalStatuses);
        } catch (ServiceException ex) {
            throw new CommandException("Can't execute command ShowContact", ex);
        }

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return isRedirected;
    }
}
