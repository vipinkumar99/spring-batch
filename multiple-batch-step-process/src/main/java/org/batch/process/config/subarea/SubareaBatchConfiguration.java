package org.batch.process.config.subarea;

import org.batch.process.constant.Constant;
import org.batch.process.entity.Subarea;
import org.batch.process.model.SubareaPojo;
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
public class SubareaBatchConfiguration {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private SubareaItemProcessor subareaItemProcessor;
	@Autowired
	private SubareaItemWriter subareaItemWriter;
	@Autowired
	private SubareaFieldSetMapper subareaFieldSetMapper;
	@Autowired
	private SubareaNotificationListener subareaNotificationListener;

	@Bean
	public FlatFileItemReader<SubareaPojo> subareaItemReader() {
		FlatFileItemReaderBuilder<SubareaPojo> builder = new FlatFileItemReaderBuilder<SubareaPojo>();
		builder.name("SubareaItemReader");
		builder.resource(new ClassPathResource("locationmapping.csv"));
		builder.lineMapper(subareaLineMapper());
		builder.linesToSkip(1);
		return builder.build();
	}

	@Bean
	public LineMapper<SubareaPojo> subareaLineMapper() {
		final DefaultLineMapper<SubareaPojo> lineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(";");
		tokenizer.setStrict(false);
		tokenizer.setNames(Constant.HEADER);
		lineMapper.setFieldSetMapper(subareaFieldSetMapper);
		lineMapper.setLineTokenizer(tokenizer);
		return lineMapper;
	}

	@Bean
	public Step subareaStep() {
		return stepBuilderFactory.get("Subarea").<SubareaPojo, Subarea>chunk(5).reader(subareaItemReader())
				.processor(subareaItemProcessor).writer(subareaItemWriter).listener(subareaNotificationListener)
				.build();
	}

}
