package org.batch.process.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.batch.process.model.Model;
import org.batch.process.model.Tracking;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class TrackingBatchConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
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
	@Autowired
	private FlatFileItemWriter<Model> writer;

	@Bean
	public JdbcPagingItemReader<Tracking> reader() {
		JdbcPagingItemReader<Tracking> dbreader = new JdbcPagingItemReader<>();
		dbreader.setDataSource(dataSource);
		dbreader.setPageSize(100);
		dbreader.setFetchSize(1000);
		dbreader.setQueryProvider(pagingQueryProvider());
		dbreader.setMaxItemCount(2000);
		dbreader.setRowMapper(rowMapper);
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
	@StepScope
	public FlatFileItemWriter<Model> writer(@Value("#{jobParameters['fileName']}") String fileName) {
		String file = "C:\\Users\\Dell\\Desktop\\New folder\\" + fileName + ".csv";
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

	@Bean
	public Step step() throws Exception {
		return stepBuilderFactory.get("TrackingStep").<Tracking, Model>chunk(50).reader(reader()).processor(processor)
				.writer(writer).build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("TrackingJob").incrementer(new RunIdIncrementer()).flow(step()).end().build();
	}

}
