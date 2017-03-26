package com.zhartunmatthew.web.contactbook.command.abstractcommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AbstractCommand {
    String execute(HttpServletRequest request, HttpServletResponse response);
    boolean isRedirectedCommand();
}
