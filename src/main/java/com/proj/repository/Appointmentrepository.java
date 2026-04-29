package com.proj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.model.Appointment;

public interface Appointmentrepository extends JpaRepository<Appointment,Long> {

List<Appointment> findByUserId(Long userid);

}
