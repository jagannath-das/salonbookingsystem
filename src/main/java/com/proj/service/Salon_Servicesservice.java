package com.proj.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.dto.salonservicesdto;
import com.proj.model.Salon;
import com.proj.model.Salon_Services;
import com.proj.model.Services;
import com.proj.repository.SalonServicesrepository;

import com.proj.repository.salonrepository;
import com.proj.repository.servicesrepository;
@Service
public class Salon_Servicesservice 
{
	@Autowired
private SalonServicesrepository repo;


	
	@Autowired
	private salonrepository salonrepo;
	
	@Autowired
	private servicesrepository servicerepo; 
	
	
	
	public String addsalonservice(Salon_Services service) {	
	repo.save(service);
		return"salonservice is added";
	}
	
	
	public String addsalonserviceui(salonservicesdto servicedto) 
	{	
		if (servicedto == null) {
			throw new IllegalArgumentException("Salon service details are required");
		}
		return saveSalonService(
				servicedto.getSalonid(),
				servicedto.getServicesid(),
				servicedto.getCost(),
				servicedto.getImageUrl());
		}

	public String addsalonserviceui(Map<String, Object> payload)
	{
		if (payload == null || payload.isEmpty()) {
			throw new IllegalArgumentException("Salon service details are required");
		}

		Long salonId = extractLong(payload, "salonid", "salonId");
		Long serviceId = extractLong(payload, "servicesid", "serviceId", "servicesId");
		Double cost = extractDouble(payload, "cost");
		String imageUrl = extractString(payload, "imageUrl");

		return saveSalonService(salonId, serviceId, cost, imageUrl);
	}

	private String saveSalonService(Long salonId, Long serviceId, Double cost, String imageUrl) {
		if (salonId == null) {
			throw new IllegalArgumentException("Salon id is required");
		}
		if (serviceId == null) {
			throw new IllegalArgumentException("Service id is required");
		}
		if (cost == null) {
			throw new IllegalArgumentException("Service cost is required");
		}
		if (cost < 0) {
			throw new IllegalArgumentException("Service cost must be non-negative");
		}

		Salon_Services service = new Salon_Services();
		Salon salon = salonrepo.findById(salonId)
				.orElseThrow(() -> new IllegalArgumentException("Salon id " + salonId + " not found"));
		Services salonService = servicerepo.findById(serviceId)
				.orElseThrow(() -> new IllegalArgumentException("Service id " + serviceId + " not found"));

		service.setSalon(salon);
		service.setServices(salonService);
		service.setCost(cost);
		service.setImageUrl(imageUrl);
		service.setDate(LocalDate.now());
		service.setTime(LocalTime.of(0, 0));
		if (service.getId() == null) {
			Long maxId = repo.findMaxId();
			service.setId((maxId == null ? 0L : maxId) + 1);
		}

		repo.save(service);
		return "service is saved";
	}

	private Long extractLong(Map<String, Object> payload, String... keys) {
		for (String key : keys) {
			Object value = payload.get(key);
			if (value instanceof Number number) {
				return number.longValue();
			}
			if (value instanceof String text && !text.isBlank()) {
				try {
					return Long.parseLong(text.trim());
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException(key + " must be a valid number");
				}
			}
		}
		return null;
	}

	private Double extractDouble(Map<String, Object> payload, String... keys) {
		for (String key : keys) {
			Object value = payload.get(key);
			if (value instanceof Number number) {
				return number.doubleValue();
			}
			if (value instanceof String text && !text.isBlank()) {
				try {
					return Double.parseDouble(text.trim());
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException(key + " must be a valid number");
				}
			}
		}
		return null;
	}

	private String extractString(Map<String, Object> payload, String... keys) {
		for (String key : keys) {
			Object value = payload.get(key);
			if (value instanceof String text) {
				String trimmed = text.trim();
				return trimmed.isEmpty() ? null : trimmed;
			}
		}
		return null;
	}


	public List<Services> getallservicesofsalon(Long id) 
	{
	Salon salon = salonrepo.findById(id).orElseThrow(()->new RuntimeException());
	List<Salon_Services> bySalonid = repo.findBySalon(salon);
	ArrayList<Services> l1=new ArrayList();
   for(Salon_Services salonservices:bySalonid)
   {
	   Services services = salonservices.getServices();
	   
	   l1.add(services);
   }
   return l1;
	}

	public List<Salon_Services> getsalonservices(Long id)
	{
		Salon salon = salonrepo.findById(id).orElseThrow(()->new RuntimeException("salon not found"));
		return repo.findBySalon(salon);
	}
}
