package com.zhartunmatthew.web.contactbook.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class AbstractDAO<PrKey, Type> {

    Logger log = Logger.getLogger(AbstractDAO.class);
    protected Connection connection;

    protected AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    protected abstract ArrayList<Type> readAll();
    protected abstract void insert(Type val);
    protected abstract Type read(PrKey key);
    protected abstract void update(PrKey key, Type val);
    protected abstract void delete(PrKey key);
}
