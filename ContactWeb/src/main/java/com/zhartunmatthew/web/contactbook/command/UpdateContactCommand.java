package com.zhartunmatthew.web.contactbook.command;

import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.jsonbuilder.JSONBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class UpdateContactCommand implements AbstractCommand {
    private static Logger log = Logger.getLogger(UpdateContactCommand.class);
    private final static String COMMAND_URL = "contact.jsp";

    @Override
    public String execute(HttpServletRequest request) {

        String jsonPhones = request.getParameter("old-phones");
        ArrayList<Phone> oldPhones = JSONBuilder.buildPhoneListFromJSON(jsonPhones);

        for (Phone phone : oldPhones) {
            log.info("Old: " + phone);
        }

        String newJsonPhones = request.getParameter("new-phones");
        ArrayList<Phone> newPhones = JSONBuilder.buildPhoneListFromJSON(newJsonPhones);
        for (Phone phone : newPhones) {
            log.info("New: " + phone);
        }

        String jsonAttachments = request.getParameter("old-attachments");
        ArrayList<Attachment> oldAttachments = JSONBuilder.buildAttachmentListFromJSON(jsonAttachments);

        for (Attachment attachment : oldAttachments) {
            log.info("Old: " + attachment);
        }

        String newJsonAttachments = request.getParameter("new-attachments");
        ArrayList<Attachment> newAttachments = JSONBuilder.buildAttachmentListFromJSON(newJsonAttachments);
        for (Attachment attachment : newAttachments) {
            log.info("New: " + attachment);
        }

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
