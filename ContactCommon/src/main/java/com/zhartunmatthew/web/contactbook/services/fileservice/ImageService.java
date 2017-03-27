package com.zhartunmatthew.web.contactbook.services.fileservice;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.ResourceBundle;

public class ImageService {

    private final static String PROPERTIES_PATH = "directories";
    private static ResourceBundle resBundle = ResourceBundle.getBundle(PROPERTIES_PATH);

    public static String writePhoto(Long id, FileItem photoItem) {
        String photoPath = null;
        if (photoItem != null && !photoItem.getName().isEmpty()) {
            photoPath = resBundle.getString("images-directory") + "image_" + id;
            File photoFile = new File(photoPath);
            try {
                photoItem.write(photoFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return photoPath;
    }
}
