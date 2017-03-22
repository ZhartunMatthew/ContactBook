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
import java.util.ArrayList;

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
        try (ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class)) {
            contactDAO.insert(contact);
            lastId = contactDAO.getLastInsertedId();
            contactDAO.insertContactAddress(contact, lastId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.createDAO(PhoneDAO.class)) {
            ArrayList<Phone> phones = contact.getPhones();
            if(phones != null) {
                for (Phone phone : phones) {
                    phone.setContactID(lastId);
                    phoneDAO.insert(phone);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.createDAO(AttachmentDAO.class)) {
            ArrayList<Attachment> attachments = contact.getAttachments();
            if(attachments != null) {
                for (Attachment attachment : attachments) {
                    attachment.setContactID(lastId);
                    attachmentDAO.insert(attachment);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
