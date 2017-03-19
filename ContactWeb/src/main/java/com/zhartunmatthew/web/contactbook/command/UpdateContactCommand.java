package com.zhartunmatthew.web.contactbook.command;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UpdateContactCommand implements AbstractCommand {
    private static Logger log = Logger.getLogger(UpdateContactCommand.class);
    private final static String COMMAND_URL = "contact.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String param = request.getParameter("last-name");
        String id = request.getParameter("id");
        log.info("Last name: " + param + "Id = " + id);
        Contact contact = new Contact();
        contact.setLastName("Фамилия");
        contact.setFirstName("обновление");
        request.setAttribute("contact", contact);
        return COMMAND_URL;
    }
}
