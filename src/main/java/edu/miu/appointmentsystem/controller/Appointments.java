package edu.miu.appointmentsystem.controller;

import edu.miu.appointmentsystem.domain.Appointment;

import java.util.Collection;
import java.util.List;

public class Appointments {
    private Collection<Appointment> appointments;

    public Appointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }
    public Collection<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}
