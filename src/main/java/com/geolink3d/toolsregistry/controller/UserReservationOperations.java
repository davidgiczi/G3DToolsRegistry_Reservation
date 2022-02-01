package com.geolink3d.toolsregistry.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.geolink3d.toolsregistry.model.GeoTool;
import com.geolink3d.toolsregistry.model.GeoToolReservation;
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
		List<GeoToolReservation> reservationStore = reservationService.findAllGeoReservations();
		model.addAttribute("instruments", instrumentStore);
		model.addAttribute("additionals", additionalStore);
		model.addAttribute("reservations", reservationStore);
		model.addAttribute("actualDate", reservationService.getActualDate());
		
		return "user/reservations";
	}
	
	@PostMapping("/userReservation")
	public String getUserData(HttpServletRequest request, RedirectAttributes redAttr) throws ParseException {
		
		String instrumentId = request.getParameter("instrument");
		String additionalId = request.getParameter("additional");
		String startDay = request.getParameter("start-day");
		String endDay = request.getParameter("end-day");
 		
		if( !reservationService.isChosenGeoToolReservation(instrumentId, additionalId) ) {
			redAttr.addAttribute("noTool", true);
			return "redirect:/tools-registry/user/reservation";
		}
		if( !reservationService.isValidGeoToolReservationDates(startDay, endDay)) {
			redAttr.addAttribute("invalidDates", true);
			return "redirect:/tools-registry/user/reservation";
		}
		if( !reservationService.isRegistableGeoToolReservation(instrumentId, additionalId, startDay, endDay)) {
			redAttr.addAttribute("invalidReservation", true);
			return "redirect:/tools-registry/user/reservation";
		}
		
		reservationService.saveGeoToolReservation
		(reservationService.getAuthUser(), instrumentId, additionalId, startDay, endDay);
		reservationService.addReservationTextToGeoTool
		(reservationService.getAuthUser(), instrumentId, additionalId, startDay, endDay);
		
		return "redirect:/tools-registry/user/reservation";
	}
	
	
	
}
