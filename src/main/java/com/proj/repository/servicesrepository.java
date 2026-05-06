package com.proj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proj.model.Services;

public interface servicesrepository extends JpaRepository<Services,Long>
{

	@Query("select coalesce(max(s.id), 0) from Services s")
	Long findMaxId();

}
