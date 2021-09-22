package edu.miu.appointmentsystem.controller;

import edu.miu.appointmentsystem.domain.*;
import edu.miu.appointmentsystem.exception.CustomErrorType;
import edu.miu.appointmentsystem.service.AppointmentServiceImpl;
import edu.miu.appointmentsystem.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl ;

    @GetMapping
    public ResponseEntity<Appointments> getAllAppointments() {
        Appointments appointments = new Appointments(appointmentServiceImpl.getAppointments());
        return new ResponseEntity<Appointments>(appointments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Integer id) {
        Appointment appointment = appointmentServiceImpl.getAppointmentById(id);
        if (appointment == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Appointment with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(Authentication authentication, @RequestBody Appointment appointment){
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        User user = userServiceImpl.findByUsername(authentication.getName());
        Appointment created = appointmentServiceImpl.saveAppointment(appointment, user);
        if(created != null){
            return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(Authentication authentication, @PathVariable Integer id, @RequestBody Appointment appointment) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        User user = userServiceImpl.findByUsername(authentication.getName());
        Appointment updated = appointmentServiceImpl.saveAppointment(appointment, user);
        if(updated != null){
            return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(Authentication authentication,@PathVariable Integer id) {
        Appointment appointment = appointmentServiceImpl.getAppointmentById(id);
        if (appointment == null) {
            CustomErrorType customErrorType = new CustomErrorType("Appointment with reference= " + id.toString() + " is not available");
            return new ResponseEntity<CustomErrorType>(customErrorType,HttpStatus.NOT_FOUND);
        }
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        User user = userServiceImpl.findByUsername(authentication.getName());
        boolean deleted = appointmentServiceImpl.deleteAppointment(id, user);
        if(deleted){
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(Authentication authentication, @PathVariable Integer id){
        Appointment appointment = appointmentServiceImpl.getAppointmentById(id);
        if (appointment == null) {
            CustomErrorType customErrorType = new CustomErrorType("Appointment with reference= " + id.toString() + " is not available");
            return new ResponseEntity<CustomErrorType>(customErrorType,HttpStatus.NOT_FOUND);
        }
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        User user = userServiceImpl.findByUsername(authentication.getName());
        boolean canceled = appointmentServiceImpl.cancelAppointment(appointment, user);
        if (canceled) {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<Appointment>(appointment, status);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<?> closeAppointment(Authentication authentication, @PathVariable Integer id){
        Appointment appointment = appointmentServiceImpl.getAppointmentById(id);
        if (appointment == null) {
            CustomErrorType customErrorType = new CustomErrorType("Appointment with reference= " + id.toString() + " is not available");
            return new ResponseEntity<CustomErrorType>(customErrorType,HttpStatus.NOT_FOUND);
        }
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        User user = userServiceImpl.findByUsername(authentication.getName());
        boolean canceled = appointmentServiceImpl.closeAppointment(appointment, user);
        if (canceled) {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<Appointment>(appointment, status);
    }
}
