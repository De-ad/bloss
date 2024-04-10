package com.brigada.bloss.listener;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalJobListener implements JobListener {

    private static final String TRIGGER_LISTENER_NAME = "GlobalJobListener";

    @Override
    public String getName() {
        return TRIGGER_LISTENER_NAME;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {

        String triggerName = context.getTrigger().getKey().toString();
        String jobName = context.getJobDetail().getKey().toString();
        // log.info("trigger : " + triggerName + " is going to fire");
        // log.info("job : " + jobName + "is going to fire");

    }

    @Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

		JobDataMap dataMap = context.getTrigger().getJobDataMap();
		boolean isRetry = dataMap.containsKey("isRetry") ? dataMap.getBoolean("isRetry") : false;
		dataMap.put("isRetry", true);

		if (jobException != null && isRetry) {
			log.error("Cannot restart job!");
		}

		if (jobException != null && !isRetry) {
			
			Trigger oldTrigger = context.getTrigger();
			String newTriggerName = context.getTrigger().getKey().getName() + "_retry";
			
			Trigger newTrigger = TriggerBuilder.newTrigger()
						.forJob(context.getJobDetail())
						.withIdentity(newTriggerName, context.getTrigger().getKey().getGroup())
						.startAt(Date.from(Instant.now().plus(1000, ChronoUnit.MILLIS)))
						.usingJobData("isRetry", true)
						.build();
			try {
				context.getScheduler().rescheduleJob(oldTrigger.getKey(), newTrigger);
			} catch (Exception e) {
				log.error("Error restarting job: " + context.getJobDetail().getKey());
			}
		}
	}

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        // pass
    }

}
