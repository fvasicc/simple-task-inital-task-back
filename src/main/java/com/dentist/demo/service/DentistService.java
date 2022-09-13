package com.dentist.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dentist.demo.entities.Dentist;
import com.dentist.demo.repository.DentistRepository;

@Service
public class DentistService {

	@Autowired
	private DentistRepository dentistRepository;
	
	public ResponseEntity<?> checkDentistKey(String key) {
		
		Dentist dentist = dentistRepository.findByUniqueCode(key);
		
		if (dentist == null) 
			return new ResponseEntity<>("You are not dentist", HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(dentist, HttpStatus.OK);
	}
}
