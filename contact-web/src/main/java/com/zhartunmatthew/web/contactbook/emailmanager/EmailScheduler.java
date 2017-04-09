package com.zhartunmatthew.web.contactbook.emailmanager;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class EmailScheduler {

    private Scheduler emailScheduler;
    private final static Logger LOG = LoggerFactory.getLogger(EmailScheduler.class);

    public void init() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("emailconfig");
            String emailJobName = bundle.getObject("email_job_name").toString();
            String emailJobGroup = bundle.getObject("email_job_group").toString();
            int timeHour = Integer.parseInt(bundle.getObject("dispatch_time_hour").toString());
            int timeMinute = Integer.parseInt(bundle.getObject("dispatch_time_minute").toString());

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            emailScheduler = schedulerFactory.getScheduler();
            JobDetail sendEmailJob = JobBuilder.newJob(SendEmailJob.class)
                    .withIdentity(emailJobName, emailJobGroup)
                    .build();

            Trigger sendEmailTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(emailJobName, emailJobGroup)
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(timeHour, timeMinute))
                    .build();

            emailScheduler.scheduleJob(sendEmailJob, sendEmailTrigger);
            emailScheduler.start();

        } catch (SchedulerException ex) {
            LOG.error("Error while init scheduler", ex);
        }
    }

    public void shutdown() {
        try {
            emailScheduler.shutdown(false);
        } catch (SchedulerException e) {
            LOG.error("Error while shutdown scheduler", e);
        }
    }
}
