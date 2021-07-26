package com.geolink3d.toolsregistry.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geolink3d.toolsregistry.model.GeoAdditional;
import com.geolink3d.toolsregistry.model.GeoInstrument;
import com.geolink3d.toolsregistry.model.GeoTool;
import com.geolink3d.toolsregistry.repository.GeoAdditionalRepository;
import com.geolink3d.toolsregistry.repository.GeoInstrumentRepository;

@Service
public class GeoToolInUseService {

	
	private GeoInstrumentRepository instrumentRepo;
	private GeoAdditionalRepository additionalRepo;
	private GeoInstrumentService instrumentService;
	private GeoAdditionalService additionalService;
	private GeoWorkerService workerService;
	
	@Autowired
	public void setInstrumentRepo(GeoInstrumentRepository instrumentRepo) {
		this.instrumentRepo = instrumentRepo;
	}
	@Autowired
	public void setAdditionalRepo(GeoAdditionalRepository additionalRepo) {
		this.additionalRepo = additionalRepo;
	}
	
	@Autowired
	public void setInstrumentService(GeoInstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}
	
	@Autowired
	public void setAdditionalService(GeoAdditionalService additionalService) {
		this.additionalService = additionalService;
	}
	
	@Autowired
	public void setWorkerService(GeoWorkerService workerService) {
		this.workerService = workerService;
	}
	public List<GeoTool> findGeoToolsInUseByText(String text){
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		List<GeoInstrument> instruments = instrumentRepo.findGeoInstrumentsInUseByText(text);
		List<GeoAdditional> additionals = additionalRepo.findGeoAdditionalsInUseByText(text);
		
		if(instruments.isEmpty()) {
		instruments = instrumentRepo.findGeoInstrumentsInUseByText(text.toUpperCase());
		}
		if(instruments.isEmpty()) {
		instruments.addAll(instrumentRepo.findGeoInstrumentsInUseByText(text.toLowerCase()));
		}
		if(additionals.isEmpty()) {
		additionals = additionalRepo.findGeoAdditionalsInUseByText(text.toUpperCase());
		}
		if(additionals.isEmpty()) {
			additionals = additionalRepo.findGeoAdditionalsInUseByText(text.toLowerCase());
		}
		
		Collections.sort(instruments, new GeoInstrumentComparator());
		Collections.sort(additionals, new GeoAdditionalComparator());
		
		List<GeoTool> toolsInUse = instrumentService.convertGeoInstrumentToGeoToolForSearching(instruments);
		toolsInUse.addAll(additionalService.convertGeoAdditionalToGeoToolForDisplay(additionals, instrumentService.isNextRowIsColored(toolsInUse)));
		
		GeoToolInUseHighlighter highlighter = new GeoToolInUseHighlighter(toolsInUse);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedGeoToolInUseStore();
		
		return highlighter.getHighlightedGeoToolInUseStore();
	}
	
public List<GeoTool> findGeoToolsInUseByTextAndUserId(String text, String authUser){
		
		Long workerId = workerService.findIdByUsername(authUser);
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		List<GeoInstrument> instruments = instrumentRepo.findGeoInstrumentsInUseByTextAndUserId(text, workerId);
		List<GeoAdditional> additionals = additionalRepo.findGeoAdditionalsInUseByTextAndUserId(text, workerId);
		
		if(instruments.isEmpty()) {
		instruments = instrumentRepo.findGeoInstrumentsInUseByTextAndUserId(text.toUpperCase(), workerId);
		}
		if(instruments.isEmpty()) {
		instruments.addAll(instrumentRepo.findGeoInstrumentsInUseByTextAndUserId(text.toLowerCase(), workerId));
		}
		if(additionals.isEmpty()) {
		additionals = additionalRepo.findGeoAdditionalsInUseByTextAndUserId(text.toUpperCase(), workerId);
		}
		if(additionals.isEmpty()) {
			additionals = additionalRepo.findGeoAdditionalsInUseByTextAndUserId(text.toLowerCase(), workerId);
		}
		
		Collections.sort(instruments, new GeoInstrumentComparator());
		Collections.sort(additionals, new GeoAdditionalComparator());
		
		List<GeoTool> toolsInUse = instrumentService.convertGeoInstrumentToGeoToolForSearching(instruments);
		toolsInUse.addAll(additionalService.convertGeoAdditionalToGeoToolForDisplay(additionals, instrumentService.isNextRowIsColored(toolsInUse)));
		
		GeoToolInUseHighlighter highlighter = new GeoToolInUseHighlighter(toolsInUse);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedGeoToolInUseStore();
		
		return highlighter.getHighlightedGeoToolInUseStore();
	}
	
	
	public List<GeoTool> findBetweenDates(String date1, String date2) throws ParseException{
		
		List<GeoInstrument> instruments;
		List<GeoAdditional> additionals;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date inputDate1 = format.parse(date1);
		Date inputDate2 = format.parse(date2);

		if(inputDate1.getTime() <= inputDate2.getTime()) {
			inputDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date2 + " 24:00");
			instruments = instrumentRepo.findBetweenDates(inputDate1, inputDate2);
			additionals = additionalRepo.findBetweenDates(inputDate1, inputDate2);
		}
		else {
			inputDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date1 + " 24:00");
			instruments = instrumentRepo.findBetweenDates(inputDate2, inputDate1);
			additionals = additionalRepo.findBetweenDates(inputDate2, inputDate1);
			
		}
		
		Collections.sort(instruments, new GeoInstrumentComparator());
		Collections.sort(additionals, new GeoAdditionalComparator());
		List<GeoTool> instrumentTools = instrumentService.convertGeoInstrumentToGeoToolForDisplay(instruments);
		instrumentTools.addAll(additionalService.convertGeoAdditionalToGeoToolForDisplay(additionals, instrumentService.isNextRowIsColored(instrumentTools)));
		
		return instrumentTools;
	}
	
	public List<GeoTool> findByPickUpDate(String date) throws ParseException{
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = format.parse(date);
		Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " 24:00");
		List<GeoInstrument> instruments = instrumentRepo.findByPickUpDate(begin, end);
		List<GeoAdditional> additionals = additionalRepo.findByPickUpDate(begin, end);
		Collections.sort(instruments, new GeoInstrumentComparator());
		Collections.sort(additionals, new GeoAdditionalComparator());
		List<GeoTool> instrumentTools = instrumentService.convertGeoInstrumentToGeoToolForDisplay(instruments);
		instrumentTools.addAll(additionalService.convertGeoAdditionalToGeoToolForDisplay(additionals, instrumentService.isNextRowIsColored(instrumentTools)));
		
		return instrumentTools;
	}
	
	public List<GeoTool> findByPickUpDateAndUserId(String date, String authUser) throws ParseException{
		
		Long workerId = workerService.findIdByUsername(authUser);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = format.parse(date);
		Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " 24:00");
		List<GeoInstrument> instruments = instrumentRepo.findByPickUpDateAndUserId(begin, end, workerId);
		List<GeoAdditional> additionals = additionalRepo.findByPickUpDateAndUserId(begin, end, workerId);
		Collections.sort(instruments, new GeoInstrumentComparator());
		Collections.sort(additionals, new GeoAdditionalComparator());
		List<GeoTool> instrumentTools = instrumentService.convertGeoInstrumentToGeoToolForDisplay(instruments);
		instrumentTools.addAll(additionalService.convertGeoAdditionalToGeoToolForDisplay(additionals, instrumentService.isNextRowIsColored(instrumentTools)));
		
		return instrumentTools;
	}
	
	public List<GeoTool> findUsedGeoTools(){
		
		List<GeoInstrument> instruments = instrumentRepo.findNotDeletedButUsedGeoInstruments();
		Collections.sort(instruments, new GeoInstrumentComparator());
		List<GeoTool> instrumentTools = instrumentService.convertGeoInstrumentToGeoToolForDisplay(instruments);
		
		List<GeoAdditional> additionals = additionalRepo.findSingleUsedGeoAdditionals();
		Collections.sort(additionals, new GeoAdditionalComparator());
		List<GeoTool> additionalTools = additionalService
				.convertGeoAdditionalToGeoToolForDisplay(additionals, instrumentService.isNextRowIsColored(instrumentTools));
		
		instrumentTools.addAll(additionalTools);
		
		return instrumentTools;
	}
	
	public List<GeoTool> findUsedGeoToolsByUser(String authUser){
		
		Long workerId = workerService.findIdByUsername(authUser);
		
		List<GeoInstrument> instruments = instrumentRepo.findGeoInstrumentsByUserId(workerId);
		Collections.sort(instruments, new GeoInstrumentComparator());
		List<GeoTool> instrumentTools = instrumentService.convertGeoInstrumentToGeoToolForDisplay(instruments);
		
		List<GeoAdditional> additionals = additionalRepo.findGeoAdditionalsByUserId(workerId);
		
		
		for (int i = additionals.size() - 1 ; i >= 0 ; i-- ) {
			for (GeoInstrument instrument : instruments) {
				if(!additionals.isEmpty() 
						&& additionals.get(i).getInstrument() != null 
							&& instrument.getId() == additionals.get(i).getInstrument().getId()) {
					additionals.remove(i);
					
				}
			}
			
		}
		
		Collections.sort(additionals, new GeoAdditionalComparator());
		List<GeoTool> additionalTools = additionalService
				.convertGeoAdditionalToGeoToolForDisplay(additionals, instrumentService.isNextRowIsColored(instrumentTools));
		
		instrumentTools.addAll(additionalTools);
		
		return instrumentTools;
	}
	
}
