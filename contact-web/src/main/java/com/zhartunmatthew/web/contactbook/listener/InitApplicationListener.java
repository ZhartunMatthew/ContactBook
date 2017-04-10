package com.zhartunmatthew.web.contactbook.listener;

import com.zhartunmatthew.web.contactbook.dbmanager.ConnectionManager;
import com.zhartunmatthew.web.contactbook.dbmanager.exception.ConnectionManagerException;
import com.zhartunmatthew.web.contactbook.emailmanager.EmailScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitApplicationListener implements ServletContextListener {
    private EmailScheduler emailScheduler = null;
    private static final Logger LOG = LoggerFactory.getLogger(InitApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionManager.getInstance();
            emailScheduler = new EmailScheduler();
            emailScheduler.init();
        } catch (ConnectionManagerException ex) {
            LOG.error("Can't init application");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionManager.getInstance().deregisterDrivers();
            emailScheduler.shutdown();
        } catch (ConnectionManagerException ex) {
            LOG.error("Can't deregister drivers");
        }
    }
}
