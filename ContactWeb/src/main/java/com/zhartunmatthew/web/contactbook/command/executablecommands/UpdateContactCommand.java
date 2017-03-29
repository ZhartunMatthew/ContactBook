package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.MainHandler;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class UpdateContactCommand implements AbstractCommand {
    private static Logger log = Logger.getLogger(UpdateContactCommand.class);
    private final static String COMMAND_URL = "controller?command=show_contact&contact_id=";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        MainHandler mainHandler = new MainHandler();
        mainHandler.handleInputs(request);

        Contact contact = (Contact) request.getAttribute("contact");
        FileItem photoItem = (FileItem) request.getAttribute("photo-item");
        ArrayList<FileItem> fileItems = (ArrayList<FileItem>) request.getAttribute("files");
        ContactService contactService = new ContactService();
        contactService.updateContact(contact, photoItem, fileItems);

        return COMMAND_URL + contact.getId();
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}