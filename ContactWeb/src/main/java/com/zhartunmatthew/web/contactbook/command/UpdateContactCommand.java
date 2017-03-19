package com.zhartunmatthew.web.contactbook.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UpdateContactCommand implements AbstractCommand {
    private static Logger log = Logger.getLogger(UpdateContactCommand.class);
    private final static String COMMAND_URL = "contact.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String param = request.getParameter("last-name");
        String id = request.getParameter("id");
        log.info("Last name: " + param + "Id = " + id);
        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
