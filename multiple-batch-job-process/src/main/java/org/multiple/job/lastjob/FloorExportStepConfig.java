package org.multiple.job.lastjob;

import javax.sql.DataSource;

import org.multiple.job.common.CommonPojo;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class FloorExportStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private DelimitedLineAggregator<CommonPojo> lineAggregator;

	@Bean
	public JdbcCursorItemReader<CommonPojo> floorExportReader() {
		JdbcCursorItemReader<CommonPojo> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(dataSource);
		reader.setSql("select id, name from floors");
		reader.setRowMapper((rs, rn) -> new CommonPojo(rs.getLong("id"), rs.getString("name")));
		return reader;
	}

	@Bean
	public FlatFileItemWriter<CommonPojo> floorExportWriter() {
		FlatFileItemWriter<CommonPojo> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource("C:\\Users\\Dell\\Desktop\\New folder\\floor.csv"));
		writer.setShouldDeleteIfEmpty(true);
		writer.setHeaderCallback((w) -> w.append("Floor_Id,Floor_Name"));
		writer.setAppendAllowed(true);
		writer.setLineAggregator(lineAggregator);
		return writer;
	}

	@Bean
	public Step floorExportStep() {
		return stepBuilderFactory.get("FloorExport").<CommonPojo, CommonPojo>chunk(50).reader(floorExportReader())
				.writer(floorExportWriter()).build();
	}
}
