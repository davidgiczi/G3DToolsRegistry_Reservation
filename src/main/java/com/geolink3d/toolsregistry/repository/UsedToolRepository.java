package com.geolink3d.toolsregistry.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.geolink3d.toolsregistry.model.UsedGeoTool;

public interface UsedToolRepository extends CrudRepository<UsedGeoTool, Long> {
	
	@Query(value = "select * from used_tools"
			+ " order by "
			+ "put_down_date desc",
			nativeQuery = true)
	List<UsedGeoTool> findAll();
	@Query(value = "select * from used_tools"
		+ " where "
		+ "(pick_up_date >= :from and put_down_date <= :to)",
		nativeQuery = true)
	List<UsedGeoTool> findBetweenDates(@Param("from") Date date1, @Param("to") Date date2);
	
	@Query(value = "select * from used_tools"
			+ " where "
			+ "(is_instrument = true and pick_up_date >= :begin and pick_up_date <= :end)",
			nativeQuery = true)
		List<UsedGeoTool> findInstrumentsByPickUpDate(@Param("begin") Date begin, @Param("end") Date end);
	
	@Query(value = "select * from used_tools"
			+ " where "
			+ "(is_instrument = false and pick_up_date >= :begin and pick_up_date <= :end)",
			nativeQuery = true)
		List<UsedGeoTool> findAdditionalsByPickUpDate(@Param("begin") Date begin, @Param("end") Date end);
	
	@Query(value = "select * from used_tools"
			+ " where "
			+ "(pick_up_date >= :begin and pick_up_date <= :end)",
			nativeQuery = true)
		List<UsedGeoTool> findByPickUpDate(@Param("begin") Date begin, @Param("end") Date end);
	
	@Query(value = "select * from used_tools"
			+ " where "
			+ "(is_instrument = true and put_down_date >= :begin and put_down_date <= :end)",
			nativeQuery = true)
		List<UsedGeoTool> findInstrumentsByPutDownDate(@Param("begin") Date begin, @Param("end") Date end);
	
	@Query(value = "select * from used_tools"
			+ " where "
			+ "(is_instrument = false and put_down_date >= :begin and put_down_date <= :end)",
			nativeQuery = true)
		List<UsedGeoTool> findAdditionalsByPutDownDate(@Param("begin") Date begin, @Param("end") Date end);
	
	@Query(value = "select * from used_tools"
			+ " where "
			+ "(put_down_date >= :begin and put_down_date <= :end)",
			nativeQuery = true)
		List<UsedGeoTool> findByPutDownDate(@Param("begin") Date begin, @Param("end") Date end);
	
	@Query(value = "select * from used_tools"
			+ " where "
			+ "toolname like %:text%"
			+ " or "
			+ "workername like %:text%"
			+ " or "
			+ "pick_up_place like %:text%"
			+ " or "
			+ "put_down_place like %:text%"
			+ " or "
			+ "comment like %:text%"
			, nativeQuery = true)
	List<UsedGeoTool> findUsedToolsByText(@Param("text") String text);
	
	@Query(value = "select * from used_tools"
			+ " where "
			+ "(is_instrument = true and toolname like %:text%)"
			+ " or "
			+ "(is_instrument = true and workername like %:text%)"
			+ " or "
			+ "(is_instrument = true and pick_up_place like %:text%)"
			+ " or "
			+ "(is_instrument = true and put_down_place like %:text%)"
			+ " or "
			+ "(is_instrument = true and comment like %:text%)"
			, nativeQuery = true)
	List<UsedGeoTool> findUsedToolIntrumentsByText(@Param("text") String text);
	
	@Query(value = "select * from used_tools"
			+ " where "
			+ "(is_instrument = false and toolname like %:text%)"
			+ " or "
			+ "(is_instrument = false and workername like %:text%)"
			+ " or "
			+ "(is_instrument = false and pick_up_place like %:text%)"
			+ " or "
			+ "(is_instrument = false and put_down_place like %:text%)"
			+ " or "
			+ "(is_instrument = false and comment like %:text%)"
			, nativeQuery = true)
	List<UsedGeoTool> findUsedToolAdditionalsByText(@Param("text") String text);
	
	@Query(value = "select * from used_tools"
			+ " order by "
			+ "put_down_date desc"
			+ " fetch first :usedGeoToolPcs row only",
			nativeQuery = true)
	List<UsedGeoTool> findFirstXUsedGeoToolsOrderByPutDownDateDesc(@Param("usedGeoToolPcs") int usedGeoToolsPsc);
	
}
