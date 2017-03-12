package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dbmanager.WrappedConnection;
import com.zhartunmatthew.web.contactbook.entity.Attachment;

import java.util.ArrayList;

public class AttachmentDAO extends AbstractDAO<Long, Attachment> {

    protected AttachmentDAO(WrappedConnection connection) {
        super(connection);
    }

    @Override
    protected ArrayList<Attachment> readAll() {
        return null;
    }

    @Override
    protected void insert(Attachment val) {

    }

    @Override
    protected Attachment read(Long aLong) {
        return null;
    }

    @Override
    protected void update(Long aLong, Attachment val) {

    }

    @Override
    protected void delete(Long aLong) {

    }
}
