package com.zhartunmatthew.web.contactbook.services.fileservice;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AttachmentService {

    private static Logger log = Logger.getLogger(AttachmentService.class);
    private final static String PROPERTIES_PATH = "directories";
    private static ResourceBundle resBundle = ResourceBundle.getBundle(PROPERTIES_PATH);

    public static void writeAttachments(Long id, ArrayList<FileItem> attachments) {
        for(FileItem fileItem : attachments) {
            writeFile(fileItem, id);
        }
    }

    private static void writeFile(FileItem fileItem, Long contactId) {
        String filePath = resBundle.getString("files-directory") + "contact_" + contactId + File.separator;
        File directory = new File(filePath);
        if(!directory.exists()) {
            if(directory.mkdir()) {
                log.error("Can't create directory for attachments");
            }
        }

        File file = new File(filePath + fileItem.getName());
        try {
            fileItem.write(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
