package com.zhartunmatthew.web.contactbook.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ShowContactCommand implements AbstractCommand {

    private Logger log = Logger.getLogger(ShowContactCommand.class);
    private final static String COMMAND_URL = "contact.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        // code
        String id = request.getParameter("contact_id");
        request.setAttribute("id", id);
        return COMMAND_URL;
    }
}
