package com.geolink3d.toolsregistry.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geolink3d.toolsregistry.model.GeoAdditional;
import com.geolink3d.toolsregistry.model.GeoTool;
import com.geolink3d.toolsregistry.repository.GeoAdditionalRepository;
import com.geolink3d.toolsregistry.repository.GeoInstrumentRepository;

@Service
public class GeoAdditionalService {

	
	private GeoAdditionalRepository additionalRepo;
	private GeoInstrumentRepository instrumentRepo;
 	private GeoInstrumentService instrumentService;

	@Autowired
	public void setAdditionalRepo(GeoAdditionalRepository additionalRepo) {
		this.additionalRepo = additionalRepo;
	}
		
	@Autowired
	public void setInstrumentService(GeoInstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}

	@Autowired
	public void setInstrumentRepo(GeoInstrumentRepository instrumentRepo) {
		this.instrumentRepo = instrumentRepo;
	}

	public boolean saveNewGeoAdditional(String name) {
		
		GeoAdditional additional = additionalRepo.findGeoAdditionalByName(name);
		
		if(additional == null) {
			additional = new GeoAdditional();
			additional.setName(name);
			additionalRepo.save(additional);
			return true;
		}
		
		return false;
	}
	
	public Optional<GeoAdditional> findById(Long id){
		return additionalRepo.findById(id);
	}
	
	public void save(GeoAdditional additional) {
		additionalRepo.save(additional);
	}
	
	public List<GeoTool> findUseableGeoTools(){
		List<GeoAdditional> usable = additionalRepo.findNotDeletedGeoAdditionals();
		Collections.sort(usable);
		return convertUsableAdditionalStoreToGeoToolStore(usable);
	}
	
	public List<GeoTool> findDeletedGeoTools(){
		List<GeoAdditional> deleted = additionalRepo.findDeletedGeoAdditionals();
		Collections.sort(deleted);
		return convertDeletedGeoAdditionalStoreToGeoToolStore(deleted);
	}
	
	public List<GeoTool> findSingleAdditionalAsGeoTools(){
		
		List<GeoAdditional> singleUsedAdditionals= additionalRepo.findSingleUsedGeoAdditionals();
		Collections.sort(singleUsedAdditionals, new GeoAdditionalComparator());
		List<GeoTool> instrumentTools = instrumentService.convertGeoInstrumentToGeoToolForDisplay(instrumentRepo.findNotDeletedButUsedGeoInstruments());
		return convertGeoAdditionalToGeoToolForDisplay(singleUsedAdditionals, instrumentService.isNextRowIsColored(instrumentTools));
	}
	
	
	public List<GeoTool> findNotDeletedGeoToolsByText(String text) {
		
		List<GeoAdditional> geoAdditionals= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		geoAdditionals = additionalRepo.findNotDeletedGeoAdditionalsByText(text);
		
		if(geoAdditionals.isEmpty()) {
		geoAdditionals= additionalRepo.findNotDeletedGeoAdditionalsByText(text.toUpperCase());
		}
		if(geoAdditionals.isEmpty()) {
		geoAdditionals.addAll(additionalRepo.findNotDeletedGeoAdditionalsByText(text.toLowerCase()));
		}
		
		Collections.sort(geoAdditionals);
		List<GeoTool> toolStore = convertUsableAdditionalStoreToGeoToolStore(geoAdditionals);
		
		GeoToolHighlighter highlighter = new GeoToolHighlighter(toolStore);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedGeoToolStore();
		
		return  highlighter.getHighlightedGeoToolStore();
		
	}
	
	public List<GeoTool> findDeletedGeoToolsByText(String text) {
		
		List<GeoAdditional> geoAdditionals= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		geoAdditionals = additionalRepo.findDeletedGeoAdditionalsByText(text);
		
		if(geoAdditionals.isEmpty()) {
		geoAdditionals= additionalRepo.findDeletedGeoAdditionalsByText(text.toUpperCase());
		}
		if(geoAdditionals.isEmpty()) {
		geoAdditionals.addAll(additionalRepo.findDeletedGeoAdditionalsByText(text.toLowerCase()));
		}
		
		Collections.sort(geoAdditionals);
		List<GeoTool> toolStore = convertDeletedGeoAdditionalStoreToGeoToolStore(geoAdditionals);
		
		GeoToolHighlighter highlighter = new GeoToolHighlighter(toolStore);
		highlighter.setSearchedExpression(text);
		highlighter.createHighlightedGeoToolStore();
		
		return highlighter.getHighlightedGeoToolStore();
		
	}
	
	public List<GeoTool> convertGeoAdditionalToGeoToolForDisplay(List<GeoAdditional> additionalStore, boolean isNextRowColored){
		
		List<GeoTool> toolStore = new ArrayList<>();
		for (GeoAdditional geoAdditional : additionalStore) {
			GeoTool additionalTool = new GeoTool();
			additionalTool.setId(geoAdditional.getId());
			additionalTool.setToolName(geoAdditional.getName());
			additionalTool.setToolUser(geoAdditional.getGeoworker().getLastname() + " " + geoAdditional.getGeoworker().getFirstname());
			if(geoAdditional.getInstrument() != null) {
				additionalTool.setInstrumentName(geoAdditional.getInstrument().getName());
			}
			additionalTool.setPickUpDate(geoAdditional.getPickUpDate());
			additionalTool.setPickUpPlace(geoAdditional.getPickUpPlace());
			additionalTool.setComment(geoAdditional.getComment());
			additionalTool.setColored(isNextRowColored);
			additionalTool.setInstrument(false);
			toolStore.add(additionalTool);
			isNextRowColored = !isNextRowColored;
	}
		return toolStore;
	}
	
	private List<GeoTool> convertUsableAdditionalStoreToGeoToolStore(List<GeoAdditional> usableAdditionalStore){
		List<GeoTool> usableToolStore = new ArrayList<>();
		for (GeoAdditional additional : usableAdditionalStore) {
			GeoTool tool = new GeoTool();
			tool.setId(additional.getId());
			tool.setToolName(additional.getName());
			if(additional.getInstrument() != null) {
				tool.setInstrumentName(additional.getInstrument().getName());
			}
			if(additional.getGeoworker() != null) {
				tool.setToolUser(additional.getGeoworker().getLastname() + ' ' + additional.getGeoworker().getFirstname());
			}
			if(additional.getPickUpDate() != null) {
				tool.setPickUpDate(additional.getPickUpDate());
			}
			if(additional.getPickUpPlace() != null) {
				tool.setPickUpPlace(additional.getPickUpPlace());
			}
			if(additional.getComment() != null) {
				tool.setComment(additional.getComment());
			}
			tool.setUsed(additional.isUsed());
			usableToolStore.add(tool);
		}
		
		return usableToolStore;
	}
	
	private List<GeoTool> convertDeletedGeoAdditionalStoreToGeoToolStore(List<GeoAdditional> deletedAdditionalStore){
		List<GeoTool> deletedToolStore = new ArrayList<>();
		for (GeoAdditional additional : deletedAdditionalStore) {
			GeoTool tool = new GeoTool();
			tool.setId(additional.getId());
			tool.setToolName(additional.getName());
			deletedToolStore.add(tool);
		}
		
		return deletedToolStore;
	}
}
