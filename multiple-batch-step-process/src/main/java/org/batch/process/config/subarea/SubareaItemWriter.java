package org.batch.process.config.subarea;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.batch.process.dao.SubareaDao;
import org.batch.process.entity.Subarea;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SubareaItemWriter implements ItemWriter<Subarea> {
	public static Logger logger = Logger.getLogger(SubareaItemWriter.class.getName());

	@Autowired
	private SubareaDao subareaDao;

	@Override
	public void write(List<? extends Subarea> subareas) throws Exception {
		if (!CollectionUtils.isEmpty(subareas)) {
			Set<Subarea> entities = new HashSet<>();
			subareas.forEach(s -> {
				if (!subareaDao.findByName(s.getName()).isPresent()) {
					entities.add(s);
				}
			});
			if (!CollectionUtils.isEmpty(entities)) {
				subareaDao.saveAll(entities);
				logger.info("Total subarea write in database:" + entities.size());
			}
		}
	}

}
