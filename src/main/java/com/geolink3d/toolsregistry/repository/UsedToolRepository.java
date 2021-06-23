package com.geolink3d.toolsregistry.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.geolink3d.toolsregistry.model.UsedTool;

public interface UsedToolRepository extends CrudRepository<UsedTool, Long> {

	List<UsedTool> findAll();
	@Query(value = "select * from used_tools"
		+ " where "
		+ "(pick_up_date >= :from and put_down_date <= :to)",
		nativeQuery = true)
	List<UsedTool> findBetweenDates(@Param("from") Date date1, @Param("to") Date date2);
	
}
