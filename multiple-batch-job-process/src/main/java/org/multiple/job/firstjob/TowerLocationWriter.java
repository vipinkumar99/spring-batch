package org.multiple.job.firstjob;

import java.util.List;
import java.util.logging.Logger;

import org.multiple.job.dao.TowerLocationDao;
import org.multiple.job.entity.TowerLocation;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TowerLocationWriter implements ItemWriter<TowerLocation> {
	private static Logger logger = Logger.getLogger(TowerLocationWriter.class.getName());
	@Autowired
	private TowerLocationDao towerLocationDao;

	@Override
	public void write(List<? extends TowerLocation> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			towerLocationDao.saveAll(items);
			logger.info("Total tower location write:" + items.size());
		}
	}

}
