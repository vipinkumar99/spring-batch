package org.batch.process.config.area;

import java.util.logging.Logger;

import org.batch.process.dao.AreaDao;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AreaNotificationListener implements StepExecutionListener {
	private static Logger logger = Logger.getLogger(AreaNotificationListener.class.getName());

	@Autowired
	private AreaDao areaDao;

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("Total area modified in db:" + areaDao.updateModified(false));
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info(stepExecution.getStepName() + " step is started......");
	}

}
