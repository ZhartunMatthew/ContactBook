package com.zhartunmatthew.web.contactbook.controller;

import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import com.zhartunmatthew.web.contactbook.entity.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class Controller extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Contact> contacts;
        try (ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(ContactDAO.class)) {
            contacts = contactDAO.readAll();
        }
        request.setAttribute("contacts", contacts);
        request.getRequestDispatcher("/contact_list.jsp").forward(request, response);
    }
}
