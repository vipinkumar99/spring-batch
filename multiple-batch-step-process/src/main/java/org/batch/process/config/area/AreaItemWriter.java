package org.batch.process.config.area;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.batch.process.dao.AreaDao;
import org.batch.process.entity.Area;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AreaItemWriter implements ItemWriter<Area> {
	public static Logger logger = Logger.getLogger(AreaItemWriter.class.getName());

	@Autowired
	private AreaDao areaDao;

	@Override
	public void write(List<? extends Area> areas) throws Exception {
		if (!CollectionUtils.isEmpty(areas)) {
			Set<Area> entities = new HashSet<>();
			areas.forEach(a -> {
				if (!areaDao.findByName(a.getName()).isPresent()) {
					entities.add(a);
				}
			});
			if (!CollectionUtils.isEmpty(entities)) {
				areaDao.saveAll(entities);
				logger.info("Total area write in database:" + entities.size());
			}
		}
	}

}
