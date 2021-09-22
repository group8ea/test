package edu.miu.appointmentsystem.controller;


import edu.miu.appointmentsystem.domain.Reservation;
import java.util.Collection;
import java.util.List;

public class Reservations {

    private Collection<Reservation> reservations;

    public Reservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }
    public Collection<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
