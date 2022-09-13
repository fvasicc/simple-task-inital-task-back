package com.dentist.demo.utils;

import com.dentist.demo.entities.Appointment;
import com.dentist.demo.entities.Patient;

public class MailTemplates {
	
	public static String mailForPatientCreatedAppointment(Appointment appointment) {		
		return "Pozdrav, \n\n"
				+ "Uspesno ste kreirali rezeraviciju termina u zubarskoj ordinaciji.\n"
				+ "Informacije o terminu:\n"
				+ "pocetak: " + appointment.getStart() + "\n"
				+ "kraj: " + appointment.getEnd() + "\n"
				+ "opis: " + appointment.getDescription() + "\n\n"
				+ "Ovaj termin kao i sve vase predstojece termine mozete pronaci u sekciji za prikaz termina"
				+ " jednostavim unosom jmbg-a koji ste naveli prilikom kreiranja rezervacije.\n"
				+ "Takodje termin mozete otkazati do 24h pred pocetak samog termina.\n\n"
				+ "Srdacan pozdrav,\n Zubarska ordinacija";
	}
	
	public static String mailForDentistCreatedAppointment(Appointment appointment) {
		Patient patient = appointment.getPatient();
		return "Upravo je kreirana nova rezervacija termina.\n\n"
				+ "Informacije o terminu:\n"
				+ "pocetak: " + appointment.getStart() + "\n"
				+ "kraj: " + appointment.getEnd() + "\n"
				+ "opis: " + appointment.getDescription() + "\n\n"
				+ "Informacije o pacijentu:\n"
				+ "ime i prezime: " + patient.getName() + " " + patient.getSurname() + "\n"
				+ "broj telefona: " + patient.getPhoneNumber()
				+ "email: " + patient.getEmail();
	}
	
	public static String mailForPatientPostponedAppointment(Appointment appointment) {		
		return "Pozdrav, \n\n"
				+ "upravo je ponistena vasa rezervacija \n"
				+ "Informacije o odlozenom terminu:\n"
				+ "pocetak: " + appointment.getStart() + "\n"
				+ "kraj: " + appointment.getEnd() + "\n"
				+ "opis: " + appointment.getDescription() + "\n\n"
				+ "Nov termin mozete zakazati u sekciji za zakazivanje termina.\n\n"
				+ "Srdacan pozdrav,\n Zubarska ordinacija";
	}
	
	public static String mailForDentistPostponedAppointment(Appointment appointment) {		
		return "Upravo je ponistena rezervacija termina.\n\n"
				+ "Informacije o ponistenom terminu:\n"
				+ "pocetak: " + appointment.getStart() + "\n"
				+ "kraj: " + appointment.getEnd() + "\n"
				+ "opis: " + appointment.getDescription() + "\n\n";
	}

}
