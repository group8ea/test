package edu.miu.appointmentsystem.service;


import edu.miu.appointmentsystem.domain.*;
import java.util.List;
import java.util.Optional;


public interface AppointmentService {


	List<Appointment> getAppointments() ;

	Appointment getAppointmentById(Integer id) ;

	Appointment saveAppointment(Appointment appointment, User user) ;

	boolean deleteAppointment(Integer id, User user) ;

	List<Appointment> getAllAppointmentByUserId(Integer id) ;

	boolean cancelAppointment(Appointment appointment, User user) ;

	boolean closeAppointment(Appointment appointment, User user);

}
