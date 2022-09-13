package com.dentist.demo.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dentist.demo.entities.mapper.AppointmendDisplayMapper;
import com.dentist.demo.service.AppointmentService;
import com.dentist.demo.service.DentistService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/dentist")
public class DentistController {

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private DentistService dentistService;
	
	AppointmendDisplayMapper adm = new AppointmendDisplayMapper();
	
	@GetMapping("/appointments")
	public ResponseEntity<?> getAllAppointments() {
		return appointmentService.getAllApointments();
	}
	
	@GetMapping("/appointments/today")
	public ResponseEntity<?> getTodayAppointments() {
		return new ResponseEntity<>(adm.toDtos(appointmentService.getAppointmentsForDate(LocalDate.now())), HttpStatus.OK);
	}
	
	@GetMapping("/appointments/{date}")
	public ResponseEntity<?> getAppointmentsByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return new ResponseEntity<>(adm.toDtos(appointmentService.getAppointmentsForDate(date)), HttpStatus.OK);
	}
	
	@GetMapping("/appointments/week/current")
	public ResponseEntity<?> getAppointmentsByWeek() {
		return appointmentService.getAppointmentsByWeek(LocalDate.now());
	}
	
	@GetMapping("/appointments/week/{date}")
	public ResponseEntity<?> getAppointmentsByWeek(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return appointmentService.getAppointmentsByWeek(date);
	}
	
	@GetMapping("checkDentist/{key}")
	public ResponseEntity<?> checkDentist(@PathVariable String key) {
		return dentistService.checkDentistKey(key);
	}
}
