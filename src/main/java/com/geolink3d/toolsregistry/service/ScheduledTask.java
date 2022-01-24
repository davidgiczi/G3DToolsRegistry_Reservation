package com.geolink3d.toolsregistry.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

	
	@Scheduled(cron = "0 5 0-23 * * *" )
	public void scheduledTask() {
		
	}
	
}
