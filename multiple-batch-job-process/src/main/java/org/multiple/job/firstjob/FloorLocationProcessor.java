package org.multiple.job.firstjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.entity.FloorLocation;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FloorLocationProcessor implements ItemProcessor<LocationPojo, FloorLocation> {

	@Override
	public FloorLocation process(LocationPojo item) throws Exception {
		if (item == null) {
			return null;
		}
		if (!item.getAssociatedlevel().equals("Floor")) {
			return null;
		}
		if (StringUtils.isEmpty(item.getLocationLevel1()) || StringUtils.isEmpty(item.getLocationLevel2())) {
			return null;
		}
		if (StringUtils.isEmpty(item.getLocationLevel3()) || StringUtils.isEmpty(item.getLocationLevel4())) {
			return null;
		}
		if (StringUtils.isEmpty(item.getServiceArea())) {
			return null;
		}
		FloorLocation location = new FloorLocation();
		location.setCity(item.getServiceArea());
		location.setArea(item.getLocationLevel1());
		location.setSociety(item.getLocationLevel2());
		location.setTower(item.getLocationLevel3());
		location.setFloor(item.getLocationLevel4());
		return location;
	}

}
