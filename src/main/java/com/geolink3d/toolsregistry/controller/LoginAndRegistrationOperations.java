package com.geolink3d.toolsregistry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.geolink3d.toolsregistry.model.GeoWorker;
import com.geolink3d.toolsregistry.model.Role;
import com.geolink3d.toolsregistry.service.GeoWorkerService;
import com.geolink3d.toolsregistry.service.RoleService;


@Controller
@RequestMapping("tools-registry")
public class LoginAndRegistrationOperations {

	
	private GeoWorkerService workerService;
	private RoleService roleService;
	

	@Autowired
	public void setWorkerService(GeoWorkerService workerService) {
		this.workerService = workerService;
	}
	
	@Autowired
	public RoleService getRoleService() {
		return roleService;
	}


	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
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
	
	@RequestMapping("/enter")
	public String enterIntoAccount() {
		
		GeoWorker worker = workerService.findGeoWorkerByUserName(getAuthUser());
		
		if(worker.getRoles().contains(new Role("ROLE_ADMIN"))){
			return "redirect:/tools-registry/admin/instruments";
		}
		
		return "redirect:/tools-registry/user/account";
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
	
	private String getAuthUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
