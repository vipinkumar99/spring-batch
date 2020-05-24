package org.batch.process.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchController {
	private static Logger logger = Logger.getLogger(BatchController.class.getName());

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;

	@GetMapping("/start")
	public String startBatch() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("timestamp", new JobParameter(System.currentTimeMillis()));
		maps.put("name", new JobParameter("TrackingBatchProcessing"));
		JobParameters jobParameters = new JobParameters(maps);
		JobExecution execution = jobLauncher.run(job, jobParameters);
		logger.info("job status:" + execution.getStatus());
		return execution.getStatus().name();
	}

}
