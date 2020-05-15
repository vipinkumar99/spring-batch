package org.multiple.job.firstjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.entity.SocietyLocation;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SocietyLocationProcessor implements ItemProcessor<LocationPojo, SocietyLocation> {

	@Override
	public SocietyLocation process(LocationPojo item) throws Exception {
		if (item == null) {
			return null;
		}
		if (!item.getAssociatedlevel().equals("Building Name/Block")) {
			return null;
		}
		if (StringUtils.isEmpty(item.getLocationLevel1()) || StringUtils.isEmpty(item.getLocationLevel4())) {
			return null;
		}
		if (StringUtils.isEmpty(item.getServiceArea())) {
			return null;
		}
		SocietyLocation location = new SocietyLocation();
		location.setName(item.getLocationLevel4());
		location.setArea(item.getLocationLevel1());
		location.setCity(item.getServiceArea());
		return location;
	}

}
