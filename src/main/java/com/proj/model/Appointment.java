package com.proj.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Appointment 
{
	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="salonservicesid")
	private Salon_Services salonservices;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@NotNull
	 private LocalDate appointmentDate; 
	@NotNull// ✅ FIXED
	    private LocalTime appointmentTime;

	    private String status; 

}
