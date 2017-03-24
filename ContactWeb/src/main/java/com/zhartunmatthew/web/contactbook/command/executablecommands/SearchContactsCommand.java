package com.zhartunmatthew.web.contactbook.command.executablecommands;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

public class SearchContactsCommand implements AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean isRedirectedCommand() {
        return false;
    }
}
