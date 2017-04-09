package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.MainHandler;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class AddContactCommand implements AbstractCommand {

    private static String REDIRECT_URL = "controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            MainHandler mainHandler = new MainHandler();
            mainHandler.handleInputs(request);

            Contact contact = (Contact) request.getAttribute("contact");
            FileItem photoItem = (FileItem) request.getAttribute("photo-item");
            ArrayList<FileItem> fileItems = (ArrayList<FileItem>) request.getAttribute("files");

            ContactService contactService = new ContactService();
            contactService.insertContact(contact, photoItem, fileItems);

            request.getSession().setAttribute("action-name", "Создан новый контакт");
            request.getSession().setAttribute("action-description",
                    String.format("Контакт '%s %s' был создан", contact.getLastName(), contact.getFirstName()) );

        } catch (ServiceException ex) {
            throw new CommandException("Can't execute command AddContact", ex);
        }

        return REDIRECT_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
