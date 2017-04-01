package com.zhartunmatthew.web.contactbook.emailmanager;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

class SendEmailJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ContactService contactService = new ContactService();
        Date date = new Date(DateTime.now().getMillis());
        ArrayList<Contact> birthdayContacts = contactService.getContactsByBirthDate(date);
        if(birthdayContacts == null || birthdayContacts.size() == 0) {
            return;
        }
        String recipient = ResourceBundle.getBundle("emailconfig").getObject("admin_email").toString();
        String subject = "Birthdays";
        String message = prepareMessage(birthdayContacts);
        EmailManager.sendMail(recipient, subject, message);
    }

    private String prepareMessage(ArrayList<Contact> birthdayContacts) {
        String message = ResourceBundle.getBundle("emailconfig").getObject("birthaday_email").toString() + "\n";
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
