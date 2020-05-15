package org.multiple.job.secondjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.config.CommonFieldSetMapper;
import org.multiple.job.entity.Floor;
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
public class FloorStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DelimitedLineTokenizer lineTokenizer;
	@Autowired
	private CommonFieldSetMapper fieldSetMapper;
	@Autowired
	private FloorProcessor floorProcessor;
	@Autowired
	private FloorWriter floorWriter;

	@Bean
	public FlatFileItemReader<LocationPojo> floorReader() {
		FlatFileItemReaderBuilder<LocationPojo> builder = new FlatFileItemReaderBuilder<>();
		builder.name("FloorReader");
		builder.resource(new ClassPathResource("location.csv"));
		builder.lineMapper(floorLineMapper());
		builder.linesToSkip(1);
		return builder.build();
	}

	@Bean
	public LineMapper<LocationPojo> floorLineMapper() {
		final DefaultLineMapper<LocationPojo> lineMapper = new DefaultLineMapper<>();
		lineMapper.setFieldSetMapper(fieldSetMapper);
		lineMapper.setLineTokenizer(lineTokenizer);
		return lineMapper;
	}

	@Bean
	public Step floorStep() {
		return stepBuilderFactory.get("Floor").<LocationPojo, Floor>chunk(50).reader(floorReader())
				.processor(floorProcessor).writer(floorWriter).build();
	}
}
