package com.zhartunmatthew.web.contactbook.listener;

import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionManager;
import com.zhartunmatthew.web.contactbook.emailmanager.EmailScheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionManager.getInstance();
        EmailScheduler.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionManager.getInstance().deregisterDrivers();
        EmailScheduler.shutdown();
    }
}
