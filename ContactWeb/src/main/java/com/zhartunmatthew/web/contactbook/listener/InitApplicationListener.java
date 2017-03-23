package com.zhartunmatthew.web.contactbook.listener;

import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionManager.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionManager.getInstance().deregisterDrivers();
    }
}
