package com.zhartunmatthew.web.contactbook.services.fileservice;

import com.zhartunmatthew.web.contactbook.dao.AttachmentDAO;
import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionUtils;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AttachmentService {

    private final static String PROPERTIES_PATH = "directories";
    private static ResourceBundle resBundle = ResourceBundle.getBundle(PROPERTIES_PATH);

    public static void writeAttachments(ArrayList<Attachment> attachments, ArrayList<FileItem> fileItems) throws ServiceException {
        Iterator<Attachment> oneAttachment = attachments.iterator();
        Iterator<FileItem> oneFleItem = fileItems.iterator();
        while (oneAttachment.hasNext() && oneFleItem.hasNext()) {
            writeFile(oneAttachment.next(), oneFleItem.next());
        }
    }

    private static void writeFile(Attachment attachment, FileItem fileItem) throws ServiceException {
        String filePath = resBundle.getString("files-directory") + "contact_" + attachment.getContactID() + File.separator;
        File directory = new File(filePath);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                throw new ServiceException("Can't create new directory");
            }
        }
        File file = new File(filePath + "file_" + attachment.getId());
        try {
            fileItem.write(file);
        } catch (Exception ex) {
            throw new ServiceException("Can't write new file", ex);
        }
    }

    public static void removeAttachmentFromDisk(Long contactId, Long fileId) {
        String directoryPath = resBundle.getString("files-directory") + "contact_" + contactId + File.separator;
        File file = new File(directoryPath + "file_" + fileId);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void removeAllContactAttachments(Long contactId) {
        String directoryPath = resBundle.getString("files-directory") + "contact_" + contactId;
        File directory = new File(directoryPath);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                file.delete();
            }
        }
        directory.delete();
    }

    public static Attachment getAttachmentById(Long id) throws ServiceException {
        Attachment attachment = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            try {
                AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
                attachment = attachmentDAO.read(id);
            } catch (DAOException ex) {
                throw new ServiceException("Can't get attachment by id");
            }
        } catch (SQLException ex) {
            throw new ServiceException("Can't get connection");
        }
        return attachment;
    }

    public static String getFullAttachmentPath(Attachment attachment) {
        return resBundle.getString("files-directory") + "contact_"
                + attachment.getContactID() + File.separator + "file_" + attachment.getId();
    }
}