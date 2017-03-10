package com.zhartunmatthew.web.contactbook.DBManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
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
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
