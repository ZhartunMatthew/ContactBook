package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionManager;
import com.zhartunmatthew.web.contactbook.dbmanager.WrappedConnection;
import com.zhartunmatthew.web.contactbook.entity.Phone;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by ZhartunMatthew on 3/13/2017.
 */
public class PhoneDAOTest {

    public static Logger log = Logger.getLogger(ContactDAOTest.class);
    private PhoneDAO phoneDAO = null;
    WrappedConnection connection = null;

    @Before
    public void initContactDAO(){
        connection = ConnectionManager.getConnection();
        phoneDAO = new PhoneDAO(connection);
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