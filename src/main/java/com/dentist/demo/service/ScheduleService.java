package com.dentist.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dentist.demo.entities.Appointment;
import com.dentist.demo.entities.dto.AppointmentFreeDto;
import com.dentist.demo.repository.AppointmentRepository;
import com.dentist.demo.utils.Consts;

@Service
public class ScheduleService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public List<AppointmentFreeDto> getFreeTermsOnHourByDate(LocalDate date) {
		return getFreeTermsByDate(date, 60);
	}
	
	public List<AppointmentFreeDto> getFreeTermsOnHalfHourByDate(LocalDate date) {
		return getFreeTermsByDate(date, 30);
	}
	
	private List<AppointmentFreeDto> getFreeTermsByDate(LocalDate date, int appointmentLengthInMinutes) {
		LocalDateTime startDay = date.atTime(0, 0);
		LocalDateTime endDay = startDay.plusDays(1);
		List<Appointment> appointmentsList = appointmentRepository.findAllByStartBetween(startDay, endDay);
	
		LocalDateTime start = startDay.plusHours(Consts.SHIFT_START);
		LocalDateTime end = start.plusMinutes(appointmentLengthInMinutes);
		LocalDateTime endShift = startDay.plusHours(Consts.SHIFT_END);
		
		List<AppointmentFreeDto> freeAppointmentsList = new ArrayList<>();
		
		while (!end.equals(endShift.plusMinutes(Consts.APPOINTMEN_MINIMAL_LENGTH_IN_MINUTES))) {
//			boolean isOk = true;
			
//			if (appointmentsList != null && !appointmentsList.isEmpty()) {
//				for (Appointment appointment : appointmentsList)				
//					if ((appointment.getStart().isBefore(start) && start.isBefore(appointment.getEnd()))
//						|| (appointment.getStart().isBefore(end) && end.isBefore(appointment.getEnd()))
//						|| (start.isBefore(appointment.getStart()) && appointment.getStart().isBefore(end)) 
//						|| (start.isBefore(appointment.getEnd()) && appointment.getEnd().isBefore(end))
//						|| (appointment.getStart().isEqual(start) && appointment.getEnd().isEqual(end))) 
//						isOk = false;
//			}
			
			if (isAppointmetFree(appointmentsList, start, end)) 
				freeAppointmentsList.add(new AppointmentFreeDto(start, end));
			
			start = start.plusMinutes(Consts.APPOINTMEN_MINIMAL_LENGTH_IN_MINUTES);
			end = end.plusMinutes(Consts.APPOINTMEN_MINIMAL_LENGTH_IN_MINUTES);
		}
		
		return freeAppointmentsList;
	}
	
	public boolean isAppointmetFree(List<Appointment> appointmentsList, LocalDateTime start, LocalDateTime end) {
		if (appointmentsList != null && !appointmentsList.isEmpty()) {
			for (Appointment appointment : appointmentsList)				
				if ((appointment.getStart().isBefore(start) && start.isBefore(appointment.getEnd()))
					|| (appointment.getStart().isBefore(end) && end.isBefore(appointment.getEnd()))
					|| (start.isBefore(appointment.getStart()) && appointment.getStart().isBefore(end)) 
					|| (start.isBefore(appointment.getEnd()) && appointment.getEnd().isBefore(end))
					|| (appointment.getStart().isEqual(start) && appointment.getEnd().isEqual(end))) 
					return false;
		}
		return true;
	}
}
