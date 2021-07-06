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
		List<GeoTool> instrumentTools = instrumentService.convertGeoInstrumentToGeoToolForDisplay(instruments);
		instrumentTools.addAll(additionalService.convertGeoAdditionalToGeoToolForDisplay(additionals, instrumentService.isNextRowIsColored()));
		
		return instrumentTools;
	}
	
	public List<GeoTool> findUsedGeoTools(){
		
		List<GeoInstrument> instruments = instrumentRepo.findNotDeletedButUsedGeoInstruments();
		Collections.sort(instruments, new GeoInstrumentComparator());
		List<GeoTool> tools = instrumentService.convertGeoInstrumentToGeoToolForDisplay(instruments);
		
		List<GeoAdditional> additionals = additionalRepo.findSingleUsedGeoAdditionals();
		Collections.sort(additionals, new GeoAdditionalComparator());
		List<GeoTool> additionalTools = additionalService
				.convertGeoAdditionalToGeoToolForDisplay(additionals, instrumentService.isNextRowIsColored());
		
		tools.addAll(additionalTools);
		
		return tools;
	}
	
}
