package org.multiple.job.secondjob;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.multiple.job.dao.TowerDao;
import org.multiple.job.entity.Tower;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TowerWriter implements ItemWriter<Tower> {
	private static Logger logger = Logger.getLogger(TowerWriter.class.getName());
	@Autowired
	private TowerDao towerDao;

	@Override
	public void write(List<? extends Tower> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			Set<Tower> entities = new HashSet<>();
			items.forEach(t -> {
				if (!towerDao.findByName(t.getName()).isPresent()) {
					entities.add(t);
				}
			});
			if (!CollectionUtils.isEmpty(entities)) {
				towerDao.saveAll(entities);
				logger.info("Total tower write:" + items.size());
			}
		}
	}

}
