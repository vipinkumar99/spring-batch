package org.batch.process.config.city;

import org.batch.process.model.CityPojo;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class CityFieldSetMapper implements FieldSetMapper<CityPojo> {

	@Override
	public CityPojo mapFieldSet(FieldSet fieldSet) throws BindException {
		CityPojo city = new CityPojo();
		city.setCity(fieldSet.readString("city"));
		return city;
	}

}
