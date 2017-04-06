package com.zhartunmatthew.web.contactbook.emailmanager;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class SendEmailJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(SendEmailJob.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle("emailconfig");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            ContactService contactService = new ContactService();
            ArrayList<Contact> birthdayContacts = contactService.getContactsByBirthDate();
            String recipient = bundle.getObject("admin_email").toString();
            String subject = "Birthdays";
            String message;
            if (birthdayContacts == null || birthdayContacts.size() == 0) {
                message = bundle.getObject("no_birthday_email").toString();
            } else {
                message = prepareMessage(birthdayContacts);
            }
            EmailManager emailManager = new EmailManager();
            emailManager.sendMail(recipient, subject, message);
        } catch (ServiceException ex) {
            LOG.error("Error in SendMailJob", ex);
        }
    }

    private String prepareMessage(ArrayList<Contact> birthdayContacts) {
        String message = bundle.getObject("birthday_email").toString() + "\n";
        for(Contact contact : birthdayContacts) {
            message += contact.getFirstName() + " ";
            message += contact.getLastName() + " ";
            if(!StringUtils.isEmpty(contact.getEmail())) {
                message += contact.getEmail();
            }
            message += "\n";
        }
        return message;
    }
}
