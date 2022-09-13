package com.dentist.demo.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dentist.demo.entities.Appointment;
import com.dentist.demo.entities.Patient;
import com.dentist.demo.entities.dto.AppointmentCreateDto;
import com.dentist.demo.entities.dto.PatientDto;
import com.dentist.demo.entities.mapper.AppointmendDisplayMapper;
import com.dentist.demo.repository.AppointmentRepository;
import com.dentist.demo.repository.PatientRepository;
import com.dentist.demo.utils.Consts;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	AppointmendDisplayMapper adm = new AppointmendDisplayMapper();
	
	public ResponseEntity<?> getAllApointments() {
		return new ResponseEntity<>(adm.toDtos(appointmentRepository.findAll()), HttpStatus.OK);
	}
	
	public ResponseEntity<?> getAllFutureAppointments() {
		return new ResponseEntity<>(adm.toDtos(appointmentRepository.findAllFutureAppointments(LocalDateTime.now())), HttpStatus.OK);
	}
	
	public List<Appointment> getAppointmentsForDate(LocalDate date) {
		LocalDateTime start = date.atTime(0, 0);
		LocalDateTime end = start.plusDays(1);
		
		return appointmentRepository.findAllByStartBetween(start, end);
	}
	
	public ResponseEntity<?> getAppointmentsByWeek(LocalDate date) {
		while (date.getDayOfWeek() != DayOfWeek.MONDAY) {
			date = date.minusDays(1);
		}
		
		LocalDateTime start = date.atTime(0, 0);
		LocalDateTime end = start.plusDays(7);
		
		return new ResponseEntity<>(adm.toDtos(appointmentRepository.findAllByStartBetween(start, end)), HttpStatus.OK);
	}
	
	public ResponseEntity<?> getFutureAppointmentsForPatient(String jmbg) {
		Patient patient = patientRepository.findByJmbg(jmbg);
		
		if(patient == null) 
			return new ResponseEntity<>("Patient with jmbg: " + jmbg + " doesn't exist", HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(adm.toDtos(appointmentRepository.findFutureAppointmentsForPatient(LocalDateTime.now(), patient)), HttpStatus.OK);
	}
	
	public ResponseEntity<?> createAppointment(AppointmentCreateDto appointmentDto) {
		LocalDateTime startDay = appointmentDto.getStart().toLocalDate().atTime(0, 0);
		LocalDateTime startShift = startDay.plusHours(Consts.SHIFT_START);
		LocalDateTime endShift = startDay.plusHours(Consts.SHIFT_END);
		
		if (appointmentDto.getStart().isBefore(startShift) || appointmentDto.getEnd().isAfter(endShift))
			return new ResponseEntity<>("Time is not in interval 9 - 17", HttpStatus.BAD_REQUEST);
		
		if (appointmentDto.getStart().isAfter(appointmentDto.getEnd()))
			return new ResponseEntity<>("Start after end", HttpStatus.BAD_REQUEST);
		
		Duration d = Duration.between(appointmentDto.getStart(), appointmentDto.getEnd());
		if (d.toMinutes() != 30 && d.toMinutes() != 60)
			return new ResponseEntity<>("Lenght wrong", HttpStatus.BAD_REQUEST);
		
		if (!scheduleService.isAppointmetFree(appointmentRepository.findAllByStartBetween(startDay, startDay.plusDays(1)), 
											appointmentDto.getStart(), appointmentDto.getEnd()))
			return new ResponseEntity<>("The appointment is taken", HttpStatus.BAD_REQUEST);
		
		Appointment appointment = new Appointment();
		appointment.setDescription(appointmentDto.getDescription());
		appointment.setStart(appointmentDto.getStart());
		appointment.setEnd(appointmentDto.getEnd());
		
		Patient patient = getPatientFromAppointmentDto(appointmentDto);
		appointment.setPatient(patient);
		appointment = appointmentRepository.save(appointment);
		patient.addAppointment(appointment);
		
//		mailService.sendMailForCreatedAppointment(appointment);
		
		return new ResponseEntity<>(adm.toDto(appointment), HttpStatus.OK);
	}
	
	private Patient getPatientFromAppointmentDto(AppointmentCreateDto appointmentDto) {
		Patient patient = patientRepository.findByJmbg(appointmentDto.getPatientInfo().getJmbg());
		
		if (patient != null) 
			return patient;
		
		patient = new Patient();
		PatientDto patientDto = appointmentDto.getPatientInfo();
		patient.setName(patientDto.getName());
		patient.setSurname(patientDto.getSurname());
		patient.setEmail(patientDto.getEmail());
		patient.setJmbg(patientDto.getJmbg());
		patient.setPhoneNumber(patientDto.getPhoneNumber());
		
		return patientRepository.save(patient);
	}

	public ResponseEntity<?> getAppointmentById(int id) {
		Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
		
		if (!appointmentOpt.isPresent())
			return new ResponseEntity<>("Appointment doesn't exist", HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(adm.toDto(appointmentOpt.get()), HttpStatus.OK);
		
	}
	
	public ResponseEntity<?> deleteAppointmentById(int id) {		
		Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
		
		if (!appointmentOpt.isPresent())
			return new ResponseEntity<>("Appointment doesn't exist", HttpStatus.NOT_FOUND);
		
		Duration duration = Duration.between(LocalDateTime.now(), appointmentOpt.get().getStart());
		if(duration.toHours() <= Consts.TIME_LIMIT_APPOINTMENT_DELETE_IN_HOURS)
			return new ResponseEntity<>("You can't postpone appointment", HttpStatus.BAD_REQUEST);
		
		Appointment appointment = appointmentOpt.get();
		appointment.getPatient().removeAppointment(appointment);
		appointmentRepository.delete(appointment);
		
//		mailService.sendMailForPostponedAppointment(appointment);
		
		return new ResponseEntity<>(appointment, HttpStatus.OK);
	}
}
