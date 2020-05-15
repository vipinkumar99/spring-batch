package org.batch.process.config.area;

import org.batch.process.constant.Constant;
import org.batch.process.entity.Area;
import org.batch.process.model.AreaPojo;
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
public class AreaBatchConfiguration {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private AreaItemProcessor areaItemProcessor;
	@Autowired
	private AreaItemWriter areaItemWriter;
	@Autowired
	private AreaFieldSetMapper areaFieldSetMapper;
	@Autowired
	private AreaNotificationListener areaNotificationListener;

	@Bean
	public FlatFileItemReader<AreaPojo> areaItemReader() {
		FlatFileItemReaderBuilder<AreaPojo> builder = new FlatFileItemReaderBuilder<AreaPojo>();
		builder.name("AreaItemReader");
		builder.resource(new ClassPathResource("locationmapping.csv"));
		builder.lineMapper(areaLineMapper());
		builder.linesToSkip(1);
		return builder.build();
	}

	@Bean
	public LineMapper<AreaPojo> areaLineMapper() {
		final DefaultLineMapper<AreaPojo> lineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(";");
		tokenizer.setStrict(false);
		tokenizer.setNames(Constant.HEADER);
		lineMapper.setFieldSetMapper(areaFieldSetMapper);
		lineMapper.setLineTokenizer(tokenizer);
		return lineMapper;
	}

	@Bean
	public Step areaStep() {
		return stepBuilderFactory.get("Area").<AreaPojo, Area>chunk(5).reader(areaItemReader())
				.processor(areaItemProcessor).writer(areaItemWriter).listener(areaNotificationListener).build();
	}

}
