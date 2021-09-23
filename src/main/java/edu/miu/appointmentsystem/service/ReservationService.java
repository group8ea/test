package edu.miu.appointmentsystem.service;


import edu.miu.appointmentsystem.domain.User;
import edu.miu.appointmentsystem.domain.Appointment;
import edu.miu.appointmentsystem.domain.Reservation;
import edu.miu.appointmentsystem.repository.ReservationRepository;
import edu.miu.appointmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


public interface ReservationService {

	List<Reservation> getReservations() ;

	Reservation getReservationById(Integer id) ;

	Reservation addReservation(Appointment appointment) ;

	Reservation updateReservation(Reservation reservation) ;

	void deleteReservation(Integer id) ;

	boolean approveReservation(Reservation reservation);

	boolean cancelReservation(Reservation reservation);





}
