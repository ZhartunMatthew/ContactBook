package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.MainHandler;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ResourceBundle;

public class UpdateContactCommand implements AbstractCommand {
    private static Logger log = Logger.getLogger(UpdateContactCommand.class);
    private final static String COMMAND_URL = "/controller?command=show_contact&contact_id=";
    private final static String PROPERTIES_PATH = "directories";
    private static ResourceBundle resBundle = ResourceBundle.getBundle(PROPERTIES_PATH);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        MainHandler mainHandler = new MainHandler();
        mainHandler.handleInputs(request);

        Contact contact = (Contact) request.getAttribute("contact");
        FileItem photoItem = (FileItem) request.getAttribute("photo-item");

        if (photoItem != null && !photoItem.getName().isEmpty()) {
            String photoPath = resBundle.getString("images-directory") + "image_" + contact.getId();
            File photoFile = new File(photoPath);
            contact.setPhotoPath(photoPath);
            try {
                photoItem.write(photoFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(photoItem == null && contact.getPhotoPath() == null) {
                contact.setPhotoPath(null);
            }
        }

        ContactService contactService = new ContactService();
        contactService.updateContact(contact);

        return COMMAND_URL + contact.getId();
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}