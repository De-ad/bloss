package com.brigada.bloss.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

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

		String triggerName = context.getTrigger().getKey().toString();
		String jobName = context.getJobDetail().getKey().toString();
		// log.info("trigger : " + triggerName + " is fired");
		// log.info("job : " + jobName + " is fired");

	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// pass
	}

}
