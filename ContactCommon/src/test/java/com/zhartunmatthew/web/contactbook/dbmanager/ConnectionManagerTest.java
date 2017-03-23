package com.zhartunmatthew.web.contactbook.dbmanager;

import com.zhartunmatthew.web.contactbook.dao.ContactDAOTest;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class ConnectionManagerTest {

    public static Logger log = Logger.getLogger(ContactDAOTest.class);

    @Test
    public void getConnection() throws Exception {
        ConnectionManager manager = ConnectionManager.getInstance();
        Connection connection = manager.getConnection();
        Assert.assertTrue(true);
    }

    @Test
    public void closeConnection() throws Exception {
        ConnectionManager manager = ConnectionManager.getInstance();
        Connection connection = manager.getConnection();
        connection.close();
        Assert.assertTrue(connection.isClosed());
    }
}