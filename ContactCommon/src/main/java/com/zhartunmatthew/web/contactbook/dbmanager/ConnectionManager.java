package com.zhartunmatthew.web.contactbook.dbmanager;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionManager {

    private static Logger log = Logger.getLogger(ConnectionManager.class);
    private static final int POOL_SIZE = ConfigManager.getPoolSize();
    private static ArrayBlockingQueue<WrappedConnection> connections
            = new ArrayBlockingQueue<>(POOL_SIZE);

    static {
        registerDriver();
        for(int i = 0; i < POOL_SIZE; i++) {
            connections.add(createConnection());
        }
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

    private static WrappedConnection createConnection() {
        WrappedConnection wrappedConnection = null;
        try {
            Connection connection = DriverManager.getConnection(ConfigManager.getUrl(),
                    ConfigManager.getUser(),
                    ConfigManager.getPassword());
            wrappedConnection = new WrappedConnection(connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return wrappedConnection;
    }

    public static WrappedConnection getConnection() {
        return connections.poll();
    }

    public static void releaseConnection(WrappedConnection connection) {
        try {
            if(!connection.getAutoCommit()) {
                connection.rollback();
                log.debug("Rollback");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        log.debug("Returned in pool");
        connections.add(connection);
    }
}
