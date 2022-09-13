package com.dentist.demo.entities.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentFreeDto {
	
	private LocalDateTime start;
	
	private LocalDateTime end;
}
