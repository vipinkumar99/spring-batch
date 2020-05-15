package org.batch.process.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.batch.process.entity.Subarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubareaDao extends JpaRepository<Subarea, Long> {
	@Query(value = "select s.id from subareas s where s.name=:name", nativeQuery = true)
	Optional<Long> findByName(@Param("name") String name);
	@Modifying
	@Transactional
	@Query(value = "update subareas c set c.modified=:modified where c.modified=true", nativeQuery = true)
	int updateModified(@Param("modified") boolean modified);
}
