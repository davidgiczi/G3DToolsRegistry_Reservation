package com.geolink3d.toolsregistry.service;

import java.util.Comparator;
import com.geolink3d.toolsregistry.model.GeoAdditional;

public class GeoAdditionalComparator implements Comparator<GeoAdditional> {

	@Override
	public int compare(GeoAdditional o1, GeoAdditional o2) {
		
		return o1.getPickUpDate().toEpochSecond() > o2.getPickUpDate().toEpochSecond()
				?  1 : o1.getPickUpDate().toEpochSecond() < o2.getPickUpDate().toEpochSecond() ? -1 : 0;
	}

	

}
