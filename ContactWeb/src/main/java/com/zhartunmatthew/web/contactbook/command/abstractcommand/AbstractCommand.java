package com.zhartunmatthew.web.contactbook.command.abstractcommand;

import javax.servlet.http.HttpServletRequest;

public interface AbstractCommand {
    String execute(HttpServletRequest request);
    boolean isRedirectedCommand();
}
