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
	
	public List<GeoTool> findUseableGeoTools(){
		List<GeoInstrument> usable = instrumentRepo.findNotDeletedGeoInstruments();
		return convertUsableGeoInstrumentStoreToGeoToolStore(usable);
	}
	
	public List<GeoTool> findDeletedGeoTools(){
		List<GeoInstrument> deleted = instrumentRepo.findDeletedGeoInstruments();
		return convertDeletedGeoInstrumentStoreToGeoToolStore(deleted);
	}
	
	public Optional<GeoInstrument> findById(Long id){
		return instrumentRepo.findById(id);
	}
	
	public void save(GeoInstrument instrument) {
		instrumentRepo.save(instrument);
	}
	
	
	public List<GeoTool> findNotDeletedGeoToolsByText(String text) {
		
		List<GeoInstrument> geoinstruments= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		geoinstruments = instrumentRepo.findNotDeletedGeoInstrumentsByText(text);
		
		if(geoinstruments.isEmpty()) {
		geoinstruments= instrumentRepo.findNotDeletedGeoInstrumentsByText(text.toUpperCase());
		}
		if(geoinstruments.isEmpty()) {
		geoinstruments.addAll(instrumentRepo.findNotDeletedGeoInstrumentsByText(text.toLowerCase()));
		}
		
		List<GeoTool> toolStore = convertUsableGeoInstrumentStoreToGeoToolStore(geoinstruments);
		
		GeoToolHighlighter highlighter = new GeoToolHighlighter(toolStore);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedGeoToolStore();
		
		return highlighter.getHighlightedGeoToolStore();
		
	}
	
	public List<GeoTool> findDeletedGeoToolsByText(String text) {
		
		List<GeoInstrument> geoinstruments= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		geoinstruments = instrumentRepo.findDeletedGeoInstrumentsByText(text);
		
		if(geoinstruments.isEmpty()) {
		geoinstruments= instrumentRepo.findDeletedGeoInstrumentsByText(text.toUpperCase());
		}
		if(geoinstruments.isEmpty()) {
		geoinstruments.addAll(instrumentRepo.findDeletedGeoInstrumentsByText(text.toLowerCase()));
		}
		
		List<GeoTool> toolStore = convertDeletedGeoInstrumentStoreToGeoToolStore(geoinstruments);
		
		GeoToolHighlighter highlighter = new GeoToolHighlighter(toolStore);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedGeoToolStore();
		
		return highlighter.getHighlightedGeoToolStore();
		
	}
	
	public List<GeoTool> convertGeoInstrumentToGeoToolForDisplay(List<GeoInstrument> instrumentStore){
		
		List<GeoTool> toolStore = new ArrayList<>();
		
		boolean isColored = true;
	
		for (GeoInstrument geoInstrument : instrumentStore) {
			
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
				toolStore.add(additionalTool);
			}
			
			isColored = !isColored;
		}
		
		return toolStore;
	}
	
	public boolean isNextRowIsColored() {
		
		List<GeoTool> instrumentTools = convertGeoInstrumentToGeoToolForDisplay(instrumentRepo.findNotDeletedButUsedGeoInstruments());
		
		if(instrumentTools != null && !instrumentTools.isEmpty()) {
		return !instrumentTools.get(instrumentTools.size() - 1).isColored();
		}
		
		return true;
	}
	
	private List<GeoTool> convertUsableGeoInstrumentStoreToGeoToolStore(List<GeoInstrument> usableInstrumentStore){
		List<GeoTool> usableToolStore = new ArrayList<>();
		for (GeoInstrument instrument : usableInstrumentStore) {
			GeoTool tool = new GeoTool();
			tool.setId(instrument.getId());
			tool.setToolName(instrument.getName());
			if(instrument.getGeoworker() != null) {
				tool.setToolUser(instrument.getGeoworker().getLastname() + ' ' + instrument.getGeoworker().getFirstname());
			}
			if(instrument.getPickUpDate() != null) {
				tool.setPickUpDate(instrument.getPickUpDate());
			}
			if(instrument.getPickUpPlace() != null) {
				tool.setPickUpPlace(instrument.getPickUpPlace());
			}
			if(instrument.getComment() != null) {
				tool.setComment(instrument.getComment());
			}
			
			tool.setUsed(instrument.isUsed());
			usableToolStore.add(tool);
		}
		
		Collections.sort(usableToolStore);
		return usableToolStore;
	}
	
	private List<GeoTool> convertDeletedGeoInstrumentStoreToGeoToolStore(List<GeoInstrument> deletedInstrumentStore){
		List<GeoTool> deletedToolStore = new ArrayList<>();
		for (GeoInstrument instrument : deletedInstrumentStore) {
			GeoTool tool = new GeoTool();
			tool.setId(instrument.getId());
			tool.setToolName(instrument.getName());
			deletedToolStore.add(tool);
		}
		
		Collections.sort(deletedToolStore);
		return deletedToolStore;
	}
}
