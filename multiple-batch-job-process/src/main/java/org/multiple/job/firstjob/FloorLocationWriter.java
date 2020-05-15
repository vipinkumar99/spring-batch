package org.multiple.job.firstjob;

import java.util.List;
import java.util.logging.Logger;

import org.multiple.job.dao.FloorLocationDao;
import org.multiple.job.entity.FloorLocation;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FloorLocationWriter implements ItemWriter<FloorLocation> {

	private static Logger logger = Logger.getLogger(FloorLocationWriter.class.getName());

	@Autowired
	private FloorLocationDao floorLocationDao;

	@Override
	public void write(List<? extends FloorLocation> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			floorLocationDao.saveAll(items);
			logger.info("Total floor location write:" + items.size());
		}
	}

}
