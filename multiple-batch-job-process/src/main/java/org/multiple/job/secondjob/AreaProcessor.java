package org.multiple.job.secondjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.entity.Area;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AreaProcessor implements ItemProcessor<LocationPojo, Area> {

	@Override
	public Area process(LocationPojo item) throws Exception {
		if (item == null) {
			return null;
		}
		if (StringUtils.isEmpty(item.getAssociatedlevel())) {
			return null;
		}
		if (item.getAssociatedlevel().equals("Location/Sector")) {
			if (StringUtils.isEmpty(item.getLocationLevel4())) {
				return null;
			}
			Area area = new Area();
			area.setName(item.getLocationLevel4());
			return area;
		} else if (item.getAssociatedlevel().equals("Building Name/Block")
				|| item.getAssociatedlevel().equals("Tower/Floor") || item.getAssociatedlevel().equals("Floor")) {
			if (StringUtils.isEmpty(item.getLocationLevel1())) {
				return null;
			}
			Area area = new Area();
			area.setName(item.getLocationLevel1());
			return area;
		} else {
			return null;
		}

	}

}
