package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.emailsender.EmailSender;
import com.zhartunmatthew.web.contactbook.command.showviewcommands.ShowContactCommand;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class SendEmailCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(ShowContactCommand.class);
    private static String REDIRECT_URL = "controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String emailText = request.getParameter("email-text");
        String emailSubject = request.getParameter("email-subject");
        ArrayList<Contact> recipients = getAllRecipients(request);
        sendEmails(recipients, emailSubject, emailText);

        return REDIRECT_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }

    private static ArrayList<Contact> getAllRecipients(HttpServletRequest request) {
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

    private static void sendEmails(ArrayList<Contact> recipients, String subject, String message) {
        EmailSender emailSender = new EmailSender();
        for(Contact tempRecipient : recipients) {
            emailSender.sendMail(tempRecipient.getEmail(), subject, message);
        }
    }
}
