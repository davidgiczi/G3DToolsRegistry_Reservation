package com.geolink3d.toolsregistry.service;

import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class EncoderService {

	
	public static String encodeByBase64(String rawPassword) {
		return Base64.getEncoder().encodeToString(rawPassword.getBytes());
	}
	
	public static String decodeByBase64(String encodedPassword) {
		return new String(Base64.getDecoder().decode(encodedPassword));
	}
}
