package com.zhartunmatthew.web.contactbook.services.fileservice;

import com.zhartunmatthew.web.contactbook.dao.AttachmentDAO;
import com.zhartunmatthew.web.contactbook.dao.exception.DAOException;
import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionUtils;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AttachmentService {

    private static Logger log = Logger.getLogger(AttachmentService.class);
    private final static String PROPERTIES_PATH = "directories";
    private static ResourceBundle resBundle = ResourceBundle.getBundle(PROPERTIES_PATH);

    public static void writeAttachments(ArrayList<Attachment> attachments, ArrayList<FileItem> fileItems) {
        Iterator<Attachment> oneAttachment = attachments.iterator();
        Iterator<FileItem> oneFleItem = fileItems.iterator();
        while (oneAttachment.hasNext() && oneFleItem.hasNext()) {
            writeFile(oneAttachment.next(), oneFleItem.next());
        }
    }

    private static void writeFile(Attachment attachment, FileItem fileItem) {
        String filePath = resBundle.getString("files-directory") + "contact_" + attachment.getContactID() + File.separator;
        File directory = new File(filePath);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                log.error("Can't create directory for attachments");
            }
        }
        File file = new File(filePath + "file_" + attachment.getId());
        try {
            fileItem.write(file);
        } catch (Exception ex) {
            log.error(ex.getMessage() + ex.getCause());
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

    public static Attachment getAttachmentById(Long id) {
        Attachment attachment = null;
        try (Connection connection = ConnectionUtils.getConnection()) {
            try {
                AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
                attachment = attachmentDAO.read(id);
            } catch (DAOException ex) {
                log.error(ex.getMessage() + ex.getCause());
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage() + ex.getCause());
        }
        return attachment;
    }

    public static String getFullAttachmentPath(Attachment attachment) {
        return resBundle.getString("files-directory") + "contact_"
                + attachment.getContactID() + File.separator + "file_" + attachment.getId();
    }
}