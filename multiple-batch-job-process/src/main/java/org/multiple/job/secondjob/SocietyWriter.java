package org.multiple.job.secondjob;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.multiple.job.dao.SocietyDao;
import org.multiple.job.entity.Society;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SocietyWriter implements ItemWriter<Society> {
	private static Logger logger = Logger.getLogger(SocietyWriter.class.getName());
	@Autowired
	private SocietyDao societyDao;

	@Override
	public void write(List<? extends Society> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			Set<Society> entities = new HashSet<>();
			items.forEach(s -> {
				if (!societyDao.findByName(s.getName()).isPresent()) {
					entities.add(s);
				}
			});
			if (!CollectionUtils.isEmpty(entities)) {
				societyDao.saveAll(entities);
				logger.info("Total society write:" + entities.size());
			}
		}
	}

}
