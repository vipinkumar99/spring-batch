package org.location.batch.process.configuration;

import org.location.batch.process.model.LocationModel;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class LocationFieldSetMapper implements FieldSetMapper<LocationModel> {

	@Override
	public LocationModel mapFieldSet(FieldSet fieldSet) throws BindException {
		final LocationModel location = new LocationModel();
		location.setCity(fieldSet.readString("city"));
		location.setArea(fieldSet.readString("area"));
		location.setSubarea(fieldSet.readString("subArea"));
		location.setCreated(fieldSet.readString("created"));
		return location;
	}

}
