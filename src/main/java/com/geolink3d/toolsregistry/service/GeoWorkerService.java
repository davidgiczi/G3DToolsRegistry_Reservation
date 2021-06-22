package com.geolink3d.toolsregistry.service;

import java.util.List;
import java.util.Optional;

import com.geolink3d.toolsregistry.model.GeoWorker;

public interface GeoWorkerService {
	
	boolean registerGeoWorker(GeoWorker workerToRegister);
	GeoWorker findGeoWorkerByUserName(String username);
	List<GeoWorker> findAll();
	Optional<GeoWorker> findById(Long id);
	void save(GeoWorker worker);
	void delete(GeoWorker worker);
	List<GeoWorker> findByText(String text);
	GeoWorker findByFullName(String fullName);
}
