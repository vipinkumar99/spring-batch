package org.batch.process.config.city;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.batch.process.dao.CityDao;
import org.batch.process.entity.City;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CityItemWriter implements ItemWriter<City> {
	public static Logger logger = Logger.getLogger(CityItemWriter.class.getName());

	@Autowired
	private CityDao cityDao;

	@Override
	public void write(List<? extends City> cities) throws Exception {
		if (!CollectionUtils.isEmpty(cities)) {
			Set<City> entities = new HashSet<>();
			cities.forEach(c -> {
				if (!cityDao.findByName(c.getName()).isPresent()) {
					entities.add(c);
				}
			});
			if (!CollectionUtils.isEmpty(entities)) {
				cityDao.saveAll(entities);
				logger.info("Total city write in database:" + entities.size());
			}
		}
	}
}
