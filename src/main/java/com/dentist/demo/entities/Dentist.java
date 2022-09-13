package com.dentist.demo.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the dentist database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "dentist")
@Entity
public class Dentist  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "Jmbg", nullable = false)
	private String jmbg;

	@Column(name = "Email", nullable = false)
	private String email;

	@Column(name = "Name", nullable = false)
	private String name;

	@Column(name = "PhoneNumber", nullable = false)
	private String phoneNumber;

	@Column(name = "Surname", nullable = false)
	private String surname;

	@Column(name = "UniqueCode", nullable = false)
	private String uniqueCode;

}