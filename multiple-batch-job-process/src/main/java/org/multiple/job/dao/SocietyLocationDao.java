package org.multiple.job.dao;

import org.multiple.job.entity.SocietyLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocietyLocationDao extends CrudRepository<SocietyLocation, Long> {

}
