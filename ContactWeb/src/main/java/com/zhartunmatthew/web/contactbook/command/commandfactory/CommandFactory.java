package com.zhartunmatthew.web.contactbook.command.commandfactory;

import com.zhartunmatthew.web.contactbook.command.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.ShowContactList;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

    private static final String PARAM_NAME = "command";

    public static AbstractCommand createCommand(HttpServletRequest request) {
        String command = request.getParameter(PARAM_NAME);
        AbstractCommand abstractCommand = null;

        if(command.equals("show_contact_list")) {
            abstractCommand = new ShowContactList();
        }

        return abstractCommand;
    }
}
