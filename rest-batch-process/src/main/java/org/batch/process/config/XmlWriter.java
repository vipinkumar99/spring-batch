package org.batch.process.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.batch.process.model.Model;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class XmlWriter implements ItemStreamWriter<Model> {
	private XmlMapper mapper;
	private ByteArrayOutputStream stream;
	private XMLStreamWriter xmlStreamWriter;

	public ByteArrayOutputStream getData() {
		return stream;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		mapper = new XmlMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		stream = new ByteArrayOutputStream();
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
		try {
			xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(stream);
			xmlStreamWriter.writeStartDocument();
			xmlStreamWriter.writeStartElement("charges");
		} catch (XMLStreamException e) {
		}
	}

	@Override
	public void write(List<? extends Model> items) throws Exception {
		for (Model item : items) {
			mapper.writeValue(xmlStreamWriter, item);
		}
	}

	@Override
	public void close() throws ItemStreamException {
		if (stream == null) {
			return;
		}
		try {
			xmlStreamWriter.writeComment("Charges list");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndDocument();
			xmlStreamWriter.flush();
			xmlStreamWriter.close();
		} catch (XMLStreamException e1) {
		} finally {
			try {
				xmlStreamWriter.close();
			} catch (XMLStreamException e) {
			}
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
