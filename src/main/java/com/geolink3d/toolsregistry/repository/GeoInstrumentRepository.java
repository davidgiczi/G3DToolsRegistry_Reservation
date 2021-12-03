package com.geolink3d.toolsregistry.repository;

import java.util.Date;
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
	
	@Query(value = "select * from instruments where deleted = false and used = true order by pick_up_date desc" , nativeQuery = true)
	List<GeoInstrument> findNotDeletedButUsedGeoInstruments();
	
	@Query(value = "select * from instruments where deleted = false and name like %:text%"
			, nativeQuery = true)
	List<GeoInstrument> findNotDeletedGeoInstrumentsByText(@Param("text") String text);
	
	@Query(value = "select * from instruments where deleted = true and name like %:text%"
			, nativeQuery = true)
	List<GeoInstrument> findDeletedGeoInstrumentsByText(@Param("text") String text);
	
	@Query(value = "select * from instruments inner join geoworkers"
			+ " on "
			+ "(instruments.geoworker_id = geoworkers.id and instruments.deleted = false and instruments.used = true and geoworkers.firstname like %:text%)"
			+ " or "
			+ "(instruments.geoworker_id = geoworkers.id and instruments.deleted = false and instruments.used = true and geoworkers.lastname like %:text%)"
			+ " or "
			+ "(instruments.geoworker_id = geoworkers.id and instruments.deleted = false and instruments.used = true and instruments.pick_up_place like %:text%)"
			+ " or "
			+ "(instruments.geoworker_id = geoworkers.id and instruments.deleted = false and instruments.used = true and instruments.name like %:text%)"
			, nativeQuery = true)
	List<GeoInstrument> findGeoInstrumentsInUseByText(@Param("text") String text);
	
	@Query(value = "select * from instruments inner join geoworkers"
			+ " on "
			+ "(instruments.geoworker_id = geoworkers.id and instruments.geoworker_id = :id and instruments.deleted = false and instruments.used = true and geoworkers.firstname like %:text%)"
			+ " or "
			+ "(instruments.geoworker_id = geoworkers.id and instruments.geoworker_id = :id and instruments.deleted = false and instruments.used = true and geoworkers.lastname like %:text%)"
			+ " or "
			+ "(instruments.geoworker_id = geoworkers.id and instruments.geoworker_id = :id and instruments.deleted = false and instruments.used = true and instruments.pick_up_place like %:text%)"
			+ " or "
			+ "(instruments.geoworker_id = geoworkers.id and instruments.geoworker_id = :id and instruments.deleted = false and instruments.used = true and instruments.name like %:text%)"
			, nativeQuery = true)
	List<GeoInstrument> findGeoInstrumentsInUseByTextAndUserId(@Param("text") String text, @Param("id") Long id);
	
	@Query(value = "select * from instruments"
			+ " where "
			+ "(deleted = false and used = true and pick_up_date >= :from and pick_up_date <= :to)"
			,nativeQuery = true)
		List<GeoInstrument> findBetweenDates(@Param("from") Date date1, @Param("to") Date date2);
	
	@Query(value = "select * from instruments"
			+ " where "
			+ "(deleted = false and used = true and pick_up_date >= :begin and pick_up_date <= :end)"
			,nativeQuery = true)
		List<GeoInstrument> findByPickUpDate(@Param("begin") Date begin, @Param("end") Date end);
	
	@Query(value = "select * from instruments"
			+ " where "
			+ "(instruments.geoworker_id = :id and deleted = false and used = true and pick_up_date >= :begin and pick_up_date <= :end)"
			,nativeQuery = true)
		List<GeoInstrument> findByPickUpDateAndUserId(@Param("begin") Date begin, @Param("end") Date end, @Param("id") Long id);
	
	@Query(value = "select * from instruments"
			+ " where "
			+ "(deleted = false and used = true and put_down_date >= :begin and put_down_date <= :end)"
			,nativeQuery = true)
		List<GeoInstrument> findByPutDownDate(@Param("begin") Date begin, @Param("end") Date end);
	
	@Query(value = "select * from instruments where instruments.geoworker_id = :id order by pick_up_date desc" , nativeQuery = true)
	List<GeoInstrument> findGeoInstrumentsByUserId(@Param("id") Long id);
}
