package com.proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proj.model.Services;
import com.proj.service.Servicesservice;

@RestController
public class Servicescontroller 
{
	@Autowired
    private Servicesservice serv;
	@PostMapping("/salon/addservices")// add services of salon
	public Services addservices(@RequestBody Services service)
	{
		Services addnewservice = serv.addnewservice(service);
		return addnewservice;
	}
	
	@PutMapping("/salon/updateservices/{id}")// update details of services
	public String updateservice(@PathVariable long id,@RequestBody Services service)
	{
		String updateservice = serv.updateservice(service,id);
		return updateservice;
	}
																																																	
	@DeleteMapping("/salon/deleteservices/{id}")// delete services
	public String deleteservices(@PathVariable long id)
	{
		String deleteservicesbyid = serv.deleteservicesbyid(id);
		return deleteservicesbyid;
	}
}
