package org.multiple.job.secondjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.entity.Tower;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TowerProcessor implements ItemProcessor<LocationPojo, Tower> {

	@Override
	public Tower process(LocationPojo item) throws Exception {
		if (item == null) {
			return null;
		}
		if (StringUtils.isEmpty(item.getAssociatedlevel())) {
			return null;
		}
		if (item.getAssociatedlevel().equals("Tower/Floor")) {
			if (StringUtils.isEmpty(item.getLocationLevel4())) {
				return null;
			}
			Tower tower = new Tower();
			tower.setName(item.getLocationLevel4());
			return tower;
		} else if (item.getAssociatedlevel().equals("Floor")) {
			if (StringUtils.isEmpty(item.getLocationLevel3())) {
				return null;
			}
			Tower tower = new Tower();
			tower.setName(item.getLocationLevel3());
			return tower;
		} else {
			return null;
		}
	}

}
