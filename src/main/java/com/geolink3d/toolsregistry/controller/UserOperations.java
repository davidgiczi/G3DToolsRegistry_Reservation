package com.geolink3d.toolsregistry.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.geolink3d.toolsregistry.model.GeoWorker;
import com.geolink3d.toolsregistry.service.EncoderService;
import com.geolink3d.toolsregistry.service.GeoWorkerService;

@Controller
@RequestMapping("/tools-registry/user")
public class UserOperations {

	
	private GeoWorkerService workerService;
	
	@Autowired
	public void setWorkerService(GeoWorkerService workerService) {
		this.workerService = workerService;
	}
	

	@RequestMapping("/account")
	public String goUserAccount() {
		
		
		return "layouts/user-account";
	}
	
	
	@PostMapping("/change-password")
	public String changePassword(HttpServletRequest request, Model model) {
		
		GeoWorker worker = workerService.findGeoWorkerByUserName(getAuthUser());
		worker.setPassword(EncoderService.encodeByBase64(request.getParameter("pass")));
		workerService.save(worker);
		model.addAttribute("changedPass", true);
		
		return "layouts/user-account";
	}
	
	private String getAuthUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
