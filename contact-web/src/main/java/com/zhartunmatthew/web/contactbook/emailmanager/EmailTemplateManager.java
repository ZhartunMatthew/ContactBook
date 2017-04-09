package com.zhartunmatthew.web.contactbook.emailmanager;

import com.zhartunmatthew.web.contactbook.entity.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmailTemplateManager {

    private final static Logger LOG = LoggerFactory.getLogger(EmailTemplateManager.class);
    private int TEMPLATE_COUNT = 3;
    private STGroup stGroup;
    private String TEMPLATE_NAME = "template_";
    private String PARAMETER_NAME = "username";

    public EmailTemplateManager() {
        String fullPath =
                ResourceBundle.getBundle("emailconfig").getObject("template_file_path").toString();
        stGroup = new STGroupFile(fullPath);
    }

    public ArrayList<String> getAllTemplates() {
        ArrayList<String> templates = new ArrayList<>();
        try {
            for(int i = 1; i <= TEMPLATE_COUNT; i++) {
                ST st = stGroup.getInstanceOf(TEMPLATE_NAME + i);
                st.add(PARAMETER_NAME, "%имя пользователя%");
                templates.add(st.render());
            }
        } catch (Exception ex) {
            LOG.error("Error in template manager", ex);
        }
        return templates;
    }

    public String createEmailFromTemplate(int templateId, Contact contact) {
        ST st = stGroup.getInstanceOf(TEMPLATE_NAME + templateId);
        st.add(PARAMETER_NAME, contact.getFirstName());
        return st.render();
    }
}
