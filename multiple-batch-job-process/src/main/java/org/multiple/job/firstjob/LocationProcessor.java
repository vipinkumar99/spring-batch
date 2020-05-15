package org.multiple.job.firstjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.entity.Location;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class LocationProcessor implements ItemProcessor<LocationPojo, Location> {

	@Override
	public Location process(LocationPojo item) throws Exception {
		if (item == null) {
			return null;
		}
		if (!item.getAssociatedlevel().equals("Location/Sector")) {
			return null;
		}
		if (StringUtils.isEmpty(item.getLocationLevel4()) || StringUtils.isEmpty(item.getServiceArea())) {
			return null;
		}
		Location location = new Location();
		location.setCity(item.getServiceArea());
		location.setArea(item.getLocationLevel4());
		return location;
	}

}
