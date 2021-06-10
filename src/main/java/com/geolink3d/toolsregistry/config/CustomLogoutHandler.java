package com.geolink3d.toolsregistry.config;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import com.geolink3d.toolsregistry.model.GeoWorker;
import com.geolink3d.toolsregistry.model.Role;
import com.geolink3d.toolsregistry.service.GeoWorkerService;



public class CustomLogoutHandler implements LogoutHandler {
	
	private GeoWorkerService workerService;
	
	public CustomLogoutHandler(GeoWorkerService workerService) {
	
		this.workerService = workerService;
	}


	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		GeoWorker authedWorker = workerService.findGeoWorkerByUserName(authentication.getName());
		Role guest = new Role("ROLE_GUEST");
		if(authedWorker != null && authedWorker.getRoles().contains(guest)) {
		authedWorker.getRoles().remove(guest);
		workerService.save(authedWorker);
		}
	}

	
	

}
