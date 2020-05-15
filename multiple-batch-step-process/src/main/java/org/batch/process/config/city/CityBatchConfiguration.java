package org.batch.process.config.city;

import org.batch.process.constant.Constant;
import org.batch.process.entity.City;
import org.batch.process.model.CityPojo;
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
public class CityBatchConfiguration {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private CityItemProcessor cityItemProcessor;
	@Autowired
	private CityItemWriter cityItemWriter;
	@Autowired
	private CityNotificationListener cityNotificationListener;
	@Autowired
	private CityFieldSetMapper cityFieldSetMapper;

	@Bean
	public FlatFileItemReader<CityPojo> cityItemReader() {
		FlatFileItemReaderBuilder<CityPojo> builder = new FlatFileItemReaderBuilder<>();
		builder.name("CityItemReader");
		builder.resource(new ClassPathResource("locationmapping.csv"));
		builder.lineMapper(cityLineMapper());
		builder.linesToSkip(1);
		return builder.build();
	}

	@Bean
	public LineMapper<CityPojo> cityLineMapper() {
		final DefaultLineMapper<CityPojo> lineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(";");
		tokenizer.setStrict(false);
		tokenizer.setNames(Constant.HEADER);
		lineMapper.setFieldSetMapper(cityFieldSetMapper);
		lineMapper.setLineTokenizer(tokenizer);
		return lineMapper;
	}

	@Bean
	public Step cityStep() {
		return stepBuilderFactory.get("City").<CityPojo, City>chunk(5).reader(cityItemReader())
				.processor(cityItemProcessor).writer(cityItemWriter).listener(cityNotificationListener).build();
	}

}
