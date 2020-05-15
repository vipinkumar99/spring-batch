package org.multiple.job.firstjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.entity.TowerLocation;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TowerLocationProcessor implements ItemProcessor<LocationPojo, TowerLocation> {

	@Override
	public TowerLocation process(LocationPojo item) throws Exception {
		if (item == null) {
			return null;
		}
		if (!item.getAssociatedlevel().equals("Tower/Floor")) {
			return null;
		}
		if (StringUtils.isEmpty(item.getServiceArea()) || StringUtils.isEmpty(item.getLocationLevel1())) {
			return null;
		}
		if (StringUtils.isEmpty(item.getLocationLevel2()) || StringUtils.isEmpty(item.getLocationLevel4())) {
			return null;
		}
		TowerLocation location = new TowerLocation();
		location.setCity(item.getServiceArea());
		location.setArea(item.getLocationLevel1());
		location.setSociety(item.getLocationLevel2());
		location.setTower(item.getLocationLevel3());
		return location;
	}

}
