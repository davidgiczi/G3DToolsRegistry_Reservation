package com.geolink3d.toolsregistry.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	 
	
}
