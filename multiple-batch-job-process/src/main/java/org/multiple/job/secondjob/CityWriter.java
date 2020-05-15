package org.multiple.job.secondjob;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.multiple.job.dao.CityDao;
import org.multiple.job.entity.City;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CityWriter implements ItemWriter<City> {
	private static Logger logger = Logger.getLogger(CityWriter.class.getName());
	@Autowired
	private CityDao cityDao;

	@Override
	public void write(List<? extends City> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			Set<City> entities = new HashSet<>();
			items.forEach(c -> {
				if (!cityDao.findByName(c.getName()).isPresent()) {
					entities.add(c);
				}
			});
			if (!CollectionUtils.isEmpty(entities)) {
				cityDao.saveAll(entities);
				logger.info("Total city write:" + entities.size());
			}
		}
	}

}
