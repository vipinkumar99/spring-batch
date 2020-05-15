package org.multiple.job.thirdjob;

import java.util.List;
import java.util.logging.Logger;

import org.multiple.job.dao.MappingDao;
import org.multiple.job.entity.Mapping;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MappingWriter implements ItemWriter<Mapping> {
	private static Logger logger = Logger.getLogger(MappingWriter.class.getName());
	@Autowired
	private MappingDao mappingDao;

	@Override
	public void write(List<? extends Mapping> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			mappingDao.saveAll(items);
			logger.info("Total mapping write:" + items.size());
		}
	}

}
