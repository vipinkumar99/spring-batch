package org.multiple.job.thirdjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.config.CommonFieldSetMapper;
import org.multiple.job.entity.Mapping;
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
public class MappingStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DelimitedLineTokenizer lineTokenizer;
	@Autowired
	private CommonFieldSetMapper fieldSetMapper;
	@Autowired
	private MappingProcessor mappingProcessor;
	@Autowired
	private MappingWriter mappingWriter;

	@Bean
	public FlatFileItemReader<LocationPojo> mappingReader() {
		FlatFileItemReaderBuilder<LocationPojo> builder = new FlatFileItemReaderBuilder<>();
		builder.name("MappingReader");
		builder.resource(new ClassPathResource("location.csv"));
		builder.lineMapper(mappingLineMapper());
		builder.linesToSkip(1);
		return builder.build();
	}

	@Bean
	public LineMapper<LocationPojo> mappingLineMapper() {
		final DefaultLineMapper<LocationPojo> lineMapper = new DefaultLineMapper<>();
		lineMapper.setFieldSetMapper(fieldSetMapper);
		lineMapper.setLineTokenizer(lineTokenizer);
		return lineMapper;
	}

	@Bean
	public Step mappingStep() {
		return stepBuilderFactory.get("Mapping").<LocationPojo, Mapping>chunk(50).reader(mappingReader())
				.processor(mappingProcessor).writer(mappingWriter).build();
	}
}
