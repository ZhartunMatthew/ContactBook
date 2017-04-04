package com.zhartunmatthew.web.contactbook.services.fileservice;

import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.ResourceBundle;

public class ImageService {

    private final static String PROPERTIES_PATH = "directories";
    private static ResourceBundle resBundle = ResourceBundle.getBundle(PROPERTIES_PATH);

    public static String writePhoto(Long id, FileItem photoItem, String oldPhotoPath) throws ServiceException {
        String photoPath = null;

        if (oldPhotoPath != null && !oldPhotoPath.isEmpty()) {
            photoPath = oldPhotoPath;
        }

        if (photoItem != null && !photoItem.getName().isEmpty()) {
            String directoryPath = resBundle.getString("files-directory") + "contact_" + id + File.separator;
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                if(!directory.mkdir()) {
                    throw new ServiceException("Can't create new directory");
                }
            }
            photoPath = "image";
            File photoFile = new File(directoryPath + photoPath);
            try {
                photoItem.write(photoFile);
            } catch (Exception ex) {
                throw new ServiceException("Can't write new photo");
            }
        }
        if (photoPath == null) {
            removePhotoFromDisk(id);
        }
        return photoPath;
    }

    private static void removePhotoFromDisk(Long id) {
        String directoryPath = resBundle.getString("files-directory") + "contact_" + id + File.separator;
        String photoPath = "image";
        File file = new File(directoryPath + photoPath);
        if (file.exists()) {
            file.delete();
        }
    }
}
