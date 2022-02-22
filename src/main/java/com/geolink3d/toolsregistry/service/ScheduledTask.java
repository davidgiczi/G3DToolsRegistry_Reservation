package com.geolink3d.toolsregistry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

	@Autowired
	private GeoToolReservationService reservationService;
	
	
	@Scheduled(cron = "0 0/1 0-23 * * *" )
	public void scheduledTask() {
		
		reservationService.restoreGeoTool();
		reservationService.issueGeoTool();
	}
	
}
