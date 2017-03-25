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
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ContactService {

    private static Logger logger = Logger.getLogger(ContactService.class);

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
        } catch (SQLException ex) {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        return contacts;
    }

    public Contact getContactById(Long id) {
        Contact contact = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contact = contactDAO.read(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contact;
    }

    public Long getContactCount() {
        Long count = 0L;
        try(Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            count = contactDAO.getContactCount();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public ArrayList<Contact> getCertainCount(int from, int limit) {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()){
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contacts = contactDAO.readCertainCount(from, limit);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contacts;
    }

    public ArrayList<Contact> findAllByParameters(SearchParameters parameters) {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()){
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contacts = contactDAO.searchUserByParameters(parameters);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contacts;
    }

    public void updateContact(Contact contact) {
        try (Connection connection = ConnectionUtils.getConnection()){
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contactDAO.update(contact.getId(), contact);
            logger.info(contact.getPhones());
            updatePhones(contact.getId(), contact.getPhones());
            updateAttachments(contact.getId(), contact.getAttachments());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updatePhones(Long id, ArrayList<Phone> phones) {
        ArrayList<Phone> phonesFromDB;
        try(Connection connection = ConnectionUtils.getConnection()) {
            PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.createDAO(PhoneDAO.class, connection);
            phonesFromDB = phoneDAO.readContactPhones(id);
            for (Phone phone : phones) {
                if(phone.getPhoneID() == null) {
                    logger.info("INSERT: " + phone);
                    phoneDAO.insert(phone);
                } else {
                    if(!phonesFromDB.contains(phone)) {
                        logger.info("UPDATE: " + phone);
                        phoneDAO.update(phone.getPhoneID(), phone);
                    }
                    Iterator<Phone> phoneIterator = phonesFromDB.iterator();
                    while(phoneIterator.hasNext()) {
                        Phone tempPhone = phoneIterator.next();
                        if(tempPhone.getPhoneID().equals(phone.getPhoneID())) {
                            phoneIterator.remove();
                            break;
                        }
                    }
                }
            }
            for (Phone phone : phonesFromDB) {
                logger.info("DELETE: " + phone);
                phoneDAO.delete(phone.getPhoneID());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateAttachments(Long id, ArrayList<Attachment> attachments) {
        ArrayList<Attachment> attachmentsFromDB;
        try(Connection connection = ConnectionUtils.getConnection()) {
            AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.createDAO(AttachmentDAO.class, connection);
            attachmentsFromDB = attachmentDAO.readContactAttachments(id);
            for (Attachment attachment : attachments) {
                if(attachment.getFileID() == null) {
                    logger.info("INSERT: " + attachment);
                    attachmentDAO.insert(attachment);
                } else {
                    if(!attachmentsFromDB.contains(attachment)) {
                        logger.info("UPDATE: " + attachment);
                        attachmentDAO.update(attachment.getFileID(), attachment);
                    }
                    Iterator<Attachment> attachmentIterator = attachmentsFromDB.iterator();
                    while(attachmentIterator.hasNext()) {
                        Attachment tempAttachment = attachmentIterator.next();
                        if(tempAttachment.getFileID().equals(attachment.getFileID())) {
                            attachmentIterator.remove();
                            break;
                        }
                    }
                }
            }
            for (Attachment attachment : attachmentsFromDB) {
                logger.info("DELETE: " + attachment);
                attachmentDAO.delete(attachment.getFileID());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}