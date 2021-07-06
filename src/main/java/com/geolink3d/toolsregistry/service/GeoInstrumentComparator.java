package com.geolink3d.toolsregistry.service;

import java.util.Comparator;
import com.geolink3d.toolsregistry.model.GeoInstrument;

public class GeoInstrumentComparator implements Comparator<GeoInstrument> {

	@Override
	public int compare(GeoInstrument o1, GeoInstrument o2) {
		
		return o1.getPickUpDate().getTime() > o2.getPickUpDate().getTime() ?  1 : o1.getPickUpDate().getTime() < o2.getPickUpDate().getTime() ? -1 : 0;
	}

	
}
