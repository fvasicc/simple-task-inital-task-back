package com.dentist.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dentist.demo.service.AppointmentService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/patient")
public class PatientController {

	@Autowired
	private AppointmentService appointmentService;
	
	@GetMapping("/appointments/{jmbg}")
	public ResponseEntity<?> getPatientsAppointments(@PathVariable String jmbg) {
		return appointmentService.getFutureAppointmentsForPatient(jmbg);
	}
}
