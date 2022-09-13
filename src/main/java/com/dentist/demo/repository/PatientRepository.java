package com.dentist.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentist.demo.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
	
	Patient findByJmbg(String jmbg);
}
