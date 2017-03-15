package com.zhartunmatthew.web.contactbook.command;

import javax.servlet.http.HttpServletRequest;

public interface AbstractCommand {
    String execute(HttpServletRequest request);
}
