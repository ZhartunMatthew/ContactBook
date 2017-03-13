package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dbmanager.WrappedConnection;
import com.zhartunmatthew.web.contactbook.entity.Attachment;

import java.util.ArrayList;

public class AttachmentDAO extends AbstractDAO<Long, Attachment> {

    public AttachmentDAO(WrappedConnection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Attachment> readAll() {
        return null;
    }

    @Override
    public void insert(Attachment val) {

    }

    @Override
    public Attachment read(Long aLong) {
        return null;
    }

    @Override
    public void update(Long aLong, Attachment val) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
