package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.MainHandler;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class AddContactCommand implements AbstractCommand {

    private static String REDIRECT_URL = "controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        MainHandler mainHandler = new MainHandler();
        mainHandler.handleInputs(request);

        Contact contact = (Contact) request.getAttribute("contact");
        FileItem photoItem = (FileItem) request.getAttribute("photo-item");
        ArrayList<FileItem> fileItems = (ArrayList<FileItem>) request.getAttribute("files");

        ContactService contactService = new ContactService();
        contactService.insertContact(contact, photoItem, fileItems);

        return REDIRECT_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
