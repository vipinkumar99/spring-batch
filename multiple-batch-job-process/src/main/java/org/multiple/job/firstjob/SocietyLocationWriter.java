package org.multiple.job.firstjob;

import java.util.List;
import java.util.logging.Logger;

import org.multiple.job.dao.SocietyLocationDao;
import org.multiple.job.entity.SocietyLocation;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SocietyLocationWriter implements ItemWriter<SocietyLocation> {
	private static Logger logger = Logger.getLogger(SocietyLocationWriter.class.getName());
	@Autowired
	private SocietyLocationDao societyLocationDao;

	@Override
	public void write(List<? extends SocietyLocation> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			societyLocationDao.saveAll(items);
			logger.info("Total society location write:" + items.size());
		}
	}

}
