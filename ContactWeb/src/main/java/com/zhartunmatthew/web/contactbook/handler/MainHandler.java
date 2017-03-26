package com.zhartunmatthew.web.contactbook.handler;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.handlerfactory.HandlerFactory;
import com.zhartunmatthew.web.contactbook.handler.handlers.AbstractHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

public class MainHandler {

    private static Logger log = Logger.getLogger(MainHandler.class);

    public void handleInputs(HttpServletRequest request) {
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
        ServletContext app = request.getServletContext();
        File tempDir = new File(String.valueOf(app.getAttribute("javax.servlet.context.tempdir")));
        fileItemFactory.setRepository(tempDir);

        HashMap<Integer, FileItem> files = new HashMap<>();

        try {
            List<FileItem> items = fileUpload.parseRequest(request);
            Contact contact = new Contact();
            items.forEach(item -> {
                if(item.isFormField()) {
                    log.info("Form: " + item.getFieldName());
                    AbstractHandler handler = HandlerFactory.getFieldHandler(item.getFieldName());
                    if(handler != null) {
                        try {
                            handler.handleField(contact, item.getString("UTF-8"));
                        } catch (UnsupportedEncodingException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        log.info("Form: " + item.getFieldName() + "is null");
                    }
                } else {
                    String fileName = item.getFieldName();
                    if(fileName.startsWith("new-attachment")) {
                        int fileIndex = Integer.parseInt(fileName.split("-")[3]);
                        files.put(fileIndex, item);
                    } else {
                        log.info("IMAGE ITEM " + item.getName());
                        request.setAttribute("photo-item", item);
                    }
                }
            });
            request.setAttribute("files", files);
            request.setAttribute("contact", contact);

        } catch (FileUploadException ex) {
            ex.printStackTrace();
        }
    }
}