package com.zhartunmatthew.web.contactbook.DAO;

import com.zhartunmatthew.web.contactbook.DBManager.ConnectionManager;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class AbstractDAO<PrKey, Type> {

    protected Connection connection;

    protected AbstractDAO() {
        connection = ConnectionManager.getConnection();
    }

    protected void close() {
        ConnectionManager.closeConnection(connection);
    }

    public abstract ArrayList<Type> readAll();

    public abstract void insert(Type val);
    public abstract Type read(PrKey key);

    public abstract void update(PrKey key, Type val);
    public abstract void delete(PrKey key);
}
