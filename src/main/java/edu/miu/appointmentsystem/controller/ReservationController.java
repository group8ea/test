package edu.miu.appointmentsystem.controller;


import edu.miu.appointmentsystem.domain.Appointment;
import edu.miu.appointmentsystem.domain.Reservation;
import edu.miu.appointmentsystem.domain.User;
import edu.miu.appointmentsystem.exception.CustomErrorType;
import edu.miu.appointmentsystem.service.AppointmentServiceImpl;
import edu.miu.appointmentsystem.service.ReservationServiceImpl;
import edu.miu.appointmentsystem.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationServiceImpl reservationServiceImpl;
    @Autowired
    private AppointmentServiceImpl appointmentService;
    @Autowired
    private UserServiceImpl userServiceImpl ;

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationServiceImpl.getReservations();
        return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Integer reservationId) {
        Reservation reservation = reservationServiceImpl.getReservationById(reservationId);
        if (reservation == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Reservation with reference = "
                    + reservationId.toString() + " is not available"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @PostMapping("/reservations")
    public ResponseEntity<?> createReservation(@PathVariable Integer appointmentId){
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Appointment with reference = "
                    + appointmentId.toString() + " is not available"), HttpStatus.NOT_FOUND);
        }
        Reservation reservation = reservationServiceImpl.addReservation(appointment);
        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<?> updateReservation(@RequestBody Reservation reservation) {
        reservationServiceImpl.updateReservation(reservation);
        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Integer reservationId) {
        Reservation reservation = reservationServiceImpl.getReservationById(reservationId);
        if (reservation == null) {
            CustomErrorType customErrorType = new CustomErrorType("Reservation with reference= " + reservationId.toString() + " is not available");
            return new ResponseEntity<CustomErrorType>(customErrorType,HttpStatus.NOT_FOUND);
        }
        reservationServiceImpl.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/reservations/{id}/approve")
    public ResponseEntity<?> approveReservation(@RequestBody Reservation reservation) {
        boolean approved = reservationServiceImpl.approveReservation(reservation);
        if(approved){
            return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
        }
        return new ResponseEntity<Reservation>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/reservations/{id}/cancel")
    public ResponseEntity<?> cancelReservation(@RequestBody Reservation reservation) {
        boolean approved = reservationServiceImpl.cancelReservation(reservation);
        if(approved){
            return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
        }
        return new ResponseEntity<Reservation>(HttpStatus.BAD_REQUEST);
    }







}
