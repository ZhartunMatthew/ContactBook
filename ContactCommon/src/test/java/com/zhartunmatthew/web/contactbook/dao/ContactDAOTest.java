package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ContactDAOTest {

    public static Logger log = Logger.getLogger(ContactDAOTest.class);

    @Test
    public void readAll() throws Exception {
        ArrayList<Contact> contacts = null;
        try (ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(ContactDAO.class)) {
            contacts = contactDAO.readAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Assert.assertEquals("русское", contacts.get(1).getFirstName());
    }
}