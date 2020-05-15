package org.multiple.job.dao;

import org.multiple.job.entity.TowerLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TowerLocationDao extends CrudRepository<TowerLocation, Long> {

}
