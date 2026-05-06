package com.proj.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Locale;

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
		if (salon == null) {
			throw new IllegalArgumentException("Salon details are required");
		}

		String name = normalize(salon.getName());
		String email = normalize(salon.getEmail());
		String phone = normalize(salon.getPhone());
		String location = normalize(salon.getLocation());

		if (name == null || email == null || phone == null || location == null
				|| salon.getOpeningtime() == null || salon.getClosingtime() == null) {
			throw new IllegalArgumentException("Salon name, email, phone, location, opening time and closing time are required");
		}

		salon.setName(name);
		salon.setEmail(email.toLowerCase(Locale.ROOT));
		salon.setPhone(phone);
		salon.setLocation(location);
		salon.setImageUrl(normalize(salon.getImageUrl()));

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

	            existing.setName(normalize(salon.getName()));
	            existing.setEmail(normalize(salon.getEmail()));
	            existing.setPhone(normalize(salon.getPhone()));
	            existing.setLocation(normalize(salon.getLocation()));
	            existing.setOpeningtime(salon.getOpeningtime());
	            existing.setClosingtime(salon.getClosingtime());
	            existing.setImageUrl(normalize(salon.getImageUrl()));
	            

	            repo.save(existing);

	            return "Salon updated successfully";
	        }

	        return "Salon not found";
	    }

	public String deletesalonbyid(long id) {
		repo.deleteById(id);
		return "salon is deleted";
		
	}

	private String normalize(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}

	

}
