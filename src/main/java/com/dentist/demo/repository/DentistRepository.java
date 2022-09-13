package com.dentist.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentist.demo.entities.Dentist;

public interface DentistRepository extends JpaRepository<Dentist, Integer> {

	Dentist findByUniqueCode(String uniqueCode);
}
