package org.batch.process.configuration;

import java.util.List;

import org.batch.process.entity.Voltage;
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
	private final String query = "SELECT id,volt,time FROM voltages";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void afterJob(final JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOGGER.info("JOB FINISHED!");
			List<Voltage> voltage = jdbcTemplate.query(query,
					(rs, num) -> new Voltage(rs.getLong(1), rs.getBigDecimal(2), rs.getDouble(3)));
			voltage.forEach(v -> LOGGER.info(v.toString()));
		}
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
	}
}
