package com.zhartunmatthew.web.contactbook.dao;

import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.entityfactory.EntityFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttachmentDAO extends AbstractDAO<Long, Attachment> {

    private static final String SELECT_CONTACT_ATTACHMENTS =
            "SELECT id_file AS id, " +
            "contact_id, " +
            "file_name, " +
            "comment, " +
            "upload_date " +
            "FROM attachments " +
            "WHERE contact_id = ?";

    private static final String SELECT_ATTACHMENT_BY_ID =
            "SELECT id_file AS id, contact_id, file_name, comment, upload_date " +
            "FROM attachments WHERE id_file = ? LIMIT 1";

    private static final String INSERT_ATTACHMENT_QUERY =
            "INSERT INTO attachments (contact_id, file_name, upload_date, comment) VALUES (?, ?, ?, ?);";

    private static final String DELETE_CONTACT_ATTACHMENTS =
            "DELETE FROM attachments WHERE contact_id = ?";

    private static final String UPDATE_ATTACHMENT =
            "UPDATE attachments SET file_name = ?, comment = ? WHERE id_file = ?;";

    private static final String DELETE_ATTACHMENT =
            "DELETE FROM attachments WHERE id_file = ?";

    public AttachmentDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<Attachment> readByContactId(Long contactId) throws DAOException {
        ArrayList<Attachment> attachments = new ArrayList<>();
        ResultSet attachmentResultSet;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CONTACT_ATTACHMENTS)) {
            statement.setLong(1, contactId);
            attachmentResultSet = statement.executeQuery();
            while (attachmentResultSet.next()) {
                Attachment attachment = (Attachment)
                        EntityFactory.createEntityFromResultSet(attachmentResultSet, Attachment.class);
                attachments.add(attachment);
            }
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't read Attachments by contact id = %s", contactId), ex);
        }
        return attachments;
    }

    @Override
    public ArrayList<Attachment> readAll() throws DAOException {
        return null;
    }

    @Override
    public void insert(Attachment val) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ATTACHMENT_QUERY,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, val.getContactID());
            statement.setString(2, val.getFileName());
            statement.setDate(3, val.getUploadDate());
            statement.setString(4, val.getComment());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                val.setId(resultSet.getLong(1));
            }
            statement.close();
        } catch (SQLException ex) {
            throw new DAOException("Can't insert new Attachment", ex);
        }
    }

    @Override
    public Attachment read(Long id) throws DAOException {
        Attachment attachment = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ATTACHMENT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                attachment = (Attachment) EntityFactory.createEntityFromResultSet(resultSet, Attachment.class);
            }
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't read Attachment with id = %d", id), ex);
        }
        return attachment;
    }

    @Override
    public void update(Long id, Attachment val) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ATTACHMENT)) {
            statement.setString(1, val.getFileName());
            statement.setString(2, val.getComment());
            statement.setLong(3, val.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't update Attachment with id = %d", id), ex);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ATTACHMENT)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't delete Attachment with id = %d", id), ex);
        }
    }

    public void deleteByContactId(Long id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT_ATTACHMENTS)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException ex) {
            throw new DAOException(String.format("Can't delete Attachments with contact id = %d", id), ex);
        }
    }
}
