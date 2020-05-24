package org.batch.process.config;

import java.util.List;
import java.util.logging.Logger;

import org.batch.process.dao.TrackingDao;
import org.batch.process.entity.Tracking;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TrackingWriter implements ItemWriter<Tracking> {
	private static Logger logger = Logger.getLogger(TrackingWriter.class.getName());

	@Autowired
	private TrackingDao trackingDao;

	@Override
	public void write(List<? extends Tracking> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			trackingDao.saveAll(items);
			logger.info("Total tracking data write:" + items.size());
		}
	}

}
