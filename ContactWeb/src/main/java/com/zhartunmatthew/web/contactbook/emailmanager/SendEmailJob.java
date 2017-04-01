package com.zhartunmatthew.web.contactbook.emailmanager;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class SendEmailJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ContactService contactService = new ContactService();
        ArrayList<Contact> birthdayContacts = contactService.getContactsByBirthDate();
        if(birthdayContacts == null || birthdayContacts.size() == 0) {
            return;
        }
        String recipient = ResourceBundle.getBundle("emailconfig").getObject("admin_email").toString();
        String subject = "Birthdays";
        String message = prepareMessage(birthdayContacts);
        EmailManager emailManager = new EmailManager();
        emailManager.sendMail(recipient, subject, message);
    }

    private String prepareMessage(ArrayList<Contact> birthdayContacts) {
        String message = ResourceBundle.getBundle("emailconfig").getObject("birthday_email").toString() + "\n";
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
