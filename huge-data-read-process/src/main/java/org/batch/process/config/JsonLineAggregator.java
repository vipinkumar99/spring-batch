package org.batch.process.config;

import org.batch.process.model.Model;
import org.springframework.batch.item.file.transform.LineAggregator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLineAggregator implements LineAggregator<Model> {

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String aggregate(Model item) {
		try {
			return objectMapper.writeValueAsString(item);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to serialize Tracking", e);
		}
	}

}
