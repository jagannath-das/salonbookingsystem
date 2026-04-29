package com.proj.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.proj.dto.logindto;
import com.proj.model.Appointment;
import com.proj.model.User;
import com.proj.service.Userservice;

@RestController
@CrossOrigin(origins="*")
public class usercontroller 
{
 @Autowired	
private Userservice service;

@PostMapping("/auth/registeruser")
public ResponseEntity<String> adduser(@RequestBody User user){

System.out.println("REGISTER API CALLED"); // ✅ ADD THIS

ResponseEntity result = service.add(user);
	return result;
}


@PostMapping("/auth/login")
public ResponseEntity<?> userlogin(@RequestBody logindto log)
{
	ResponseEntity result = service.login(log);
	return result;
}

@GetMapping("/user/{id}")
public Optional<User> getuserbyid(@PathVariable long id)
{
	Optional<User> optional = service.getuserbyid(id);
	return optional;
}

@PutMapping("/user/updateuser/{id}")
public ResponseEntity<String> updateuserbyid(@PathVariable long id,@RequestBody User user)
{   
	
	ResponseEntity<String> updateuserbyid = service.updateuserbyid(id,user);
	return updateuserbyid;
	
}

@DeleteMapping("/user/deleteuser/{id}")
public ResponseEntity<String> deleteuserbyid(@PathVariable long id)
{
	ResponseEntity<String> deleteuserbyid = service.deleteuserbyid(id);
	return deleteuserbyid;
}

@PostMapping("/forgot-password")
public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request)
{
	return service.sendPasswordOtp(request.get("email"));
}

@PostMapping("/verify-otp")
public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request)
{
	return service.verifyPasswordOtp(request.get("email"), request.get("otp"));
}

@PostMapping("/reset-password")
public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request)
{
	return service.resetPassword(request.get("email"), request.get("password"));
}

//@PostMapping("/getappointment/userid/{id}")
//public List<Appointment> getallappointmentofuser(@PathVariable Long id)
//{
//	 List<Appointment> list = service.getallappointmentbyuserid(id);
//	 return list;
//}



}
