package org.batch.process.config;

import java.util.HashMap;
import java.util.Map;

import org.batch.process.entity.Tracking;
import org.batch.process.model.Model;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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
	private TrackingProcessor processor;
	@Autowired
	private TrackingWriter writer;

	@Bean
	public JsonItemReader<Model> jsonReader() {
		JsonItemReaderBuilder<Model> builder = new JsonItemReaderBuilder<>();
		builder.resource(new ClassPathResource("Tracking.json"));
		builder.jsonObjectReader(new JacksonJsonObjectReader<>(Model.class));
		builder.name("JsonReader");
		builder.strict(false);
		return builder.build();
	}

	@Bean
	public StaxEventItemReader<Model> xmlReader() {
		StaxEventItemReaderBuilder<Model> builder = new StaxEventItemReaderBuilder<>();
		builder.addFragmentRootElements("tracking");
		builder.name("XmlReader");
		builder.resource(new ClassPathResource("Tracking.xml"));
		builder.unmarshaller(unmarshaller());
		builder.strict(false);
		return builder.build();
	}

	@Bean
	public XStreamMarshaller unmarshaller() {
		Map<String, Class<?>> aliases = new HashMap<>();
		aliases.put("tracking", Model.class);
		aliases.put("created", String.class);
		aliases.put("mobileCharging", String.class);
		aliases.put("batteryPercentage", Double.class);
		aliases.put("longitude", Double.class);
		aliases.put("latitude", Double.class);
		aliases.put("speed", Double.class);
		aliases.put("calculatedSpeed", Double.class);
		aliases.put("calculatedDistanceInKm", Double.class);
		aliases.put("provider", String.class);
		aliases.put("networkOperator", String.class);
		aliases.put("simOperator", String.class);
		aliases.put("deviceTimeStamp", String.class);
		aliases.put("deviceTime", Long.class);
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliases);
		return marshaller;
	}

//	@Bean
//	public CompositeItemReadListener<Model> writer() throws Exception {
//		List<ItemReader<? super Model>> listener = new ArrayList<>();
//		listener.add(jsonReader());
//		listener.add(xmlReader());
//		CompositeItemReadListener<Model> compositeItemWriter = new CompositeItemReadListener<>();
//		compositeItemWriter.setListeners(listener);
//		compositeItemWriter.afterPropertiesSet();
//		return compositeItemWriter;
//	}

	@Bean
	public Step step() throws Exception {
		return stepBuilderFactory.get("JsonTracking").<Model, Tracking>chunk(50).reader(jsonReader())
				.processor(processor).writer(writer).build();
	}

	@Bean
	public Step step2() throws Exception {
		return stepBuilderFactory.get("XmlTracking").<Model, Tracking>chunk(50).reader(xmlReader()).processor(processor)
				.writer(writer).build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("TrackingJob").incrementer(new RunIdIncrementer()).listener(notificationListener)
				.flow(step()).next(step2()).end().build();
	}

}
