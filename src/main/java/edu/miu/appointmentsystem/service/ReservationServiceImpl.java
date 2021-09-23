package edu.miu.appointmentsystem.service;


import edu.miu.appointmentsystem.domain.Appointment;
import edu.miu.appointmentsystem.domain.Reservation;
import edu.miu.appointmentsystem.domain.User;
import edu.miu.appointmentsystem.domain.enums.AppointmentStatus;
import edu.miu.appointmentsystem.domain.enums.ReservationStatus;
import edu.miu.appointmentsystem.dto.UserAdapter;
import edu.miu.appointmentsystem.dto.UserDTO;
import edu.miu.appointmentsystem.repository.AppointmentRepository;
import edu.miu.appointmentsystem.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    private UserServiceImpl userServiceImpl ;


    @Override
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Reservation addReservation(Appointment appointment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = userServiceImpl.findByUsername(authentication.getName());
        UserAdapter adapter = new UserAdapter();
        User client = adapter.getUser(userDTO);
        Reservation reservation = new Reservation(appointment, client);
        if (appointment.getStatus() == AppointmentStatus.OPENED) {
            appointment.addReservation(reservation);
            appointmentRepository.save(appointment);
            return reservation ;
        }
        return null;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        if (reservation.getAppointment().getStatus() == AppointmentStatus.OPENED) {
            return reservationRepository.save(reservation);
        }
        return null;
    }

    @Override
    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public boolean approveReservation(Reservation reservation) {
        Appointment appointment = reservation.getAppointment() ;
        if (appointment.getStatus() == AppointmentStatus.OPENED) {
            reservation.setStatus(ReservationStatus.APPROVED);
            cancelAllReservation(reservation);
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

    private void cancelAllReservation(Reservation reservation) {
        reservation.getAppointment().getReservations()
                .stream()
                .filter(r -> r.getStatus() == ReservationStatus.PENDING)
                .forEach(r -> r.setStatus(ReservationStatus.CANCELED));
    }
    @Override
    public boolean cancelReservation(Reservation reservation) {
        if (reservation.getAppointment().getStatus() == AppointmentStatus.OPENED) {
            reservation.setStatus(ReservationStatus.CANCELED);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }


}
