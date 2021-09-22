package edu.miu.appointmentsystem.controller;


import edu.miu.appointmentsystem.domain.Reservation;
import edu.miu.appointmentsystem.domain.User;

import java.util.Collection;
import java.util.List;

public class Users {

    private Collection<User> users;

    public Users(Collection<User> users) {
        this.users = users;
    }
    public Collection<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
