package com.zhartunmatthew.web.contactbook.emailmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmailManager {
    private String senderEmail = null;
    private String senderPassword = null;
    private Session session = null;
    private final static Logger LOG = LoggerFactory.getLogger(EmailManager.class);

    public EmailManager() {
        senderEmail = ResourceBundle.getBundle("emailconfig").getObject("sender_name").toString();
        senderPassword = ResourceBundle.getBundle("emailconfig").getObject("sender_password").toString();
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }

    public void sendMail(String email, String subject, String text) throws MessagingException {
        LOG.info("Sending mail. to: {} subject: {} text: {}", email, subject, text);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }
}
