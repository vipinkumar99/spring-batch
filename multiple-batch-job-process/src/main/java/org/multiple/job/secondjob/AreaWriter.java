package org.multiple.job.secondjob;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.multiple.job.dao.AreaDao;
import org.multiple.job.entity.Area;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AreaWriter implements ItemWriter<Area> {
	private static Logger logger = Logger.getLogger(AreaWriter.class.getName());

	@Autowired
	private AreaDao areaDao;

	@Override
	public void write(List<? extends Area> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			Set<Area> entities = new HashSet<>();
			items.forEach(a -> {
				if (!areaDao.findByName(a.getName()).isPresent()) {
					entities.add(a);
				}
			});
			if (!CollectionUtils.isEmpty(entities)) {
				areaDao.saveAll(entities);
				logger.info("Total area write:" + entities.size());
			}
		}
	}

}
