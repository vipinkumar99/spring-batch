package org.multiple.job.dao;

import java.util.Optional;

import org.multiple.job.entity.Tower;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TowerDao extends CrudRepository<Tower, Long> {
	@Query(value = "select c.id from towers c where c.name=:name", nativeQuery = true)
	Optional<Long> findByName(@Param("name") String name);
}
