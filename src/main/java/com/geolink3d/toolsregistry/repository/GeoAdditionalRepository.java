package com.geolink3d.toolsregistry.repository;

import java.util.Date;
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
	@Query(value = "select * from additionals where deleted = false and used = true and instrument_id is null" , nativeQuery = true)
	List<GeoAdditional> findSingleUsedGeoAdditionals();
	@Query(value = "select * from additionals where deleted = false and used = true and instrument_id is not null" , nativeQuery = true)
	List<GeoAdditional> findPairedUsedGeoAdditionals();
	@Query(value = "select * from additionals where deleted = false and name like %:text%"
			, nativeQuery = true)
	List<GeoAdditional> findNotDeletedGeoAdditionalsByText(@Param("text") String text);
	@Query(value = "select * from additionals where deleted = true and name like %:text%"
			, nativeQuery = true)
	List<GeoAdditional> findDeletedGeoAdditionalsByText(@Param("text") String text);
	@Query(value = "select * from additionals"
			+ " where "
			+ "(deleted = false and used = true and pick_up_date >= :from and pick_up_date <= :to)"
			,nativeQuery = true)
		List<GeoAdditional> findBetweenDates(@Param("from") Date date1, @Param("to") Date date2);
	
}
