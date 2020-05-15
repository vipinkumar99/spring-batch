package org.multiple.job.secondjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.entity.City;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CityProcessor implements ItemProcessor<LocationPojo, City> {

	@Override
	public City process(LocationPojo item) throws Exception {
		if (item == null) {
			return null;
		}
		if (StringUtils.isEmpty(item.getServiceArea())) {
			return null;
		}
		City city = new City();
		city.setName(item.getServiceArea());
		return city;
	}

}
