package com.geolink3d.toolsregistry.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geolink3d.toolsregistry.model.GeoAdditional;
import com.geolink3d.toolsregistry.repository.GeoAdditionalRepository;

@Service
public class GeoAdditionalService {

	
	private GeoAdditionalRepository additionalRepo;

	@Autowired
	public void setAdditionalRepo(GeoAdditionalRepository additionalRepo) {
		this.additionalRepo = additionalRepo;
	}
	
	public boolean saveNewGeoAdditional(String name) {
		
		GeoAdditional additional = additionalRepo.findGeoAdditionalByName(name);
		
		if(additional == null) {
			additional = new GeoAdditional();
			additional.setName(name);
			additionalRepo.save(additional);
			return true;
		}
		
		return false;
	}
	
	public Optional<GeoAdditional> findById(Long id){
		return additionalRepo.findById(id);
	}
	
	public void save(GeoAdditional additional) {
		additionalRepo.save(additional);
	}
	
	public List<GeoAdditional> findUseableGeoAdditional(){
		List<GeoAdditional> usable = additionalRepo.findNotDeletedGeoAdditionals();
		Collections.sort(usable);
		return usable;
	}
	
	public List<GeoAdditional> findDeletedGeoAdditionals(){
		List<GeoAdditional> deleted = additionalRepo.findDeletedGeoAdditionals();
		Collections.sort(deleted);
		return deleted;
	}
	
}
