package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import com.zhartunmatthew.web.contactbook.emailmanager.EmailManager;
import com.zhartunmatthew.web.contactbook.emailmanager.EmailTemplateManager;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class SendEmailCommand implements AbstractCommand {

    private final static Logger LOG = LoggerFactory.getLogger(SendEmailCommand.class);
    private String REDIRECT_URL = "controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String emailText = request.getParameter("email-text");
            if(emailText != null) {
                emailText = emailText.trim();
            }
            String emailSubject = request.getParameter("email-subject").trim();
            LOG.debug("Email text: {} Email subject: {}", emailText, emailSubject);
            ArrayList<Contact> recipients = getAllRecipients(request);
            int emailTemplate = Integer.parseInt(request.getParameter("selected-template-index"));
            sendEmails(recipients, emailTemplate, emailSubject, emailText);

            if(recipients.size() > 0) {
                String allRecipients = "";
                for (Contact contact : recipients) {
                    allRecipients += "[" + contact.getFirstName() + " ";
                    allRecipients += contact.getLastName() + "] ";
                }
                request.getSession().setAttribute("action-name", "Письмо было отправлено");
                request.getSession().setAttribute("action-description", "Получатели: " + allRecipients);
            } else {
                request.getSession().setAttribute("action-name", "Письмо не было отправлено");
                request.getSession().setAttribute("action-description", "Ошибка отправки");
            }

        } catch (ServiceException | MessagingException ex) {
            request.getSession().setAttribute("action-name", "Письмо не было отправлено");
            request.getSession().setAttribute("action-description",
                    "Ошибка подключеня к почтовому серверу. Повторите позже");
            LOG.error("Can't send mail. Could not connect to SMTP host");
        }
        return REDIRECT_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }

    private ArrayList<Contact> getAllRecipients(HttpServletRequest request) throws ServiceException {
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
            LOG.error("Error in get recipients:", ex);
        }

        return recipients;
    }

    private void sendEmails(ArrayList<Contact> recipients, int emailTemplate, String subject, String message)
            throws MessagingException {
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
