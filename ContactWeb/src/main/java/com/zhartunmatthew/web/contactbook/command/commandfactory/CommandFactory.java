package com.zhartunmatthew.web.contactbook.command.commandfactory;

import com.zhartunmatthew.web.contactbook.command.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class CommandFactory {

    private static final String PARAM_NAME = "command";
    private static Logger log = Logger.getLogger(CommandFactory.class);

    public static AbstractCommand createCommand(HttpServletRequest request) {
        log.debug("Command factory");
        String command = request.getParameter(PARAM_NAME);
        AbstractCommand abstractCommand = null;

        if(Objects.equals("show_contact_list", command)) {
            abstractCommand = new ShowContactListCommand();
            log.debug("Returned command 'ShowContactList'");
        }

        if(Objects.equals("show_contact", command)) {
            abstractCommand = new ShowContactCommand();
            log.debug("Returned command 'ShowContact'");
        }

        if(Objects.equals("update_contact", command)) {
            abstractCommand = new UpdateContactCommand();
            log.debug("Returned command 'UpdateContact'");
        }

        if(Objects.equals("add_contact", command)) {
            abstractCommand = new AddContactCommand();
            log.debug("Returned command 'AddContact'");
        }

        if(abstractCommand == null) {
            abstractCommand = new ShowContactListCommand();
            log.debug("Returned default command 'ShowContactList'");
        }

        return abstractCommand;
    }
}
