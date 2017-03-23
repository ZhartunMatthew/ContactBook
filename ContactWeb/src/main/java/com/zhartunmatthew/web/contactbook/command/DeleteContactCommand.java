package com.zhartunmatthew.web.contactbook.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class DeleteContactCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(DeleteContactCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String[] items = request.getParameterValues("contact-check");
        ArrayList<Long> checkedItems = new ArrayList<>();

        if(items != null) {
            for(String val : items) {
                checkedItems.add(Long.parseLong(val));
                log.info("Checked: " + Integer.parseInt(val));
                log.info("ITEMS: " + items.length);
            }
        } else {
            log.info("NO CHECKED ITEMS");
        }

        return null;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
