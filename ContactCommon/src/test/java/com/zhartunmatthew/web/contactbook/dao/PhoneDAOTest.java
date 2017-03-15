package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;

public class PhoneDAOTest {

    public static Logger log = Logger.getLogger(ContactDAOTest.class);

    @Test
    public void readContactPhones() throws Exception {
        try (PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.createDAO(PhoneDAO.class)) {
            ArrayList<Phone> phones = phoneDAO.readContactPhones((long) 1);
            for (Phone tempPhone : phones) {
                if (tempPhone == null) {
                    log.info("NULL");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
