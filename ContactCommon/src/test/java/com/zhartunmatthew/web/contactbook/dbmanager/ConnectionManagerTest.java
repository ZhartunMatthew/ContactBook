package com.zhartunmatthew.web.contactbook.dbmanager;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class ConnectionManagerTest {
    @Test
    public void getConnection() throws Exception {
        Connection connection = ConnectionManager.getConnection();
        Assert.assertFalse(connection.isClosed());
    }

    @Test
    public void closeConnection() throws Exception {
        Connection connection = ConnectionManager.getConnection();
        connection.close();
        Assert.assertTrue(connection.isClosed());
    }
}