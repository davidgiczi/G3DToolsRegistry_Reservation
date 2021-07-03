package com.geolink3d.toolsregistry.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="geoworkers")
public class GeoWorker implements Comparable<GeoWorker> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstname;
	private String lastname;
	@OneToMany(mappedBy = "geoworker")
	private List<GeoInstrument> instruments;
	@OneToMany(mappedBy = "geoworker")
	private List<GeoAdditional> additionals;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	private boolean enabled;
	private String role;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name= "geoworkers_roles",
			joinColumns= {@JoinColumn(name = "geoworker_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")}
			)
	private Set<Role> roles = new HashSet<>();

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public List<GeoAdditional> getAdditionals() {
		return additionals;
	}

	public void setAdditionals(List<GeoAdditional> additionals) {
		this.additionals = additionals;
	}

	public void addRoles(String roleName) {
		
		if(this.roles == null || this.roles.isEmpty()) {
			this.roles = new HashSet<>();
		}
			this.roles.add(new Role(roleName));
		
	}
	
	@Override
	public int compareTo(GeoWorker o) {
		
		String fullName1 = this.getLastname() + " " + this.getFirstname();
		String fullName2 = o.getLastname() + " " + o.getFirstname();
		
		return fullName1.compareTo(fullName2) > 0 ? 1 : fullName1.compareTo(fullName2) < 0 ? -1 : 0;
	}
	
	@Override
	public String toString() {
		return "GeoWorker [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", instruments="
				+ instruments + ", username=" + username + ", password=" + password + ", enabled=" + enabled
				+ ", role=" + role + "]";
	}

	
	
	
}
