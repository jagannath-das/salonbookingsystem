package com.proj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proj.dto.AuthResponse;
import com.proj.dto.logindto;
import com.proj.jwt.JwtUtil;
import com.proj.model.Appointment;
import com.proj.model.User;
import com.proj.repository.Userrepository;

@Service
public class Userservice 
{
@Autowired
private Userrepository repo;

@Autowired
private PasswordEncoder passwordencoder;

@Autowired
private JwtUtil jwtUtil;

private final Map<String, String> passwordResetOtps = new ConcurrentHashMap<>();

public ResponseEntity<String> add(User user) 
{
	if(repo.findByEmailid(user.getEmailid())!=null)
	{
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email have already registered");
	}
	 if(user.getRole() == null || user.getRole().isEmpty())
	    {
	        user.setRole(new ArrayList<>(List.of("ROLE_USER")));
	    }
	    
	String encode = passwordencoder.encode(user.getPassword());
	user.setPassword(encode);
	
	User save = repo.save(user);
	    
	
	if(save != null)
	{
	return ResponseEntity.ok("user is registered");
	}
	else
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user registration fails");
	}
}

public ResponseEntity<?> login(logindto log) 
{
	User user= repo.findByEmailid(log.getEmailid());
	   if(user == null)
	    {
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body("User not found");
	    }
	boolean matches = passwordencoder.matches(log.getPassword(), user.getPassword());
	if(!matches)
	{
		return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid Password");
	}
	 // ✅ GENERATE JWT TOKEN
    String token = jwtUtil.generateToken(user);
    
    
    AuthResponse response =
            new AuthResponse(token,user.getId(),user.getRole());

        return ResponseEntity.ok(response);

}
public Optional<User> getuserbyid(long id) {
	
	Optional<User> byId = repo.findById(id);
	return byId;
	
}



public ResponseEntity<String> updateuserbyid(long id, User user) {
	Optional<User> optional = repo.findById(id);
	
	if(optional.isPresent())
	{
		User oguser = optional.get();
	
		if (user.getFullname() != null && !user.getFullname().isBlank()) {
			oguser.setFullname(user.getFullname());
		}
		if (user.getEmailid() != null && !user.getEmailid().isBlank()) {
			User existing = repo.findByEmailid(user.getEmailid());
			if (existing != null && !existing.getId().equals(id)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email have already registered");
			}
			oguser.setEmailid(user.getEmailid());
		}
		if (user.getPhone() != null && !user.getPhone().isBlank()) {
			oguser.setPhone(user.getPhone());
		}
		if (user.getRole() != null) {
			oguser.setRole(new ArrayList<>(user.getRole()));
		}
		
		repo.save(oguser);
		 
		return ResponseEntity.ok("user is updated");
	}
	else
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not found");
	}
	
	
	
	
	
	
}

public ResponseEntity<String> deleteuserbyid(long id) {
	   if(!repo.existsById(id))
	    {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("User not found");
	    }
	repo.deleteById(id);
	return ResponseEntity.ok("user is deleted");
	
}



public List<Appointment> getallappointmentbyuserid(Long id) {
	
	User user = repo.findById(id).orElseThrow(()->new RuntimeException("user not found"));
	
	List<Appointment> appointments = user.getAppointments();
	return appointments;
}///get appointment from user

public ResponseEntity<String> changeRoleToSalonOwner(Long id){

    User user = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    user.setRole(new ArrayList<>(List.of("ROLE_USER", "ROLE_SALON")));

    repo.save(user);

    return ResponseEntity.ok("User promoted to SALON OWNER");
}

public ResponseEntity<String> sendPasswordOtp(String email){
	if (email == null || email.isBlank()) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
	}
	User user = repo.findByEmailid(email);
	if (user == null) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	}
	String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
	passwordResetOtps.put(email, otp);
	return ResponseEntity.ok("OTP sent: " + otp);
}

public ResponseEntity<String> verifyPasswordOtp(String email, String otp){
	if (email == null || email.isBlank() || otp == null || otp.isBlank()) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and OTP are required");
	}
	if (otp.equals(passwordResetOtps.get(email))) {
		return ResponseEntity.ok("OTP Verified");
	}
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
}

public ResponseEntity<String> resetPassword(String email, String password){
	if (email == null || email.isBlank() || password == null || password.isBlank()) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password are required");
	}
	if (!passwordResetOtps.containsKey(email)) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verify OTP first");
	}
	User user = repo.findByEmailid(email);
	if (user == null) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	}
	user.setPassword(passwordencoder.encode(password));
	repo.save(user);
	passwordResetOtps.remove(email);
	return ResponseEntity.ok("Password reset successful");
}



}
