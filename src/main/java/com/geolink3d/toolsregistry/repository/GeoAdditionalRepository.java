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
	
	@Query(value = "select * from additionals where deleted = false and name like %:text%"
			, nativeQuery = true)
	List<GeoAdditional> findNotDeletedGeoAdditionalsByText(@Param("text") String text);
	
	@Query(value = "select * from additionals where deleted = true and name like %:text%"
			, nativeQuery = true)
	List<GeoAdditional> findDeletedGeoAdditionalsByText(@Param("text") String text);
	
	@Query(value = "select * from additionals inner join geoworkers"
			+ " on "
			+ "(additionals.geoworker_id = geoworkers.id and additionals.deleted = false and additionals.used = true and geoworkers.firstname like %:text%)"
			+ " or "
			+ "(additionals.geoworker_id = geoworkers.id and additionals.deleted = false and additionals.used = true and geoworkers.lastname like %:text%)"
			+ " or "
			+ "(additionals.geoworker_id = geoworkers.id and additionals.deleted = false and additionals.used = true and additionals.pick_up_place like %:text%)"
			+ " or "
			+ "(additionals.geoworker_id = geoworkers.id and additionals.deleted = false and additionals.used = true and additionals.name like %:text%)"
			, nativeQuery = true)
	List<GeoAdditional> findGeoAdditionalsInUseByText(@Param("text") String text);
	
	@Query(value = "select * from additionals inner join geoworkers"
			+ " on "
			+ "(additionals.geoworker_id = geoworkers.id and additionals.geoworker_id = :id and additionals.deleted = false and additionals.used = true and geoworkers.firstname like %:text%)"
			+ " or "
			+ "(additionals.geoworker_id = geoworkers.id and additionals.geoworker_id = :id and additionals.deleted = false and additionals.used = true and geoworkers.lastname like %:text%)"
			+ " or "
			+ "(additionals.geoworker_id = geoworkers.id and additionals.geoworker_id = :id and additionals.deleted = false and additionals.used = true and additionals.pick_up_place like %:text%)"
			+ " or "
			+ "(additionals.geoworker_id = geoworkers.id and additionals.geoworker_id = :id and additionals.deleted = false and additionals.used = true and additionals.name like %:text%)"
			, nativeQuery = true)
	List<GeoAdditional> findGeoAdditionalsInUseByTextAndUserId(@Param("text") String text, @Param("id") Long id);
	
	@Query(value = "select * from additionals"
			+ " where "
			+ "(deleted = false and used = true and instrument_id is null and pick_up_date >= :from and pick_up_date <= :to)"
			,nativeQuery = true)
		List<GeoAdditional> findBetweenDates(@Param("from") Date date1, @Param("to") Date date2);
	
	@Query(value = "select * from additionals"
			+ " where "
			+ "(deleted = false and used = true and instrument_id is null and pick_up_date >= :begin and pick_up_date <= :end)"
			,nativeQuery = true)
		List<GeoAdditional> findByPickUpDate(@Param("begin") Date begin, @Param("end") Date end);
	
	@Query(value = "select * from additionals"
			+ " where "
			+ "(additionals.geoworker_id = :id and deleted = false and used = true and instrument_id is null and pick_up_date >= :begin and pick_up_date <= :end)"
			,nativeQuery = true)
		List<GeoAdditional> findByPickUpDateAndUserId(@Param("begin") Date begin, @Param("end") Date end, @Param("id") Long id);
	
	@Query(value = "select * from additionals"
			+ " where "
			+ "(deleted = false and used = true and instrument_id is null and put_down_date >= :begin and put_down_date <= :end)"
			,nativeQuery = true)
		List<GeoAdditional> findByPutDownDate(@Param("begin") Date begin, @Param("end") Date end);
	
	@Query(value = "select * from additionals where additionals.geoworker_id = :id" , nativeQuery = true)
	List<GeoAdditional> findGeoAdditionalsByUserId(@Param("id") Long id);
} 
