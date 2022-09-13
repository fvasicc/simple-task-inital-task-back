package com.dentist.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dentist.demo.entities.Appointment;
import com.dentist.demo.entities.Patient;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	
	@Query("select a from Appointment a where a.start between :startPoint and :endPoint order by a.start")
	List<Appointment> findAllByStartBetween(@Param("startPoint") LocalDateTime startPoint, 
											@Param("endPoint") LocalDateTime endPoint);
	
	@Query("select a from Appointment a where a.patient = :patient and a.start >= :currentDate")
	List<Appointment> findFutureAppointmentsForPatient(@Param("currentDate") LocalDateTime currentDate, 
													   @Param("patient") Patient patient);
	
	@Query("select a from Appointment a where a.start > :currentDate")
	List<Appointment> findAllFutureAppointments(@Param("currentDate") LocalDateTime currentDate);
	
}
