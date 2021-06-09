package com.geolink3d.toolsregistry.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.geolink3d.toolsregistry.model.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String roleName);
	void deleteById(Long id);
	List<Role> findAll();
	
}

