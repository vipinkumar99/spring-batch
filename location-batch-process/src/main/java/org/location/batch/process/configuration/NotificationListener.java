package org.location.batch.process.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener extends JobExecutionListenerSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationListener.class);
	private final String query = "SELECT distinct city from locations";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void afterJob(final JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOGGER.info("JOB FINISHED!");
			List<String> cities = jdbcTemplate.query(query, (rs, num) -> rs.getString(1));
			cities.forEach(c -> LOGGER.info(c));
		}
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
	}
}
