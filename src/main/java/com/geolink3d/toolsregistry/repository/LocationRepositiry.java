package com.geolink3d.toolsregistry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.geolink3d.toolsregistry.model.Location;

public interface LocationRepositiry extends CrudRepository<Location, Long> {

	@Query(value = "select * from locations where name like :name" , nativeQuery = true)
	Location getLocationByName(@Param("name") String name);
	
	List<Location> findAll();
}
