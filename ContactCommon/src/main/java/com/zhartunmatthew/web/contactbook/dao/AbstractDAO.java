package com.zhartunmatthew.web.contactbook.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class AbstractDAO<PrKey, Type> {

    protected Connection connection;

    protected AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    protected void close() {
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
