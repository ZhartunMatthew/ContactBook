package com.zhartunmatthew.web.contactbook.dbmanager;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class ConnectionManager {

    private Logger log = Logger.getLogger(ConnectionManager.class);
    private DataSource dataSource;
    private static ConnectionManager instance;

    private ConnectionManager() {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/contactbook");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConnectionManager getInstance() {
        if(instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public void deregisterDrivers() {
        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        while(driverEnumeration.hasMoreElements()) {
            Driver driver = driverEnumeration.nextElement();
            ClassLoader driverClassLoader = driver.getClass().getClassLoader();
            ClassLoader thisClassLoader = this.getClass().getClassLoader();
            if(driverClassLoader != null && thisClassLoader != null) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
