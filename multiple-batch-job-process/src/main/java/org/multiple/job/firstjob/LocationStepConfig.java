package org.multiple.job.firstjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.config.CommonFieldSetMapper;
import org.multiple.job.entity.Location;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class LocationStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DelimitedLineTokenizer lineTokenizer;
	@Autowired
	private CommonFieldSetMapper fieldSetMapper;
	@Autowired
	private LocationProcessor locationProcessor;
	@Autowired
	private LocationWriter locationWriter;

	@Bean
	public FlatFileItemReader<LocationPojo> locationReader() {
		FlatFileItemReaderBuilder<LocationPojo> builder = new FlatFileItemReaderBuilder<>();
		builder.name("LocationReader");
		builder.resource(new ClassPathResource("location.csv"));
		builder.lineMapper(locationLineMapper());
		builder.linesToSkip(1);
		return builder.build();
	}

	@Bean
	public LineMapper<LocationPojo> locationLineMapper() {
		final DefaultLineMapper<LocationPojo> lineMapper = new DefaultLineMapper<>();
		lineMapper.setFieldSetMapper(fieldSetMapper);
		lineMapper.setLineTokenizer(lineTokenizer);
		return lineMapper;
	}

	@Bean
	public Step locationStep() {
		return stepBuilderFactory.get("Location").<LocationPojo, Location>chunk(50).reader(locationReader())
				.processor(locationProcessor).writer(locationWriter).build();
	}

}
