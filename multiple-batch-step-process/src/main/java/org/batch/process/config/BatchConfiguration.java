package org.batch.process.config;

import org.batch.process.config.area.AreaBatchConfiguration;
import org.batch.process.config.city.CityBatchConfiguration;
import org.batch.process.config.subarea.SubareaBatchConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private NotificationListener notificationListener;
	@Autowired
	private CityBatchConfiguration cityBatchConfiguration;
	@Autowired
	private AreaBatchConfiguration areaBatchConfiguration;
	@Autowired
	private SubareaBatchConfiguration subareaBatchConfiguration;

	@Bean
	public Job locationJob() {
		return jobBuilderFactory.get("LocationJob").incrementer(new RunIdIncrementer()).listener(notificationListener)
				.flow(cityBatchConfiguration.cityStep()).next(areaBatchConfiguration.areaStep())
				.next(subareaBatchConfiguration.subareaStep()).end().build();
	}

}
