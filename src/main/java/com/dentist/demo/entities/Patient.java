package com.dentist.demo.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the patient database table.
 * 
 */
@NoArgsConstructor
@Getter
@Setter
@Table(name = "patient")
@Entity
public class Patient  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "Jmbg", nullable = false)
	private String jmbg;

	@Column(name = "Email", nullable = false)
	private String email;

	@Column(name = "Name", nullable = false)
	private String name;
	
	@Column(name = "Surname", nullable = false)
	private String surname;

	@Column(name = "PhoneNumber", nullable = false)
	private String phoneNumber;

	//bi-directional many-to-one association to Appointment
	@JsonIgnore
	@OneToMany(mappedBy="patient")
	private List<Appointment> appointments;

	public Appointment addAppointment(Appointment appointment) {
		if (getAppointments() == null) 
			setAppointments(new ArrayList<Appointment>());
		
		getAppointments().add(appointment);
		appointment.setPatient(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setPatient(null);

		return appointment;
	}
	
}