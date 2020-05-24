package org.batch.process.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.batch.process.model.Model;
import org.batch.process.model.Tracking;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

@Configuration
@EnableBatchProcessing
public class TrackingBatchConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private NotificationListener notificationListener;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private TrackingHeader header;
	@Autowired
	private TrackingProcessor processor;
	@Autowired
	private TrackingRowMapper rowMapper;

	private static String path = "C:\\Users\\Dell\\Desktop\\New folder\\";

	@Bean
	public JdbcPagingItemReader<Tracking> reader() {
		JdbcPagingItemReader<Tracking> dbreader = new JdbcPagingItemReader<>();
		dbreader.setDataSource(dataSource);
		dbreader.setPageSize(500);
		dbreader.setFetchSize(1000);
		dbreader.setQueryProvider(pagingQueryProvider());
		dbreader.setMaxItemCount(2000);
		dbreader.setRowMapper(rowMapper);
		try {
			dbreader.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbreader;
	}

	private MySqlPagingQueryProvider pagingQueryProvider() {
		String select = "select id, batteryPercentage, calculatedDistanceInKm, calculatedSpeed, created, deviceTime, isMobileCharging, latitude, networkOperator, provider, simOperator, speed, deviceTimeStamp, longitude";
		MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
		provider.setSelectClause(select);
		provider.setFromClause(" from employee_tracking_archive ");
		Map<String, Order> sortKeys = new LinkedHashMap<>();
		sortKeys.put("id", Order.ASCENDING);
		provider.setSortKeys(sortKeys);
		return provider;
	}

	@Bean
	public FlatFileItemWriter<Model> csvWriter() {
		String file = path + "Tracking.csv";
		FlatFileItemWriter<Model> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource(file));
		writer.setShouldDeleteIfEmpty(true);
		writer.setHeaderCallback(header);
		writer.setAppendAllowed(true);
		DelimitedLineAggregator<Model> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(",");
		BeanWrapperFieldExtractor<Model> extractor = new BeanWrapperFieldExtractor<>();
		extractor.setNames(Model.NAMES);
		aggregator.setFieldExtractor(extractor);
		writer.setLineAggregator(aggregator);
		return writer;
	}

//	@Bean
//	public FlatFileItemWriter<Model> jsonWriter() throws Exception {
//		String file = path + "Tracking.json";
//		FlatFileItemWriter<Model> writer = new FlatFileItemWriter<>();
//		writer.setLineAggregator(new JsonLineAggregator());
//		writer.setResource(new FileSystemResource(file));
//		writer.afterPropertiesSet();
//		return writer;
//	}

	@Bean
	public JsonFileItemWriter<Model> jsonWriter() {
		String file = path + "Tracking.json";
		JsonFileItemWriterBuilder<Model> builder = new JsonFileItemWriterBuilder<>();
		builder.jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>());
		builder.resource(new FileSystemResource(file));
		builder.name("JsonObjectWriter");
		return builder.build();
	}

	@Bean
	public StaxEventItemWriter<Model> xmlWriter() throws Exception {
		String file = path + "Tracking.xml";
		Map<String, Class<?>> aliases = new HashMap<>();
		aliases.put("tracking", Model.class);
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliases);
		StaxEventItemWriter<Model> writer = new StaxEventItemWriter<>();
		writer.setRootTagName("trackings");
		writer.setMarshaller(marshaller);
		writer.setResource(new FileSystemResource(file));
		writer.afterPropertiesSet();
		return writer;
	}

	@Bean
	public CompositeItemWriter<Model> writer() throws Exception {
		List<ItemWriter<? super Model>> writers = new ArrayList<>();
		writers.add(csvWriter());
		writers.add(xmlWriter());
		writers.add(jsonWriter());
		CompositeItemWriter<Model> compositeItemWriter = new CompositeItemWriter<>();
		compositeItemWriter.setDelegates(writers);
		compositeItemWriter.afterPropertiesSet();
		return compositeItemWriter;
	}

	@Bean
	public Step step() throws Exception {
		return stepBuilderFactory.get("Tracking").<Tracking, Model>chunk(50).reader(reader()).processor(processor)
				.writer(writer()).build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("TrackingJob").incrementer(new RunIdIncrementer()).listener(notificationListener)
				.flow(step()).end().build();
	}

}
