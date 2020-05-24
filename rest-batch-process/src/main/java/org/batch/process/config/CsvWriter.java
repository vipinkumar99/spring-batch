package org.batch.process.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.batch.process.model.Model;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CsvWriter implements ItemStreamWriter<Model> {

	private ByteArrayOutputStream stream;
	private PrintWriter writer;

	public ByteArrayOutputStream getData() {
		return stream;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		stream = new ByteArrayOutputStream();
		writer = new PrintWriter(stream, true);
		createHeaderRow(writer);
	}

	private void createHeaderRow(PrintWriter writer) {
		StringBuilder sb = new StringBuilder();
		sb.append("Date,");
		sb.append("UserId,");
		sb.append("Charge,");
		sb.append("AmountWithoutTax,");
		sb.append("TaxAmount,");
		sb.append("Tax%,");
		sb.append("AmountWithTax");
		writer.println(sb.toString());
	}

	@Override
	public void write(List<? extends Model> items) throws Exception {
		for (Model ch : items) {
			StringBuilder sb = new StringBuilder();
			sb.append(ch.getDate()).append(",");
			sb.append(!StringUtils.isEmpty(ch.getUserId()) ? ch.getUserId() : "").append(",");
			sb.append(!StringUtils.isEmpty(ch.getChargeName()) ? ch.getChargeName() : "").append(",");
			sb.append(ch.getAmountWithoutTax() != null ? ch.getAmountWithoutTax().toString() : "0").append(",");
			sb.append(ch.getTaxAmount() != null ? ch.getTaxAmount().toString() : "0").append(",");
			sb.append(ch.getTax() != null ? ch.getTax().toString() : "0").append(",");
			sb.append(ch.getAmountWithTax() != null ? ch.getAmountWithTax().toString() : "0");
			writer.println(sb.toString());
		}
	}

	@Override
	public void close() throws ItemStreamException {
		if (stream == null || writer == null) {
			return;
		}
		writer.flush();
		writer.close();
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
