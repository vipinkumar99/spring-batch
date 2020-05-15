package org.multiple.job.dao;

import org.multiple.job.entity.Mapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MappingDao extends CrudRepository<Mapping, Long> {

}
