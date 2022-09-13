package com.dentist.demo.entities.mapper;

import java.util.ArrayList;
import java.util.List;

import com.dentist.demo.entities.Patient;
import com.dentist.demo.entities.dto.PatientDto;

public class PatientMapper {
	
	public PatientDto toDto(Patient patient) {
		return new PatientDto(patient.getName(), patient.getSurname(), patient.getJmbg(), patient.getEmail(), patient.getPhoneNumber());
	}
	
	public List<PatientDto> toDtos(List<Patient> patients) {
		List<PatientDto> dtosList = new ArrayList<>();
		
		for (Patient patient : patients) {
			dtosList.add(toDto(patient));
		}
		
		return dtosList;
	}

}
