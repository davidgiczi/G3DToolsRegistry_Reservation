package com.geolink3d.toolsregistry.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.geolink3d.toolsregistry.model.GeoToolReservation;
import com.geolink3d.toolsregistry.repository.GeoToolReservationRepository;
import com.geolink3d.toolsregistry.repository.GeoWorkerRepository;

@Service
public class GeoToolReservationService {

	
	@Autowired
	private GeoToolReservationRepository reservationRepo;
	@Autowired
	private GeoWorkerRepository workerRepo;
	
	public String getActualDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date(System.currentTimeMillis()));
	}
	
	public boolean isChosenGeoToolReservation(String instrumentId, String additionalId) {
		
		if("-".equals(instrumentId) && "-".equals(additionalId)) {
			return false;
		}
		
		return true;
	}
	
	public boolean isValidGeoToolReservationDates(String startDate, String endDate) {
		
		if(startDate.isEmpty() || endDate.isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public boolean isRegistableGeoToolReservation(String instrumentId, String additionalId, String startDate, String endDate) {
		
		List<GeoToolReservation> reservationStore = null;
		
		if( !"-".equals(instrumentId) ) {
			reservationStore = reservationRepo.findGeoInstrumentReservationsByToolId(Long.parseLong(instrumentId));
		}
		if( !"-".equals(additionalId) && reservationStore == null) {
			reservationStore = reservationRepo.findGeoAdditionalReservationsByToolId(Long.parseLong(additionalId));
		}
		if( !"-".equals(additionalId) && reservationStore != null) {
			reservationStore.addAll(reservationRepo.findGeoAdditionalReservationsByToolId(Long.parseLong(additionalId)));
		}
		
		
		return true;
	}
	
	public List<GeoToolReservation> findAllGeoReservations(){
		return reservationRepo.findAll();
	}
	
	public void saveGeoToolReservation(String instrumentId, String additionalId, String startDate, String endDate) throws ParseException {
		
		GeoToolReservation instrumentReservation = null;
		GeoToolReservation additionalReservation = null;
		
		if( !"-".equals(instrumentId) && "-".equals(additionalId)) {
		instrumentReservation = new GeoToolReservation();
		instrumentReservation.setToolId(Long.parseLong(instrumentId));
		instrumentReservation.setInstrument(true);
		}
		else if( "-".equals(instrumentId) && !"-".equals(additionalId)) {
		additionalReservation = new GeoToolReservation();
		additionalReservation.setToolId(Long.parseLong(additionalId));
		additionalReservation.setInstrument(false);
		}
		else if( !"-".equals(instrumentId) && !"-".equals(additionalId)) {
		instrumentReservation = new GeoToolReservation();
		instrumentReservation.setToolId(Long.parseLong(instrumentId));
		instrumentReservation.setInstrument(true);
		additionalReservation = new GeoToolReservation();
		additionalReservation.setToolId(Long.parseLong(additionalId));
		additionalReservation.setInstrument(false);
		}
		Long userId = workerRepo.findIdByUsername( getAuthUser() );
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ZonedDateTime startDay = format.parse(startDate).toInstant().atZone(ZoneId.of("Europe/Budapest"));
		ZonedDateTime endDay = format.parse(endDate).toInstant().atZone(ZoneId.of("Europe/Budapest"));
		if(isExchangeDates(startDay, endDay)) {
			ZonedDateTime temp;
			temp = startDay;
			startDay = endDay;
			endDay = temp;
		}
		endDay = endDay.plusMinutes( 60 * 23 + 59 );
		if(instrumentReservation != null) {
			instrumentReservation.setUserId(userId);
			instrumentReservation.setInstrument(true);
			instrumentReservation.setActive(false);
			instrumentReservation.setTakeAwayDate(startDay);
			instrumentReservation.setBringBackDate(endDay);
			reservationRepo.save(instrumentReservation);
		}
		if(additionalReservation != null) {
			additionalReservation.setUserId(userId);
			additionalReservation.setInstrument(false);
			additionalReservation.setActive(false);
			additionalReservation.setTakeAwayDate(startDay);
			additionalReservation.setBringBackDate(endDay);
			reservationRepo.save(additionalReservation);
		}
		
	}
	
	private boolean isExchangeDates(ZonedDateTime startDate, ZonedDateTime endDate) {
		
		if(endDate.toEpochSecond() - startDate.toEpochSecond() < 0) {
	
			return true;
		}
		return false;
	}
	
	private String getAuthUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
}
