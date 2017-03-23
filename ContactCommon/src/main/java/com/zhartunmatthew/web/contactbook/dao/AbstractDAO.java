package com.zhartunmatthew.web.contactbook.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class AbstractDAO<PrKey, Type> implements AutoCloseable {

    Logger log = Logger.getLogger(AbstractDAO.class);
    protected Connection connection;

    protected AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected abstract ArrayList<Type> readAll();
    protected abstract void insert(Type val);
    protected abstract Type read(PrKey key);
    protected abstract void update(PrKey key, Type val);
    protected abstract void delete(PrKey key);
}
