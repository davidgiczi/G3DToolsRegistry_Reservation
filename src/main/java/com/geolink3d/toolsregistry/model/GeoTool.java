package com.geolink3d.toolsregistry.model;

import java.util.Date;

public class GeoTool {

	private Long id;
	private String toolName;
	private String toolUser;
	private Date pickUpDate;
	private String pickUpPlace;
	private String comment;
	private boolean isColored;
	private boolean isInstruction;
	
	
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
	public Date getPickUpDate() {
		return pickUpDate;
	}
	public void setPickUpDate(Date pickUpDate) {
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
	public boolean isInstruction() {
		return isInstruction;
	}
	public void setInstruction(boolean isInstruction) {
		this.isInstruction = isInstruction;
	}
	
	
}
