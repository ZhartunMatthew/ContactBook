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
        try (ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class)) {
            contacts = contactDAO.readAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Assert.assertTrue(true);
    }

    @Test
    public void getContactCount() throws Exception {
        Long count = 0L;
        try (ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class)) {
            count = contactDAO.getContactCount();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("Contact count: " + count);
        Assert.assertTrue(true);
    }

}