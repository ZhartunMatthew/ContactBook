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

        log.info("ID = " + request.getParameter("id"));
        log.info("Last name = " + request.getParameter("last-name"));

        String jsonPhones = request.getParameter("old-phones");
        if(jsonPhones != null) {
            ArrayList<Phone> oldPhones = JSONBuilder.buildPhoneListFromJSON(jsonPhones);

            for (Phone phone : oldPhones) {
                log.info("Old: " + phone);
            }
        } else {
            log.info("OLD PHONES NULL");
        }

        String newJsonPhones = request.getParameter("new-phones");
        if(newJsonPhones != null) {
            ArrayList<Phone> newPhones = JSONBuilder.buildPhoneListFromJSON(newJsonPhones);
            for (Phone phone : newPhones) {
                log.info("New: " + phone);
            }
        } else {
            log.info("NEW PHONES NULL");
        }

        String jsonAttachments = request.getParameter("old-attachments");

        if(jsonAttachments != null) {
            ArrayList<Attachment> oldAttachments = JSONBuilder.buildAttachmentListFromJSON(jsonAttachments);
            for (Attachment attachment : oldAttachments) {
                log.info("Old: " + attachment);
            }
        } else {
            log.info("OLD ATTACH NULL");
        }

        String newJsonAttachments = request.getParameter("new-attachments");
        if(newJsonAttachments != null) {
            ArrayList<Attachment> newAttachments = JSONBuilder.buildAttachmentListFromJSON(newJsonAttachments);
            for (Attachment attachment : newAttachments) {
                log.info("New: " + attachment);
            }
        } else {
            log.info("NEW ATTACHMENT NULL");
        }

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
