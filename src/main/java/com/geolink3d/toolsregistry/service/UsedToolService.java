package com.geolink3d.toolsregistry.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geolink3d.toolsregistry.model.UsedGeoTool;
import com.geolink3d.toolsregistry.repository.UsedToolRepository;

@Service
public class UsedToolService {

	
	private UsedToolRepository usedToolRepo;

	@Autowired
	public void setUsedToolRepo(UsedToolRepository usedToolRepo) {
		this.usedToolRepo = usedToolRepo;
	}
	
	public List<UsedGeoTool> findAll(){
		List<UsedGeoTool> used = usedToolRepo.findAll();
		Collections.sort(used);
		return used;
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
}
