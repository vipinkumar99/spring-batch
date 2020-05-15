package org.batch.process.config.area;

import org.batch.process.model.AreaPojo;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class AreaFieldSetMapper implements FieldSetMapper<AreaPojo> {

	@Override
	public AreaPojo mapFieldSet(FieldSet fieldSet) throws BindException {
		AreaPojo area = new AreaPojo();
		area.setCity(fieldSet.readString("city"));
		area.setArea(fieldSet.readString("area"));
		return area;
	}

}
