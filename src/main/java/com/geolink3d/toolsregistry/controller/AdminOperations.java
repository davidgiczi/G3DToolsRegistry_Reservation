package com.geolink3d.toolsregistry.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.geolink3d.toolsregistry.model.GeoInstrument;
import com.geolink3d.toolsregistry.model.GeoWorker;
import com.geolink3d.toolsregistry.model.Location;
import com.geolink3d.toolsregistry.model.Role;
import com.geolink3d.toolsregistry.service.GeoInstrumentService;
import com.geolink3d.toolsregistry.service.GeoWorkerService;
import com.geolink3d.toolsregistry.service.LocationService;
import com.geolink3d.toolsregistry.service.RoleService;

@Controller
@RequestMapping("/tools-registry/admin")
public class AdminOperations {

	
	private GeoWorkerService workerService;
	private RoleService roleService;
	private GeoInstrumentService instrumentService;
	private LocationService locationService;
 	
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
		
		List<GeoWorker> workers = workerService.findAll();
		List<GeoInstrument> usableInstruments = instrumentService.findUseableGeoInstrument();
		List<GeoInstrument> deletedInstruments = instrumentService.findDeletedGeoInstrument();
		List<Location> locations = locationService.findAll();
		model.addAttribute("workers", workers);
		model.addAttribute("usable", usableInstruments);
		model.addAttribute("deleted", deletedInstruments);
		model.addAttribute("locations", locations);
		model.addAttribute("delindex", usableInstruments.size());
		
		return "admin/instruments";
	}
	
	@RequestMapping("/additionals")
	public String goAdditionalsPage() {
		return "admin/additionals";
	}
	
	@RequestMapping("/tools-in-use")
	public String goToolsInUsePage() {
		return "admin/tools-in-use";
	}
	
	@RequestMapping("/tools-history")
	public String goToolsHistoryPage() {
		return "admin/tools-history";
	}
	
	@RequestMapping("/enter")
	public String enterUserAccount(@RequestParam("id") Long id, HttpServletRequest request, Model model) {
		
		
		Optional<GeoWorker> worker = workerService.findGeoWorkerById(id); 
		
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
				
		return "redirect:/tools-registry/user/account";
	}
	
	
	@RequestMapping("/enabled")
	public String enabledUserAccount(@RequestParam("id") Long id) {
		
		Optional<GeoWorker> worker = workerService.findGeoWorkerById(id);
		
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
		
		Optional<GeoWorker> worker = workerService.findGeoWorkerById(id);
		
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
	
	@RequestMapping("/search")
	public String searchPassenger(@RequestParam(value = "text") String text, Model model) {
		
		
		if(text.isEmpty()) {
			return "redirect:/tools-registry/admin/workers";
		}else {
			List<GeoWorker> workers = workerService.findByText(text);
			model.addAttribute("txt", text);
			model.addAttribute("workers", workers);		
		}
		
		return "admin/workers";
	}
	
	@RequestMapping("/add-instrument")
	public String addInstrument(@RequestParam(value="inst") String instrument, RedirectAttributes rdAttr) {
		
		if(instrumentService.saveNewGeoInstrument(instrument)) {
			rdAttr.addAttribute("instSaved","\"" + instrument + "\" műszer hozzáadva a nyilvántartáshoz.");	
		}
		else {
			rdAttr.addAttribute("instSaved", "\""+ instrument + "\" néven már szerepel műszer a nyilvántartásban.");
		}
		
		return "redirect:/tools-registry/admin/instruments";
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
	
	@RequestMapping("/cancel-restore")
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
}
