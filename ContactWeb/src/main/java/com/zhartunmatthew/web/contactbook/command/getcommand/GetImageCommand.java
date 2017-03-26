package com.zhartunmatthew.web.contactbook.command.getcommand;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GetImageCommand implements AbstractCommand {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
