package org.batch.process.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.batch.process.entity.Charge;
import org.batch.process.model.Model;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class ChargeBatchConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private NotificationListener notificationListener;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private ChargeImportProcessor chargeImportProcessor;
	@Autowired
	private ChargeExportExcelRowMapper rowMapper;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private ChargeExportRowMapper chargeRowMapper;
	@Autowired
	private ChargeImportPreparedStatementSetter statementSetter;
	@Autowired
	private ChargeExportWriter chargeExportWriter;

	@Bean
	public PoiItemReader<Model> chargeImportReader() {
		PoiItemReader<Model> reader = new PoiItemReader<>();
		reader.setResource(new ClassPathResource("PostCharges.xlsx"));
		reader.setName("ChargeImport");
		reader.setRowMapper(rowMapper);
		reader.setStrict(false);
		reader.setLinesToSkip(1);
		return reader;
	}

	@Bean
	public JdbcBatchItemWriter<Charge> chargeImportWriter() {
		final String query = "INSERT INTO charges(date, userId, chargeName, type, serviceName, amountWithoutTax, taxAmount, amountWithTax, tax, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		JdbcBatchItemWriter<Charge> dbWriter = new JdbcBatchItemWriter<>();
		dbWriter.setDataSource(dataSource);
		dbWriter.setSql(query);
		dbWriter.setItemPreparedStatementSetter(statementSetter);
		return dbWriter;
	}

	@Bean
	public JdbcPagingItemReader<Charge> chargeExportReader() {
		JdbcPagingItemReader<Charge> dbreader = new JdbcPagingItemReader<>();
		dbreader.setDataSource(dataSource);
		dbreader.setPageSize(100);
		dbreader.setFetchSize(1000);
		dbreader.setQueryProvider(queryProvider());
		dbreader.setRowMapper(chargeRowMapper);
		return dbreader;
	}

	private MySqlPagingQueryProvider queryProvider() {
		String select = "select id, date, userId, chargeName, amountWithoutTax, amountWithTax, tax, taxAmount";
		MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
		provider.setSelectClause(select);
		provider.setFromClause(" from charges ");
		Map<String, Order> sortKeys = new LinkedHashMap<>();
		sortKeys.put("id", Order.ASCENDING);
		provider.setSortKeys(sortKeys);
		return provider;

	}

	@Bean
	public Step chargeImportStep() throws Exception {
		return stepBuilderFactory.get("ChargeImportStep").<Model, Charge>chunk(50).reader(chargeImportReader())
				.processor(chargeImportProcessor).writer(chargeImportWriter()).build();
	}

	@Bean
	public Step chargeExportStep() throws Exception {
		return stepBuilderFactory.get("ChargeExportStep").<Charge, Charge>chunk(50).reader(chargeExportReader())
				.writer(chargeExportWriter).build();
	}

	@Bean
	public Job chargeJob() throws Exception {
		return jobBuilderFactory.get("ChargeExcelJob").incrementer(new RunIdIncrementer()).listener(notificationListener)
				.flow(chargeImportStep()).next(chargeExportStep()).end().build();
	}

}
