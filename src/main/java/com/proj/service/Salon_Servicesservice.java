package com.proj.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

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
		
		Salon_Services service =new Salon_Services();
		  Salon byId = salonrepo.findById(servicedto.getSalonid()).orElseThrow(()->new RuntimeException("salon not found"));
		 Services ser= servicerepo.findById(servicedto.getServicesid()).orElseThrow(()->new RuntimeException("services not found"));
		 
		 
		 service.setSalon(byId);
		 service.setServices(ser);
		 service.setCost(servicedto.getCost());
		 service.setImageUrl(servicedto.getImageUrl());
		 service.setDate(servicedto.getDate() != null ? servicedto.getDate() : LocalDate.now());
		 service.setTime(servicedto.getTime() != null ? servicedto.getTime() : LocalTime.of(0, 0));
		
		 
		 repo.save(service);
		 return "service is saved";
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
