package org.batch.process.config.area;

import org.batch.process.dao.CityDao;
import org.batch.process.entity.Area;
import org.batch.process.model.AreaPojo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AreaItemProcessor implements ItemProcessor<AreaPojo, Area> {

	@Autowired
	private CityDao cityDao;

	@Override
	public Area process(AreaPojo item) throws Exception {
		if (!StringUtils.isEmpty(item.getArea())) {
			Area area = new Area();
			area.setName(item.getArea());
			area.setCityId(cityDao.findByName(item.getCity()).get());
			area.setModified(true);
			return area;
		}
		return null;
	}

}
