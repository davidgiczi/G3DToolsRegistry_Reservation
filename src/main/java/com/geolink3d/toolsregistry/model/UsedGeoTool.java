package com.geolink3d.toolsregistry.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "used_tools")
public class UsedGeoTool implements Comparable<UsedGeoTool> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String workername;
	private String toolname;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private ZonedDateTime pickUpDate;
	private String pickUpPlace;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private ZonedDateTime putDownDate;
	private String putDownPlace;
	@Column(length = 1000)
	private String comment;
	private boolean isInstrument;
	
	public UsedGeoTool() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWorkername() {
		return workername;
	}
	public void setWorkername(String workername) {
		this.workername = workername;
	}
	public String getToolname() {
		return toolname;
	}
	public void setToolname(String toolname) {
		this.toolname = toolname;
	}
	
	public String getPickUpPlace() {
		return pickUpPlace;
	}
	public void setPickUpPlace(String pickUpPlace) {
		this.pickUpPlace = pickUpPlace;
	}
	
	public ZonedDateTime getPickUpDate() {
		if(pickUpDate != null) {
		return pickUpDate.withZoneSameInstant(ZoneId.of("Europe/Budapest"));
		}
		return pickUpDate;
	}

	public void setPickUpDate(ZonedDateTime pickUpDate) {
		this.pickUpDate = pickUpDate;
	}

	public ZonedDateTime getPutDownDate() {
		if(putDownDate != null) {
		return putDownDate.withZoneSameInstant(ZoneId.of("Europe/Budapest"));
		}
		return putDownDate;
	}

	public void setPutDownDate(ZonedDateTime putDownDate) {
		this.putDownDate = putDownDate;
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

	public boolean isInstrument() {
		return isInstrument;
	}

	public void setInstrument(boolean isInstrument) {
		this.isInstrument = isInstrument;
	}

	@Override
	public int compareTo(UsedGeoTool o) {
		
		return this.getPutDownDate().toEpochSecond() > o.getPutDownDate().toEpochSecond() ?
				- 1 : o.getPutDownDate().toEpochSecond() < this.getPutDownDate().toEpochSecond() ? 1 : 0;
	}

	@Override
	public String toString() {
		return "UsedGeoTool [id=" + id + ", workername=" + workername + ", toolname=" + toolname + ", pickUpDate="
				+ pickUpDate + ", pickUpPlace=" + pickUpPlace + ", putDownDate=" + putDownDate + ", putDownPlace="
				+ putDownPlace + ", comment=" + comment + ", isInstrument=" + isInstrument + "]";
	}
	
		
}
