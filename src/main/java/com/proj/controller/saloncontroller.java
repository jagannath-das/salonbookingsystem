package com.proj.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.proj.model.Salon;
import com.proj.model.User;
import com.proj.service.salonservice;
import com.proj.service.Userservice;

@RestController
public class saloncontroller 
{
	@Autowired	
	private salonservice service;


	@PostMapping("/salon/addsalon")
	public Salon adduser(@RequestBody Salon salon){
	    return service.addsalon(salon);
	}
	

	@GetMapping("/salon/findsalon/{location}")//find salon by location
	public ArrayList<Salon> getsalonbylocation(@PathVariable String location)
	{
		ArrayList<Salon> optional = service.getsalonbylocation(location);
		return optional;
	}

	@GetMapping("/salon/getallsalons")
	public List<Salon> getallsalons()
	{
		return service.getallsalons();
	}

	@PutMapping("/salon/updatesalon/{id}")// update details of salon from salon id
	public String updatesalonbyid(@PathVariable long id,@RequestBody Salon salon)
	{   
		
		String byId = service.updateSalonById(id,salon);
		return byId;
		
	}

	@DeleteMapping("/salon/deletesalon/{id}")// delete salon
	public String deletesalonbyid(@PathVariable long id)
	{
		String deletesalonbyid = service.deletesalonbyid(id);
		return deletesalonbyid;
	}

	
}
