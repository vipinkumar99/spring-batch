package org.batch.process.config.subarea;

import org.batch.process.dao.AreaDao;
import org.batch.process.entity.Subarea;
import org.batch.process.model.SubareaPojo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SubareaItemProcessor implements ItemProcessor<SubareaPojo, Subarea> {

	@Autowired
	private AreaDao areaDao;

	@Override
	public Subarea process(SubareaPojo item) throws Exception {
		if (!StringUtils.isEmpty(item.getSubarea())) {
			if (!item.getSubarea().equals("\\N")) {
				Subarea subarea = new Subarea();
				subarea.setName(item.getSubarea());
				subarea.setAreaId(areaDao.findByName(item.getArea()).get());
				subarea.setModified(true);
				return subarea;
			}
		}
		return null;
	}

}
