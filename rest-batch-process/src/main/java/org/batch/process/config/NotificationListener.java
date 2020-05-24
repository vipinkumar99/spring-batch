package org.batch.process.config;

import java.util.logging.Logger;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener extends JobExecutionListenerSupport {
	private static Logger logger = Logger.getLogger(NotificationListener.class.getName());

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus().name().equals("COMPLETED")) {
			String name = jobExecution.getJobParameters().getString("name");
			if (name.equals("ExcelProcessing")) {
				logger.info("Excel writing job is completed!");
			} else if (name.equals("CsvProcessing")) {
				logger.info("Csv writing job is completed!");
			} else if (name.equals("JsonProcessing")) {
				logger.info("Json writing job is completed!");
			} else if (name.equals("XmlProcessing")) {
				logger.info("Xml writing job is completed!");
			}
		}
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		String name = jobExecution.getJobParameters().getString("name");
		if (name.equals("ExcelProcessing")) {
			logger.info("Excel writing job is started!");
		} else if (name.equals("CsvProcessing")) {
			logger.info("Csv writing job is started!");
		} else if (name.equals("JsonProcessing")) {
			logger.info("Json writing job is started!");
		} else if (name.equals("XmlProcessing")) {
			logger.info("Xml writing job is started!");
		}
	}

}
