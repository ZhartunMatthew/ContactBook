package com.zhartunmatthew.web.contactbook.dbmanager;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionManagerTest {
    @Test
    public void getConnection() throws Exception {
        WrappedConnection connection = ConnectionManager.getConnection();
        Assert.assertTrue(connection.isValid(500));
    }

    @Test
    public void closeConnection() throws Exception {
        WrappedConnection connection = ConnectionManager.getConnection();
        connection.realClose();
        Assert.assertTrue(connection.isClosed());
    }
}