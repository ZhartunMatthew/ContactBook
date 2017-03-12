package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.entity.Phone;

import java.sql.Connection;
import java.util.ArrayList;

public class PhoneDAO extends AbstractDAO<Long, Phone> {

    protected PhoneDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected ArrayList<Phone> readAll() {
        return null;
    }

    @Override
    protected void insert(Phone val) {

    }

    @Override
    protected Phone read(Long aLong) {
        return null;
    }

    @Override
    protected void update(Long aLong, Phone val) {

    }

    @Override
    protected void delete(Long aLong) {

    }
}
