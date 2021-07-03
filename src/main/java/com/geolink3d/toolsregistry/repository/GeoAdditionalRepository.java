package com.geolink3d.toolsregistry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.geolink3d.toolsregistry.model.GeoAdditional;


public interface GeoAdditionalRepository extends CrudRepository<GeoAdditional, Long> {

	@Query(value = "select * from additionals where name like :name" , nativeQuery = true)
	GeoAdditional findGeoAdditionalByName(@Param("name") String name);
	@Query(value = "select * from additionals where deleted = false" , nativeQuery = true)
	List<GeoAdditional> findNotDeletedGeoAdditionals();
	@Query(value = "select * from additionals where deleted = true" , nativeQuery = true)
	List<GeoAdditional> findDeletedGeoAdditionals();
	@Query(value = "select * from additionals where deleted = false and used = true" , nativeQuery = true)
	List<GeoAdditional> findNotDeletedButUsedGeoAdditionals();
	
}
