package com.zhartunmatthew.web.contactbook.emailmanager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmailManager {
    private static String senderEmail =
            ResourceBundle.getBundle("emailconfig").getObject("sender_name").toString();
    private static String senderPassword =
            ResourceBundle.getBundle("emailconfig").getObject("sender_password").toString();

    public static void sendMail(String email, String subject, String text) {
        System.out.println(">>>>>>>>>>EMAIL: " +  email);
        System.out.println(">>>>>>>>>>SUBJECT: " +  subject);
        System.out.println(">>>>>>>>>>TEXT: " +  text);

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
