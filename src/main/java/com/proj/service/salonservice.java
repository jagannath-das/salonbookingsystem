package com.proj.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.proj.model.Salon;
import com.proj.model.User;
import com.proj.repository.salonrepository;

@Service
public class salonservice {

	@Autowired
	private salonrepository repo;

	public Salon addsalon(Salon salon) {
		
		 return repo.save(salon);
		
	}

	public ArrayList<Salon> getsalonbylocation(String location) {
		
		ArrayList<Salon> byId = repo.findByLocation(location);
		return byId;
		
	}

	public List<Salon> getallsalons() {
		return repo.findAll();
	}


	 public String updateSalonById(long id, Salon salon) {

	        Optional<Salon> optional = repo.findById(id);

	        if(optional.isPresent())
	        {
	            Salon existing = optional.get();

	            existing.setName(salon.getName());
	            existing.setEmail(salon.getEmail());
	            existing.setPhone(salon.getPhone());
	            existing.setLocation(salon.getLocation());
	            existing.setOpeningtime(salon.getOpeningtime());
	            existing.setClosingtime(salon.getClosingtime());
	            existing.setImageUrl(salon.getImageUrl());
	            

	            repo.save(existing);

	            return "Salon updated successfully";
	        }

	        return "Salon not found";
	    }

	public String deletesalonbyid(long id) {
		repo.deleteById(id);
		return "salon is deleted";
		
	}

	

}
