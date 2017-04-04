package com.zhartunmatthew.web.contactbook.handler;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.handler.exception.WrongInputException;
import com.zhartunmatthew.web.contactbook.handler.handlerfactory.HandlerFactory;
import com.zhartunmatthew.web.contactbook.handler.handlers.AbstractHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainHandler {

    private final static Logger LOG = LoggerFactory.getLogger(MainHandler.class);

    public void handleInputs(HttpServletRequest request) {
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
        ServletContext app = request.getServletContext();
        File tempDir = new File(String.valueOf(app.getAttribute("javax.servlet.context.tempdir")));
        fileItemFactory.setRepository(tempDir);

        ArrayList<FileItem> files = new ArrayList<>();

        try {
            List<FileItem> items = fileUpload.parseRequest(request);
            Contact contact = new Contact();
            items.forEach(item -> {
                if(item.isFormField()) {
                    AbstractHandler handler = HandlerFactory.getFieldHandler(item.getFieldName());
                    if(handler != null) {
                        try {
                            handler.handleField(contact, item.getString("UTF-8"));
                        } catch (UnsupportedEncodingException | WrongInputException ex) {
                            LOG.error("Handler error", ex);
                        }
                    }
                } else {
                    String fileName = item.getFieldName();
                    if(fileName.startsWith("new-attachment")) {
                        files.add(item);
                    } else {
                        request.setAttribute("photo-item", item);
                    }
                }
            });
            request.setAttribute("files", files);
            request.setAttribute("contact", contact);
        } catch (FileUploadException ex) {
            LOG.error("File uploading error", ex);
        }
    }
}