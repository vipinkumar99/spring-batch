package org.batch.process.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.batch.process.model.Model;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonWriter implements ItemStreamWriter<Model> {
	private ByteArrayOutputStream stream;
	private JsonGenerator generator;

	public ByteArrayOutputStream getData() {
		return stream;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		try {
			stream = new ByteArrayOutputStream();
			ObjectMapper mapper = new ObjectMapper();
			generator = mapper.getFactory().createGenerator(stream, JsonEncoding.UTF8);
			generator.useDefaultPrettyPrinter();
			generator.writeStartArray();
		} catch (IOException e) {
		}
	}

	@Override
	public void write(List<? extends Model> items) throws Exception {
		// generator.writeStartArray();
		for (Model ch : items) {
			generator.writeStartObject();
			generator.writeStringField("date", ch.getDate());
			generator.writeStringField("userId", ch.getUserId());
			generator.writeStringField("chargeName", ch.getChargeName());
			generator.writeNumberField("amountWithoutTax", ch.getAmountWithoutTax());
			generator.writeNumberField("taxAmount", ch.getTaxAmount());
			generator.writeNumberField("tax", ch.getTax());
			generator.writeNumberField("amountWithTax", ch.getAmountWithTax());
			generator.writeEndObject();
		}
		// generator.writeEndArray();
	}

	@Override
	public void close() throws ItemStreamException {
		if (stream == null) {
			return;
		}
		try {
			generator.writeEndArray();
			generator.close();
		} catch (IOException e1) {
		}

		try {
			stream.flush();
		} catch (IOException e) {
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {

	}

}
