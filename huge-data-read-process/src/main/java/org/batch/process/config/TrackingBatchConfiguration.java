package org.batch.process.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

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

	@Bean
	public Job job() {
		return jobBuilderFactory.get("TrackingJob").incrementer(new RunIdIncrementer()).listener(notificationListener)
				.flow(step()).end().build();
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("Tracking").<Tracking, Model>chunk(50).reader(reader()).processor(processor)
				.writer(writer()).build();
	}

	@Bean
	public FlatFileItemWriter<Model> writer() {
		FlatFileItemWriter<Model> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource("C:\\Users\\Dell\\Desktop\\New folder\\Tracking.csv"));
		writer.setShouldDeleteIfEmpty(true);
		writer.setHeaderCallback(header);
		writer.setAppendAllowed(true);
		DelimitedLineAggregator<Model> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(",");
		BeanWrapperFieldExtractor<Model> extractor = new BeanWrapperFieldExtractor<>();
		extractor.setNames(Model.names);
		aggregator.setFieldExtractor(extractor);
		writer.setLineAggregator(aggregator);
		return writer;
	}

	@Bean
	public JdbcPagingItemReader<Tracking> reader() {
		JdbcPagingItemReader<Tracking> dbreader = new JdbcPagingItemReader<>();
		dbreader.setDataSource(dataSource);
		dbreader.setPageSize(500);
		dbreader.setFetchSize(1000);
		dbreader.setQueryProvider(pagingQueryProvider());
	//	dbreader.setMaxItemCount(250000);
		dbreader.setRowMapper(rowMapper);
		try {
			dbreader.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbreader;
	}

	private MySqlPagingQueryProvider pagingQueryProvider() {
		MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
		provider.setSelectClause("select * ");
		provider.setFromClause("from employee_tracking_archive ");
		Map<String, Order> sortKeys = new LinkedHashMap<>();
		sortKeys.put("id", Order.ASCENDING);
		provider.setSortKeys(sortKeys);
		return provider;
	}

}
