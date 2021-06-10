package com.geolink3d.toolsregistry.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geolink3d.toolsregistry.model.Role;
import com.geolink3d.toolsregistry.repository.RoleRepository;

@Service
public class RoleService {
	
	
	private RoleRepository roleRepo;
	
	
	@Autowired
	public void setRoleRepo(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}


	public List<String> getRoleStringStore(){
			
	List<Role> roles = roleRepo.findAll();
	
	List<String> roleStringStore = roles.stream().map(r -> r.getRole()).collect(Collectors.toList());
		
	return roleStringStore;
	}
	
	
	public String getGeoWorkerRoleAsString(Set<Role> workerRoles) {
		
		List<Role> roles = roleRepo.findAll();
		String roleString = "ROLE_USER";
		
		for (Role role : roles) {
			if(workerRoles.contains(role)) {
				roleString = role.getRole();
			}
		}
		
		return roleString;
		
	}
	
	public Role findByRole(String roleName) {
		return roleRepo.findByRole(roleName);
	}
	
}
