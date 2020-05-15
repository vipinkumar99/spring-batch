package org.batch.process.config.subarea;

import java.util.logging.Logger;

import org.batch.process.dao.SubareaDao;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubareaNotificationListener implements StepExecutionListener {
	private static Logger logger = Logger.getLogger(SubareaNotificationListener.class.getName());

	@Autowired
	private SubareaDao subareaDao;

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("Total subarea modified in db:" + subareaDao.updateModified(false));
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info(stepExecution.getStepName() + " step is started......");
	}

}
