package com.zhartunmatthew.web.contactbook.dbmanager;

import com.zhartunmatthew.web.contactbook.dao.ContactDAOTest;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionManagerTest {

    public static Logger log = Logger.getLogger(ContactDAOTest.class);

    @Test
    public void getConnection() throws Exception {
        WrappedConnection connection = ConnectionManager.getConnection();
        Assert.assertTrue(true);
    }

    @Test
    public void closeConnection() throws Exception {
        WrappedConnection connection = ConnectionManager.getConnection();
        connection.realClose();
        Assert.assertTrue(connection.isClosed());
    }
}