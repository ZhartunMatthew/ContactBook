package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class AbstractDAO<PrKey, Type> {

    protected Connection connection;

    AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract ArrayList<Type> readAll() throws DAOException;

    public abstract void insert(Type val) throws DAOException;

    public abstract Type read(PrKey key) throws DAOException;

    public abstract void update(PrKey key, Type val) throws DAOException;

    public abstract void delete(PrKey key) throws DAOException;
}
