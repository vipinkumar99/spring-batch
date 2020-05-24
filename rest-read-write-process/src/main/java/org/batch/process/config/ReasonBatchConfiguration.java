package org.batch.process.config;

import javax.sql.DataSource;

import org.batch.process.entity.Reason;
import org.batch.process.model.Model;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class ReasonBatchConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private NotificationListener notificationListener;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private ReasonProcessor processor;
	@Autowired
	private ReasonReader reader;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ReasonPreparedStatementSetter statementSetter;

	@Bean
	public JdbcBatchItemWriter<Reason> writer() {
		final String query = "INSERT INTO reasons(date, reasonName, departmentName, serviceName) VALUES (?, ?, ?, ?)";
		JdbcBatchItemWriter<Reason> dbWriter = new JdbcBatchItemWriter<>();
		dbWriter.setDataSource(dataSource);
		dbWriter.setSql(query);
		dbWriter.setItemPreparedStatementSetter(statementSetter);
		return dbWriter;
	}

	@Bean
	public Step restStep() throws Exception {
		return stepBuilderFactory.get("RestStep").<Model, Reason>chunk(5).reader(reader).processor(processor)
				.writer(writer()).build();
	}

	@Bean
	public Job restJob() throws Exception {
		return jobBuilderFactory.get("RestJob").incrementer(new RunIdIncrementer()).listener(notificationListener)
				.flow(restStep()).end().build();
	}

}
