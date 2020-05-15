package org.multiple.job.firstjob;

import java.util.List;
import java.util.logging.Logger;

import org.multiple.job.dao.LocationDao;
import org.multiple.job.entity.Location;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class LocationWriter implements ItemWriter<Location> {
	private static Logger logger = Logger.getLogger(LocationWriter.class.getName());
	@Autowired
	private LocationDao locationDao;

	@Override
	public void write(List<? extends Location> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			locationDao.saveAll(items);
			logger.info("Total location write:" + items.size());
		}
	}

}
