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
        try (ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(ContactDAO.class)) {
            ArrayList<Contact> contacts;
            contacts = contactDAO.readAll();
            for (Contact tempContact : contacts) {
                log.debug(tempContact);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Assert.assertTrue(true);
    }
}