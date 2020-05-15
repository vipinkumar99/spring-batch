package org.multiple.job.dao;

import org.multiple.job.entity.FloorLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorLocationDao extends CrudRepository<FloorLocation, Long> {

}
