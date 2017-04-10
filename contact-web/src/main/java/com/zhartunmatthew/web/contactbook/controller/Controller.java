package com.zhartunmatthew.web.contactbook.controller;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.commandfactory.CommandFactory;
import com.zhartunmatthew.web.contactbook.command.exception.CommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private final static Logger LOG = LoggerFactory.getLogger(Controller.class);
    private final static String ERROR_PAGE_URL = "/error.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AbstractCommand command = CommandFactory.createCommand(request);
            String commandURL = command.execute(request, response);
            LOG.debug("Command URL: " + commandURL);
            if (command.isRedirectedCommand()) {
                response.sendRedirect(commandURL);
            } else if (commandURL != null) {
                request.getRequestDispatcher(commandURL).forward(request, response);
            }
        } catch (CommandException ex) {
            LOG.error("Can't process request", ex);
            request.getRequestDispatcher(ERROR_PAGE_URL).forward(request, response);
        }

    }
}
