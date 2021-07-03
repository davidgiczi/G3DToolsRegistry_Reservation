package com.geolink3d.toolsregistry.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geolink3d.toolsregistry.model.GeoAdditional;
import com.geolink3d.toolsregistry.model.GeoInstrument;
import com.geolink3d.toolsregistry.model.GeoTool;
import com.geolink3d.toolsregistry.repository.GeoInstrumentRepository;


@Service
public class GeoInstrumentService {

	
	private GeoInstrumentRepository instrumentRepo;
	
	@Autowired
	public void setInstrumentRepo(GeoInstrumentRepository instrumentRepo) {
		this.instrumentRepo = instrumentRepo;
	}
	
	public boolean saveNewGeoInstrument(String name) {
		
		GeoInstrument instrument = instrumentRepo.findGeoInstrumentByName(name);
		
		if(instrument == null) {
			instrument = new GeoInstrument();
			instrument.setName(name);
			instrumentRepo.save(instrument);
			return true;
		}
		
		return false;
	}
	
	public List<GeoInstrument> findUseableGeoInstrument(){
		List<GeoInstrument> usable = instrumentRepo.findNotDeletedGeoInstruments();
		Collections.sort(usable);
		return usable;
	}
	
	public List<GeoInstrument> findDeletedGeoInstrument(){
		List<GeoInstrument> deleted = instrumentRepo.findDeletedGeoInstruments();
		Collections.sort(deleted);
		return deleted;
	}
	
	public List<GeoTool> findUsedGeoTool(){
		
		List<GeoInstrument> usedIntsruments= instrumentRepo.findNotDeletedButUsedGeoInstruments();
		Collections.sort(usedIntsruments, new UsedGeoInstrumentComparator());
		
		List<GeoTool> toolStore = new ArrayList<>();
		boolean isColored = true;
		for (GeoInstrument geoInstrument : usedIntsruments) {
			GeoTool instrumentTool = new GeoTool();
			instrumentTool.setId(geoInstrument.getId());
			instrumentTool.setToolName(geoInstrument.getName());
			instrumentTool.setToolUser(geoInstrument.getGeoworker().getLastname() + " " + geoInstrument.getGeoworker().getFirstname());
			instrumentTool.setPickUpDate(geoInstrument.getPickUpDate());
			instrumentTool.setPickUpPlace(geoInstrument.getPickUpPlace());
			instrumentTool.setComment(geoInstrument.getComment());
			instrumentTool.setColored(isColored);
			instrumentTool.setInstruction(true);
			toolStore.add(instrumentTool);
			
			for (GeoAdditional geoAdditional : geoInstrument.getAdditionals()) {
				GeoTool additionalTool = new GeoTool();
				additionalTool.setId(geoAdditional.getId());
				additionalTool.setToolName(geoInstrument.getName() + "/" + geoAdditional.getName());
				additionalTool.setToolUser(geoAdditional.getGeoworker().getLastname() + " " + geoAdditional.getGeoworker().getFirstname());
				additionalTool.setPickUpDate(geoAdditional.getPickUpDate());
				additionalTool.setPickUpPlace(geoAdditional.getPickUpPlace());
				additionalTool.setComment(geoAdditional.getComment());
				additionalTool.setColored(isColored);
				additionalTool.setInstruction(false);
				toolStore.add(instrumentTool);
			}
			
			isColored = !isColored;
			
		}
		
		return toolStore;
	}
	
	public Optional<GeoInstrument> findById(Long id){
		return instrumentRepo.findById(id);
	}
	
	public void save(GeoInstrument instrument) {
		instrumentRepo.save(instrument);
	}
	
	
	public List<GeoInstrument> findNotDeletedInstrumentsByText(String text) {
		
		List<GeoInstrument> geoinstruments= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		geoinstruments = instrumentRepo.findNotDeletedInstrumentsByText(text);
		
		if(geoinstruments.isEmpty()) {
		geoinstruments= instrumentRepo.findNotDeletedInstrumentsByText(text.toUpperCase());
		}
		if(geoinstruments.isEmpty()) {
		geoinstruments.addAll(instrumentRepo.findNotDeletedInstrumentsByText(text.toLowerCase()));
		}
		
		Collections.sort(geoinstruments);
		
		GeoInstrumentHighlighter highlighter = new GeoInstrumentHighlighter(geoinstruments);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedGeoInstrumentStore();
		
		return highlighter.getHighlightedGeoIntsrumentStore();
		
	}
	
	public List<GeoInstrument> findDeletedInstrumentsByText(String text) {
		
		List<GeoInstrument> geoinstruments= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		geoinstruments = instrumentRepo.findDeletedInstrumentsByText(text);
		
		if(geoinstruments.isEmpty()) {
		geoinstruments= instrumentRepo.findDeletedInstrumentsByText(text.toUpperCase());
		}
		if(geoinstruments.isEmpty()) {
		geoinstruments.addAll(instrumentRepo.findDeletedInstrumentsByText(text.toLowerCase()));
		}
		
		Collections.sort(geoinstruments);
		
		GeoInstrumentHighlighter highlighter = new GeoInstrumentHighlighter(geoinstruments);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedGeoInstrumentStore();
		
		return highlighter.getHighlightedGeoIntsrumentStore();
		
	}
}
