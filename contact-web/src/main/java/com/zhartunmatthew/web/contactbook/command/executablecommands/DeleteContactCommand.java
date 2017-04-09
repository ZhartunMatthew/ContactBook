package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import com.zhartunmatthew.web.contactbook.services.ContactService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class DeleteContactCommand implements AbstractCommand {

    private final static Logger LOG = LoggerFactory.getLogger(DeleteContactCommand.class);
    private static String REDIRECT_URL = "controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String[] items = request.getParameterValues("contact-check");
            ArrayList<Long> checkedItems = new ArrayList<>();
            if (items != null) {
                for (String val : items) {
                    checkedItems.add(Long.parseLong(val));
                    LOG.info("Checked: {}", Integer.parseInt(val));
                }
                ContactService contactService = new ContactService();
                contactService.deleteContacts(checkedItems);

                request.getSession().setAttribute("action-name", "Удаление контактов");
                request.getSession().setAttribute("action-description",
                        String.format("Количество удаленных контаков: %d", checkedItems.size()));
            } else {
                LOG.info("NO CHECKED ITEMS");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Can't execute command DeleteContact", ex);
        }

        return REDIRECT_URL;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
