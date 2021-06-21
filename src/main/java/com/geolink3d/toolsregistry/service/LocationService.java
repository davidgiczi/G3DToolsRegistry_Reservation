package com.geolink3d.toolsregistry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geolink3d.toolsregistry.model.Location;
import com.geolink3d.toolsregistry.repository.LocationRepositiry;

@Service
public class LocationService {

	
	private LocationRepositiry locationRepo;
	
	@Autowired
	public void setLocationRepo(LocationRepositiry locationRepo) {
		this.locationRepo = locationRepo;
	}
	
public boolean saveNewGeoLocation(String name) {
		
		Location location = locationRepo.getLocationByName(name);
		
		if(location == null) {
			location = new Location();
			location.setName(name);
			locationRepo.save(location);
			return true;
		}
		
		return false;
	}
	
public List<Location> findAll(){
	return locationRepo.findAll();
}

}
