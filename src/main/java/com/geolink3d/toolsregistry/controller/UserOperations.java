package com.geolink3d.toolsregistry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tools-registry/user")
public class UserOperations {

	
	@RequestMapping("/account")
	public String goUserAccount() {
		return "layouts/user-account";
	}
	
}