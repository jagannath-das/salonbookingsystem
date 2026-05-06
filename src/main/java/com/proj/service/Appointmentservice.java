package com.proj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proj.dto.Appointmentdto;
import com.proj.model.Appointment;
import com.proj.model.Salon_Services;
import com.proj.model.User;
import com.proj.repository.Appointmentrepository;
import com.proj.repository.SalonServicesrepository;
import com.proj.repository.Userrepository;


@Service
public class Appointmentservice {

	@Autowired
	private Appointmentrepository repo;
	
	
	
	@Autowired
	private Userrepository userrepo;

	@Autowired
	private SalonServicesrepository ssrepo;
	
	
	
	//only dto from controller
	public ResponseEntity<String> bookappointmentui(Appointmentdto apdto)
	{
		if (apdto.getUserid() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID is required");
		}
		if (apdto.getSalonservicesid() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Select a salon service");
		}
		if (apdto.getAppointmentDate() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment date is required");
		}
		if (apdto.getAppointmentTime() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment time is required");
		}

		Appointment appointment =new Appointment();
		Optional<User> userOptional = userrepo.findById(apdto.getUserid());
		if (userOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		Salon_Services salonservice = ssrepo.findById(apdto.getSalonservicesid())
		        .orElseGet(() -> ssrepo.findByServiceId(apdto.getSalonservicesid())
		        		.stream()
		        		.findFirst()
		        		.orElse(null));

		if (salonservice == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salon service not found");
		}

		if (salonservice.getSalon() != null
				&& salonservice.getSalon().getOpeningtime() != null
				&& salonservice.getSalon().getClosingtime() != null
				&& (apdto.getAppointmentTime().isBefore(salonservice.getSalon().getOpeningtime())
						|| apdto.getAppointmentTime().isAfter(salonservice.getSalon().getClosingtime()))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Select a time within salon opening hours");
		}

		appointment.setUser(userOptional.get());
		appointment.setSalonservices(salonservice);
		appointment.setAppointmentDate(apdto.getAppointmentDate());
		appointment.setAppointmentTime(apdto.getAppointmentTime());
		appointment.setStatus(apdto.getStatus() == null || apdto.getStatus().isBlank() ? "BOOKED" : apdto.getStatus());
		if (appointment.getId() == null) {
			Long maxId = repo.findMaxId();
			appointment.setId((maxId == null ? 0L : maxId) + 1);
		}
		repo.save(appointment);
		return ResponseEntity.ok("your appointment is saved");
	}
	
	public String bookappointment(Appointment appointment) {
	 if (appointment.getId() == null) {
	 	Long maxId = repo.findMaxId();
	 	appointment.setId((maxId == null ? 0L : maxId) + 1);
	 }
	 Appointment save = repo.save(appointment);
	 
    return "appointment is booked";
	}
	
	
	public List<Appointment> getallappointmentbyuserid(Long id) {
		
	List<Appointment> byuserId = repo.findByUserId(id);
	return byuserId;
	}

	public ResponseEntity<String> deleteappointment(Long id) 
	{
		if (id == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment ID is required");
		}
		if (!repo.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
		}

     repo.deleteById(id);
     return ResponseEntity.ok("your booking is cancelled");
	}

	
}
