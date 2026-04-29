package com.proj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.proj.model.User;
import com.proj.service.Adminservice;
import com.proj.service.Userservice;


@RestController
@RequestMapping("/admin")
public class Admincontroller 
{
@Autowired	
private Adminservice service;	

@Autowired
private Userservice userservice;
	
@GetMapping("/getallusers")
public List<User> getallusers()
{
	return service.getallusers();
}

@DeleteMapping("/deleteuser/{id}")
public  ResponseEntity deleteuserbyid(@PathVariable Long id)
{
 return service.deletebyid(id);	
  
}

// ✅ Change USER → SALON OWNER
@PutMapping("/makeSalonOwner/{id}")
public ResponseEntity<String> makeSalonOwner(@PathVariable Long id){
    return userservice.changeRoleToSalonOwner(id);
}
}
