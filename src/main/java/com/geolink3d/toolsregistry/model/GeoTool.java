package com.geolink3d.toolsregistry.model;

import java.time.ZonedDateTime;

public class GeoTool implements Comparable<GeoTool> {

	private Long id;
	private String toolName;
	private String toolUser;
	private String instrumentName;
	private ZonedDateTime pickUpDate;
	private String pickUpPlace;
	private String comment;
	private boolean isColored;
	private boolean isInstrument;
	private boolean isUsed;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public String getToolUser() {
		return toolUser;
	}
	public void setToolUser(String toolUser) {
		this.toolUser = toolUser;
		
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	
	public ZonedDateTime getPickUpDate() {
		return pickUpDate;
	}
	public void setPickUpDate(ZonedDateTime pickUpDate) {
		this.pickUpDate = pickUpDate;
	}
	public String getPickUpPlace() {
		return pickUpPlace;
	}
	public void setPickUpPlace(String pickUpPlace) {
		this.pickUpPlace = pickUpPlace;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public boolean isColored() {
		return isColored;
	}
	public void setColored(boolean isColored) {
		this.isColored = isColored;
	}
	
	public boolean isInstrument() {
		return isInstrument;
	}
	public void setInstrument(boolean isInstrument) {
		this.isInstrument = isInstrument;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	@Override
	public int compareTo(GeoTool o) {
		
		return this.getPickUpDate().toEpochSecond() > o.getPickUpDate().toEpochSecond()
				?  1 : o.getPickUpDate().toEpochSecond() < this.getPickUpDate().toEpochSecond() ? -1 : 0;
	}
	@Override
	public String toString() {
		return "GeoTool [id=" + id + ", toolName=" + toolName + ", toolUser=" + toolUser + ", instrumentName="
				+ instrumentName + ", pickUpDate=" + pickUpDate + ", pickUpPlace=" + pickUpPlace + ", comment="
				+ comment + ", isColored=" + isColored + ", isInstrument=" + isInstrument + ", isUsed=" + isUsed + "]";
	}
	
}
