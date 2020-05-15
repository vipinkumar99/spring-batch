package org.multiple.job.secondjob;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.multiple.job.dao.FloorDao;
import org.multiple.job.entity.Floor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FloorWriter implements ItemWriter<Floor> {

	private static Logger logger = Logger.getLogger(FloorWriter.class.getName());

	@Autowired
	private FloorDao floorDao;

	@Override
	public void write(List<? extends Floor> items) throws Exception {
		if (!CollectionUtils.isEmpty(items)) {
			Set<Floor> entities = new HashSet<>();
			items.forEach(f -> {
				if (!floorDao.findByName(f.getName()).isPresent()) {
					entities.add(f);
				}
			});
			if (!CollectionUtils.isEmpty(entities)) {
				floorDao.saveAll(entities);
				logger.info("Total floor write:" + entities.size());
			}
		}
	}

}
