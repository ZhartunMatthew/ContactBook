package com.zhartunmatthew.web.contactbook.DBManager;

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
        ConnectionManager.closeConnection(connection);
        Assert.assertTrue(connection.isClosed());
    }

}