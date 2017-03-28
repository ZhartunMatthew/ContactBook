package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class DeleteContactCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(DeleteContactCommand.class);
    private static String REDIRECT_URL = "/controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String[] items = request.getParameterValues("contact-check");
        ArrayList<Long> checkedItems = new ArrayList<>();
        if(items != null) {
            for(String val : items) {
                checkedItems.add(Long.parseLong(val));
                log.info("Checked: " + Integer.parseInt(val));
            }
        } else {
            log.info("NO CHECKED ITEMS");
        }
        ContactService contactService = new ContactService();
        contactService.deleteContacts(checkedItems);

        return REDIRECT_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
