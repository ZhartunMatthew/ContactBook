package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PhoneDAOTest {

    public static Logger log = Logger.getLogger(ContactDAOTest.class);
    private PhoneDAO phoneDAO = null;

    @Before
    public void initContactDAO(){
        phoneDAO = (PhoneDAO) DAOFactory.getDAO(PhoneDAO.class);
    }

    @Test
    public void readContactPhones() throws Exception {
        ArrayList<Phone> phones = phoneDAO.readContactPhones((long)1);
        for(Phone tempPhone : phones) {
            if(tempPhone == null) {
                log.info("NULL");
                continue;
            }
            log.info(tempPhone.toString());
        }
    }

}