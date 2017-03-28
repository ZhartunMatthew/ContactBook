package com.zhartunmatthew.web.contactbook.services.fileservice;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.ResourceBundle;

public class ImageService {

    private final static String PROPERTIES_PATH = "directories";
    private static ResourceBundle resBundle = ResourceBundle.getBundle(PROPERTIES_PATH);

    public static String writePhoto(Long id, FileItem photoItem, String oldPhotoPath) {
        String photoPath = null;

        if(oldPhotoPath != null && !oldPhotoPath.isEmpty()) {
            photoPath = oldPhotoPath;
        }

        if (photoItem != null && !photoItem.getName().isEmpty()) {
            String directoryPath = resBundle.getString("files-directory") + "contact_" + id + File.separator;
            File directory = new File(directoryPath);
            if(!directory.exists()) {
                directory.mkdir();
            }
            photoPath = "image" + directoryPath.hashCode();
            File photoFile = new File(directoryPath + photoPath);
            try {
                photoItem.write(photoFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(photoPath == null) {
            removePhotoFromDisk(id);
        }

        return photoPath;
    }

    private static void removePhotoFromDisk(Long id) {
        String directoryPath = resBundle.getString("files-directory") + "contact_" + id + File.separator;
        String photoPath = "image" + directoryPath.hashCode();
        File file = new File(directoryPath + photoPath);
        if(file.exists()) {
            file.delete();
        }
    }
}
