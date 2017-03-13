package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ContactDAOTest {

    public static Logger log = Logger.getLogger(ContactDAOTest.class);
    private ContactDAO contactDAO = null;

    @Before
    public void initContactDAO(){
        contactDAO = (ContactDAO) DAOFactory.getDAO(ContactDAO.class);
    }

    @Test
    public void readAll() throws Exception {
        ArrayList<Contact> contacts;
        contacts = contactDAO.readAll();
        for(Contact tempContact : contacts) {
            log.debug(tempContact);
        }
        Assert.assertTrue(true);
    }
}