package org.multiple.job.config;

import org.multiple.job.common.LocationPojo;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class CommonFieldSetMapper implements FieldSetMapper<LocationPojo> {

	@Override
	public LocationPojo mapFieldSet(FieldSet fieldSet) throws BindException {
		LocationPojo location = new LocationPojo();
		location.setAssociatedlevel(fieldSet.readString("associatedlevel"));
		location.setLocationLevel1(fieldSet.readString("locationLevel1"));
		location.setLocationLevel2(fieldSet.readString("locationLevel2"));
		location.setLocationLevel3(fieldSet.readString("locationLevel3"));
		location.setLocationLevel4(fieldSet.readString("locationLevel4"));
		location.setServiceArea(fieldSet.readString("serviceArea"));
		return location;
	}

}
