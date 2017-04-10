package com.zhartunmatthew.web.contactbook.dbmanager;

import com.zhartunmatthew.web.contactbook.dbmanager.exception.ConnectionManagerException;

import java.sql.Connection;

public class ConnectionUtils {
    public static Connection getConnection() throws ConnectionManagerException {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        return connectionManager.getConnection();
    }
}
