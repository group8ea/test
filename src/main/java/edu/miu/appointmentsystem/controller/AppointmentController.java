package edu.miu.appointmentsystem.controller;

import edu.miu.appointmentsystem.domain.*;
import edu.miu.appointmentsystem.dto.UserAdapter;
import edu.miu.appointmentsystem.dto.UserDTO;
import edu.miu.appointmentsystem.exception.CustomErrorType;
import edu.miu.appointmentsystem.service.AppointmentServiceImpl;
import edu.miu.appointmentsystem.service.CategoryServiceImpl;
import edu.miu.appointmentsystem.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    @Autowired
    private CategoryServiceImpl categoryService ;

    @GetMapping("/appointments")
    public ResponseEntity<Appointments> getAllAppointments() {
        Appointments appointments = new Appointments(appointmentServiceImpl.getAppointments());
        return new ResponseEntity<Appointments>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Integer appointmentId) {
        Appointment appointment = appointmentServiceImpl.getAppointmentById(appointmentId);
        if (appointment == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Appointment with reference = "
                    + appointmentId.toString() + " is not available"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
    }

    @PostMapping("/appointments/{categoryId}")
    public ResponseEntity<?> createAppointment(@PathVariable Integer categoryId, @RequestBody Appointment appointment){
        appointmentServiceImpl.addAppointment(appointment, categoryId);
        return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
    }

    @PutMapping("/appointments/{appointmentId}")
    public ResponseEntity<?> updateAppointment(@PathVariable Integer appointmentId, @RequestBody Appointment appointment) {
        Appointment updated = appointmentServiceImpl.getAppointmentById(appointmentId);
        appointmentServiceImpl.updateAppointment(updated);
        return new ResponseEntity<Appointment>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer appointmentId) {
        Appointment appointment = appointmentServiceImpl.getAppointmentById(appointmentId);
        if (appointment == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Appointment with reference = "
                    + appointmentId.toString() + " is not available"), HttpStatus.NOT_FOUND);
        }
        appointmentServiceImpl.deleteAppointment(appointmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/appointments/{appointmentId}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Integer appointmentId){
        Appointment appointment = appointmentServiceImpl.getAppointmentById(appointmentId);
        if (appointment == null) {
            CustomErrorType customErrorType = new CustomErrorType("Appointment with reference= " + appointmentId.toString() + " is not available");
            return new ResponseEntity<CustomErrorType>(customErrorType,HttpStatus.NOT_FOUND);
        }
        boolean canceled = appointmentServiceImpl.cancelAppointment(appointment);
        if (!canceled) {
            CustomErrorType customErrorType = new CustomErrorType("Appointment with reference= " + appointmentId.toString()
                    + " contains already some reservations");
            return new ResponseEntity<CustomErrorType>(customErrorType,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
    }

    @PutMapping("/appointments/{appointmentId}/close")
    public ResponseEntity<?> closeAppointment(@PathVariable Integer appointmentId){
        Appointment appointment = appointmentServiceImpl.getAppointmentById(appointmentId);
        if (appointment == null) {
            CustomErrorType customErrorType = new CustomErrorType("Appointment with reference= " + appointmentId.toString() + " is not available");
            return new ResponseEntity<CustomErrorType>(customErrorType,HttpStatus.NOT_FOUND);
        }
        appointmentServiceImpl.closeAppointment(appointment);
        return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
    }
}
