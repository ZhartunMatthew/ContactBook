package com.zhartunmatthew.web.contactbook.command.commandfactory;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.AddContactCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.DeleteContactCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.SendEmailCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.UpdateContactCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.SearchContactsCommand;
import com.zhartunmatthew.web.contactbook.command.getcommand.DownloadAttachmentCommand;
import com.zhartunmatthew.web.contactbook.command.getcommand.GetImageCommand;
import com.zhartunmatthew.web.contactbook.command.showviewcommands.ShowContactCommand;
import com.zhartunmatthew.web.contactbook.command.showviewcommands.ShowContactListCommand;
import com.zhartunmatthew.web.contactbook.command.showviewcommands.ShowContactSearchCommand;
import com.zhartunmatthew.web.contactbook.command.showviewcommands.ShowEmailPageCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static final String PARAM_NAME = "command";
    private final static Logger LOG = LoggerFactory.getLogger(CommandFactory.class);
    private final static Map<String, AbstractCommand> commandMap = new HashMap<>();

    static {
        commandMap.put("show_contact_list", new ShowContactListCommand());
        commandMap.put("show_contact", new ShowContactCommand());
        commandMap.put("show_contact_search", new ShowContactSearchCommand());
        commandMap.put("search_contacts", new SearchContactsCommand());
        commandMap.put("update_contact", new UpdateContactCommand());
        commandMap.put("add_contact", new AddContactCommand());
        commandMap.put("delete_contact", new DeleteContactCommand());
        commandMap.put("get_image", new GetImageCommand());
        commandMap.put("download_attachment", new DownloadAttachmentCommand());
        commandMap.put("show_email_page", new ShowEmailPageCommand());
        commandMap.put("send_email", new SendEmailCommand());
    }

    public static AbstractCommand createCommand(HttpServletRequest request) {
        String command = request.getParameter(PARAM_NAME);
        AbstractCommand abstractCommand = commandMap.get(command);
        if(abstractCommand == null) {
            abstractCommand = new ShowContactListCommand();
            LOG.debug("Returned default command 'ShowContactList'");
        } else {
            LOG.debug(String.format("Returned command '%s'", command));
        }

        return abstractCommand;
    }
}