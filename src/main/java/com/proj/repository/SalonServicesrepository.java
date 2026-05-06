package com.proj.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proj.model.Salon;
import com.proj.model.Salon_Services;
import com.proj.model.Services;

public interface SalonServicesrepository extends JpaRepository<Salon_Services,Long> {

	List<Salon_Services> findBySalon(Salon salon);

	@Query("select ss from Salon_Services ss where ss.services.id = :serviceId order by ss.id desc")
	List<Salon_Services> findByServiceId(@Param("serviceId") Long serviceId);

	@Query("select coalesce(max(ss.id), 0) from Salon_Services ss")
	Long findMaxId();

}
