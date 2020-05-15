package org.location.batch.process.configuration;

import javax.sql.DataSource;

import org.location.batch.process.entity.Location;
import org.location.batch.process.model.LocationModel;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public FlatFileItemReader<LocationModel> reader() {
		FlatFileItemReaderBuilder<LocationModel> builder = new FlatFileItemReaderBuilder<LocationModel>();
		builder.name("LocationReader");
		builder.resource(new ClassPathResource("locationmapping.csv"));
		builder.delimited().names("id", "created", "updated", "area", "city", "departmentId", "subArea", "teamAssignId",
				"teamId", "empId", "reasonId").lineMapper(lineMapper());
		builder.fieldSetMapper(fieldSetMapper());
		builder.linesToSkip(1);
		return builder.build();
	}

	@Bean
	public BeanWrapperFieldSetMapper<LocationModel> fieldSetMapper() {
		BeanWrapperFieldSetMapper<LocationModel> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(LocationModel.class);
		return fieldSetMapper;
	}

	@Bean
	public LineMapper<LocationModel> lineMapper() {
		final DefaultLineMapper<LocationModel> defaultLineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(";");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id", "created", "updated", "area", "city", "departmentId", "subArea", "teamAssignId",
				"teamId", "empId", "reasonId");
		final LocationFieldSetMapper fieldSetMapper = new LocationFieldSetMapper();
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		return defaultLineMapper;
	}

	@Bean
	public LocationProcessor processor() {
		return new LocationProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Location> writer(final DataSource dataSource) {
		final String query = "INSERT INTO locations(city, area, subarea, date, time) VALUES (?, ?, ?, ?, ?)";
		JdbcBatchItemWriter<Location> databaseWriter = new JdbcBatchItemWriter<>();
		databaseWriter.setDataSource(dataSource);
		databaseWriter.setSql(query);
		ItemPreparedStatementSetter<Location> setter = new LocationPreparedStatementSetter();
		databaseWriter.setItemPreparedStatementSetter(setter);
		return databaseWriter;
	}

	@Bean
	public Job locationJob(NotificationListener listener, Step step) {
		return jobBuilderFactory.get("locationJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step)
				.end().build();
	}

	@Bean
	public Step step(JdbcBatchItemWriter<Location> writer) {
		return stepBuilderFactory.get("step").<LocationModel, Location>chunk(10).reader(reader()).processor(processor())
				.writer(writer).build();
	}

}
