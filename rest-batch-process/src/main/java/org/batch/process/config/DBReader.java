package org.batch.process.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.batch.process.entity.Charge;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBReader {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private DBRowMapper rowMapper;

	private final String select = "select id, date, userId, chargeName, amountWithoutTax, amountWithTax, tax, taxAmount";

	@Bean
	public JdbcPagingItemReader<Charge> jdbcPagingItemReader() {
		JdbcPagingItemReader<Charge> dbreader = new JdbcPagingItemReader<>();
		dbreader.setDataSource(dataSource);
		dbreader.setPageSize(100);
		dbreader.setFetchSize(500);
		//dbreader.setMaxItemCount(100);
		dbreader.setQueryProvider(query());
		dbreader.setRowMapper(rowMapper);
		return dbreader;
	}

	private MySqlPagingQueryProvider query() {
		MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
		provider.setSelectClause(select);
		provider.setFromClause(" from charges ");
		Map<String, Order> sortKeys = new LinkedHashMap<>();
		sortKeys.put("id", Order.ASCENDING);
		provider.setSortKeys(sortKeys);
		return provider;
	}

}
