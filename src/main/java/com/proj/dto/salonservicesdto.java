package com.proj.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data

public class salonservicesdto
{
	
private Long salonid;
private Long servicesid;
private LocalDate date;
private LocalTime time;
private Double cost;
private String imageUrl;
}
