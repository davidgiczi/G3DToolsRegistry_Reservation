package com.geolink3d.toolsregistry.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class GeoToolReservationService {

	
	public String getActualDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date(System.currentTimeMillis()));
	}
	
}
