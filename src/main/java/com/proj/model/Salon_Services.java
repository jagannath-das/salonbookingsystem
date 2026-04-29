package com.proj.model;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Salon_Services 
{
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;

@ManyToOne
@JoinColumn(name="salonid")
private Salon salon;

@ManyToOne
@JoinColumn(name="servicesid")
private Services services;

private String imageUrl;

@NotNull
private Double cost;

@NotNull
private LocalDate date;

@NotNull
private LocalTime time;

}
