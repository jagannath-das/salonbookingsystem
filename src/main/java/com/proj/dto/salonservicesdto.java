package com.proj.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data

public class salonservicesdto
{
	
@JsonAlias({"salonId"})
private Long salonid;
@JsonAlias({"serviceId", "servicesId"})
private Long servicesid;
private LocalDate date;
private LocalTime time;
private Double cost;
private String imageUrl;
}
