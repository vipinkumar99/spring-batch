package org.batch.process.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.batch.process.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaDao extends JpaRepository<Area, Long> {
	@Query(value = "select a.id from areas a where a.name=:name", nativeQuery = true)
	Optional<Long> findByName(@Param("name") String name);
	@Modifying
	@Transactional
	@Query(value = "update areas c set c.modified=:modified where c.modified=true", nativeQuery = true)
	int updateModified(@Param("modified") boolean modified);
}
