package com.zhartunmatthew.web.contactbook.services.fileservice;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.ResourceBundle;

public class ImageService {

    private final static String PROPERTIES_PATH = "directories";
    private static ResourceBundle resBundle = ResourceBundle.getBundle(PROPERTIES_PATH);

    public static void writePhoto(Contact contact, FileItem photoItem) {
        if (photoItem != null && !photoItem.getName().isEmpty()) {
            String photoPath = resBundle.getString("images-directory") + "image_" + contact.getId();
            File photoFile = new File(photoPath);
            contact.setPhotoPath(photoPath);
            try {
                photoItem.write(photoFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(photoItem == null && contact.getPhotoPath() == null) {
                contact.setPhotoPath(null);
            }
        }
    }
}
