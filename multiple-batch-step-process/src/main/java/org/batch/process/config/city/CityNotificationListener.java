package org.batch.process.config.city;

import java.util.logging.Logger;

import org.batch.process.dao.CityDao;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityNotificationListener extends StepExecutionListenerSupport {
	private static Logger logger = Logger.getLogger(CityNotificationListener.class.getName());

	@Autowired
	private CityDao cityDao;

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("Total city modified in db:" + cityDao.updateModified(false));
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info(stepExecution.getStepName() + " step is started......");
	}

}
