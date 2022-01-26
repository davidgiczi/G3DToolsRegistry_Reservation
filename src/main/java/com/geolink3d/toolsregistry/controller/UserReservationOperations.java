package com.geolink3d.toolsregistry.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.geolink3d.toolsregistry.model.GeoTool;
import com.geolink3d.toolsregistry.service.GeoAdditionalService;
import com.geolink3d.toolsregistry.service.GeoInstrumentService;
import com.geolink3d.toolsregistry.service.GeoToolReservationService;


@Controller
@RequestMapping("/tools-registry/user")
public class UserReservationOperations {

	
	@Autowired
	private GeoToolReservationService reservationService;
	@Autowired
	private GeoInstrumentService instrumentService;
	@Autowired
	private GeoAdditionalService additionalService;
	
	@RequestMapping("/reservation")
	public String goReservationPage(Model model) {
		
		List<GeoTool> instrumentStore = instrumentService.findUseableGeoTools();
		List<GeoTool> additionalStore = additionalService.findUseableGeoTools();
		model.addAttribute("instruments", instrumentStore);
		model.addAttribute("additionals", additionalStore);
		model.addAttribute("actualDate", reservationService.getActualDate());
		
		return "user/reservations";
	}
	
	@PostMapping("/userReservation")
	public String getUserData(HttpServletRequest request) {
		
		String instrumentId = request.getParameter("instrument");
		String additionalId = request.getParameter("additional");
		String startDay = request.getParameter("start-day");
		String endDay = request.getParameter("end-day");
 		
		System.out.println("instrument: " + instrumentId);
		System.out.println("additional: " + additionalId);
		System.out.println("Start date: " + startDay);
		System.out.println("End date: " + endDay);
		
		return "redirect:/tools-registry/user/reservation";
	}
	
}
