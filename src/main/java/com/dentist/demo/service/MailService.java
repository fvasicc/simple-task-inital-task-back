package com.dentist.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dentist.demo.entities.Appointment;
import com.dentist.demo.repository.DentistRepository;
import com.dentist.demo.utils.MailTemplates;

@Service
public class MailService {
	
	@Autowired
	JavaMailSender jms;
	
	@Value("${spring.mail.username}")
	private String sender;
	
	@Autowired
	private DentistRepository dentistRepository;
	
	public void sendMailForCreatedAppointment(Appointment appointment) {
		sendMailToPatientForCreatedAppointment(appointment);
		sendMailToDentistForCreatedAppointment(appointment);
	}
	
	private void sendMailToPatientForCreatedAppointment(Appointment appointment) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(sender);
		message.setTo(appointment.getPatient().getEmail());
		message.setSubject("Uspesno kreirana rezervacija termina");
		message.setText(MailTemplates.mailForPatientCreatedAppointment(appointment));
		jms.send(message);
	}
	
	private void sendMailToDentistForCreatedAppointment(Appointment appointment) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(sender);
		message.setTo(dentistRepository.findAll().get(0).getEmail());
		message.setSubject("Kreirana rezervacija");
		message.setText(MailTemplates.mailForDentistCreatedAppointment(appointment));
		jms.send(message);
	}
	
	public void sendMailForPostponedAppointment(Appointment appointment) {
		sendMailToPatientForPosponedAppointment(appointment);
		sendMailToDentistForPostponedAppointment(appointment);
	}
	
	private void sendMailToPatientForPosponedAppointment(Appointment appointment) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(sender);
		message.setTo(appointment.getPatient().getEmail());
		message.setSubject("Otkazana rezervacija termina");
		message.setText(MailTemplates.mailForPatientPostponedAppointment(appointment));
		jms.send(message);
	}
	
	private void sendMailToDentistForPostponedAppointment(Appointment appointment) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(sender);
		message.setTo(dentistRepository.findAll().get(0).getEmail());
		message.setSubject("Otkazana rezervacija");
		message.setText(MailTemplates.mailForDentistPostponedAppointment(appointment));
		jms.send(message);
	}
}
