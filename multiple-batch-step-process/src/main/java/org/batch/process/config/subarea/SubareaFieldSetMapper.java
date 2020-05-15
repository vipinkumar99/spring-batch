package org.batch.process.config.subarea;

import org.batch.process.model.SubareaPojo;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class SubareaFieldSetMapper implements FieldSetMapper<SubareaPojo> {

	@Override
	public SubareaPojo mapFieldSet(FieldSet fieldSet) throws BindException {
		SubareaPojo subarea = new SubareaPojo();
		subarea.setArea(fieldSet.readString("area"));
		subarea.setSubarea(fieldSet.readString("subArea"));
		return subarea;
	}

}
