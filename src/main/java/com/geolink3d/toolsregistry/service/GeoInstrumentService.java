package com.geolink3d.toolsregistry.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geolink3d.toolsregistry.model.GeoInstrument;
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
	
	public List<GeoInstrument> findUsedGeoInstrument(){
		List<GeoInstrument> used = instrumentRepo.findNotDeletedButUsedGeoInstruments();
		Collections.sort(used, new UsedGeoInstrumentComparator());
		return used;
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
