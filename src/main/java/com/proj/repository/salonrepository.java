package com.proj.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proj.model.Salon;

@Repository
public interface salonrepository extends JpaRepository<Salon,Long>
{
ArrayList<Salon> findByLocation(String Location);


}
