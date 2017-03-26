package com.zhartunmatthew.web.contactbook.services;


import com.zhartunmatthew.web.contactbook.dao.AbstractDAO;
import com.zhartunmatthew.web.contactbook.dao.AttachmentDAO;
import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.PhoneDAO;
import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionUtils;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Entity;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.search.SearchParameters;
import com.zhartunmatthew.web.contactbook.services.fileservice.AttachmentService;
import com.zhartunmatthew.web.contactbook.services.fileservice.ImageService;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ContactService {

    private static Logger logger = Logger.getLogger(ContactService.class);

    public ContactService() {}

    public void insertContact(Contact contact, FileItem fileItem, ArrayList<FileItem> files) {
        Long lastId;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            connection.setAutoCommit(false);
            try {
                ImageService.writePhoto(contact, fileItem);

                contactDAO.insert(contact);
                lastId = contactDAO.getLastInsertedId();
                contactDAO.insertContactAddress(contact, lastId);

                AttachmentService.writeAttachments(lastId, files);

                PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.createDAO(PhoneDAO.class, connection);
                ArrayList<Phone> phones = contact.getPhones();
                if (phones != null) {
                    for (Phone phone : phones) {
                        phone.setContactID(lastId);
                        phoneDAO.insert(phone);
                    }
                }

                AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.createDAO(AttachmentDAO.class, connection);
                ArrayList<Attachment> attachments = contact.getAttachments();
                if (attachments != null) {
                    for (Attachment attachment : attachments) {
                        attachment.setContactID(lastId);
                        attachmentDAO.insert(attachment);
                    }
                }
                connection.commit();
            } catch (DAOException ex) {
                connection.rollback();
                ex.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
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
                connection.setAutoCommit(false);
                try {
                    attachmentDAO.deleteByContactId(id);
                    phoneDAO.deleteByContactId(id);
                    contactDAO.deleteContactAddress(id);
                    contactDAO.delete(id);

                    connection.commit();
                } catch (DAOException ex) {
                    connection.rollback();
                    ex.printStackTrace();
                } finally {
                    connection.setAutoCommit(true);
                }
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
        } catch (SQLException | DAOException ex) {
            ex.printStackTrace();
        }


        return contacts;
    }

    public Contact getContactById(Long id) {
        Contact contact = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contact = contactDAO.read(id);
        } catch (SQLException | DAOException ex) {
            ex.printStackTrace();
        }
        return contact;
    }

    public Long getContactCount() {
        Long count = 0L;
        try(Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            count = contactDAO.getContactCount();
        } catch (SQLException | DAOException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public ArrayList<Contact> getCertainCount(int from, int limit) {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()){
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contacts = contactDAO.readCertainCount(from, limit);
        } catch (SQLException | DAOException ex) {
            ex.printStackTrace();
        }
        return contacts;
    }

    public ArrayList<Contact> findAllByParameters(SearchParameters parameters) {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()){
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            contacts = contactDAO.searchUserByParameters(parameters);
        } catch (SQLException | DAOException ex) {
            ex.printStackTrace();
        }
        return contacts;
    }

    public void updateContact(Contact contact, FileItem fileItem, ArrayList<FileItem> files) {
        try (Connection connection = ConnectionUtils.getConnection()){
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.createDAO(AttachmentDAO.class, connection);
            PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.createDAO(PhoneDAO.class, connection);

            connection.setAutoCommit(false);
            try {
                ImageService.writePhoto(contact, fileItem);
                AttachmentService.writeAttachments(contact.getId(), files);
                contactDAO.update(contact.getId(), contact);
                logger.info(contact.getPhones());
                updateEntities(contact.getId(), contact.getPhones(), phoneDAO.readByContactId(contact.getId()), phoneDAO);
                updateEntities(contact.getId(), contact.getAttachments(), attachmentDAO.readByContactId(contact.getId()), attachmentDAO);
                connection.commit();
            } catch (DAOException ex) {
                connection.rollback();
                ex.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private <Type extends Entity> void updateEntities(Long id, ArrayList<Type> entities,
                                                      ArrayList<Type> entitiesFromDB,
                                                      AbstractDAO entityDAO) throws DAOException {
        try {
            for (Type entity : entities) {
                if(entity.getId() == null) {
                    logger.info("INSERT: " + entity);
                    entityDAO.insert(entity);
                } else {
                    if(!entitiesFromDB.contains(entity)) {
                        logger.info("UPDATE: " + entity);
                        entityDAO.update(entity.getId(), entity);
                    }
                    Iterator<? extends Entity> entityIterator = entitiesFromDB.iterator();
                    while(entityIterator.hasNext()) {
                        Entity tempEntity = entityIterator.next();
                        if(tempEntity.getId().equals(entity.getId())) {
                            entityIterator.remove();
                            break;
                        }
                    }
                }
            }
            for (Type entity : entitiesFromDB) {
                logger.info("DELETE: " + entity);
                entityDAO.delete(entity.getId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Long getLastInsertedContactId() {
        Long id = null;
        try(Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class, connection);
            id = contactDAO.getLastInsertedId();
        } catch (SQLException | DAOException ex) {
            ex.printStackTrace();
        }
        return id;
    }
}