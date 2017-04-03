package com.zhartunmatthew.web.contactbook.command.showviewcommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.emailmanager.EmailTemplateManager;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowEmailPageCommand implements AbstractCommand {
    private Logger log = Logger.getLogger(ShowEmailPageCommand.class);
    private String COMMAND_URL = "send_email.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ContactService contactService = new ContactService();
        String[] items = request.getParameterValues("contact-check");
        ArrayList<Contact> recipients = new ArrayList<>();
        EmailTemplateManager templateManager = new EmailTemplateManager();
        if(items != null) {
            for(String val : items) {
                Long id = Long.parseLong(val);
                Contact contact = contactService.getContactById(id);
                if(contact != null) {
                    if(!StringUtils.isEmpty(contact.getEmail())) {
                        recipients.add(contact);
                    }
                }
            }
        } else {
            log.info("NO CHECKED ITEMS");
        }
        request.setAttribute("recipientsCount", recipients.size());
        request.setAttribute("recipients", recipients);
        request.setAttribute("templates", templateManager.getAllTemplates());

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
