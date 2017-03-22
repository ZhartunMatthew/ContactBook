package com.zhartunmatthew.web.contactbook.command;

import com.zhartunmatthew.web.contactbook.dao.AttachmentDAO;
import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.PhoneDAO;
import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.handler.MainHandler;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddContactCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(AddContactCommand.class);
    private final static String COMMAND_URL = "contact.jsp";

    @Override
    public String execute(HttpServletRequest request) {

        MainHandler mainHandler = new MainHandler();
        mainHandler.handleInputs(request);

        Contact contact = (Contact) request.getAttribute("contact");
        log.info(contact);

        createNewContact(contact);

        return COMMAND_URL;
    }

    private void createNewContact(Contact contact) {
        Long lastId = 0L;
        try (ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class)){
            contactDAO.insert(contact);
            lastId = contactDAO.getLastInsertedId();
            contactDAO.insertContactAddress(contact, lastId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.createDAO(PhoneDAO.class)) {
            for (Phone phone : contact.getPhones()) {
                phone.setContactID(lastId);
                phoneDAO.insert(phone);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.createDAO(AttachmentDAO.class)){
            for (Attachment attachment : contact.getAttachments()) {
                attachment.setContactID(lastId);
                attachmentDAO.insert(attachment);
            }
        }

    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
