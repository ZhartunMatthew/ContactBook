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
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import com.zhartunmatthew.web.contactbook.services.fileservice.AttachmentService;
import com.zhartunmatthew.web.contactbook.services.fileservice.ImageService;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ContactService {

    private final static Logger LOG = LoggerFactory.getLogger(ContactService.class);

    public ContactService() {}

    public void insertContact(Contact contact, FileItem contactPhoto, ArrayList<FileItem> files) throws ServiceException {
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
                throw new ServiceException("Can't insert new contact", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new ServiceException("Can't get connection", ex);
        }
    }

    public void deleteContacts(ArrayList<Long> items) throws ServiceException {
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
                    throw new ServiceException("Can't delete contact", ex);
                } finally {
                    connection.setAutoCommit(true);
                }
            }
        } catch (SQLException ex) {
            throw new ServiceException("Can't get connection", ex);
        }
    }

    public Contact getContactById(Long id) throws ServiceException {
        Contact contact = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            contact = contactDAO.read(id);
        } catch (SQLException | DAOException ex) {
            throw new ServiceException(String.format("Can't get contact by id = %d", id), ex);
        }
        return contact;
    }

    public Long getContactCount() throws ServiceException {
        Long count = 0L;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            count = contactDAO.getContactCount();
        } catch (SQLException | DAOException ex) {
            throw new ServiceException("Can't get contact count", ex);
        }
        return count;
    }

    public ArrayList<Contact> getCertainCount(int from, int limit) throws ServiceException {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            contacts = contactDAO.readCertainCount(from, limit);
        } catch (SQLException | DAOException ex) {
            throw new ServiceException("Can't get certain count of attachments", ex);
        }
        return contacts;
    }

    public ArrayList<Contact> findAllByParameters(SearchParameters parameters) throws ServiceException {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            contacts = contactDAO.searchUserByParameters(parameters);
        } catch (SQLException | DAOException ex) {
            throw new ServiceException("Can't find contacts by parameters", ex);
        }
        return contacts;
    }

    public void updateContact(Contact contact, FileItem contactPhoto, ArrayList<FileItem> files) throws ServiceException {
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
                throw new ServiceException("Can't update contact", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new ServiceException("Can't get connection", ex);
        }
    }

    private <Type extends ContactEntity> ArrayList<Type> updateEntities(ArrayList<Type> entities,
                                         ArrayList<Type> entitiesFromDB,
                                         AbstractDAO<Long, Type> entityDAO) throws DAOException, ServiceException {
        ArrayList<Type> entitiesForInsert = new ArrayList<>();
        try {
            for (Type entity : entities) {
                if (entity.getId() == null) {
                    LOG.debug("INSERT: {}", entity);
                    entityDAO.insert(entity);
                    entitiesForInsert.add(entity);
                } else {
                    if (!entitiesFromDB.contains(entity)) {
                        LOG.debug("UPDATE: {}", entity);
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
                LOG.debug("DELETE: {}", entity);
                entityDAO.delete(entity.getId());
                if (entity.getClass() == Attachment.class) {
                    AttachmentService.removeAttachmentFromDisk(entity.getContactID(), entity.getId());
                }
            }
        } catch (DAOException ex) {
            throw new ServiceException("Can't get connection", ex);
        }
        return entitiesForInsert;
    }

    public ArrayList<Contact> getContactsByBirthDate() throws ServiceException {
        ArrayList<Contact> contacts = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            contacts = contactDAO.readByBirthDate();
        } catch (DAOException | SQLException ex) {
            throw new ServiceException("Can't get contact by birth date", ex);
        }
        return contacts;
    }
}