package com.geolink3d.toolsregistry.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.geolink3d.toolsregistry.model.GeoWorker;
import com.geolink3d.toolsregistry.model.Role;
import com.geolink3d.toolsregistry.repository.GeoWorkerRepository;

@Service
public class GeoWorkerServiceImpl implements GeoWorkerService, UserDetailsService {
	
	private GeoWorkerRepository workerRepo;
	private RoleService roleService;
	
	@Autowired
	public void setWorkerRepo(GeoWorkerRepository workerRepo) {
		this.workerRepo = workerRepo;
	}
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		GeoWorker worker = workerRepo.findByUsername(username);
		
		if(worker == null) {
			throw new UsernameNotFoundException(username);
		}	
		
		return new GeoWorkerDetailsImpl(worker);
	}

	@Override
	public boolean registerGeoWorker(GeoWorker workerToRegister) {
		
		GeoWorker workerCheck = workerRepo.findByUsername(workerToRegister.getUsername());
		
		if(workerCheck != null) {
			return false;
		}
		
		Role userRole = roleService.findByRole("ROLE_USER");
		
		if(userRole != null) {
			workerToRegister.getRoles().add(userRole);
		}
		else {
			workerToRegister.addRoles("ROLE_USER");
		}
		
		workerToRegister.setPassword(EncoderService.encodeByBase64(workerToRegister.getPassword()));
		workerRepo.save(workerToRegister);
		
		return true;
	}

	@Override
	public GeoWorker findGeoWorkerByUserName(String username) {
		return workerRepo.findByUsername(username);
	}
	
	@Override
	public List<GeoWorker> findAll() {
		
		List<GeoWorker> workers = workerRepo.findAll();
		workers.forEach(w -> w.setRole(roleService.getGeoWorkerRoleAsString(w.getRoles())));
		Collections.sort(workers);
		
		return workers;
	}
	
	@Override
	public List<GeoWorker> findAllIfEnabled() {
		
		List<GeoWorker> workers = workerRepo.findAllIfEnabled();
		workers.forEach(w -> w.setRole(roleService.getGeoWorkerRoleAsString(w.getRoles())));
		Collections.sort(workers);
		
		return workers;
	}

	@Override
	public Optional<GeoWorker> findById(Long id) {
		return workerRepo.findById(id);
	}

	@Override
	public void save(GeoWorker worker) {	
		workerRepo.save(worker);
	}

	@Override
	public void delete(GeoWorker worker) {
		workerRepo.delete(worker);
	}

	@Override
	public List<GeoWorker> findByText(String text) {
		
		List<GeoWorker> geoworkers= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		geoworkers = workerRepo.findByText(text);
		
		if(geoworkers.isEmpty()) {
		geoworkers= workerRepo.findByText(text.toUpperCase());
		}
		if(geoworkers.isEmpty()) {
		geoworkers.addAll(workerRepo.findByText(text.toLowerCase()));
		}
		
		geoworkers.addAll(workerRepo.findByPassword(EncoderService.encodeByBase64(text)));
		
		geoworkers.forEach(w -> w.setRole(roleService.getGeoWorkerRoleAsString(w.getRoles())));
		Collections.sort(geoworkers);
		
		GeoWorkerHighlighter highlighter = new GeoWorkerHighlighter(geoworkers);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedGeoWorkerStore();
		
		return highlighter.getHighlightedGeoWorkerStore();
		
	}

	@Override
	public GeoWorker findByFullName(String fullName) {
		
		String firstName = fullName.split("\\s+")[1];
		String lastName = fullName.split("\\s+")[0];
		
		return workerRepo.findByFullName(firstName, lastName);
	}

	@Override
	public Long findIdByUsername(String username) {
		return workerRepo.findIdByUsername(username);
	}
	
	
	
}
