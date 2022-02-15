package com.geolink3d.toolsregistry.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.geolink3d.toolsregistry.model.GeoAdditional;
import com.geolink3d.toolsregistry.model.GeoInstrument;
import com.geolink3d.toolsregistry.model.GeoTool;
import com.geolink3d.toolsregistry.model.GeoWorker;
import com.geolink3d.toolsregistry.model.Location;
import com.geolink3d.toolsregistry.model.UsedGeoTool;
import com.geolink3d.toolsregistry.service.EncoderService;
import com.geolink3d.toolsregistry.service.GeoAdditionalService;
import com.geolink3d.toolsregistry.service.GeoInstrumentService;
import com.geolink3d.toolsregistry.service.GeoToolInUseService;
import com.geolink3d.toolsregistry.service.GeoToolReservationService;
import com.geolink3d.toolsregistry.service.GeoWorkerService;
import com.geolink3d.toolsregistry.service.LocationService;
import com.geolink3d.toolsregistry.service.UsedGeoToolService;

@Controller
@RequestMapping("/tools-registry/user")
public class UserOperations {

	
	private GeoWorkerService workerService;
	private GeoInstrumentService instrumentService;
	private GeoAdditionalService additionalService;
	private GeoToolInUseService toolInUseService;
	private UsedGeoToolService usedToolService;
	private LocationService locationService; 
	private GeoToolReservationService reservationService;
	
	@Autowired
	public void setWorkerService(GeoWorkerService workerService) {
		this.workerService = workerService;
	}
	
	
	@Autowired
	public void setInstrumentService(GeoInstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}
	
	
	@Autowired
	public void setAdditionalService(GeoAdditionalService additionalService) {
		this.additionalService = additionalService;
	}
	
	
	@Autowired
	public void setToolInUseService(GeoToolInUseService toolInUseService) {
		this.toolInUseService = toolInUseService;
	}
	
	
	@Autowired
	public void setUsedToolService(UsedGeoToolService usedToolService) {
		this.usedToolService = usedToolService;
	}


	@Autowired
	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}
	
	@Autowired
	public void setReservationService(GeoToolReservationService reservationService) {
		this.reservationService = reservationService;
	}


	@RequestMapping("/account")
	public String goUserAccount() {
		
		
		return "layouts/user-account";
	}
	
	@RequestMapping("/instruments")
	public String goInstrumentsPage(Model model) {
		
		List<GeoTool> usableInstrumentTools = instrumentService.findUseableGeoTools();
		List<Location> locations = locationService.findAll();
		model.addAttribute("usable", usableInstrumentTools);
		model.addAttribute("locations", locations);
		
		return "user/instruments";
	}
	
	@RequestMapping("/additionals")
	public String goAdditionalsPage(Model model) {
		
		List<GeoTool> usableAdditionalTools = additionalService.findUseableGeoTools();
		List<GeoTool> usableInstrumentTools = instrumentService.findUseableGeoTools();
		List<Location> locations = locationService.findAll();
		model.addAttribute("usable", usableAdditionalTools);
		model.addAttribute("instruments", usableInstrumentTools);
		model.addAttribute("locations", locations);
		
		return "user/additionals";
	}
	
	@RequestMapping("/tools-in-use")
	public String goToolsInUsePage(Model model) {
		
		List<GeoTool> toolsInUse = toolInUseService.findUsedGeoToolsByUser(getAuthUser());
		List<Location> locations = locationService.findAll();
		model.addAttribute("locations", locations);
		model.addAttribute("toolsInUse", toolsInUse);
		
		return "user/tools-in-use";
	}
	
	@RequestMapping("/tools-history")
	public String goToolsHistoryPage(Model model) {
		
		List<UsedGeoTool> used = usedToolService.findAll();
		model.addAttribute("tools", used);
		
		return "user/tools-history";
	}
	
	
	@PostMapping("/change-password")
	public String changePassword(HttpServletRequest request, RedirectAttributes rdAttr) {
		
		GeoWorker worker = workerService.findGeoWorkerByUserName(getAuthUser());
		worker.setPassword(EncoderService.encodeByBase64(request.getParameter("pass")));
		workerService.save(worker);
		rdAttr.addAttribute("changedPass", true);
		
		return "redirect:/tools-registry/login";
	}
	
	@PostMapping("/takeaway-instrument")
	public String takeawayInstrument(HttpServletRequest request, RedirectAttributes rdAttr) {
		
		Optional<GeoInstrument> instrument = instrumentService.findById(Long.valueOf(request.getParameter("instrument-id")));
		
		if(instrument.get().isUsed()) {
			rdAttr.addAttribute("alreadyUsed", "Az eszköz nem vehető fel mivel már használatban van. Lásd \"Felvett eszközök\" oldalon.");
			return "redirect:/tools-registry/user/tools-in-use";
		}
		
		instrument.get().setUsed(true);
		instrument.get().setGeoworker(workerService.findGeoWorkerByUserName(getAuthUser()));
		instrument.get().setPickUpPlace(request.getParameter("from-location"));
		instrument.get().setPickUpDate(getCurrentDateTime());
		String comment = cutLengthOfComment(request.getParameter("comment"));
		instrument.get().setComment(comment);
		
		instrumentService.save(instrument.get());
		
		return "redirect:/tools-registry/user/tools-in-use";
	}
	
	@PostMapping("/validate-for-takeaway-additional")
	public String validateForTakeawayAdditional(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rdAttr) throws UnsupportedEncodingException {
		
		String additionalId = request.getParameter("additional-id");
		Optional<GeoAdditional> additional = additionalService.findById(Long.valueOf(additionalId));
		if(additional.get().isUsed()) {
			rdAttr.addAttribute("alreadyUsed", "A kiegészítő nem vehető fel mivel már használatban van. Lásd \"Felvett eszközök\" oldalon.");
			return "redirect:/tools-registry/user/tools-in-use";
		}
		String instrumentId = request.getParameter("instrument-id");
		String pickUpPlace = request.getParameter("from-location");
		String comment = cutLengthOfComment(request.getParameter("comment"));
		
		if("-".equals(instrumentId)) {
			
		takeawaySingleAdditionalProcess(Long.valueOf(additionalId), getAuthUser(), pickUpPlace, comment);
		
		}
		else {
		
		Optional<GeoInstrument> instrument = instrumentService.findById(Long.valueOf(instrumentId));
		
		if(instrument.get().getGeoworker() != null && !instrument.get().getGeoworker().getUsername().equals(getAuthUser())) {
			
			Cookie c1 = new Cookie("additionalId",  additionalId);
			Cookie c2 = new Cookie("instrumentId", instrumentId);
			Cookie c3 = new Cookie("pickUpPlace", URLEncoder.encode(pickUpPlace, "UTF-8"));
			Cookie c4 = new Cookie("comment", URLEncoder.encode(comment, "UTF-8"));
			
			response.addCookie(c1);
			response.addCookie(c2);
			response.addCookie(c3);
			response.addCookie(c4);
			
			rdAttr.addAttribute("otherUser", "A kiegészítőhöz választott műszert már más használja. Biztos, hogy ehhez a műszerhez veszed fel a kiegészítőt?");
			return "redirect:/tools-registry/user/additionals";
		}
		
		takeawayAdditionalProcess(Long.valueOf(additionalId), Long.valueOf(instrumentId), getAuthUser(), pickUpPlace, comment);
		
	}
		
		return "redirect:/tools-registry/user/tools-in-use";
	}
	
	@RequestMapping("/takeaway-additional")
	public String takeawayAdditional(@CookieValue(value="additionalId") Long additionalId,
									 @CookieValue(value="instrumentId")Long instrumentId,
									 @CookieValue(value="pickUpPlace")String pickUpPlace,
									 @CookieValue(value="comment")String comment, HttpServletResponse response) throws UnsupportedEncodingException{
		
		
		takeawayAdditionalProcess(additionalId, instrumentId, getAuthUser(),
				URLDecoder.decode(pickUpPlace, "UTF-8").toString(),
				URLDecoder.decode(comment, "UTF-8").toString());
		
		Cookie c1 = new Cookie("additionalId",  null);
		Cookie c2 = new Cookie("instrumentId", null);
		Cookie c3 = new Cookie("pickUpPlace", null);
		Cookie c4 = new Cookie("comment", null);
		c1.setMaxAge(0);
		c2.setMaxAge(0);
		c3.setMaxAge(0);
		c4.setMaxAge(0);
		response.addCookie(c1);
		response.addCookie(c2);
		response.addCookie(c3);
		response.addCookie(c4);
		
		return "redirect:/tools-registry/user/tools-in-use";
	}
	
	
	private void takeawaySingleAdditionalProcess(Long additionalId, String username, String pickUpPlace, String comment) {
		
		Optional<GeoAdditional> additional = additionalService.findById(additionalId);
		GeoWorker worker = workerService.findGeoWorkerByUserName(username);
		
		additional.get().setUsed(true);
		additional.get().setPickUpDate(getCurrentDateTime());
		additional.get().setPickUpPlace(pickUpPlace);
		additional.get().setComment(comment);
		additional.get().setGeoworker(worker);
		additionalService.save(additional.get());
		
	}
	
	private void takeawayAdditionalProcess(Long additionalId, Long instrumentId, String username, String pickUpPlace, String comment) {
		
		Optional<GeoAdditional> additional = additionalService.findById(additionalId);
		Optional<GeoInstrument> instrument = instrumentService.findById(instrumentId);
		GeoWorker worker = workerService.findGeoWorkerByUserName(username);
		
		additional.get().setUsed(true);
		additional.get().setPickUpDate(getCurrentDateTime());
		additional.get().setPickUpPlace(pickUpPlace);
		additional.get().setComment(comment);
		additional.get().setGeoworker(worker);
		if(!instrument.get().isUsed()) {
			instrument.get().setUsed(true);
			instrument.get().setPickUpDate(getCurrentDateTime());
			instrument.get().setPickUpPlace(pickUpPlace);
			instrument.get().setComment(comment);
			instrument.get().setGeoworker(worker);
		}
		additional.get().setInstrument(instrument.get());
		additionalService.save(additional.get());
		instrumentService.save(instrument.get());
	}
	
	@PostMapping("/restore-tool")
	public String restoreGeoTool(HttpServletRequest request) {
		
		Long toolId = Long.parseLong(request.getParameter("tool-id"));
		String place = request.getParameter("location");
		boolean isInstrument = Boolean.parseBoolean(request.getParameter("isInstrument"));
		String comment = request.getParameter("comment");
		
		if(isInstrument) {
			
			Optional<GeoInstrument> instrument = instrumentService.findById(toolId);
			UsedGeoTool usedInstrument = new UsedGeoTool();
			usedInstrument.setComment(instrument.get().getComment());
			instrument.get().setUsed(false);
			instrument.get().setPutDownDate(getCurrentDateTime());
			instrument.get().setPutDownPlace(place);
			instrument.get().setComment(modifyInputComment(comment, instrument.get().getComment()));
			instrument.get().setFrequency(instrument.get().getFrequency() + 1);
			
			usedInstrument.setToolname(instrument.get().getName());
			usedInstrument.setWorkername(instrument.get().getGeoworker().getLastname() + " " + instrument.get().getGeoworker().getFirstname());
			usedInstrument.setPickUpDate(instrument.get().getPickUpDate());
			usedInstrument.setPickUpPlace(instrument.get().getPickUpPlace());
			usedInstrument.setPutDownDate(instrument.get().getPutDownDate());
			usedInstrument.setPutDownPlace(instrument.get().getPutDownPlace());
			usedInstrument.setInstrument(true);
			usedToolService.save(usedInstrument);
			
			instrument.get().setGeoworker(null);
			instrument.get().setPickUpPlace(instrument.get().getPutDownPlace());
			instrumentService.save(instrument.get());
			
			for (GeoAdditional additional : instrument.get().getAdditionals()) {
				
				UsedGeoTool usedAdditional = new UsedGeoTool();
				usedAdditional.setComment(additional.getComment());
				additional.setUsed(false);
				additional.setPutDownDate(getCurrentDateTime());
				additional.setPutDownPlace(place);
				additional.setComment(null);
				additional.setFrequency(additional.getFrequency() + 1);
					
				usedAdditional.setToolname(additional.getName());
				usedAdditional.setWorkername(additional.getGeoworker().getLastname() + " " + additional.getGeoworker().getFirstname());
				usedAdditional.setPickUpDate(additional.getPickUpDate());
				usedAdditional.setPickUpPlace(additional.getPickUpPlace());
				usedAdditional.setPutDownDate(additional.getPutDownDate());
				usedAdditional.setPutDownPlace(additional.getPutDownPlace());
				usedAdditional.setInstrument(false);
				usedToolService.save(usedAdditional);
				
				additional.setGeoworker(null);
				additional.setInstrument(null);
				additional.setPickUpPlace(additional.getPutDownPlace());
				additionalService.save(additional);
			}
			
		}
		else {
			
			Optional<GeoAdditional> additional = additionalService.findById(toolId); 
			GeoInstrument instrument = additional.get().getInstrument();
			
			if(instrument != null) {
				
				UsedGeoTool usedAdditional = new UsedGeoTool();
				usedAdditional.setComment(additional.get().getComment());
				additional.get().setUsed(false);
				additional.get().setPutDownDate(getCurrentDateTime());
				additional.get().setPutDownPlace(place);
				additional.get().setComment(modifyInputComment(comment, additional.get().getComment()));
				additional.get().setFrequency(additional.get().getFrequency() + 1);
				
				usedAdditional.setToolname(additional.get().getName());
				usedAdditional.setWorkername(additional.get().getGeoworker().getLastname() + " " + additional.get().getGeoworker().getFirstname());
				usedAdditional.setPickUpDate(additional.get().getPickUpDate());
				usedAdditional.setPickUpPlace(additional.get().getPickUpPlace());
				usedAdditional.setPutDownDate(additional.get().getPutDownDate());
				usedAdditional.setPutDownPlace(additional.get().getPutDownPlace());
				usedAdditional.setInstrument(false);
				usedToolService.save(usedAdditional);
				
				additional.get().setGeoworker(null);
				additional.get().setInstrument(null);
				additional.get().setPickUpPlace(additional.get().getPutDownPlace());
				additionalService.save(additional.get());
				
			}
			else {
				
				UsedGeoTool usedAdditional = new UsedGeoTool();
				usedAdditional.setComment(additional.get().getComment());
				additional.get().setUsed(false);
				additional.get().setPutDownDate(getCurrentDateTime());
				additional.get().setPutDownPlace(place);
				additional.get().setComment(modifyInputComment(comment, additional.get().getComment()));
				additional.get().setFrequency(additional.get().getFrequency() + 1);
				
				usedAdditional.setToolname(additional.get().getName());
				usedAdditional.setWorkername(additional.get().getGeoworker().getLastname() + " " + additional.get().getGeoworker().getFirstname());
				usedAdditional.setPickUpDate(additional.get().getPickUpDate());
				usedAdditional.setPickUpPlace(additional.get().getPickUpPlace());
				usedAdditional.setPutDownDate(additional.get().getPutDownDate());
				usedAdditional.setPutDownPlace(additional.get().getPutDownPlace());
				usedAdditional.setInstrument(false);
				usedToolService.save(usedAdditional);
				
				additional.get().setGeoworker(null);
				additional.get().setPickUpPlace(additional.get().getPutDownPlace());
				additionalService.save(additional.get());

			}
		}
		
		reservationService.reservationProcess(toolId, isInstrument);
		
		return "redirect:/tools-registry/user/tools-in-use";
	}
	
	private String modifyInputComment(String inputComment, String savedComment) {
		
		inputComment = cutLengthOfComment(inputComment);
		
		if(inputComment.trim().equals(savedComment.trim()) || inputComment.replace("\r", "").equals(savedComment)){
			return null;
		}
		
		return inputComment;
	}
	
	private String cutLengthOfComment(String comment) {
		
		if(comment.length() > 999) {
			return comment.substring(0, 999);
		}
		
		return comment;
	}
	
	@RequestMapping("/search-instrument")
	public String searchGeoInstrument(@RequestParam(value = "text") String text, Model model) {
		
		
		if(text.isEmpty()) {
			return "redirect:/tools-registry/user/instruments";
		}else {
			List<GeoTool> usable = instrumentService.findNotDeletedGeoToolsByText(text);
			List<GeoTool> deleted= instrumentService.findDeletedGeoToolsByText(text);
			model.addAttribute("usable", usable);
			model.addAttribute("deleted", deleted);	
			List<GeoWorker> workers = workerService.findAllIfEnabled();
			List<Location> locations = locationService.findAll();
			model.addAttribute("workers", workers);
			model.addAttribute("locations", locations);
			model.addAttribute("txt", text);
			model.addAttribute("useableSize", usable.size());
		}
		
		return "user/instruments";
	}
	
	@RequestMapping("/search-additional")
	public String searchGeoAdditional(@RequestParam(value = "text") String text, Model model) {
		
		
		if(text.isEmpty()) {
			return "redirect:/tools-registry/user/additionals";
		}else {
			List<GeoTool> usable = additionalService.findNotDeletedGeoToolsByText(text);
			List<GeoTool> deleted= additionalService.findDeletedGeoToolsByText(text);
			List<GeoTool> usableInstrumentTools = instrumentService.findUseableGeoTools();
			model.addAttribute("usable", usable);
			model.addAttribute("deleted", deleted);	
			List<GeoWorker> workers = workerService.findAllIfEnabled();
			List<Location> locations = locationService.findAll();
			model.addAttribute("instruments", usableInstrumentTools);
			model.addAttribute("workers", workers);
			model.addAttribute("locations", locations);
			model.addAttribute("txt", text);
			model.addAttribute("useableSize", usable.size());
		}
		
		return "user/additionals";
	}
	
	@RequestMapping("/search-in-tools-in-use")
	public String searchInToolsInUse(@RequestParam(value = "text") String text, Model model) {
		
		if(text.isEmpty()) {
			return "redirect:/tools-registry/user/tools-in-use";
		}
		else {
			
			List<GeoTool> toolsInUse = toolInUseService.findGeoToolsInUseByTextAndUserId(text, getAuthUser());
			List<Location> locations = locationService.findAll();
			model.addAttribute("toolsInUse", toolsInUse);
			model.addAttribute("locations", locations);
			model.addAttribute("txt", text);
		}
		
		return "user/tools-in-use";
	}
	
	@RequestMapping("/search-by-pick-up-date-in-tools-in-use")
	public String searchByPickUpDateInToolsInUse(@RequestParam(value = "date") String date, Model model) {
	
		try {
			List<GeoTool> usedToolStore = toolInUseService.findByPickUpDateAndUserId(date, getAuthUser());
			model.addAttribute("toolsInUse", usedToolStore);
			List<Location> locations = locationService.findAll();
			model.addAttribute("locations", locations);
			model.addAttribute("date", date);
		} catch (ParseException e) {
			System.out.println("BAD DATE FORMAT: " + date);
		}
		
		return "user/tools-in-use";
	}
	
	private String getAuthUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	} 
	
	private ZonedDateTime getCurrentDateTime() {
		Instant now = Instant.now();
		ZoneId hun = ZoneId.of("Europe/Budapest");
		ZonedDateTime nowHun = ZonedDateTime.ofInstant(now, hun);
		return nowHun;
	}

	@RequestMapping("/search-by-dates-in-tools-history")
	public String searchByDatesInToolHistory(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to, Model model) {
	
		try {
			List<UsedGeoTool> usedToolStore = usedToolService.findBetweenDates(from, to);
			model.addAttribute("tools", usedToolStore);
		} catch (ParseException e) {
			System.out.println("BAD DATE FORMAT: " + from +", " + to);
		}
		
		return "user/tools-history";
	}
	
	@RequestMapping("/search-by-dates-in-tools-in-use")
	public String searchByDatesInToolsInUse(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to, Model model) {
	
		try {
			List<GeoTool> usedToolStore = toolInUseService.findBetweenDates(from, to);
			model.addAttribute("toolsInUse", usedToolStore);
			List<Location> locations = locationService.findAll();
			model.addAttribute("locations", locations);
		} catch (ParseException e) {
			System.out.println("BAD DATE FORMAT: " + from +", " + to);
		}
		
		return "user/tools-in-use";
	}
}
