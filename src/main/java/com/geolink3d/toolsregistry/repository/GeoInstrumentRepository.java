package com.geolink3d.toolsregistry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.geolink3d.toolsregistry.model.GeoInstrument;

public interface GeoInstrumentRepository extends CrudRepository<GeoInstrument, Long> {

	@Query(value = "select * from instruments where name like :name" , nativeQuery = true)
	GeoInstrument findGeoInstrumentByName(@Param("name") String name);
	@Query(value = "select * from instruments where deleted = false" , nativeQuery = true)
	List<GeoInstrument> findNotDeletedGeoInstruments();
	@Query(value = "select * from instruments where deleted = true" , nativeQuery = true)
	List<GeoInstrument> findDeletedGeoInstruments();
	@Query(value = "select * from instruments where deleted = false and used = true" , nativeQuery = true)
	List<GeoInstrument> findNotDeletedButUsedGeoInstruments();
	@Query(value = "select * from instruments where"
			+ " (deleted = false and name like %:text%)"
			+ " or "
			+ "(deleted = false and comment like %:text%)"
			+ " or "
			+ "(deleted = false and pick_up_place like %:text%)"
			, nativeQuery = true)
	List<GeoInstrument> findNotDeletedInstrumentsByText(@Param("text") String text);
	@Query(value = "select * from instruments where"
			+ " (deleted = true and name like %:text%)"
			+ " or "
			+ "(deleted = true and comment like %:text%)"
			+ " or "
			+ "(deleted = true and pick_up_place like %:text%)"
			, nativeQuery = true)
	List<GeoInstrument> findDeletedInstrumentsByText(@Param("text") String text);
	
}
