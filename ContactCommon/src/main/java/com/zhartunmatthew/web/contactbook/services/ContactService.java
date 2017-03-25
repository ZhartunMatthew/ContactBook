package com.zhartunmatthew.web.contactbook.services;


import com.zhartunmatthew.web.contactbook.dao.AttachmentDAO;
import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.PhoneDAO;
import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionUtils;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.search.SearchParameters;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactService {

    public ContactService() {}

    public void insertContact(Contact contact) {
        Long lastId;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contactDAO.insert(contact);
            lastId = contactDAO.getLastInsertedId();
            contactDAO.insertContactAddress(contact, lastId);

            PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.createDAO(PhoneDAO.class, connection);
            ArrayList<Phone> phones = contact.getPhones();
            if(phones != null) {
                for (Phone phone : phones) {
                    phone.setContactID(lastId);
                    phoneDAO.insert(phone);
                }
            }

            AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.createDAO(AttachmentDAO.class, connection);
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

    public void deleteContacts(ArrayList<Long> items) {
        try (Connection connection = ConnectionUtils.getConnection()) {
            AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.createDAO(AttachmentDAO.class, connection);
            PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.createDAO(PhoneDAO.class, connection);
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            for(Long id : items) {
                attachmentDAO.deleteContactAttachment(id);
                phoneDAO.deleteContactPhones(id);
                contactDAO.deleteContactAddress(id);
                contactDAO.delete(id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contacts = contactDAO.readAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return contacts;
    }

    public Contact getContactById(Long id) {
        Contact contact = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contact = contactDAO.read(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return contact;
    }

    public Long getContactCount() {
        Long count = 0L;
        try(Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            count = contactDAO.getContactCount();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public ArrayList<Contact> getCertainCount(int from, int limit) {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()){
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contacts = contactDAO.readCertainCount(from, limit);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return contacts;
    }

    public ArrayList<Contact> findAllByParameters(SearchParameters parameters) {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()){
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contacts = contactDAO.searchUserByParameters(parameters);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return contacts;
    }
}
