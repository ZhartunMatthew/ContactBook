package com.zhartunmatthew.web.contactbook.services.fileservice;

import com.zhartunmatthew.web.contactbook.entity.Attachment;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.io.File;
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
        while(oneAttachment.hasNext()) {
            writeFile(oneAttachment.next(), oneFleItem.next());
        }
    }

    private static void writeFile(Attachment attachment, FileItem fileItem) {
        String filePath = resBundle.getString("files-directory") + "contact_" + attachment.getContactID() + File.separator;
        File directory = new File(filePath);
        if(!directory.exists()) {
            if(directory.mkdir()) {
                log.error("Can't create directory for attachments");
            }
        }
        File file = new File(filePath + "file_" + attachment.getId());
        try {
            fileItem.write(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void removeAttachmentFromDisk(Long contactId, Long fileId) {
        String directoryPath = resBundle.getString("files-directory") + "contact_" + contactId + File.separator;
        File file = new File(directoryPath + "file_" + fileId);
        if(file.exists()) {
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
}
