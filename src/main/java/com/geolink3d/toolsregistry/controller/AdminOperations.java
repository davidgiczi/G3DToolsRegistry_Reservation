package com.geolink3d.toolsregistry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tools-registry/admin")
public class AdminOperations {

	
	@RequestMapping("/err")
	public String goErrorPage() {
		return "error";
	}
	
}
