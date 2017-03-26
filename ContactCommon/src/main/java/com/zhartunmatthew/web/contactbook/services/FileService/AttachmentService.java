package com.zhartunmatthew.web.contactbook.services.FileService;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AttachmentService {

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
            directory.mkdir();
        }

        File file = new File(filePath + fileItem.getName());
        try {
            fileItem.write(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
