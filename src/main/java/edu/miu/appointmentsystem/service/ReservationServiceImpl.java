package edu.miu.appointmentsystem.service;


import edu.miu.appointmentsystem.domain.Appointment;
import edu.miu.appointmentsystem.domain.Reservation;
import edu.miu.appointmentsystem.domain.User;
import edu.miu.appointmentsystem.domain.enums.AppointmentStatus;
import edu.miu.appointmentsystem.domain.enums.ReservationStatus;
import edu.miu.appointmentsystem.repository.AppointmentRepository;
import edu.miu.appointmentsystem.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
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
    public Reservation createReservation(Appointment appointment, User user) {
        Reservation reservation = new Reservation(appointment, user);
        if (user.isAdmin() || user.isClient()) {
            if (appointment.getStatus() == AppointmentStatus.OPENED) {
                appointment.addReservation(reservation);
                return reservationRepository.save(reservation);
            }
        }
        return null;
    }

    @Override
    public boolean approveReservation(Reservation reservation, User user) {
        if (user.isAdmin() || user.isProvider()) {
            if (reservation.getAppointment().getStatus() == AppointmentStatus.OPENED) {
                reservation.setStatus(ReservationStatus.APPROVED);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cancelReservation(Reservation reservation, User user) {
        if (user.isAdmin() || user.isProvider()) {
            if (reservation.getAppointment().getStatus() == AppointmentStatus.OPENED) {
                reservation.setStatus(ReservationStatus.CANCELED);
                return true;
            }
        }
        return false;
    }


}
