package com.geolink3d.toolsregistry.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geolink3d.toolsregistry.model.UsedGeoTool;
import com.geolink3d.toolsregistry.repository.UsedToolRepository;

@Service
public class UsedGeoToolService {

	
	private UsedToolRepository usedToolRepo;

	@Autowired
	public void setUsedToolRepo(UsedToolRepository usedToolRepo) {
		this.usedToolRepo = usedToolRepo;
	}
	
	public List<UsedGeoTool> findAll(){
		List<UsedGeoTool> used = usedToolRepo.findAll();
		//Collections.sort(used);
		return used;
	}
	
	public List<UsedGeoTool> findFirstXPcsOrderByPutDownDateDesc(int pcs){
		return usedToolRepo.findFirstXUsedGeoToolsOrderByPutDownDateDesc(pcs);
	}
	
	public void save(UsedGeoTool usedTool) {
		usedToolRepo.save(usedTool);
	}
	
	public List<UsedGeoTool> findBetweenDates(String date1, String date2) throws ParseException{
		
		List<UsedGeoTool> tools;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date inputDate1 = format.parse(date1);
		Date inputDate2 = format.parse(date2);

		if(inputDate1.getTime() <= inputDate2.getTime()) {
			inputDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date2 + " 24:00");
			tools = usedToolRepo.findBetweenDates(inputDate1, inputDate2);
		}
		else {
			inputDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date1 + " 24:00");
			tools = usedToolRepo.findBetweenDates(inputDate2, inputDate1);
		}
		Collections.sort(tools);
		return tools;
	}
	
	
	
	public List<UsedGeoTool> findUsedGeoToolsByText(String text){
		
		List<UsedGeoTool> usedGeoTools= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		usedGeoTools = usedToolRepo.findUsedToolsByText(text);
		
		if(usedGeoTools.isEmpty()) {
		usedGeoTools= usedToolRepo.findUsedToolsByText(text.toUpperCase());
		}
		if(usedGeoTools.isEmpty()) {
		usedGeoTools= usedToolRepo.findUsedToolsByText(text.toLowerCase());
		}
		
		Collections.sort(usedGeoTools);
	
		UsedGeoToolHighlighter highlighter = new UsedGeoToolHighlighter(usedGeoTools);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedUsedGeoToolStore();
		
		return highlighter.getHighlightedUsedGeoToolStore();
	}
	
	public List<UsedGeoTool> findUsedGeoToolIntrumentsByText(String text){
		
		List<UsedGeoTool> usedGeoTools= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		usedGeoTools = usedToolRepo.findUsedToolIntrumentsByText(text);
		
		if(usedGeoTools.isEmpty()) {
		usedGeoTools= usedToolRepo.findUsedToolIntrumentsByText(text.toUpperCase());
		}
		if(usedGeoTools.isEmpty()) {
		usedGeoTools= usedToolRepo.findUsedToolIntrumentsByText(text.toLowerCase());
		}
		
		Collections.sort(usedGeoTools);
	
		UsedGeoToolHighlighter highlighter = new UsedGeoToolHighlighter(usedGeoTools);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedUsedGeoToolStore();
		
		return highlighter.getHighlightedUsedGeoToolStore();
	}
	
public List<UsedGeoTool> findUsedGeoToolAdditionalsByText(String text){
		
		List<UsedGeoTool> usedGeoTools= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		usedGeoTools = usedToolRepo.findUsedToolAdditionalsByText(text);
		
		if(usedGeoTools.isEmpty()) {
		usedGeoTools= usedToolRepo.findUsedToolAdditionalsByText(text.toUpperCase());
		}
		if(usedGeoTools.isEmpty()) {
		usedGeoTools= usedToolRepo.findUsedToolAdditionalsByText(text.toLowerCase());
		}
		
		Collections.sort(usedGeoTools);
	
		UsedGeoToolHighlighter highlighter = new UsedGeoToolHighlighter(usedGeoTools);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedUsedGeoToolStore();
		
		return highlighter.getHighlightedUsedGeoToolStore();
	}

	public List<UsedGeoTool> findUsedGeoToolByPickUpDate(String date, String option) throws ParseException{
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = format.parse(date);
		Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " 24:00");
		List<UsedGeoTool> used;
		
		switch (option) {
		case "instruments":
			used = usedToolRepo.findInstrumentsByPickUpDate(begin, end);
			break;
		case "additionals":
			used = usedToolRepo.findAdditionalsByPickUpDate(begin, end);
			break;

		default:
			used = usedToolRepo.findByPickUpDate(begin, end);
		}
		
		Collections.sort(used);
		return used;
	}
	public List<UsedGeoTool> findUsedGeoToolByPutDownDate(String date, String option) throws ParseException{
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = format.parse(date);
		Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " 24:00");
		List<UsedGeoTool> used;
		
		switch (option) {
		case "instruments":
			used = usedToolRepo.findInstrumentsByPutDownDate(begin, end);
			break;
		case "additionals":
			used = usedToolRepo.findAdditionalsByPutDownDate(begin, end);
			break;

		default:
			used = usedToolRepo.findByPutDownDate(begin, end);
		}
		
		Collections.sort(used);
		return used;
	}
}
