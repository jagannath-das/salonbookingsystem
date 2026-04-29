package com.proj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.dto.Appointmentdto;
import com.proj.model.Appointment;
import com.proj.service.Appointmentservice;

@RequestMapping("/user")
@RestController
public class Appointmentcontroller 
{
@Autowired
private Appointmentservice bookingservice;


@PostMapping("/bookappointment")   //take a appointment by connecting salonservice with appointment and users using 
                                     //model class
public String bookappontment(@RequestBody Appointment appointment)
{
	String string = bookingservice.bookappointment(appointment);
	return string;
}




@PostMapping("/bookappointmentui") //take a appointment by connecting salonservice with appointment and users using 
//dto class
public ResponseEntity<String> bookappontmentui(@RequestBody Appointmentdto apdto)
{
	return bookingservice.bookappointmentui(apdto);
}

@GetMapping("/getappointment/userid/{id}") // get all appointments of user from userid 
public List<Appointment> getallappointmentofuser(@PathVariable Long id)
{
	 List<Appointment> list = bookingservice.getallappointmentbyuserid(id);
	 return list;
}


@DeleteMapping("/deleteappointment/{id}")//delete the appointment
public ResponseEntity<String> deleteappontment(@PathVariable Long id)
{
	return bookingservice.deleteappointment(id);
}
}
