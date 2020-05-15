package org.multiple.job.secondjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.entity.Society;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SocietyProcessor implements ItemProcessor<LocationPojo, Society> {

	@Override
	public Society process(LocationPojo item) throws Exception {
		if (item == null) {
			return null;
		}
		if (StringUtils.isEmpty(item.getAssociatedlevel())) {
			return null;
		}
		if (item.getAssociatedlevel().equals("Building Name/Block")) {
			if (StringUtils.isEmpty(item.getLocationLevel4())) {
				return null;
			}
			Society society = new Society();
			society.setName(item.getLocationLevel4());
			return society;
		} else if (item.getAssociatedlevel().equals("Tower/Floor") || item.getAssociatedlevel().equals("Floor")) {
			if (StringUtils.isEmpty(item.getLocationLevel2())) {
				return null;
			}
			Society society = new Society();
			society.setName(item.getLocationLevel2());
			return society;
		} else {
			return null;
		}
	}

}
