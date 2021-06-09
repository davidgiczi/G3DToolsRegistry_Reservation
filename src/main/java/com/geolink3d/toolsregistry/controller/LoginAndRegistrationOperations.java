package com.geolink3d.toolsregistry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.geolink3d.toolsregistry.model.GeoWorker;
import com.geolink3d.toolsregistry.service.GeoWorkerService;


@Controller
@RequestMapping("tools-registry")
public class LoginAndRegistrationOperations {

	
	private GeoWorkerService workerService;
	

	@Autowired
	public void setWorkerService(GeoWorkerService workerService) {
		this.workerService = workerService;
	}

	@RequestMapping("/login")
	public String goLoginPage() {
		return "auth/login";
	}
	
	@RequestMapping("/registration")
	public String goRegistrationPage(Model model) {
		
		model.addAttribute("worker", new GeoWorker());
		
		return "regist";
	}
	
	@PostMapping("/registration")
	public String registration(@ModelAttribute GeoWorker user, RedirectAttributes attribute, Model model) {
		
		if(!workerService.registerGeoWorker(user)) {
			
			attribute.addAttribute("exists", true);
			
			return "redirect:/tools-registry/registration";
		}
		
		model.addAttribute("activationSuccess", true);
		
		return "auth/login";
	}
	
}
