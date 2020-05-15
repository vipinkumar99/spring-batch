package org.batch.process.configuration;

import javax.sql.DataSource;

import org.batch.process.entity.Voltage;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
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
	public FlatFileItemReader<Voltage> reader() {
		FlatFileItemReaderBuilder<Voltage> builder = new FlatFileItemReaderBuilder<Voltage>();
		builder.name("VoltageItemReader");
		builder.resource(new ClassPathResource("Volts.csv"));
		builder.delimited().names("volt", "time").lineMapper(lineMapper());
		builder.fieldSetMapper(fieldSetMapper());
		return builder.build();
	}

	@Bean
	public BeanWrapperFieldSetMapper<Voltage> fieldSetMapper() {
		BeanWrapperFieldSetMapper<Voltage> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Voltage.class);
		return fieldSetMapper;
	}

	@Bean
	public LineMapper<Voltage> lineMapper() {
		final DefaultLineMapper<Voltage> defaultLineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(";");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("volt", "time");
		final VoltageFieldSetMapper fieldSetMapper = new VoltageFieldSetMapper();
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		return defaultLineMapper;
	}

	@Bean
	public VoltageProcessor processor() {
		return new VoltageProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Voltage> writer(final DataSource dataSource) {
		JdbcBatchItemWriterBuilder<Voltage> builder = new JdbcBatchItemWriterBuilder<>();
		builder.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		builder.sql("INSERT INTO voltages (volt, time) VALUES (:volt, :time)");
		builder.dataSource(dataSource);
		return builder.build();
	}

	@Bean
	public Job voltageJob(NotificationListener listener, Step step) {
		return jobBuilderFactory.get("voltageJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step)
				.end().build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Voltage> writer) {
		return stepBuilderFactory.get("step").<Voltage, Voltage>chunk(10).reader(reader()).processor(processor())
				.writer(writer).build();
	}

}
