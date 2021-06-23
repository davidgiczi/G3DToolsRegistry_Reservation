package com.geolink3d.toolsregistry.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "used_tools")
public class UsedTool implements Comparable<UsedTool> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String workername;
	private String toolname;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date pickUpDate;
	private String pickUpPlace;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date putDownDate;
	private String putDownPlace;
	@Column(length = 1000)
	private String comment;
	
	public UsedTool() {
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
	public Date getPutDownDate() {
		return putDownDate;
	}
	public void setPutDownDate(Date putDownDate) {
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

	@Override
	public int compareTo(UsedTool o) {
		
		return this.getPickUpDate().getTime() > o.getPickUpDate().getTime() ?  - 1 : o.getPickUpDate().getTime() < this.getPickUpDate().getTime() ? 1 : 0;
	}
	
	@Override
	public String toString() {
		return "UsedTool [id=" + id + ", workername=" + workername + ", toolname=" + toolname + ", pickUpDate="
				+ pickUpDate + ", pickUpPlace=" + pickUpPlace + ", putDownDate=" + putDownDate + ", putDownPlace="
				+ putDownPlace + ", comment=" + comment + "]";
	}
		
}
