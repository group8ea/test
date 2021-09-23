package edu.miu.appointmentsystem.service;


import edu.miu.appointmentsystem.domain.*;
import java.util.List;
import java.util.Optional;


public interface AppointmentService {


	List<Appointment> getAppointments() ;

	Appointment getAppointmentById(Integer id) ;

	Appointment addAppointment(Appointment appointment, Integer categoryId) ;

	Appointment updateAppointment(Appointment appointment) ;

	void deleteAppointment(Integer id) ;

	List<Appointment> getAllAppointmentByProviderId(Integer id) ;

	boolean cancelAppointment(Appointment appointment) ;

	void closeAppointment(Appointment appointment);

}
