package com.zhartunmatthew.web.contactbook.dbmanager;

import java.sql.Connection;

public class ConnectionUtils {
    public static Connection getConnection() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        return connectionManager.getConnection();
    }
}
