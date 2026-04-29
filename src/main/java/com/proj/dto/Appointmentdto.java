package com.proj.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class Appointmentdto 
{
	
	
	 private Long salonservicesid;
	 private Long userid;
	 private LocalDate appointmentDate;   
	    private LocalTime appointmentTime;

	    private String status; 
}
