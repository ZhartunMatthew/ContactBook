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

    private static final String INSERT_ATTACHMENT_QUERY =
            "INSERT INTO attachments (contact_id, file_path, upload_date, comment) " +
                    "VALUES (?, ?, ?, ?);";

    private static final String DELETE_ATTACHMENT =
            "DELETE FROM attachments WHERE contact_id = ?";

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
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_ATTACHMENT_QUERY);
            statement.setLong(1, val.getContactID());
            statement.setString(2, val.getFilePath());
            statement.setDate(3, val.getUploadDate());
            statement.setString(4, val.getComment());
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Attachment read(Long id) {
        return null;
    }

    @Override
    public void update(Long id, Attachment val) {

    }

    @Override
    public void delete(Long id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_ATTACHMENT);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
