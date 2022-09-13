package com.dentist.demo.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dentist.demo.entities.dto.AppointmentCreateDto;
import com.dentist.demo.service.AppointmentService;
import com.dentist.demo.service.ScheduleService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/appointments")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("/new")
	public ResponseEntity<?> createAppointment(@RequestBody AppointmentCreateDto appointmentDto) {
		return appointmentService.createAppointment(appointmentDto);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAppointment(@PathVariable int id){
		return appointmentService.deleteAppointmentById(id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAppointmentById(@PathVariable int id) {
		return appointmentService.getAppointmentById(id);
	}
	
	@GetMapping("/freeOnHour/{date}")
	public ResponseEntity<?> getFreeAppointmentByDateOnHour(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return new ResponseEntity<>(scheduleService.getFreeTermsOnHourByDate(date), HttpStatus.OK);
	}
	
	@GetMapping("/freeOnHalfHour/{date}")
	public ResponseEntity<?> getFreeAppointmentByDateOnHalfHour(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return new ResponseEntity<>(scheduleService.getFreeTermsOnHalfHourByDate(date), HttpStatus.OK);
	}
}
