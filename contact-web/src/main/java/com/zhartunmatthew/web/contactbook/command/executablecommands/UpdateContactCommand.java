package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.MainHandler;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class UpdateContactCommand implements AbstractCommand {

    private final static String COMMAND_URL = "controller?command=show_contact&contact_id=";
    private final static Logger LOG = LoggerFactory.getLogger(UpdateContactCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Contact contact = null;
        try {
            MainHandler mainHandler = new MainHandler();
            mainHandler.handleInputs(request);

            contact = (Contact) request.getAttribute("contact");
            FileItem photoItem = (FileItem) request.getAttribute("photo-item");

            ArrayList<FileItem> fileItems = (ArrayList<FileItem>) request.getAttribute("files");
            LOG.info("Updating contact {}", contact);

            ContactService contactService = new ContactService();
            contactService.updateContact(contact, photoItem, fileItems);
        } catch (ServiceException ex) {
            throw new CommandException("Can't execute command UpdateContact", ex);
        }

        return COMMAND_URL + contact.getId();
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}