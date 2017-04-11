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
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SendEmailJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(SendEmailJob.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle("emailconfig");
    private STGroup stg;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            ContactService contactService = new ContactService();
            ArrayList<Contact> birthdayContacts = contactService.getContactsByBirthDate();
            stg = new STGroupFile(bundle.getObject("template_file_path").toString());

            String recipient = bundle.getObject("admin_email").toString();
            String subject = "Birthdays";
            String message;
            if (birthdayContacts == null || birthdayContacts.size() == 0) {
                message = stg.getInstanceOf("no_birthday_notification").render();
            } else {
                message = prepareMessage(birthdayContacts);
            }
            EmailManager emailManager = new EmailManager();
            emailManager.sendMail(recipient, subject, message);
        } catch (ServiceException | MessagingException ex) {
            LOG.error("Error in SendMailJob", ex);
        }
    }

    private String prepareMessage(ArrayList<Contact> birthdayContacts) {
        String message = stg.getInstanceOf("birthday_notification").render();
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
