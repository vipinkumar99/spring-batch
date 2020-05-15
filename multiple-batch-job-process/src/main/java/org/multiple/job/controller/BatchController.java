package org.multiple.job.controller;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multipleJob")
public class BatchController {
	private static Logger logger = Logger.getLogger(BatchController.class.getName());

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	@Qualifier("firstJob")
	private Job job1;
	@Autowired
	@Qualifier("secondJob")
	private Job job2;
	@Autowired
	@Qualifier("thirdJob")
	private Job job3;
	@Autowired
	@Qualifier("lastJob")
	private Job job4;

//	@GetMapping("/start")
//	public String startBatch() throws JobExecutionAlreadyRunningException, JobRestartException,
//			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
//		Map<String, JobParameter> maps = new HashMap<>();
//		maps.put("timestamp", new JobParameter(System.currentTimeMillis()));
//		maps.put("name", new JobParameter("MultipleBatchJobProcessing"));
//		JobParameters jobParameters = new JobParameters(maps);
//		JobExecution execution = jobLauncher.run(job, jobParameters);
//		logger.info("job status:" + execution.getStatus());
//		
//		job.
//		
//		return execution.getStatus().name();
//	}

	@GetMapping("/start")
	public String startBatch() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("timestamp", new JobParameter(System.currentTimeMillis()));
		maps.put("name", new JobParameter("MultipleBatchJobProcessing"));
		JobParameters jobParameters = new JobParameters(maps);
		JobExecution execution = jobLauncher.run(job1, jobParameters);
		logger.info("first job started!");
		if (execution.getStatus().name().equals("COMPLETED")) {
			execution = jobLauncher.run(job2, jobParameters);
			logger.info("second job started!");
			if (execution.getStatus().name().equals("COMPLETED")) {
				logger.info("third job started!");
				execution = jobLauncher.run(job3, jobParameters);
			}
			if (execution.getStatus().name().equals("COMPLETED")) {
				logger.info("last job started!");
				execution = jobLauncher.run(job4, jobParameters);
			}
		}
		return execution.getStatus().name();
	}

}
