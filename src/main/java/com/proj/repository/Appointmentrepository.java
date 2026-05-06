package com.proj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proj.model.Appointment;

public interface Appointmentrepository extends JpaRepository<Appointment,Long> {

List<Appointment> findByUserId(Long userid);

@Query("select coalesce(max(a.id), 0) from Appointment a")
Long findMaxId();

}
