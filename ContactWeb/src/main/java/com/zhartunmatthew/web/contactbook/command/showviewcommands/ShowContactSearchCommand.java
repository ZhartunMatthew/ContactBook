package com.zhartunmatthew.web.contactbook.command.showviewcommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ShowContactSearchCommand implements AbstractCommand {

    private Logger log = Logger.getLogger(ShowContactCommand.class);
    private final static String COMMAND_URL = "contact_search.jsp";

    @Override
    public String execute(HttpServletRequest request) {

        return COMMAND_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
