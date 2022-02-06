package com.geolink3d.toolsregistry.controller;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.geolink3d.toolsregistry.dao.GeoToolReservationDAO;
import com.geolink3d.toolsregistry.model.GeoTool;
import com.geolink3d.toolsregistry.model.GeoWorker;
import com.geolink3d.toolsregistry.repository.GeoWorkerRepository;
import com.geolink3d.toolsregistry.service.GeoAdditionalService;
import com.geolink3d.toolsregistry.service.GeoInstrumentService;
import com.geolink3d.toolsregistry.service.GeoToolReservationService;

@Controller
@RequestMapping("/tools-registry/admin")
public class AdminReservationOperations {

	
	@Autowired
	private GeoToolReservationService reservationService;
	@Autowired
	private GeoInstrumentService instrumentService;
	@Autowired
	private GeoAdditionalService additionalService;
	@Autowired
	private GeoWorkerRepository workerRepo;
	
	@RequestMapping("/reservation")
	public String goReservationPage(Model model) {
		
		List<GeoTool> instrumentStore = instrumentService.findUseableGeoTools();
		List<GeoTool> additionalStore = additionalService.findUseableGeoTools();
		List<GeoToolReservationDAO> reservationStore = reservationService.findAllGeoReservationAsDAO();
		List<GeoWorker> workers = workerRepo.findAllIfEnabled();
		model.addAttribute("instruments", instrumentStore);
		model.addAttribute("additionals", additionalStore);
		model.addAttribute("reservations", reservationStore);
		model.addAttribute("actualDate", reservationService
				.getCurrentDateTime().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		model.addAttribute("workers", workers);
		
		return "admin/reservations";
	}
	
	@PostMapping("/adminReservation")
	public String getUserData(HttpServletRequest request, RedirectAttributes redAttr) throws ParseException {
		
		String workerId = request.getParameter("worker");
		String instrumentId = request.getParameter("instrument");
		String additionalId = request.getParameter("additional");
		String startDay = request.getParameter("start-day");
		String endDay = request.getParameter("end-day");
 		
		if( !reservationService.isChosenWorker(workerId) ) {
			redAttr.addAttribute("noWorker", true);
			return "redirect:/tools-registry/admin/reservation";
		}
		if( !reservationService.isChosenGeoToolReservation(instrumentId, additionalId) ) {
			redAttr.addAttribute("noTool", true);
			return "redirect:/tools-registry/admin/reservation";
		}
		if( !reservationService.isValidGeoToolReservationDates(startDay, endDay)) {
			redAttr.addAttribute("invalidDates", true);
			return "redirect:/tools-registry/admin/reservation";
		}
		if( !reservationService.isRegistableGeoToolReservation(instrumentId, additionalId, startDay, endDay)) {
			redAttr.addAttribute("invalidReservation", true);
			return "redirect:/tools-registry/admin/reservation";
		}
		
		GeoWorker worker = workerRepo.findById(Long.parseLong(workerId)).get();
		reservationService.saveGeoToolReservation
		(worker.getUsername(), instrumentId, additionalId, startDay, endDay);
		reservationService.addReservationTextToGeoTool
		(worker.getUsername(), instrumentId, additionalId, startDay, endDay);
		
		return "redirect:/tools-registry/admin/reservation";
	}
	
	@RequestMapping("/reservation/delete")
	public String deleteReservation(@RequestParam(value = "id") Long id) {
		
		reservationService.deleteReservation(id);
		
		return "redirect:/tools-registry/admin/reservation";
	}
	
}
