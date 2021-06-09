package com.geolink3d.toolsregistry.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.geolink3d.toolsregistry.model.GeoWorker;
import com.geolink3d.toolsregistry.model.Role;


public class GeoWorkerDetailsImpl implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GeoWorker worker;
		
	public GeoWorkerDetailsImpl(GeoWorker worker) {
	
		this.worker = worker;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> authorities = new HashSet<>();
		Set<Role> roles = worker.getRoles();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return  worker.getPassword();
	}

	@Override
	public String getUsername() {
		return worker.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return worker.isEnabled();
	}

}
