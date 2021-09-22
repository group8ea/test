package edu.miu.appointmentsystem.service;


import edu.miu.appointmentsystem.domain.Appointment;
import edu.miu.appointmentsystem.domain.Reservation;
import edu.miu.appointmentsystem.domain.User;
import edu.miu.appointmentsystem.domain.enums.AppointmentStatus;
import edu.miu.appointmentsystem.repository.AppointmentRepository;
import edu.miu.appointmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Integer appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment, User user) {
        if (user.isAdmin() || user.isProvider()) {
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    @Override
    public boolean deleteAppointment(Integer id, User user) {
        if (user.isAdmin()) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Appointment> getAllAppointmentByUserId(Integer id) {
        return userRepository.findById(id).get().getAppointments();
    }

    @Override
    public boolean cancelAppointment(Appointment appointment, User user) {
        if (user.isAdmin() || user.isProvider()) {
            if (appointment.getReservations().size() == 0) {
                appointment.setStatus(AppointmentStatus.CANCELED);
                appointmentRepository.save(appointment);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean closeAppointment(Appointment appointment, User user) {
        if (user.isAdmin() || user.isProvider()) {
            appointment.setStatus(AppointmentStatus.CLOSED);
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }


}
