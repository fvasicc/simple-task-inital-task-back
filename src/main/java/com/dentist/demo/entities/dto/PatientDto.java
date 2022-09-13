package com.dentist.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
	
	private String name;
	
	private String surname;
	
	private String jmbg;
	
	private String email;
	
	private String phoneNumber;

}
