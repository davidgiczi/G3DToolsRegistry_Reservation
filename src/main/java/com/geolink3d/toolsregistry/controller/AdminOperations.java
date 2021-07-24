package com.geolink3d.toolsregistry.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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
import com.geolink3d.toolsregistry.model.Role;
import com.geolink3d.toolsregistry.model.UsedGeoTool;
import com.geolink3d.toolsregistry.service.GeoAdditionalService;
import com.geolink3d.toolsregistry.service.GeoInstrumentService;
import com.geolink3d.toolsregistry.service.GeoWorkerService;
import com.geolink3d.toolsregistry.service.LocationService;
import com.geolink3d.toolsregistry.service.RoleService;
import com.geolink3d.toolsregistry.service.GeoToolInUseService;
import com.geolink3d.toolsregistry.service.UsedGeoToolService;

@Controller
@RequestMapping("/tools-registry/admin")
public class AdminOperations {

	
	private GeoWorkerService workerService;
	private RoleService roleService;
	private GeoInstrumentService instrumentService;
	private LocationService locationService;
	private UsedGeoToolService usedToolService;
	private GeoAdditionalService additionalService;
	private GeoToolInUseService toolInUseService;
 	
	@Autowired
	public void setWorkerService(GeoWorkerService workerService) {
		this.workerService = workerService;
	}
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@Autowired
	public void setInstrumentService(GeoInstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}
	
	@Autowired
	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}

	@Autowired
	public void setUsedToolService(UsedGeoToolService usedToolService) {
		this.usedToolService = usedToolService;
	}
	
	@Autowired
	public void setAdditionalService(GeoAdditionalService additionalService) {
		this.additionalService = additionalService;
	}

	@Autowired
	public void setToolInUseService(GeoToolInUseService toolInUseService) {
		this.toolInUseService = toolInUseService;
	}

	@RequestMapping("/account")
	public String goAdminAccount() {
		
		return "layouts/admin-account";
	}
	
	@RequestMapping("/workers")
	public String goWorkersPage(Model model) {
		
		List<GeoWorker> workers = workerService.findAll();
		model.addAttribute("workers",workers);
		
		return "admin/workers";
	}
	
	@RequestMapping("/instruments")
	public String goInstrumentsPage(Model model) {
		
		List<GeoWorker> workers = workerService.findAllIfEnabled();
		List<GeoTool> usableInstrumentTools = instrumentService.findUseableGeoTools();
		List<GeoTool> deletedInstrumentTools = instrumentService.findDeletedGeoTools();
		List<Location> locations = locationService.findAll();
		model.addAttribute("workers", workers);
		model.addAttribute("usable", usableInstrumentTools);
		model.addAttribute("deleted", deletedInstrumentTools);
		model.addAttribute("locations", locations);
		model.addAttribute("useableSize", usableInstrumentTools.size());
		
		return "admin/instruments";
	}
	
	@RequestMapping("/additionals")
	public String goAdditionalsPage(Model model) {
		
		List<GeoWorker> workers = workerService.findAllIfEnabled();
		List<GeoTool> usableAdditionalTools = additionalService.findUseableGeoTools();
		List<GeoTool> deletedAdditionalTools = additionalService.findDeletedGeoTools();
		List<GeoTool> usableInstrumentTools = instrumentService.findUseableGeoTools();
		List<Location> locations = locationService.findAll();
		model.addAttribute("workers", workers);
		model.addAttribute("usable", usableAdditionalTools);
		model.addAttribute("instruments", usableInstrumentTools);
		model.addAttribute("deleted", deletedAdditionalTools);
		model.addAttribute("locations", locations);
		model.addAttribute("useableSize", usableAdditionalTools.size());
		
		return "admin/additionals";
	}
	
	@RequestMapping("/tools-in-use")
	public String goToolsInUsePage(Model model) {
		
		List<GeoTool> toolsInUse = toolInUseService.findUsedGeoTools();
		List<Location> locations = locationService.findAll();
		model.addAttribute("locations", locations);
		model.addAttribute("toolsInUse", toolsInUse);
		
		return "admin/tools-in-use";
	}
	
	@RequestMapping("/tools-history")
	public String goToolsHistoryPage(Model model) {
		
		List<UsedGeoTool> used = usedToolService.findAll();
		model.addAttribute("tools", used);
		
		return "admin/tools-history";
	}
	
	@RequestMapping("/enter")
	public String enterUserAccount(@RequestParam("id") Long id, HttpServletRequest request, Model model) {
		
		
		Optional<GeoWorker> worker = workerService.findById(id); 
		
		UsernamePasswordAuthenticationToken token = 
	            new UsernamePasswordAuthenticationToken(worker.get().getUsername(), worker.get().getPassword());
	    token.setDetails(new WebAuthenticationDetails(request));
	    AuthenticationProvider authenticationProvider = new AuthenticationProvider() {
			
			@Override
			public boolean supports(Class<?> authentication) {
				
				return authentication.equals(UsernamePasswordAuthenticationToken.class);
			}
			
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				
				String username = authentication.getName();
		        String password = authentication.getCredentials()
		            .toString();
		        
		       Role guestRole = roleService.findByRole("ROLE_GUEST");
			    
		 	   if(guestRole != null) {
		 		   worker.get().getRoles().add(guestRole);
		 	   }
		 	   else {
		 		   worker.get().addRoles("ROLE_GUEST");
		 	   }
		            
				return new UsernamePasswordAuthenticationToken
			              (username, password, Collections.emptyList());
			}
		};
		
		Authentication auth = authenticationProvider.authenticate(token);
		
	    SecurityContextHolder.getContext().setAuthentication(auth);	
	
		workerService.save(worker.get());
				
		return "redirect:/tools-registry/user/instruments";
	}
	
	
	@RequestMapping("/enabled")
	public String enabledUserAccount(@RequestParam("id") Long id) {
		
		Optional<GeoWorker> worker = workerService.findById(id);
		
		if(worker.isPresent()) {
			if(worker.get().isEnabled()) {
				worker.get().setEnabled(false);
			}
			else {
				worker.get().setEnabled(true);
			}
			
			workerService.save(worker.get());
			
		}
		return "redirect:/tools-registry/admin/workers";
	}
	

	@RequestMapping("/change-role")
	public String changeRole(@RequestParam("id") Long id) {
		
		Optional<GeoWorker> worker = workerService.findById(id);
		
		if(worker.isPresent()) {
			
			Role adminRole = roleService.findByRole("ROLE_ADMIN");
			Role userRole = roleService.findByRole("ROLE_USER");
			
			if(worker.get().getRoles().contains(new Role("ROLE_USER"))) {
			
			worker.get().getRoles().clear();
			worker.get().getRoles().add(adminRole);
				
			}
			else if(worker.get().getRoles().contains(new Role("ROLE_ADMIN"))){
			
			worker.get().getRoles().clear();
			worker.get().getRoles().add(userRole);
				
			}		
			workerService.save(worker.get());
		}
		
		return "redirect:/tools-registry/admin/workers";
	}
	
	@RequestMapping("/search-worker")
	public String searchGeoWorker(@RequestParam(value = "text") String text, Model model) {
		
		
		if(text.isEmpty()) {
			return "redirect:/tools-registry/admin/workers";
		}else {
			List<GeoWorker> workers = workerService.findByText(text);
			model.addAttribute("txt", text);
			model.addAttribute("workers", workers);		
		}
		
		return "admin/workers";
	}
	
	@RequestMapping("/search-instrument")
	public String searchGeoInstrument(@RequestParam(value = "text") String text, Model model) {
		
		
		if(text.isEmpty()) {
			return "redirect:/tools-registry/admin/instruments";
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
		
		return "admin/instruments";
	}
	
	@RequestMapping("/search-additional")
	public String searchGeoAdditional(@RequestParam(value = "text") String text, Model model) {
		
		
		if(text.isEmpty()) {
			return "redirect:/tools-registry/admin/additionals";
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
		
		return "admin/additionals";
	}
	
	@RequestMapping("/add-instrument")
	public String addInstrument(@RequestParam(value="inst") String instrumentName, RedirectAttributes rdAttr) {
		
		if(instrumentService.saveNewGeoInstrument(instrumentName)) {
			rdAttr.addAttribute("instSaved","\"" + instrumentName + "\" műszer hozzáadva a nyilvántartáshoz.");	
		}
		else {
			rdAttr.addAttribute("instSaved", "\""+ instrumentName + "\" néven már szerepel műszer a nyilvántartásban.");
		}
		
		return "redirect:/tools-registry/admin/instruments";
	}
	
	@RequestMapping("/add-additional")
	public String addAdditional(@RequestParam(value="additional") String additionalName, RedirectAttributes rdAttr) {
		
		if(additionalService.saveNewGeoAdditional(additionalName)) {
			rdAttr.addAttribute("additionalSaved","\"" + additionalName + "\" kiegészítő hozzáadva a nyilvántartáshoz.");	
		}
		else {
			rdAttr.addAttribute("additionalSaved", "\""+ additionalName + "\" néven már szerepel kiegészítő a nyilvántartásban.");
		}
		
		return "redirect:/tools-registry/admin/additionals";
	}
	
	@RequestMapping("/add-location")
	public String addLocation(@RequestParam(value="location") String location, RedirectAttributes rdAttr) {
		
		if(locationService.saveNewGeoLocation(location)) {
			rdAttr.addAttribute("locSaved","\"" + location + "\"  telephely hozzáadva a nyilvántartáshoz.");	
		}
		else {
			rdAttr.addAttribute("locSaved", "\""+ location + "\" néven már szerepel telephely a nyilvántartásban.");
		}
		
		return "redirect:/tools-registry/admin/instruments";
	}
	
	@RequestMapping("/cancel-restore-instrument")
	public String cancelRestoreInstrument(@RequestParam("id") Long id) {
		
		Optional<GeoInstrument> instrument = instrumentService.findById(id);
		
		if(instrument.isPresent()) {
			if(instrument.get().isDeleted()) {
				instrument.get().setDeleted(false);
			}
			else {
				instrument.get().setDeleted(true);
			}
			
			instrumentService.save(instrument.get());	
		}
		return "redirect:/tools-registry/admin/instruments";
	}
	
	@RequestMapping("/cancel-restore-additional")
	public String cancelRestoreAdditional(@RequestParam("id") Long id) {
		
		Optional<GeoAdditional> additional = additionalService.findById(id);
		
		if(additional.isPresent()) {
			if(additional.get().isDeleted()) {
				additional.get().setDeleted(false);
			}
			else {
				additional.get().setDeleted(true);
			}
			
			additionalService.save(additional.get());	
		}
		return "redirect:/tools-registry/admin/additionals";
	}
	
	@PostMapping("/takeaway-instrument")
	public String takeawayInstrument(HttpServletRequest request, RedirectAttributes rdAttr) {
		
		Optional<GeoInstrument> instrument = instrumentService.findById(Long.valueOf(request.getParameter("instrument-id")));
		
		if(instrument.get().isUsed()) {
			rdAttr.addAttribute("alreadyUsed", "Az eszköz nem vehető fel mivel már használatban van. Lásd \"Felvett eszközök\" oldalon.");
			return "redirect:/tools-registry/admin/tools-in-use";
		}
		
		Long id = Long.valueOf(request.getParameter("worker-id"));
		Optional<GeoWorker> worker = workerService.findById(id);
		instrument.get().setUsed(true);
		instrument.get().setGeoworker(worker.get());
		instrument.get().setPickUpPlace(request.getParameter("from-location"));
		instrument.get().setPickUpDate(getCurrentDateTime());
		String comment = request.getParameter("comment");
		if(comment.length() > 999) {
		instrument.get().setComment(comment.substring(999));
		}
		else {
			instrument.get().setComment(comment);
		}
		instrumentService.save(instrument.get());
		
		return "redirect:/tools-registry/admin/tools-in-use";
	}
	
	@PostMapping("/restore-instrument")
	public String restoreInstrument(HttpServletRequest request) {
		
		Optional<GeoInstrument> instrument = instrumentService.findById(Long.valueOf(request.getParameter("instrument-id")));
		
		instrument.get().setUsed(false);
		instrument.get().setPutDownDate(getCurrentDateTime());
		instrument.get().setPutDownPlace(request.getParameter("location"));
			
		String comment = request.getParameter("comment");
		if(comment.length() > 999) {
		instrument.get().setComment(comment.substring(999));
			}
		else {
			instrument.get().setComment(comment);
			}

		createAndSaveUsedTool(instrument.get());
		instrument.get().setPickUpPlace(request.getParameter("location"));
		instrument.get().setGeoworker(null);
		instrumentService.save(instrument.get());
		
		return "redirect:/tools-registry/admin/instruments";
	}
	
	private void createAndSaveUsedTool(GeoInstrument usedInstrument) {
		
		UsedGeoTool used = new UsedGeoTool();
		used.setToolname(usedInstrument.getName());
		used.setWorkername(usedInstrument.getGeoworker().getLastname() + " " + usedInstrument.getGeoworker().getFirstname());
		used.setPickUpPlace(usedInstrument.getPickUpPlace());
		used.setPickUpDate(usedInstrument.getPickUpDate());
		used.setPutDownPlace(usedInstrument.getPutDownPlace());
		used.setPutDownDate(usedInstrument.getPutDownDate());
		used.setComment(usedInstrument.getComment());
		used.setInstrument(true);
		usedToolService.save(used);
	}
	
	@PostMapping("/validate-for-takeaway-additional")
	public String validateForTakeawayAdditional(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rdAttr) throws UnsupportedEncodingException {
		
		String additionalId = request.getParameter("additional-id");
		Optional<GeoAdditional> additional = additionalService.findById(Long.valueOf(additionalId));
		if(additional.get().isUsed()) {
			rdAttr.addAttribute("alreadyUsed", "A kiegészítő nem vehető fel mivel már használatban van. Lásd \"Felvett eszközök\" oldalon.");
			return "redirect:/tools-registry/admin/tools-in-use";
		}
		String instrumentId = request.getParameter("instrument-id");
		String workerId = request.getParameter("worker-id");
		String pickUpPlace = request.getParameter("from-location");
		String comment = request.getParameter("comment");
		
		if("-".equals(instrumentId)) {
			
		takeawaySingleAdditionalProcess(Long.valueOf(additionalId), Long.valueOf(workerId), pickUpPlace, comment);
		
		}
		else {
		
		Optional<GeoInstrument> instrument = instrumentService.findById(Long.valueOf(instrumentId));
		Optional<GeoWorker> worker = workerService.findById(Long.valueOf(workerId));
		
		
		if(instrument.get().getGeoworker() != null && !instrument.get().getGeoworker().getUsername().equals(worker.get().getUsername())) {
			
			Cookie c1 = new Cookie("additionalId",  additionalId);
			Cookie c2 = new Cookie("instrumentId", instrumentId);
			Cookie c3 = new Cookie("workerId", workerId);
			Cookie c4 = new Cookie("pickUpPlace", URLEncoder.encode(pickUpPlace, "UTF-8"));
			Cookie c5 = new Cookie("comment", URLEncoder.encode(comment, "UTF-8"));
			
			response.addCookie(c1);
			response.addCookie(c2);
			response.addCookie(c3);
			response.addCookie(c4);
			response.addCookie(c5);
			
			rdAttr.addAttribute("otherUser", "A kiegészítőhöz választott műszert már más használja. Biztos, hogy ehhez a műszerhez veszed fel a kiegészítőt?");
			return "redirect:/tools-registry/admin/additionals";
		}
		
		takeawayAdditionalProcess(Long.valueOf(additionalId), Long.valueOf(instrumentId), Long.valueOf(workerId), pickUpPlace, comment);
		
	}
		
		return "redirect:/tools-registry/admin/tools-in-use";
	}
	
	@RequestMapping("/takeaway-additional")
	public String takeawayAdditional(@CookieValue(value="additionalId") Long additionalId,
									 @CookieValue(value="instrumentId")Long instrumentId,
									 @CookieValue(value="workerId")Long workerId,
									 @CookieValue(value="pickUpPlace")String pickUpPlace,
									 @CookieValue(value="comment")String comment, HttpServletResponse response) throws UnsupportedEncodingException{
		
		
		takeawayAdditionalProcess(additionalId, instrumentId, workerId,
				URLDecoder.decode(pickUpPlace, "UTF-8").toString(),
				URLDecoder.decode(comment, "UTF-8").toString());
		
		Cookie c1 = new Cookie("additionalId",  null);
		Cookie c2 = new Cookie("instrumentId", null);
		Cookie c3 = new Cookie("workerId", null);
		Cookie c4 = new Cookie("pickUpPlace", null);
		Cookie c5 = new Cookie("comment", null);
		c1.setMaxAge(0);
		c2.setMaxAge(0);
		c3.setMaxAge(0);
		c4.setMaxAge(0);
		c5.setMaxAge(0);
		response.addCookie(c1);
		response.addCookie(c2);
		response.addCookie(c3);
		response.addCookie(c4);
		response.addCookie(c5);
		
		return "redirect:/tools-registry/admin/tools-in-use";
	}
	
	
	private void takeawaySingleAdditionalProcess(Long additionalId, Long workerId, String pickUpPlace, String comment) {
		
		Optional<GeoAdditional> additional = additionalService.findById(additionalId);
		Optional<GeoWorker> worker = workerService.findById(workerId);
		
		additional.get().setUsed(true);
		additional.get().setPickUpDate(getCurrentDateTime());
		additional.get().setPickUpPlace(pickUpPlace);
		additional.get().setComment(comment);
		additional.get().setGeoworker(worker.get());
		additionalService.save(additional.get());
		
	}
	
	private void takeawayAdditionalProcess(Long additionalId, Long instrumentId, Long workerId, String pickUpPlace, String comment) {
		
		Optional<GeoAdditional> additional = additionalService.findById(additionalId);
		Optional<GeoInstrument> instrument = instrumentService.findById(instrumentId);
		Optional<GeoWorker> worker = workerService.findById(workerId);
		
		additional.get().setUsed(true);
		additional.get().setPickUpDate(getCurrentDateTime());
		additional.get().setPickUpPlace(pickUpPlace);
		additional.get().setComment(comment);
		additional.get().setGeoworker(worker.get());
		if(!instrument.get().isUsed()) {
			instrument.get().setUsed(true);
			instrument.get().setPickUpDate(getCurrentDateTime());
			instrument.get().setPickUpPlace(pickUpPlace);
			instrument.get().setGeoworker(worker.get());
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
			instrument.get().setUsed(false);
			instrument.get().setPutDownDate(getCurrentDateTime());
			instrument.get().setPutDownPlace(place);
			instrument.get().setComment(comment);
			
			UsedGeoTool usedInstrument = new UsedGeoTool();
			usedInstrument.setToolname(instrument.get().getName());
			usedInstrument.setWorkername(instrument.get().getGeoworker().getLastname() + " " + instrument.get().getGeoworker().getFirstname());
			usedInstrument.setPickUpDate(instrument.get().getPickUpDate());
			usedInstrument.setPickUpPlace(instrument.get().getPickUpPlace());
			usedInstrument.setPutDownDate(instrument.get().getPutDownDate());
			usedInstrument.setPutDownPlace(instrument.get().getPutDownPlace());
			usedInstrument.setComment(instrument.get().getComment());
			usedInstrument.setInstrument(true);
			usedToolService.save(usedInstrument);
			
			instrument.get().setGeoworker(null);
			instrument.get().setPickUpPlace(instrument.get().getPutDownPlace());
			instrumentService.save(instrument.get());
			
			for (GeoAdditional additional : instrument.get().getAdditionals()) {
				
				additional.setUsed(false);
				additional.setPutDownDate(getCurrentDateTime());
				additional.setPutDownPlace(place);
				additional.setComment(additional.getComment());
				
				UsedGeoTool usedAdditional = new UsedGeoTool();
				usedAdditional.setToolname(additional.getName());
				usedAdditional.setWorkername(additional.getGeoworker().getLastname() + " " + additional.getGeoworker().getFirstname());
				usedAdditional.setPickUpDate(additional.getPickUpDate());
				usedAdditional.setPickUpPlace(additional.getPickUpPlace());
				usedAdditional.setPutDownDate(additional.getPutDownDate());
				usedAdditional.setPutDownPlace(additional.getPutDownPlace());
				usedAdditional.setComment(additional.getComment());
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
				
				additional.get().setUsed(false);
				additional.get().setPutDownDate(getCurrentDateTime());
				additional.get().setPutDownPlace(place);
				additional.get().setComment(comment);
				
				UsedGeoTool usedAdditional = new UsedGeoTool();
				usedAdditional.setToolname(additional.get().getName());
				usedAdditional.setWorkername(additional.get().getGeoworker().getLastname() + " " + additional.get().getGeoworker().getFirstname());
				usedAdditional.setPickUpDate(additional.get().getPickUpDate());
				usedAdditional.setPickUpPlace(additional.get().getPickUpPlace());
				usedAdditional.setPutDownDate(additional.get().getPutDownDate());
				usedAdditional.setPutDownPlace(additional.get().getPutDownPlace());
				usedAdditional.setComment(additional.get().getComment());
				usedAdditional.setInstrument(false);
				usedToolService.save(usedAdditional);
				
				additional.get().setGeoworker(null);
				additional.get().setInstrument(null);
				additional.get().setPickUpPlace(additional.get().getPutDownPlace());
				additionalService.save(additional.get());
				
			}
			else {
				
				additional.get().setUsed(false);
				additional.get().setPutDownDate(getCurrentDateTime());
				additional.get().setPutDownPlace(place);
				additional.get().setComment(comment);
				
				UsedGeoTool usedAdditional = new UsedGeoTool();
				usedAdditional.setToolname(additional.get().getName());
				usedAdditional.setWorkername(additional.get().getGeoworker().getLastname() + " " + additional.get().getGeoworker().getFirstname());
				usedAdditional.setPickUpDate(additional.get().getPickUpDate());
				usedAdditional.setPickUpPlace(additional.get().getPickUpPlace());
				usedAdditional.setPutDownDate(additional.get().getPutDownDate());
				usedAdditional.setPutDownPlace(additional.get().getPutDownPlace());
				usedAdditional.setComment(additional.get().getComment());
				usedAdditional.setInstrument(false);
				usedToolService.save(usedAdditional);
				
				additional.get().setGeoworker(null);
				additional.get().setPickUpPlace(additional.get().getPutDownPlace());
				additionalService.save(additional.get());

			}
			
		}
		return "redirect:/tools-registry/admin/tools-in-use";
	}
	
	@RequestMapping("/search-in-tools-history")
	public String searchInToolsHistory(@RequestParam(value = "text") String text,@RequestParam(value = "option") String option, Model model) {
		
		List<UsedGeoTool> used;
		
		if(text.isEmpty()) {
			return "redirect:/tools-registry/admin/tools-history";
		}
		else {
			
			switch (option) {
		
			case "instruments":
				used = usedToolService.findUsedGeoToolIntrumentsByText(text);
				break;
			case "additionals":
				used = usedToolService.findUsedGeoToolAdditionalsByText(text);
				break;
			default:
				used = usedToolService.findUsedGeoToolsByText(text);
			}
			
			model.addAttribute("option", option);
			model.addAttribute("tools", used);
			model.addAttribute("txt", text);
		}
		
		return "admin/tools-history";
	}
	
	@RequestMapping("/search-in-tools-in-use")
	public String searchInToolsInUse(@RequestParam(value = "text") String text, Model model) {
		
		if(text.isEmpty()) {
			return "redirect:/tools-registry/admin/tools-in-use";
		}
		else {
			
			List<GeoTool> toolsInUse = toolInUseService.findGeoToolsInUseByText(text);
			List<Location> locations = locationService.findAll();
			model.addAttribute("toolsInUse", toolsInUse);
			model.addAttribute("locations", locations);
			model.addAttribute("txt", text);
		}
		
		return "admin/tools-in-use";
	}
	
	@RequestMapping("/search-by-pick-up-date-in-tools-in-use")
	public String searchByPickUpDateInToolsInUse(@RequestParam(value = "date") String date, Model model) {
	
		try {
			List<GeoTool> usedToolStore = toolInUseService.findByPickUpDate(date);
			model.addAttribute("toolsInUse", usedToolStore);
			List<Location> locations = locationService.findAll();
			model.addAttribute("locations", locations);
			model.addAttribute("date", date);
		} catch (ParseException e) {
			System.out.println("BAD DATE FORMAT: " + date);
		}
		
		return "admin/tools-in-use";
	}
	
	@RequestMapping("/search-by-pick-up-date-in-tools-history")
	public String searchByPickUpDateInToolHistory(@RequestParam(value = "date") String date, @RequestParam(value = "option") String option, Model model) {
	
			List<UsedGeoTool> used;
			
			try {
				used = usedToolService.findUsedGeoToolByPickUpDate(date, option);
				model.addAttribute("date", date);
				model.addAttribute("option", option);
				model.addAttribute("tools", used);
			} catch (ParseException e) {
				System.out.println("BAD DATE FORMAT: " + date);
			}
			
		return "admin/tools-history";
	}
	
	@RequestMapping("/search-by-put-down-date-in-tools-history")
	public String searchByPutDownDateInToolHistory(@RequestParam(value = "date") String date, @RequestParam(value = "option") String option, Model model) {
		
		List<UsedGeoTool> used;
		
		try {
			used = usedToolService.findUsedGeoToolByPutDownDate(date, option);
			model.addAttribute("date", date);
			model.addAttribute("option", option);
			model.addAttribute("tools", used);
		} catch (ParseException e) {
			System.out.println("BAD DATE FORMAT: " + date);
		}
		
		
		return "admin/tools-history";
	}
	
	
	private ZonedDateTime getCurrentDateTime() {
		Instant now = Instant.now();
		ZoneId hun = ZoneId.of("Europe/Budapest");
		ZonedDateTime nowHun = ZonedDateTime.ofInstant(now, hun);
		return nowHun;
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
		
		return "admin/tools-in-use";
	}
	
	
	@RequestMapping("/search-by-dates-in-tools-history")
	public String searchByDatesInToolHistory(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to, Model model) {
	
		try {
			List<UsedGeoTool> usedToolStore = usedToolService.findBetweenDates(from, to);
			model.addAttribute("tools", usedToolStore);
		} catch (ParseException e) {
			System.out.println("BAD DATE FORMAT: " + from +", " + to);
		}
		
		return "admin/tools-history";
	}
	
}
