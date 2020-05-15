package org.multiple.job.thirdjob;

import org.multiple.job.common.LocationPojo;
import org.multiple.job.common.MappingType;
import org.multiple.job.dao.AreaDao;
import org.multiple.job.dao.CityDao;
import org.multiple.job.dao.FloorDao;
import org.multiple.job.dao.SocietyDao;
import org.multiple.job.dao.TowerDao;
import org.multiple.job.entity.Mapping;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MappingProcessor implements ItemProcessor<LocationPojo, Mapping> {

	@Autowired
	private CityDao cityDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private SocietyDao societyDao;
	@Autowired
	private TowerDao towerDao;
	@Autowired
	private FloorDao floorDao;

	@Override
	public Mapping process(LocationPojo item) throws Exception {
		if (item == null) {
			return null;
		}
		if (StringUtils.isEmpty(item.getAssociatedlevel())) {
			return null;
		}
		if (item.getAssociatedlevel().equals("Location/Sector")) {
			if (StringUtils.isEmpty(item.getLocationLevel4()) || StringUtils.isEmpty(item.getServiceArea())) {
				return null;
			}
			Mapping mapping = new Mapping();
			mapping.setType(MappingType.getName(MappingType.CITY_MAPPING));
			mapping.setCityId(cityDao.findByName(item.getServiceArea()).orElse(null));
			mapping.setAreaId(areaDao.findByName(item.getLocationLevel4()).orElse(null));
			return mapping;

		} else if (item.getAssociatedlevel().equals("Building Name/Block")) {
			if (StringUtils.isEmpty(item.getLocationLevel1()) || StringUtils.isEmpty(item.getLocationLevel4())
					|| StringUtils.isEmpty(item.getServiceArea())) {
				return null;
			}
			Mapping mapping = new Mapping();
			mapping.setType(MappingType.getName(MappingType.SOCIETY_MAPPING));
			mapping.setCityId(cityDao.findByName(item.getServiceArea()).orElse(null));
			mapping.setAreaId(areaDao.findByName(item.getLocationLevel1()).orElse(null));
			mapping.setSocietyId(societyDao.findByName(item.getLocationLevel4()).orElse(null));
			return mapping;
		} else if (item.getAssociatedlevel().equals("Tower/Floor")) {
			if (StringUtils.isEmpty(item.getLocationLevel1()) || StringUtils.isEmpty(item.getLocationLevel2())
					|| StringUtils.isEmpty(item.getLocationLevel4()) || StringUtils.isEmpty(item.getServiceArea())) {
				return null;
			}
			Mapping mapping = new Mapping();
			mapping.setType(MappingType.getName(MappingType.TOWER_MAPPING));
			mapping.setCityId(cityDao.findByName(item.getServiceArea()).orElse(null));
			mapping.setAreaId(areaDao.findByName(item.getLocationLevel1()).orElse(null));
			mapping.setSocietyId(societyDao.findByName(item.getLocationLevel2()).orElse(null));
			mapping.setTowerId(towerDao.findByName(item.getLocationLevel4()).orElse(null));
			return mapping;
		} else if (item.getAssociatedlevel().equals("Floor")) {
			if (StringUtils.isEmpty(item.getLocationLevel1()) || StringUtils.isEmpty(item.getLocationLevel2())
					|| StringUtils.isEmpty(item.getLocationLevel3()) || StringUtils.isEmpty(item.getLocationLevel4())
					|| StringUtils.isEmpty(item.getServiceArea())) {
				return null;
			}
			Mapping mapping = new Mapping();
			mapping.setType(MappingType.getName(MappingType.FLOOR_MAPPING));
			mapping.setCityId(cityDao.findByName(item.getServiceArea()).orElse(null));
			mapping.setAreaId(areaDao.findByName(item.getLocationLevel1()).orElse(null));
			mapping.setSocietyId(societyDao.findByName(item.getLocationLevel2()).orElse(null));
			mapping.setTowerId(towerDao.findByName(item.getLocationLevel3()).orElse(null));
			mapping.setFloorId(floorDao.findByName(item.getLocationLevel4()).orElse(null));
			return mapping;
		} else {
			return null;
		}
	}

}
