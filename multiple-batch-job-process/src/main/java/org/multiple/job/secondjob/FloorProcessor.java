package org.multiple.job.secondjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.entity.Floor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FloorProcessor implements ItemProcessor<LocationPojo, Floor> {

	@Override
	public Floor process(LocationPojo item) throws Exception {
		if (item == null) {
			return null;
		}
		if (StringUtils.isEmpty(item.getAssociatedlevel())) {
			return null;
		}
		if (!item.getAssociatedlevel().equals("Floor")) {
			return null;
		}
		if (StringUtils.isEmpty(item.getLocationLevel4())) {
			return null;
		}
		Floor floor = new Floor();
		floor.setName(item.getLocationLevel4());
		return floor;
	}

}
