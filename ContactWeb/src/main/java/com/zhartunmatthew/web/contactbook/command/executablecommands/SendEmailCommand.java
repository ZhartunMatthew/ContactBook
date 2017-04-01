package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.emailmanager.EmailManager;
import com.zhartunmatthew.web.contactbook.command.showviewcommands.ShowContactCommand;
import com.zhartunmatthew.web.contactbook.emailmanager.EmailTemplateManager;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class SendEmailCommand implements AbstractCommand {

    private Logger log = Logger.getLogger(ShowContactCommand.class);
    private String REDIRECT_URL = "controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String emailText = request.getParameter("email-text");
        String emailSubject = request.getParameter("email-subject");
        ArrayList<Contact> recipients = getAllRecipients(request);
        int emailTemplate = Integer.parseInt(request.getParameter("selected-template-index"));
        sendEmails(recipients, emailTemplate, emailSubject, emailText);
        return REDIRECT_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }

    private ArrayList<Contact> getAllRecipients(HttpServletRequest request) {
        ArrayList<Contact> recipients = new ArrayList<>();
        try {
            ContactService contactService = new ContactService();
            for (String idString : request.getParameterValues("recipient-id")) {
                Contact contact = contactService.getContactById(Long.parseLong(idString));
                if (!StringUtils.isEmpty(contact.getEmail())) {
                    recipients.add(contact);
                }
            }
        } catch (NumberFormatException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }

        return recipients;
    }

    private void sendEmails(ArrayList<Contact> recipients, int emailTemplate, String subject, String message) {
        EmailTemplateManager templateManager = new EmailTemplateManager();
        EmailManager emailManager = new EmailManager();
        for(Contact tempRecipient : recipients) {
            if(emailTemplate == 0) {
                emailManager.sendMail(tempRecipient.getEmail(), subject, message);
            } else {
                String templateMessage = templateManager.createEmailFromTemplate(emailTemplate, tempRecipient);
                emailManager.sendMail(tempRecipient.getEmail(), subject, templateMessage);
            }
        }
    }
}
