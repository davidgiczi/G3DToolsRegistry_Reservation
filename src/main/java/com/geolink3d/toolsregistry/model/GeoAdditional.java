package com.geolink3d.toolsregistry.model;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "additionals")
public class GeoAdditional implements Comparable<GeoAdditional> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private ZonedDateTime pickUpDate;
	private String pickUpPlace;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private ZonedDateTime putDownDate;
	private String putDownPlace;
	@Column(length = 1000)
	private String comment;
	@ManyToOne
	private GeoInstrument instrument;
	@ManyToOne
	private GeoWorker geoworker;
	private boolean deleted;
	private boolean used;
	
	public GeoAdditional() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ZonedDateTime getPickUpDate() {
		return pickUpDate;
	}

	public void setPickUpDate(ZonedDateTime pickUpDate) {
		this.pickUpDate = pickUpDate;
	}

	public ZonedDateTime getPutDownDate() {
		return putDownDate;
	}

	public void setPutDownDate(ZonedDateTime putDownDate) {
		this.putDownDate = putDownDate;
	}

	public String getPickUpPlace() {
		return pickUpPlace;
	}

	public void setPickUpPlace(String pickUpPlace) {
		this.pickUpPlace = pickUpPlace;
	}

	

	public String getPutDownPlace() {
		return putDownPlace;
	}

	public void setPutDownPlace(String putDownPlace) {
		this.putDownPlace = putDownPlace;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public GeoInstrument getInstrument() {
		return instrument;
	}

	public void setInstrument(GeoInstrument instrument) {
		this.instrument = instrument;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public GeoWorker getGeoworker() {
		return geoworker;
	}

	public void setGeoworker(GeoWorker geoworker) {
		this.geoworker = geoworker;
	}
	
	@Override
	public int compareTo(GeoAdditional o) {
		
		return  this.name.compareTo(o.getName()) > 0 ? 1 : this.name.compareTo(o.getName()) < 0 ? -1 : 0;
	}

	@Override
	public String toString() {
		return "GeoAdditional [id=" + id + ", name=" + name + ", pickUpDate=" + pickUpDate + ", pickUpPlace="
				+ pickUpPlace + ", putDownDate=" + putDownDate + ", putDownPlace=" + putDownPlace + ", comment="
				+ comment + ", instrument=" + instrument + ", deleted=" + deleted + ", used=" + used + "]";
	}

}
