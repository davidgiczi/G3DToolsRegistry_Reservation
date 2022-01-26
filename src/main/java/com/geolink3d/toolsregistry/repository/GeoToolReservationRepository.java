package com.geolink3d.toolsregistry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.geolink3d.toolsregistry.model.GeoToolReservation;

public interface GeoToolReservationRepository extends CrudRepository<GeoToolReservation, Long> {

	
	@Query(value = "select * from reservations" , nativeQuery = true)
	List<GeoToolReservation> findAll();
	
	@Query(value = "select * from reservations where tool_id = :toolId and is_instrument = true" , nativeQuery = true)
	List<GeoToolReservation> findGeoInstrumentReservationsByToolId(@Param("toolId") Long toolId);
	
	@Query(value = "select * from reservations where tool_id = :toolId and is_instrument = false" , nativeQuery = true)
	List<GeoToolReservation> findGeoAdditionalReservationsByToolId(@Param("toolId") Long toolId);

}
