package org.batch.process.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.batch.process.config.CsvWriter;
import org.batch.process.config.ExcelWriter;
import org.batch.process.config.JsonWriter;
import org.batch.process.config.XmlWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ChargeServiceImpl implements ChargeService {

	@Autowired
	private ExcelWriter excelWriter;
	@Autowired
	private CsvWriter csvWriter;
	@Autowired
	private JsonWriter jsonWriter;
	@Autowired
	private XmlWriter xmlWriter;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	@Qualifier("excelJob")
	private Job excelJob;
	@Autowired
	@Qualifier("csvJob")
	private Job csvJob;
	@Autowired
	@Qualifier("jsonJob")
	private Job jsonJob;
	@Autowired
	@Qualifier("xmlJob")
	private Job xmlJob;

	@Override
	public Resource getExcelFile() throws Exception {
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("timestamp", new JobParameter(System.currentTimeMillis()));
		maps.put("name", new JobParameter("ExcelProcessing"));
		JobParameters jobParameters = new JobParameters(maps);
		JobExecution execution = jobLauncher.run(excelJob, jobParameters);
		ByteArrayOutputStream stream = null;
		if (execution.getStatus().name().equals("COMPLETED")) {
			stream = excelWriter.getData();
		}
		return stream != null ? new ByteArrayResource(stream.toByteArray()) : new ByteArrayResource(null);
	}

	@Override
	public Resource getCsvFile() throws Exception {
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("timestamp", new JobParameter(System.currentTimeMillis()));
		maps.put("name", new JobParameter("CsvProcessing"));
		JobParameters jobParameters = new JobParameters(maps);
		JobExecution execution = jobLauncher.run(csvJob, jobParameters);
		ByteArrayOutputStream stream = null;
		if (execution.getStatus().name().equals("COMPLETED")) {
			stream = csvWriter.getData();
		}
		return stream != null ? new ByteArrayResource(stream.toByteArray()) : new ByteArrayResource(null);
	}

	@Override
	public Resource getXmlFile() throws Exception {
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("timestamp", new JobParameter(System.currentTimeMillis()));
		maps.put("name", new JobParameter("XmlProcessing"));
		JobParameters jobParameters = new JobParameters(maps);
		JobExecution execution = jobLauncher.run(xmlJob, jobParameters);
		ByteArrayOutputStream stream = null;
		if (execution.getStatus().name().equals("COMPLETED")) {
			stream = xmlWriter.getData();
		}
		return stream != null ? new ByteArrayResource(stream.toByteArray()) : new ByteArrayResource(null);
	}

	@Override
	public Resource getJsonFile() throws Exception {
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("timestamp", new JobParameter(System.currentTimeMillis()));
		maps.put("name", new JobParameter("JsonProcessing"));
		JobParameters jobParameters = new JobParameters(maps);
		JobExecution execution = jobLauncher.run(jsonJob, jobParameters);
		ByteArrayOutputStream stream = null;
		if (execution.getStatus().name().equals("COMPLETED")) {
			stream = jsonWriter.getData();
		}
		return stream != null ? new ByteArrayResource(stream.toByteArray()) : new ByteArrayResource(null);
	}

}
