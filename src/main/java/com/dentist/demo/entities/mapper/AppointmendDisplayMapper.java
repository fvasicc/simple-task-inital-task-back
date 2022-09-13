package com.dentist.demo.entities.mapper;

import java.util.ArrayList;
import java.util.List;

import com.dentist.demo.entities.Appointment;
import com.dentist.demo.entities.dto.AppointmentDisplayDto;

public class AppointmendDisplayMapper {

	public AppointmentDisplayDto toDto(Appointment appointment) {
		PatientMapper pm = new PatientMapper();
		return new AppointmentDisplayDto(appointment.getId(), 
										 appointment.getDescription(), 
										 appointment.getStart(), 
										 appointment.getEnd(),
										 pm.toDto(appointment.getPatient()));
	}
	
	public List<AppointmentDisplayDto> toDtos(List<Appointment> appointments) {
		List<AppointmentDisplayDto> dtosList = new ArrayList<>();
		
		for (Appointment app : appointments) {
			dtosList.add(toDto(app));
		}
		
		return dtosList;
	}
}
