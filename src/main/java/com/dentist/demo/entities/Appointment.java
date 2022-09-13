package com.dentist.demo.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * The persistent class for the appointment database table.
 * 
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name = "Description", nullable = false)
	private String description;

	@Column(name = "End", nullable = false)
	private LocalDateTime end;

	@Column(name = "Start", nullable = false)
	private LocalDateTime start;

	//bi-directional many-to-one association to Patient
	@ManyToOne
	private Patient patient;

}