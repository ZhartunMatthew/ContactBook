package com.zhartunmatthew.web.contactbook.dbmanager;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Logger log = Logger.getLogger(ConnectionManager.class);
    private static Connection connection;

    static {
        registerDriver();
        createConnection();
    }

    private ConnectionManager() {}

    private static void registerDriver(){
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(ConfigManager.getUrl(), ConfigManager.getUser(), ConfigManager.getPassword());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if (connection.isClosed()) {
                createConnection();
                log.info("Reconnection");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
