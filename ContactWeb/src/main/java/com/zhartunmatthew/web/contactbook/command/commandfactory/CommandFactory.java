package com.zhartunmatthew.web.contactbook.command.commandfactory;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.AddContactCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.DeleteContactCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.SendEmailCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.UpdateContactCommand;
import com.zhartunmatthew.web.contactbook.command.executablecommands.search.SearchContactsCommand;
import com.zhartunmatthew.web.contactbook.command.getcommand.DownloadAttachmentCommand;
import com.zhartunmatthew.web.contactbook.command.getcommand.GetImageCommand;
import com.zhartunmatthew.web.contactbook.command.showviewcommands.ShowContactCommand;
import com.zhartunmatthew.web.contactbook.command.showviewcommands.ShowContactListCommand;
import com.zhartunmatthew.web.contactbook.command.showviewcommands.ShowContactSearchCommand;
import com.zhartunmatthew.web.contactbook.command.showviewcommands.ShowEmailPageCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static final String PARAM_NAME = "command";
    private static Logger log = Logger.getLogger(CommandFactory.class);
    private static Map<String, AbstractCommand> commandMap = new HashMap<>();

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
            log.debug("Returned default command 'ShowContactList'");
        }

        return abstractCommand;
    }
}