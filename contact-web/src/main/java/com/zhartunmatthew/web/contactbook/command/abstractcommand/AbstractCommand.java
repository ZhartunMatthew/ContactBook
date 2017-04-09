package com.zhartunmatthew.web.contactbook.command.abstractcommand;

import com.zhartunmatthew.web.contactbook.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AbstractCommand {
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException;

    boolean isRedirectedCommand();
}
