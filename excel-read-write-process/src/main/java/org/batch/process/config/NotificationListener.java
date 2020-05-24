package org.batch.process.config;

import java.util.logging.Logger;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener extends JobExecutionListenerSupport {
	private static final Logger logger = Logger.getLogger(NotificationListener.class.getName());

	@Override
	public void afterJob(final JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("Job finished!");
			logger.info("total charge write:"+ChargeExportWriter.count);
		}
	}

}
