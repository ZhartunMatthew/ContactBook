package com.zhartunmatthew.web.contactbook.controller;

import com.zhartunmatthew.web.contactbook.command.abstractcommand.AbstractCommand;
import com.zhartunmatthew.web.contactbook.command.commandfactory.CommandFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static Logger log = Logger.getLogger(Controller.class);
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
        log.debug("SERVLET LOG");
        AbstractCommand command = CommandFactory.createCommand(request);
        String commandURL = command.execute(request);
        log.debug("Command URL: " + commandURL);
        if(command.isRedirectedCommand()) {
            response.sendRedirect(commandURL);
        } else {
            request.getRequestDispatcher(commandURL).forward(request, response);
        }
    }
}
