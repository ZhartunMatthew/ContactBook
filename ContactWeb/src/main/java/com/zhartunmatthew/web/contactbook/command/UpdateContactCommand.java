package com.zhartunmatthew.web.contactbook.command;

import com.zhartunmatthew.web.contactbook.entity.Phone;
import com.zhartunmatthew.web.contactbook.jsonbuilder.JSONBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class UpdateContactCommand implements AbstractCommand {
    private static Logger log = Logger.getLogger(UpdateContactCommand.class);
    private final static String COMMAND_URL = "contact.jsp";

    @Override
    public String execute(HttpServletRequest request) {

        String jsonPhones = request.getParameter("old-phones");
        ArrayList<Phone> oldPhones = JSONBuilder.buildPhoneListFromJSON(jsonPhones);

        for (Phone phone : oldPhones) {
            log.info(phone);
        }

        String newJsonPhones = request.getParameter("new-phones");
        ArrayList<Phone> newPhones = JSONBuilder.buildPhoneListFromJSON(newJsonPhones);
        for (Phone phone : newPhones) {
            log.info(phone);
        }

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
