package com.zhartunmatthew.web.contactbook.command.getcommand;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.filenamehelper.FileNameHelper;
import com.zhartunmatthew.web.contactbook.services.fileservice.AttachmentService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadAttachmentCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(DownloadAttachmentCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Long attachmentId = Long.parseLong(request.getParameter("id"));
        Attachment attachment = AttachmentService.getAttachmentById(attachmentId);
        String fullPath = AttachmentService.getFullAttachmentPath(attachment);
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;

        try {
            fileInputStream = new FileInputStream(fullPath);
            outputStream = response.getOutputStream();

            response.setContentType("application/download");
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    FileNameHelper.cyr2lat(attachment.getFileName()));

            byte[] content = new byte[(int) new File(fullPath).length()];
            while (fileInputStream.read(content) != -1) {
                outputStream.write(content);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage() + ex.getCause());
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage() + ex.getCause());
            }
        }
        return null;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
