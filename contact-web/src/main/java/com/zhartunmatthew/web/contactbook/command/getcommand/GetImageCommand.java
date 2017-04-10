package com.zhartunmatthew.web.contactbook.command.getcommand;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GetImageCommand implements AbstractCommand {

    private final static Logger LOG = LoggerFactory.getLogger(GetImageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String imageFile = request.getParameter("name");
        File image = null;
        byte[] content;
        if(imageFile != null) {
            image = new File(imageFile);
        }
        try {
            content = Files.readAllBytes(image.toPath());
            response.getOutputStream().write(content);
        } catch (IOException ex) {
            LOG.error("Error in command GetImage", ex);
        }
        return null;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
