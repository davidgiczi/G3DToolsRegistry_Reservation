package com.geolink3d.toolsregistry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tools-registry/user")
public class UserReservationOperations {

	
	@RequestMapping("/reservation")
	public String goReservationPage() {
		
		
		return "user/reservations";
	}
	
}
