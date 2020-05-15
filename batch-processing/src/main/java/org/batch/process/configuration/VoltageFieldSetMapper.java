package org.batch.process.configuration;

import org.batch.process.entity.Voltage;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class VoltageFieldSetMapper implements FieldSetMapper<Voltage> {

	@Override
	public Voltage mapFieldSet(FieldSet fieldSet) throws BindException {
		final Voltage volt = new Voltage();
		volt.setVolt(fieldSet.readBigDecimal("volt"));
		volt.setTime(fieldSet.readDouble("time"));
		return volt;
	}

}
