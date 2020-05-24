package org.batch.process.dao;

import org.batch.process.entity.Tracking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingDao extends CrudRepository<Tracking, Integer> {

}
