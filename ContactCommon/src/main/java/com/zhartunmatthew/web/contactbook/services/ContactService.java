package com.zhartunmatthew.web.contactbook.services;


import com.zhartunmatthew.web.contactbook.dao.AbstractDAO;
import com.zhartunmatthew.web.contactbook.dao.AttachmentDAO;
import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.PhoneDAO;
import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionUtils;
import com.zhartunmatthew.web.contactbook.dto.search.SearchParameters;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.entity.abstractions.ContactEntity;
import com.zhartunmatthew.web.contactbook.entity.abstractions.Entity;
import com.zhartunmatthew.web.contactbook.services.fileservice.AttachmentService;
import com.zhartunmatthew.web.contactbook.services.fileservice.ImageService;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ContactService {

    private static Logger log = Logger.getLogger(ContactService.class);

    public ContactService() {}

    public void insertContact(Contact contact, FileItem contactPhoto, ArrayList<FileItem> files) {
        Long lastId;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            connection.setAutoCommit(false);
            try {
                contactDAO.insert(contact);
                lastId = contactDAO.getLastInsertedId();
                contactDAO.insertContactAddress(contact, lastId);
                String photoPath = ImageService.writePhoto(lastId, contactPhoto, contact.getPhotoPath());
                contactDAO.updateContactPhoto(lastId, photoPath);
                PhoneDAO phoneDAO = new PhoneDAO(connection);
                ArrayList<Phone> phones = contact.getPhones();
                if (phones != null) {
                    for (Phone phone : phones) {
                        phone.setContactID(lastId);
                        phoneDAO.insert(phone);
                    }
                }

                AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
                ArrayList<Attachment> attachments = contact.getAttachments();
                if (attachments != null) {
                    for (Attachment attachment : attachments) {
                        attachment.setContactID(lastId);
                        attachmentDAO.insert(attachment);
                    }
                }

                AttachmentService.writeAttachments(attachments, files);
                connection.commit();
            } catch (DAOException ex) {
                connection.rollback();
                log.error(ex.getMessage() + ex.getCause());
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteContacts(ArrayList<Long> items) {
        try (Connection connection = ConnectionUtils.getConnection()) {
            AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
            PhoneDAO phoneDAO = new PhoneDAO(connection);
            ContactDAO contactDAO = new ContactDAO(connection);
            for (Long id : items) {
                connection.setAutoCommit(false);
                try {
                    attachmentDAO.deleteByContactId(id);
                    phoneDAO.deleteByContactId(id);
                    contactDAO.deleteContactAddress(id);
                    contactDAO.delete(id);
                    connection.commit();

                    AttachmentService.removeAllContactAttachments(id);
                } catch (DAOException ex) {
                    connection.rollback();
                    log.error(ex.getMessage() + ex.getCause());
                } finally {
                    connection.setAutoCommit(true);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Contact getContactById(Long id) {
        Contact contact = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            contact = contactDAO.read(id);
        } catch (SQLException | DAOException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return contact;
    }

    public Long getContactCount() {
        Long count = 0L;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            count = contactDAO.getContactCount();
        } catch (SQLException | DAOException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return count;
    }

    public ArrayList<Contact> getCertainCount(int from, int limit) {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            contacts = contactDAO.readCertainCount(from, limit);
        } catch (SQLException | DAOException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return contacts;
    }

    public ArrayList<Contact> findAllByParameters(SearchParameters parameters) {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            contacts = contactDAO.searchUserByParameters(parameters);
        } catch (SQLException | DAOException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return contacts;
    }

    public void updateContact(Contact contact, FileItem contactPhoto, ArrayList<FileItem> files) {
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
            PhoneDAO phoneDAO = new PhoneDAO(connection);
            connection.setAutoCommit(false);
            try {
                String photoPath = ImageService.writePhoto(contact.getId(), contactPhoto, contact.getPhotoPath());
                contactDAO.update(contact.getId(), contact);
                contactDAO.updateContactPhoto(contact.getId(), photoPath);
                updateEntities(contact.getPhones(), phoneDAO.readByContactId(contact.getId()), phoneDAO);
                ArrayList<Attachment> attachmentsForUploading =
                        updateEntities(contact.getAttachments(),
                                attachmentDAO.readByContactId(contact.getId()), attachmentDAO);
                AttachmentService.writeAttachments(attachmentsForUploading, files);
                connection.commit();
            } catch (DAOException ex) {
                connection.rollback();
                log.error(ex.getMessage() + ex.getCause());
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
    }

    private <Type extends ContactEntity> ArrayList<Type> updateEntities(ArrayList<Type> entities,
                                         ArrayList<Type> entitiesFromDB,
                                         AbstractDAO<Long, Type> entityDAO) throws DAOException {
        ArrayList<Type> entitiesForInsert = new ArrayList<>();
        try {
            for (Type entity : entities) {
                if (entity.getId() == null) {
                    log.info("INSERT: " + entity);
                    entityDAO.insert(entity);
                    entitiesForInsert.add(entity);
                } else {
                    if (!entitiesFromDB.contains(entity)) {
                        log.info("UPDATE: " + entity);
                        entityDAO.update(entity.getId(), entity);
                    }
                    Iterator<? extends Entity> entityIterator = entitiesFromDB.iterator();
                    while (entityIterator.hasNext()) {
                        Entity tempEntity = entityIterator.next();
                        if (tempEntity.getId().equals(entity.getId())) {
                            entityIterator.remove();
                            break;
                        }
                    }
                }
            }
            for (Type entity : entitiesFromDB) {
                log.info("DELETE: " + entity);
                entityDAO.delete(entity.getId());
                if (entity.getClass() == Attachment.class) {
                    AttachmentService.removeAttachmentFromDisk(entity.getContactID(), entity.getId());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entitiesForInsert;
    }

    public Long getLastInsertedContactId() {
        Long id = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            id = contactDAO.getLastInsertedId();
        } catch (SQLException | DAOException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return id;
    }

    public ArrayList<Contact> getContactsByBirthDate(Date date) {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            contacts = contactDAO.readByBirthDate(date);
        } catch (SQLException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return contacts;
    }
}