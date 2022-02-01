package com.geolink3d.toolsregistry.dao;

import java.time.ZonedDateTime;

public class GeoToolReservationDAO {

	
	private Long userId;
	private String toolName;
	private String userName;
	private ZonedDateTime takeAwayDate;
	private ZonedDateTime bringBackDate;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	
}
