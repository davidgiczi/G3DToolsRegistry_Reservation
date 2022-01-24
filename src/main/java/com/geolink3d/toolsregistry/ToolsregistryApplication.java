package com.geolink3d.toolsregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ToolsregistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolsregistryApplication.class, args);
		
	}

}
