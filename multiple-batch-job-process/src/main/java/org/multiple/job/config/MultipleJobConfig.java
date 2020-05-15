package org.multiple.job.config;

import org.multiple.job.common.CommonPojo;
import org.multiple.job.common.Constant;
import org.multiple.job.firstjob.FloorLocationStepConfig;
import org.multiple.job.firstjob.LocationStepConfig;
import org.multiple.job.firstjob.SocietyLocationStepConfig;
import org.multiple.job.firstjob.TowerLocationStepConfig;
import org.multiple.job.lastjob.AreaExportStepConfig;
import org.multiple.job.lastjob.CityExportStepConfig;
import org.multiple.job.lastjob.FloorExportStepConfig;
import org.multiple.job.lastjob.SocietyExportStepConfig;
import org.multiple.job.lastjob.TowerExportStepConfig;
import org.multiple.job.secondjob.AreaStepConfig;
import org.multiple.job.secondjob.CityStepConfig;
import org.multiple.job.secondjob.FloorStepConfig;
import org.multiple.job.secondjob.SocietyStepConfig;
import org.multiple.job.secondjob.TowerStepConfig;
import org.multiple.job.thirdjob.MappingStepConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class MultipleJobConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private NotificationListener notificationListener;
	@Autowired
	private LocationStepConfig locationStep;
	@Autowired
	private SocietyLocationStepConfig societyLocationStep;
	@Autowired
	private TowerLocationStepConfig towerLocationStep;
	@Autowired
	private FloorLocationStepConfig floorLocationStep;
	@Autowired
	private CityStepConfig cityStep;
	@Autowired
	private AreaStepConfig areaStep;
	@Autowired
	private SocietyStepConfig societyStep;
	@Autowired
	private TowerStepConfig towerStep;
	@Autowired
	private FloorStepConfig floorStep;
	@Autowired
	private MappingStepConfig mappingStep;
	@Autowired
	private AreaExportStepConfig areaExportStep;
	@Autowired
	private CityExportStepConfig cityExportStep;
	@Autowired
	private SocietyExportStepConfig societyExportStep;
	@Autowired
	private TowerExportStepConfig towerExportStep;
	@Autowired
	private FloorExportStepConfig floorExportStep;

	@Bean
	public DelimitedLineTokenizer delimitedLineTokenizer() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setStrict(false);
		tokenizer.setNames(Constant.HEADER);
		return tokenizer;
	}

	@Bean
	public DelimitedLineAggregator<CommonPojo> delimitedLineAggregator() {
		DelimitedLineAggregator<CommonPojo> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(",");
		BeanWrapperFieldExtractor<CommonPojo> extractor = new BeanWrapperFieldExtractor<>();
		extractor.setNames(new String[] { "id", "name" });
		aggregator.setFieldExtractor(extractor);
		return aggregator;
	}

	@Bean
	public Job firstJob() {
		return jobBuilderFactory.get("FirstJob").incrementer(new RunIdIncrementer()).listener(notificationListener)
				.flow(locationStep.locationStep()).next(societyLocationStep.societyLocationStep())
				.next(towerLocationStep.towerLocationStep()).next(floorLocationStep.floorLocationStep()).end().build();
	}

	@Bean
	public Job secondJob() {
		return jobBuilderFactory.get("SecondJob").incrementer(new RunIdIncrementer()).listener(notificationListener)
				.flow(cityStep.cityStep()).next(areaStep.areaStep()).next(societyStep.societyStep())
				.next(towerStep.towerStep()).next(floorStep.floorStep()).end().build();
	}

	@Bean
	public Job thirdJob() {
		return jobBuilderFactory.get("ThirdJob").incrementer(new RunIdIncrementer()).listener(notificationListener)
				.flow(mappingStep.mappingStep()).end().build();
	}

	@Bean
	public Job lastJob() {
		return jobBuilderFactory.get("LastJob").incrementer(new RunIdIncrementer()).listener(notificationListener)
				.flow(cityExportStep.cityExportStep()).next(areaExportStep.areaExportStep())
				.next(societyExportStep.societyExportStep()).next(towerExportStep.towerExportStep())
				.next(floorExportStep.floorExportStep()).end().build();
	}
}
