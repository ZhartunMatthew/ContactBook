package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.MainHandler;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddContactCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(AddContactCommand.class);
    private final static String COMMAND_URL = "contact.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        MainHandler mainHandler = new MainHandler();
        mainHandler.handleInputs(request);

        Contact contact = (Contact) request.getAttribute("contact");
        log.info(contact);

        ContactService contactService = new ContactService();
        contactService.insertContact(contact);

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
