package com.proj.model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Salon 
{
	@Id
	private Long id;
	@NotNull
	private String name;
	@NotNull
	@Email
	private String email;
	@NotNull
	private String phone;
	@NotNull
	private String location;
	
	
	
	private String imageUrl;
	
	
	private LocalTime  openingtime;
	
	private LocalTime  closingtime;
	
}
