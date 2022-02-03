package com.geolink3d.toolsregistry.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.geolink3d.toolsregistry.dao.GeoToolReservationDAO;
import com.geolink3d.toolsregistry.model.GeoAdditional;
import com.geolink3d.toolsregistry.model.GeoInstrument;
import com.geolink3d.toolsregistry.model.GeoToolReservation;
import com.geolink3d.toolsregistry.model.GeoWorker;
import com.geolink3d.toolsregistry.repository.GeoAdditionalRepository;
import com.geolink3d.toolsregistry.repository.GeoInstrumentRepository;
import com.geolink3d.toolsregistry.repository.GeoToolReservationRepository;
import com.geolink3d.toolsregistry.repository.GeoWorkerRepository;

@Service
public class GeoToolReservationService {

	
	@Autowired
	private GeoToolReservationRepository reservationRepo;
	@Autowired
	private GeoWorkerRepository workerRepo;
	@Autowired
	private GeoInstrumentRepository instrumentRepo;
	@Autowired
	private GeoAdditionalRepository additionalRepo;
	
	
	public String getActualDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date(System.currentTimeMillis()));
	}
	
	public String getAuthUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	public boolean isChosenGeoToolReservation(String instrumentId, String additionalId) {
	
		try {
			Long.parseLong(instrumentId);
			return true;
		} catch (NumberFormatException e) {
			
			try {
				Long.parseLong(additionalId);
			} catch (NumberFormatException f) {
				return false;
			}
		
		}
		
		return true;
	}
	
	public boolean isValidGeoToolReservationDates(String startDate, String endDate) {
		
		if(startDate.isEmpty() || endDate.isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public boolean isRegistableGeoToolReservation(String instrumentId, String additionalId, String startDate, String endDate) 
			throws ParseException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ZonedDateTime startDay = format.parse(startDate).toInstant().atZone(ZoneId.of("Europe/Budapest"));
		ZonedDateTime endDay = format.parse(endDate).toInstant().atZone(ZoneId.of("Europe/Budapest"));
		
		return isRegistableGeoInstrumentReservation(instrumentId, startDay, endDay) &&
			   isRegistableGeoAdditionReservation(additionalId, startDay, endDay);
	}
	
	private boolean isRegistableGeoInstrumentReservation(String instrumentId, ZonedDateTime startDay, ZonedDateTime endDay) {
		
		Long id;
		try {
		id = Long.parseLong(instrumentId);
		} catch (NumberFormatException e) {
			return true;
		}

		List<GeoToolReservation> instrumentReservations = reservationRepo.findGeoInstrumentReservationsByToolId(id);
		
		if(instrumentReservations.isEmpty()) {
			return true;
		}
		
		Long startDate = startDay.toEpochSecond();
		Long endDate = endDay.plusMinutes( 60 * 23 + 59 ).toEpochSecond();
		Long firstTakeAwayDate = instrumentReservations.get(0).getTakeAwayDate().toEpochSecond();
		Long lastBringBackDate = instrumentReservations
									.get( instrumentReservations.size() - 1 )
									.getBringBackDate()
									.toEpochSecond();
		
		if(startDate < firstTakeAwayDate && endDate < firstTakeAwayDate) {
			return true;
		}
		
		for(int i = 1; i < instrumentReservations.size(); i++) {
			
			if(instrumentReservations.get( i - 1 ).getBringBackDate().toEpochSecond() < startDate &&
				startDate < instrumentReservations.get(i).getTakeAwayDate().toEpochSecond() &&
				instrumentReservations.get( i - 1 ).getBringBackDate().toEpochSecond() < endDate &&
				endDate < instrumentReservations.get(i).getTakeAwayDate().toEpochSecond()) {
				return true;
			}	
		}
			
		if(lastBringBackDate < startDate && lastBringBackDate < endDate) {
			return true;
		}
		
		return false;
	}
	
	private boolean isRegistableGeoAdditionReservation(String additionalId, ZonedDateTime startDay, ZonedDateTime endDay) {
	
		Long id;
		try {
			id = Long.parseLong(additionalId);
		} catch (NumberFormatException e) {
			return true;
		}
	
		List<GeoToolReservation> additionalReservations = reservationRepo.findGeoAdditionalReservationsByToolId(id);
		
		if(additionalReservations.isEmpty()) {
			return true;
		}
		
		Long startDate = startDay.toEpochSecond();
		Long endDate = endDay.plusMinutes( 60 * 23 + 59 ).toEpochSecond();	
		Long firstTakeAwayDate = additionalReservations.get(0).getTakeAwayDate().toEpochSecond();
		Long lastBringBackDate = additionalReservations
									.get( additionalReservations.size() - 1 )
									.getBringBackDate()
									.toEpochSecond();
		
		if(startDate < firstTakeAwayDate && endDate < firstTakeAwayDate) {
			return true;
		}
		
		for(int i = 1; i < additionalReservations.size(); i++) {
			
			if(additionalReservations.get( i - 1 ).getBringBackDate().toEpochSecond() < startDate &&
				startDate < additionalReservations.get(i).getTakeAwayDate().toEpochSecond() &&
				additionalReservations.get( i - 1 ).getBringBackDate().toEpochSecond() < endDate &&
				endDate < additionalReservations.get(i).getTakeAwayDate().toEpochSecond()) {
				return true;
			}	
		}
		
		if(lastBringBackDate < startDate && lastBringBackDate < endDate) {
			return true;
		}
		
		return false;
	}
	
	
	public List<GeoToolReservationDAO> findAllGeoReservationAsDAO(){
		
		List<GeoToolReservation> reservations = reservationRepo.findAll();
		List<GeoToolReservationDAO> reservationDAOStore = new ArrayList<>();
		
		for (GeoToolReservation reservation : reservations) {
			GeoToolReservationDAO dao = new GeoToolReservationDAO();
			dao.setId(reservation.getId());
			dao.setUserId(reservation.getUserId());
			GeoInstrument instrument;
			GeoAdditional additional;
			if(reservation.isInstrument()) {
				instrument  = instrumentRepo.findById(reservation.getToolId()).get();
				dao.setToolName(instrument.getName());
			}
			else {
				additional = additionalRepo.findById(reservation.getToolId()).get();
				dao.setToolName(additional.getName());
			}
			GeoWorker user = workerRepo.findById(reservation.getUserId()).get();
			dao.setUserName(user.getLastname() + " " + user.getFirstname());
			dao.setTakeAwayDate(reservation.getTakeAwayDate());
			dao.setBringBackDate(reservation.getBringBackDate());
			dao.setActive(reservation.isActive());
			reservationDAOStore.add(dao);
		}
		
		return reservationDAOStore;
	}
	
	public void saveGeoToolReservation(String userName, String instrumentId, String additionalId, String startDate, String endDate) 
			throws ParseException {
		
		GeoToolReservation instrumentReservation = null;
		GeoToolReservation additionalReservation = null;
		boolean isParseableInstrumentId;
		Long id;
		
		try {
			id = Long.parseLong(instrumentId);
			instrumentReservation = new GeoToolReservation();
			instrumentReservation.setToolId(id);
			instrumentReservation.setInstrument(true);
			isParseableInstrumentId = true;
		} catch (NumberFormatException e) {
			id = Long.parseLong(additionalId);
			additionalReservation = new GeoToolReservation();
			additionalReservation.setToolId(id);
			additionalReservation.setInstrument(false);
			isParseableInstrumentId = false;
		}
		
		if(isParseableInstrumentId) {
			
			try {
				id = Long.parseLong(additionalId);
				additionalReservation = new GeoToolReservation();
				additionalReservation.setToolId(id);
				additionalReservation.setInstrument(false);
			} catch (NumberFormatException e) {
			}
				
		}
	
		Long userId = workerRepo.findIdByUsername(userName);
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
	
	public void addReservationTextToGeoTool(String userName, String instrumentId, String additionalId, String startDate, String endDate) {
		
	
		try {
			
			Long id = Long.parseLong(instrumentId);
			GeoInstrument instrument = instrumentRepo.findById(id).get();

			if(instrument.getComment() == null || instrument.getComment().isEmpty()) {
				instrument.setComment( createReservationComment(userName, startDate, endDate) );
			}
			else {
				instrument.setComment( instrument.getComment() + "\n" + createReservationComment(userName, startDate, endDate) );
			}
			
			instrumentRepo.save(instrument);
			
		} catch (NumberFormatException e) {
			
		}
					
		try {
			Long id = Long.parseLong(additionalId);
			GeoAdditional additional = additionalRepo.findById(id).get();
			
			if(additional.getComment() == null || additional.getComment().isEmpty()) {
				additional.setComment(createReservationComment(userName, startDate, endDate));
			}
			else {
				additional.setComment( additional.getComment() + "\n" + createReservationComment(userName, startDate, endDate) );
			}
			additionalRepo.save(additional);
		} catch (Exception e) {
			
		}
		
	}
	
	private String createReservationComment(String userName, String startDate, String endDate) {
		String userFirstName = workerRepo.findByUsername(userName).getFirstname();
		String userLastName = workerRepo.findByUsername(userName).getLastname();
		return userLastName + " " + userFirstName + " előjegyezte az eszközt " + startDate + " - " + endDate + " időszakra.";
	}
	
	private String convertReservationComment(String comment, String userName, 
			String startDate, String endDate) {
		
		String[] commentComponents = comment.split("\n");
		
		if(commentComponents.length == 1) {
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < commentComponents.length; i++) {
			if(commentComponents[i].equals(createReservationComment(userName, startDate, endDate))) {
				continue;
			}
			builder.append(commentComponents[i]).append("\n");
		}
		
		builder.setLength(builder.length() - 1);
		
		return builder.toString();
	}
	
	public void deleteReservation(Long id) {
		
		GeoToolReservation reservation = reservationRepo.findById(id).get();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		if(reservation.isInstrument()) {
			GeoInstrument instrument = instrumentRepo.findById(reservation.getToolId()).get();
			String comment = instrument.getComment();
			instrument.setComment(convertReservationComment(comment, getAuthUser(),
					reservation.getTakeAwayDate().format(formatter), reservation.getBringBackDate().format(formatter)));
			instrumentRepo.save(instrument);
		}
		else {
			GeoAdditional additional = additionalRepo.findById(reservation.getToolId()).get();
			String comment = additional.getComment();
			additional.setComment(convertReservationComment(comment, getAuthUser(),
					reservation.getTakeAwayDate().format(formatter), reservation.getBringBackDate().format(formatter)));
			additionalRepo.save(additional);
		}
		
		reservationRepo.deleteById(id);
	}
	
	private boolean isExchangeDates(ZonedDateTime startDate, ZonedDateTime endDate) {
		
		if(endDate.toEpochSecond() - startDate.toEpochSecond() < 0) {
	
			return true;
		}
		return false;
	}
	
}
