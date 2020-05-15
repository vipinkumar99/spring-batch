package org.batch.process.config.city;

import org.batch.process.entity.City;
import org.batch.process.model.CityPojo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CityItemProcessor implements ItemProcessor<CityPojo, City> {

	@Override
	public City process(CityPojo item) throws Exception {
		if (!StringUtils.isEmpty(item.getCity())) {
			City city = new City();
			city.setName(item.getCity());
			city.setModified(true);
			return city;
		}
		return null;
	}

}
