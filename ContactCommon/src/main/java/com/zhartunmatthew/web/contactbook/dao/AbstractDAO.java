package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dbmanager.WrappedConnection;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class AbstractDAO<PrKey, Type> implements AutoCloseable {

    Logger log = Logger.getLogger(AbstractDAO.class);
    protected WrappedConnection connection;

    protected AbstractDAO(WrappedConnection connection) {
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
