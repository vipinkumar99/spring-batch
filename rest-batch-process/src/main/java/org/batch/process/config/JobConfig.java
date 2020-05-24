package org.batch.process.config;

import org.batch.process.entity.Charge;
import org.batch.process.model.Model;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private NotificationListener jobListener;
	@Autowired
	private JdbcPagingItemReader<Charge> dbReader;
	@Autowired
	private Processor processor;
	@Autowired
	private ExcelWriter excelWriter;
	@Autowired
	private CsvWriter csvWriter;
	@Autowired
	private JsonWriter jsonWriter;
	@Autowired
	private XmlWriter xmlWriter;

	@Bean
	public Step excelStep() throws Exception {
		return stepBuilderFactory.get("ExcelStep").<Charge, Model>chunk(50).reader(dbReader).processor(processor)
				.writer(excelWriter).build();
	}

	@Bean
	public Step csvStep() throws Exception {
		return stepBuilderFactory.get("CsvStep").<Charge, Model>chunk(50).reader(dbReader).processor(processor)
				.writer(csvWriter).build();
	}

	@Bean
	public Step jsonStep() throws Exception {
		return stepBuilderFactory.get("JsonStep").<Charge, Model>chunk(50).reader(dbReader).processor(processor)
				.writer(jsonWriter).build();
	}

	@Bean
	public Step xmlStep() throws Exception {
		return stepBuilderFactory.get("XmlStep").<Charge, Model>chunk(50).reader(dbReader).processor(processor)
				.writer(xmlWriter).build();
	}

	@Bean
	public Job excelJob() throws Exception {
		return jobBuilderFactory.get("ExcelJob").incrementer(new RunIdIncrementer()).listener(jobListener)
				.flow(excelStep()).end().build();
	}

	@Bean
	public Job csvJob() throws Exception {
		return jobBuilderFactory.get("CsvJob").incrementer(new RunIdIncrementer()).listener(jobListener).flow(csvStep())
				.end().build();
	}

	@Bean
	public Job jsonJob() throws Exception {
		return jobBuilderFactory.get("JsonJob").incrementer(new RunIdIncrementer()).listener(jobListener)
				.flow(jsonStep()).end().build();
	}

	@Bean
	public Job xmlJob() throws Exception {
		return jobBuilderFactory.get("XmlJob").incrementer(new RunIdIncrementer()).listener(jobListener).flow(xmlStep())
				.end().build();
	}
}
