package edu.miu.appointmentsystem.controller;


import edu.miu.appointmentsystem.domain.Reservation;
import edu.miu.appointmentsystem.domain.User;
import edu.miu.appointmentsystem.dto.UserDTO;

import java.util.Collection;
import java.util.List;

public class Users {

    private Collection<UserDTO> users;

    public Users(Collection<UserDTO> users) {
        this.users = users;
    }
    public Collection<UserDTO> getUsers() {
        return users;
    }
    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
