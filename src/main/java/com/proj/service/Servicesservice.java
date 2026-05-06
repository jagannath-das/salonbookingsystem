package com.proj.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.model.Services;
import com.proj.repository.servicesrepository;
@Service
public class Servicesservice 
{
	
@Autowired
private servicesrepository repo;

public Services addnewservice(Services service) {
	if (service.getId() == null) {
		Long maxId = repo.findMaxId();
		service.setId((maxId == null ? 0L : maxId) + 1);
	}
	return repo.save(service);
	
}

public String deleteservicesbyid(long id) {
	repo.deleteById(id);
	return"service is deleted";
	
}

public String updateservice(Services service, long id)
{
Optional<Services> byId = repo.findById(id);	
if(byId.isPresent())
{
	Services services = byId.get();
	
	services.setName(service.getName());
	
	repo.save(services);
	return "service is updated";
}
else
{
	return "service is not available";
}
}


}
