package com.zhartunmatthew.web.contactbook.handler.handlers;

import com.zhartunmatthew.web.contactbook.entity.Attachment;
import com.zhartunmatthew.web.contactbook.entity.Contact;
import com.zhartunmatthew.web.contactbook.jsonbuilder.JSONBuilder;

import java.util.ArrayList;

public class AttachmentHandler extends AbstractHandler  {
    @Override
    public void handleField(Contact contact, String data) {
        ArrayList<Attachment> attachments = JSONBuilder.buildAttachmentListFromJSON(data);
        if(attachments != null) {
            attachments.forEach(attachment -> attachment.setContactID(contact.getId()));
            if(contact.getAttachments() != null) {
                contact.getAttachments().addAll(attachments);
            } else {
                contact.setAttachments(attachments);
            }
        }
    }
}