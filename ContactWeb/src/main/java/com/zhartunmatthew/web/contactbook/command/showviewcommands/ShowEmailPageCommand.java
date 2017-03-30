package com.zhartunmatthew.web.contactbook.command.showviewcommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.DeleteContactCommand;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowEmailPageCommand implements AbstractCommand {
    private static Logger log = Logger.getLogger(DeleteContactCommand.class);
    private static String COMMAND_URL = "send_email.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ContactService contactService = new ContactService();
        String[] items = request.getParameterValues("contact-check");
        ArrayList<Contact> recipients = new ArrayList<>();
        if(items != null) {
            for(String val : items) {
                Long id = Long.parseLong(val);
                Contact contact = contactService.getContactById(id);
                if(contact != null) {
                    if(!StringUtils.isEmpty(contact.getEmail())) {
                        recipients.add(contact);
                    }
                }
                log.info("Checked: " + Integer.parseInt(val));
            }
        } else {
            log.info("NO CHECKED ITEMS");
        }
        request.setAttribute("recipients", recipients);

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
