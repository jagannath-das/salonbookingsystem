package com.proj.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proj.dto.salonservicesdto;
import com.proj.model.Salon_Services;
import com.proj.model.Services;
import com.proj.service.Salon_Servicesservice;

@RestController
public class Salon_Servicescontroller 
{
	@Autowired
private Salon_Servicesservice service;
@PostMapping("/salon/addsalonservices")// connect salon with services using model class
public String addsalonservice(@RequestBody Salon_Services services)
{
	return service.addsalonservice(services);
}



@PostMapping("/salon/addsalonservicesui")//connect salon with services using dto class//it works
public String addsalonserviceui(@RequestBody salonservicesdto servicedto)
{
	return service.addsalonserviceui(servicedto);
}

@GetMapping("/salon/getallservicesofsalon/salonid/{id}")
public List<Services> getallservicesofsalon(@PathVariable Long id){
List<Services> list = service.getallservicesofsalon(id);
return list;
}

@GetMapping("/salon/getsalonservices/salonid/{id}")
public List<Salon_Services> getsalonservices(@PathVariable Long id){
return service.getsalonservices(id);
}
}
