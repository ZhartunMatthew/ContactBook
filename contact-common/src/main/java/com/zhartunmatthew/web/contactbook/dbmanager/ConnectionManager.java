package com.zhartunmatthew.web.contactbook.dbmanager;


import com.zhartunmatthew.web.contactbook.dbmanager.exception.ConnectionManagerException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class ConnectionManager {

    private DataSource dataSource;
    private static ConnectionManager instance;

    private ConnectionManager() throws ConnectionManagerException {
        try {
            dataSource = (DataSource)
                    new InitialContext().lookup("java:comp/env/jdbc/zhartun_matthew_contactbook");
        } catch (NamingException ex) {
            throw new ConnectionManagerException("Can't get DataSource", ex);
        }
    }

    public static synchronized ConnectionManager getInstance() throws ConnectionManagerException {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() throws ConnectionManagerException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            throw new ConnectionManagerException("Can't get connection", ex);
        }
        return connection;
    }

    public void deregisterDrivers() throws ConnectionManagerException {
        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        while (driverEnumeration.hasMoreElements()) {
            Driver driver = driverEnumeration.nextElement();
            ClassLoader driverClassLoader = driver.getClass().getClassLoader();
            ClassLoader thisClassLoader = this.getClass().getClassLoader();
            if (driverClassLoader != null && thisClassLoader != null) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                    throw new ConnectionManagerException("Can't deregister driver", ex);
                }
            }
        }
    }
}
