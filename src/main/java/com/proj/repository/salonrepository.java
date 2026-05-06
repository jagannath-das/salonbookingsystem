package com.proj.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proj.model.Salon;

@Repository
public interface salonrepository extends JpaRepository<Salon,Long>
{
ArrayList<Salon> findByLocation(String Location);

@Query("select coalesce(max(s.id), 0) from Salon s")
Long findMaxId();


}
