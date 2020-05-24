package org.batch.process.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
@EnableScheduling
public class BatchController {
	private static Logger logger = Logger.getLogger(BatchController.class.getName());
	private AtomicBoolean enable = new AtomicBoolean(false);
	private AtomicInteger runCounter = new AtomicInteger(0);

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;

	@GetMapping("/start")
	public void startBatch(@RequestParam(required = true, defaultValue = "STOP") String status)
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException {
		enable.set(status.equals("START") ? true : false);
		if (!status.equals("START")) {
			runCounter.getAndIncrement();
		}
		runJob();
	}

	@Scheduled(fixedDelay = 30000)
	private void runJob() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		if (enable.get()) {
			long current = System.currentTimeMillis();
			Map<String, JobParameter> maps = new HashMap<>();
			maps.put("JobId", new JobParameter(current));
			maps.put("name", new JobParameter("SchedulingBatchProcessing"));
			String date = "T" + new SimpleDateFormat("ddMMyyyy-HHmmss").format(new Date(current));
			maps.put("fileName", new JobParameter(date));
			JobParameters jobParameters = new JobParameters(maps);
			logger.info(current + " job is started!");
			JobExecution execution = jobLauncher.run(job, jobParameters);
			logger.info(current + " job status:" + execution.getStatus());
		} else {
			logger.info("Job is stoped:" + runCounter.get() + " times");
		}
	}

}
