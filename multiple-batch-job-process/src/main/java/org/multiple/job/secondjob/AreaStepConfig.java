package org.multiple.job.secondjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.config.CommonFieldSetMapper;
import org.multiple.job.entity.Area;
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
public class AreaStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DelimitedLineTokenizer lineTokenizer;
	@Autowired
	private CommonFieldSetMapper fieldSetMapper;
	@Autowired
	private AreaProcessor areaProcessor;
	@Autowired
	private AreaWriter areaWriter;

	@Bean
	public FlatFileItemReader<LocationPojo> areaReader() {
		FlatFileItemReaderBuilder<LocationPojo> builder = new FlatFileItemReaderBuilder<>();
		builder.name("AreaReader");
		builder.resource(new ClassPathResource("location.csv"));
		builder.lineMapper(areaLineMapper());
		builder.linesToSkip(1);
		return builder.build();
	}

	@Bean
	public LineMapper<LocationPojo> areaLineMapper() {
		final DefaultLineMapper<LocationPojo> lineMapper = new DefaultLineMapper<>();
		lineMapper.setFieldSetMapper(fieldSetMapper);
		lineMapper.setLineTokenizer(lineTokenizer);
		return lineMapper;
	}

	@Bean
	public Step areaStep() {
		return stepBuilderFactory.get("Area").<LocationPojo, Area>chunk(50).reader(areaReader())
				.processor(areaProcessor).writer(areaWriter).build();
	}
}
