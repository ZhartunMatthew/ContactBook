package com.zhartunmatthew.web.contactbook.command;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddContactCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(AddContactCommand.class);
    private final static String COMMAND_URL = "contact.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String param = request.getParameter("last-name");
        log.info("Last name: " + param);
        Contact contact = new Contact();
        contact.setLastName("Фамилия");
        contact.setFirstName("добавление");
        request.setAttribute("contact", contact);
        return COMMAND_URL;
    }
}
