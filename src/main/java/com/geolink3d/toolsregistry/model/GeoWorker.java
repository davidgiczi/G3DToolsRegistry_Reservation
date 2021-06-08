package com.geolink3d.toolsregistry.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="geoworkers")
public class GeoWorker implements Comparable<GeoWorker>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstname;
	private String lastname;
	@OneToMany(mappedBy = "geoworker")
	private List<GeoInstrument> instruments;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	private boolean enabled;

	public GeoWorker() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<GeoInstrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<GeoInstrument> instruments) {
		this.instruments = instruments;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int compareTo(GeoWorker o) {
		
		String fullname1 = this.lastname + " " + this.firstname;
		String fullname2 = o.lastname + " " + o.firstname; 
		
		return fullname1.compareTo(fullname2) > 0 ? 1 : fullname1.compareTo(fullname2) < 0 ? -1 : 0;
	}
	
	
	
}
