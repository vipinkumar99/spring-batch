package org.multiple.job.firstjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.config.CommonFieldSetMapper;
import org.multiple.job.entity.SocietyLocation;
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
public class SocietyLocationStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DelimitedLineTokenizer lineTokenizer;
	@Autowired
	private CommonFieldSetMapper fieldSetMapper;
	@Autowired
	private SocietyLocationProcessor societyLocationProcessor;
	@Autowired
	private SocietyLocationWriter societyLocationWriter;

	@Bean
	public FlatFileItemReader<LocationPojo> societyLocationReader() {
		FlatFileItemReaderBuilder<LocationPojo> builder = new FlatFileItemReaderBuilder<>();
		builder.name("SocietyLocationReader");
		builder.resource(new ClassPathResource("location.csv"));
		builder.lineMapper(societyLocationLineMapper());
		builder.linesToSkip(1);
		return builder.build();
	}

	@Bean
	public LineMapper<LocationPojo> societyLocationLineMapper() {
		final DefaultLineMapper<LocationPojo> lineMapper = new DefaultLineMapper<>();
		lineMapper.setFieldSetMapper(fieldSetMapper);
		lineMapper.setLineTokenizer(lineTokenizer);
		return lineMapper;
	}

	@Bean
	public Step societyLocationStep() {
		return stepBuilderFactory.get("SocietyLocation").<LocationPojo, SocietyLocation>chunk(50)
				.reader(societyLocationReader()).processor(societyLocationProcessor).writer(societyLocationWriter)
				.build();
	}

}
