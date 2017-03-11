package com.zhartunmatthew.web.contactbook.dbmanager;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class ConnectionManagerTest {
    @Test
    public void getConnection() throws Exception {
        Connection connection = ConnectionManager.getConnection();
        Assert.assertTrue(connection.isValid(1000));
    }

    @Test
    public void closeConnection() throws Exception {
        Connection connection = ConnectionManager.getConnection();
        connection.close();
        Assert.assertTrue(connection.isClosed());
    }

    @Test
    public void testGetConnection() throws Exception {
        Connection connection = ConnectionManager.getConnection();
        connection.close();
        connection = ConnectionManager.getConnection();
        Assert.assertTrue(connection.isValid(100));
    }
}