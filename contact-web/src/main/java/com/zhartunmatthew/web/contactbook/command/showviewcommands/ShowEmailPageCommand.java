package com.zhartunmatthew.web.contactbook.command.showviewcommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import com.zhartunmatthew.web.contactbook.emailmanager.EmailTemplateManager;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowEmailPageCommand implements AbstractCommand {

    private final static Logger LOG = LoggerFactory.getLogger(ShowEmailPageCommand.class);
    private String COMMAND_URL = "send_email.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ContactService contactService = new ContactService();
            String[] items = request.getParameterValues("contact-check");
            ArrayList<Contact> recipients = new ArrayList<>();
            EmailTemplateManager templateManager = new EmailTemplateManager();
            if (items != null) {
                for (String val : items) {
                    Long id = Long.parseLong(val);
                    Contact contact = contactService.getContactById(id);
                    if (contact != null) {
                        if (!StringUtils.isEmpty(contact.getEmail())) {
                            recipients.add(contact);
                        }
                    }
                }
            } else {
                LOG.info("NO CHECKED ITEMS");
            }
            request.setAttribute("recipientsCount", recipients.size());
            request.setAttribute("recipients", recipients);
            request.setAttribute("templates", templateManager.getAllTemplates());
        } catch (ServiceException ex) {
            throw new CommandException("Can't execute command ShowEmailPage", ex);
        }

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
