package org.multiple.job.dao;

import java.util.Optional;

import org.multiple.job.entity.Floor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorDao extends CrudRepository<Floor, Long> {
	@Query(value = "select c.id from floors c where c.name=:name", nativeQuery = true)
	Optional<Long> findByName(@Param("name") String name);
}
