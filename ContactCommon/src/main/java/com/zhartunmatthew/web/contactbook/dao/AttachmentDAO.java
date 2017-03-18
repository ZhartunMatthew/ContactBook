package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dbmanager.WrappedConnection;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.entityfactory.EntityFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttachmentDAO extends AbstractDAO<Long, Attachment> {

    private static final String SELECT_CONTACT_ATTACHMENTS =
            "SELECT id_file AS id, " +
                    "contact_id, " +
                    "file_path, " +
                    "comment, " +
                    "upload_date " +
                    "FROM contactbook.attachments " +
                    "WHERE contact_id = ?";

    public AttachmentDAO(WrappedConnection connection) {
        super(connection);
    }

    public ArrayList<Attachment> readContactAttachments(Long contactId) {
        ArrayList<Attachment> attachments = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet attachmentResultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_CONTACT_ATTACHMENTS);
            statement.setLong(1, contactId);
            attachmentResultSet = statement.executeQuery();
            while(attachmentResultSet.next()) {
                Attachment attachment = (Attachment) EntityFactory.createEntityFromResultSet(attachmentResultSet, Attachment.class);
                attachments.add(attachment);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return attachments;
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
