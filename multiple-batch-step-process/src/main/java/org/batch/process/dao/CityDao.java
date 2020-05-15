package org.batch.process.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.batch.process.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao extends JpaRepository<City, Long> {
	@Query(value = "select c.id from cities c where c.name=:name", nativeQuery = true)
	Optional<Long> findByName(@Param("name") String name);

	@Query(value = "select c.name from cities c ", nativeQuery = true)
	List<String> findName();

	@Modifying
	@Transactional
	@Query(value = "update cities c set c.modified=:modified where c.modified=true", nativeQuery = true)
	int updateModified(@Param("modified") boolean modified);
}
