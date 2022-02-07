package com.geolink3d.toolsregistry.model;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="reservations")
public class GeoToolReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private ZonedDateTime takeAwayDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private ZonedDateTime bringBackDate;
	private Long toolId;
	private Long userId;
	private boolean isInstrument;
	private boolean isActive;
	
	public GeoToolReservation() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getTakeAwayDate() {
		
		return takeAwayDate;
	}

	public void setTakeAwayDate(ZonedDateTime takeAwayDate) {
		this.takeAwayDate = takeAwayDate;
	}

	public ZonedDateTime getBringBackDate() {
		
		return bringBackDate;
	}

	public void setBringBackDate(ZonedDateTime bringBackDate) {
		this.bringBackDate = bringBackDate;
	}

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isInstrument() {
		return isInstrument;
	}

	public void setInstrument(boolean isInstrument) {
		this.isInstrument = isInstrument;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
