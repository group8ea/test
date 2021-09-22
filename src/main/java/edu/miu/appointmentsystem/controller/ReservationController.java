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
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationServiceImpl reservationServiceImpl;
    @Autowired
    private AppointmentServiceImpl appointmentService;
    @Autowired
    private UserServiceImpl userServiceImpl ;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationServiceImpl.getReservations();
        return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Integer reservationId) {
        Reservation reservation = reservationServiceImpl.getReservationById(reservationId);
        if (reservation == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Reservation with reference = "
                    + reservationId.toString() + " is not available"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(Authentication authentication, @PathVariable Integer appointmentId){
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Appointment with reference = "
                    + appointmentId.toString() + " is not available"), HttpStatus.NOT_FOUND);
        }
        User user = userServiceImpl.findByUsername(authentication.getName());
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        Reservation reservation = reservationServiceImpl.createReservation(appointment, user);
        if (reservation != null) {
            return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
        }
        return new ResponseEntity<Reservation>(status);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveReservation(Authentication authentication, @RequestBody Reservation reservation) {
        User user = userServiceImpl.findByUsername(authentication.getName());
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        boolean approved = reservationServiceImpl.approveReservation(reservation, user);
        if(approved){
            return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
        }
        return new ResponseEntity<Reservation>(status);
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelReservation(Authentication authentication, @RequestBody Reservation reservation) {
        User user = userServiceImpl.findByUsername(authentication.getName());
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        boolean approved = reservationServiceImpl.cancelReservation(reservation, user);
        if(approved){
            return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
        }
        return new ResponseEntity<Reservation>(status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@RequestBody Reservation reservation) {
        reservationServiceImpl.updateReservation(reservation);
        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Integer reservationId) {
        Reservation reservation = reservationServiceImpl.getReservationById(reservationId);
        if (reservation == null) {
            CustomErrorType customErrorType = new CustomErrorType("Reservation with reference= " + reservationId.toString() + " is not available");
            return new ResponseEntity<CustomErrorType>(customErrorType,HttpStatus.NOT_FOUND);
        }
        reservationServiceImpl.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
